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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class SelectRoomPanel extends JPanel {
	
	private Connection con;
	
	public SelectRoomPanel() {		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel manageRoomLbl = new JLabel("Select Room", JLabel.CENTER);
    	manageRoomLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	manageRoomLbl.setForeground(new Color(21, 78, 149));
    	p1.add(manageRoomLbl, BorderLayout.CENTER);
    	
    	JPanel p2 = new JPanel() {
    		public void paintComponent(Graphics g) {
    			URL imageURL = getClass().getResource("images/background.jpg");
    			Image img = new ImageIcon(imageURL).getImage();
    			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    		}
    	};
    	p2.setLayout(null);
    	
    	JScrollPane centerPanel = new JScrollPane(); centerPanel.setBackground(Color.WHITE);
    	centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
    	centerPanel.setBounds(320, 10, 300, 350);
    	p2.add(centerPanel);
    	
    	centerPanel.setViewportView(make_table());
        
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton backBtn = new JButton("Back");
    	backBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "manageReservationPanel");
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
		JTable roomTable = null;
	
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
			String sqlStr = "select count(*) from TB_ROOM where cl_able = 'T' and cl_room_type = '" + ManageReservationPanel.get_selected_type() + "'";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int num = rs.getInt("count(*)"); //전체 사이즈를 가져옴
			
			System.out.println(sqlStr);
			
			String tableHeader[] = {"Room No.", "Room Type"};
			String tableObject[][] = new String[num][2];
			
			String sqlStr1 = "select cl_room_num, cl_room_type from TB_ROOM where cl_able = 'T' and cl_room_type = '" + ManageReservationPanel.get_selected_type() + "' order by CL_ROOM_NUM";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();
			
			System.out.println(sqlStr1);
			
			for(int i=0; rs1.next(); i++) {
				tableObject[i][0] = rs1.getString("CL_ROOM_NUM");
				tableObject[i][1] = rs1.getString("CL_ROOM_TYPE");
	         }
			
			roomTable = new JTable();
			roomTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
	    		public boolean isCellEditable(int row, int column) {
	    			return true;
	    		}
	    	});
			roomTable.setFillsViewportHeight(true);
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        for(int i = 0; i < 2; i++) {
	        	roomTable.getColumnModel().getColumn(i).setResizable(false);
	        	roomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        	roomTable.getColumnModel().getColumn(i).setCellEditor(new ButtonEditor4(new JTextField()));
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomTable;
	}

}

@SuppressWarnings("serial")
class ButtonEditor4 extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private Boolean clicked;
	private JTable roomTable;
	private int target_row;
	
	private Connection con;
	
	public ButtonEditor4(JTextField txt) {
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
		roomTable = table;
		target_row = row;
		return btn;
	}

	public Object getCellEditorValue() {
		if(clicked) {
			String room_num = roomTable.getModel().getValueAt(target_row, 0).toString();
			
			System.out.println(room_num);
			
			try {
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
				String sqlStr = "update tb_reservation set CL_ROOM_NUM = '" + room_num + "', CL_SIGN = 'I' where CL_RS_NUM = '" + ManageReservationPanel.get_selected_rsv() + "'";
				String sqlStr2 = "update tb_room set CL_ABLE = 'F' where CL_ROOM_NUM = " + room_num;
				
				System.out.println(sqlStr);
				System.out.println(sqlStr2);
				
				PreparedStatement stmt = con.prepareStatement(sqlStr);
				PreparedStatement stmt2 = con.prepareStatement(sqlStr2);
				
				stmt.executeUpdate();
				stmt2.executeUpdate();
				
				stmt.close();
				stmt2.close();
				
				First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "manageReservationPanel");
				
			} catch (SQLException e) {
				e.printStackTrace();
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