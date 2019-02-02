package HotelSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ManageReservationPanel extends JPanel {

	private Connection con;

	private JScrollPane centerPanel;
	
	private static String select_rsv;
	private static String select_type;
	
	public ManageReservationPanel() {
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				centerPanel.setViewportView(make_table());
			}
		});
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel manageRsvLbl = new JLabel("Reservations Management", JLabel.CENTER);
    	manageRsvLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	manageRsvLbl.setForeground(new Color(21, 78, 149));
    	p1.add(manageRsvLbl, BorderLayout.CENTER);
    	
    	JPanel p2 = new JPanel() {
    		public void paintComponent(Graphics g) {
    			URL imageURL = getClass().getResource("images/background.jpg");
    			Image img = new ImageIcon(imageURL).getImage();
    			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    		}
    	};
    	p2.setLayout(null);
    	
    	centerPanel = new JScrollPane(); centerPanel.setBackground(Color.WHITE);
    	centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
    	centerPanel.setBounds(100, 10, 740, 350);
    	p2.add(centerPanel);
    	
    	centerPanel.setViewportView(make_table());
        
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton backBtn = new JButton("Back");
    	backBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.card.show(First.contentsPanel, "adminPanel");
    		}
    	});
    	backBtn.setBorderPainted(false);
    	backBtn.setContentAreaFilled(false);
    	backBtn.setForeground(new Color(21, 78, 149));
    	p3.add(backBtn);
    	
    	add(p1, BorderLayout.NORTH);
    	add(p2, BorderLayout.CENTER);
    	add(p3, BorderLayout.SOUTH);
	}
	
	JTable make_table() {
		JTable rsvTable = null;
	
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
			String sqlStr = "select count(*) from tb_reservation where CL_SIGN != 'O'";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int num = rs.getInt("count(*)"); //전체 사이즈를 가져옴
			
			String tableHeader[] = {"Reservation No.", "Customer ID", "Check-In Date", "Check-Out Date", "Headcount", "Room Type", "Room No.", "Check-In Status"};
			String tableObject[][] = new String[num][8];
			
			String sqlStr1 = "select * from tb_reservation where CL_SIGN != 'O' order by CL_RS_NUM";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();
			
			for(int i=0; rs1.next(); i++) {
				tableObject[i][0] = rs1.getString("CL_RS_NUM");
				tableObject[i][1] = rs1.getString("CL_CUS_ID");
				tableObject[i][2] = rs1.getDate("CL_CHECK_IN").toString();
				tableObject[i][3] = rs1.getDate("CL_CHECK_OUT").toString();
				tableObject[i][4] = rs1.getString("CL_CO_NUM");
				tableObject[i][5] = rs1.getString("CL_ROOM_TYPE");
				tableObject[i][6] = rs1.getString("CL_ROOM_NUM");
				tableObject[i][7] = rs1.getString("CL_SIGN");
	         }
			
			rsvTable = new JTable();
	    	rsvTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
	    		public boolean isCellEditable(int row, int column) {
	    			if (column == 7) return true;
	    			return false;
	    		}
	    	});
	    	rsvTable.setFillsViewportHeight(true);
	    	
	    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        for(int i = 0; i < 7; i++) {
	        	rsvTable.getColumnModel().getColumn(i).setResizable(false);
	        	rsvTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
	        rsvTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
	        rsvTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JTextField()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsvTable;
	}
	
	public static void set_selected_rsv(String rsv_num) {
		select_rsv = rsv_num;
	}
	public static String get_selected_rsv() {
		return select_rsv;
	}
	public static void set_selected_type(String type) {
		select_type = type;
	}
	public static String get_selected_type() {
		return select_type;
	}

}

@SuppressWarnings("serial")
class ButtonRenderer extends JButton implements TableCellRenderer {
	public ButtonRenderer() {
	    setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {
		if(obj.equals("N")) {
			setText("Chk-In");
			return this;
		}
		else if(obj.equals("I")) {
			setText("Chk-Out");
			return this;
		}
		else {
			setText("OK");
			return this;
		}
	}
}

@SuppressWarnings("serial")
class ButtonEditor extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private Boolean clicked;
	private JTable rsvTable;
	private int target_row;
	
	private Connection con;
	
	public ButtonEditor(JTextField txt) {
		super(txt);
		
		btn = new JButton();
		btn.setOpaque(true);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {
		lbl = (obj == null) ? "" : obj.toString();
		btn.setText(lbl);
		clicked = true;
		rsvTable = table;new String("Check-In");
		target_row = row;
		return btn;
	}

	public Object getCellEditorValue() {
		if(clicked && rsvTable.getModel().getValueAt(target_row, 7).equals("N")) {
			// 체크인 처리
			String buttons[] = {"Ok", "Cancel"};
			int sel = JOptionPane.showOptionDialog(First.contentsPanel, "체크인 하시겠습니까?", "Check-In", 0, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if(sel == 0) {
				ManageReservationPanel.set_selected_rsv(rsvTable.getModel().getValueAt(target_row, 0).toString());
				ManageReservationPanel.set_selected_type(rsvTable.getModel().getValueAt(target_row, 5).toString());

				First.contentsPanel.add("selectRoomPanel", new SelectRoomPanel());
    			First.card.show(First.contentsPanel, "selectRoomPanel");
			}
		}
		else if(clicked && rsvTable.getModel().getValueAt(target_row, 7).equals("I")) {
			// 체크아웃 처리
			String buttons[] = {"Ok", "Cancel"};
			int sel = JOptionPane.showOptionDialog(First.contentsPanel, "체크아웃 하시겠습니까?", "Check-Out", 0, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if(sel == 0) {
				String room_num = rsvTable.getModel().getValueAt(target_row, 6).toString();
				
				try {
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
					String sqlStr = "update tb_reservation set CL_SIGN = 'O' where CL_RS_NUM = '" + rsvTable.getModel().getValueAt(target_row, 0).toString() + "'";
					String sqlStr2 = "update tb_room set CL_ABLE = 'T' where CL_ROOM_NUM = " + room_num;
					
					System.out.println(sqlStr);
					System.out.println(sqlStr2);
					
					PreparedStatement stmt = con.prepareStatement(sqlStr);
					PreparedStatement stmt2 = con.prepareStatement(sqlStr2);
					
					stmt.executeUpdate();
					stmt2.executeUpdate();
					
					stmt.close();
					stmt2.close();
					
					First.card.show(First.contentsPanel, "adminPanel");
					First.card.show(First.contentsPanel, "manageReservationPanel");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		clicked=false;
		return new String(lbl);
	}
	
	public boolean stopCellEditing() {
		clicked = false;
		return super.stopCellEditing();
	}
	
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}