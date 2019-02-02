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
public class SelectStaffPanel extends JPanel {
	
	private Connection con;
	
	public SelectStaffPanel() {		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel staffLbl = new JLabel("Select Staff", JLabel.CENTER);
    	staffLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	staffLbl.setForeground(new Color(21, 78, 149));
    	p1.add(staffLbl, BorderLayout.CENTER);
    	
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
    	centerPanel.setBounds(222, 10, 500, 350);
    	p2.add(centerPanel);
    	
    	centerPanel.setViewportView(make_table());
        
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton backBtn = new JButton("Back");
    	backBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "manageRequirementPanel");
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
		JTable staffTable = null;
	
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
			String sqlStr = "select count(*) from tb_staff where cl_workable = 'Y'";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int num = rs.getInt("count(*)"); //전체 사이즈를 가져옴
			
			String tableHeader[] = {"Staff ID", "Staff Name", "Department", "Floor"};
			String tableObject[][] = new String[num][4];
			
			String sqlStr1 = "select cl_staff_id, cl_staff_nm, cl_dep, cl_now_floor, cl_workable from TB_staff where cl_workable = 'Y' order by cl_now_floor";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();
			
			for(int i=0; rs1.next(); i++) {
				tableObject[i][0] = rs1.getString("CL_STAFF_ID");
				tableObject[i][1] = rs1.getString("CL_STAFF_NM");
				tableObject[i][2] = rs1.getString("CL_DEP");
				tableObject[i][3] = rs1.getString("CL_NOW_FLOOR");
	         }
			
			staffTable = new JTable();
			staffTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
	    		public boolean isCellEditable(int row, int column) {
	    			return true;
	    		}
	    	});
			staffTable.setFillsViewportHeight(true);
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        for(int i = 0; i < 4; i++) {
	        	staffTable.getColumnModel().getColumn(i).setResizable(false);
	        	staffTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        	staffTable.getColumnModel().getColumn(i).setCellEditor(new ButtonEditor5(new JTextField()));
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staffTable;
	}

}

@SuppressWarnings("serial")
class ButtonEditor5 extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private Boolean clicked;
	private JTable staffTable;
	private int target_row;
	
	private Connection con;
	
	public ButtonEditor5(JTextField txt) {
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
		staffTable = table;
		target_row = row;
		return btn;
	}

	public Object getCellEditorValue() {
		if(clicked) {
			String staff = staffTable.getModel().getValueAt(target_row, 0).toString();
			
			System.out.println(staff);
			
			try {
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
				String sqlStr = "update tb_complain set CL_STAFF = '" + staff + "', CL_STATE = 'I' where CL_CMPL_NUM = '" + ManageRequirementPanel.get_selected_req() + "'";
				String sqlStr2 = "update tb_staff set CL_WORKABLE = 'N' where CL_staff_id = " + staff;
				
				System.out.println(sqlStr);
				System.out.println(sqlStr2);
				
				PreparedStatement stmt = con.prepareStatement(sqlStr);
				PreparedStatement stmt2 = con.prepareStatement(sqlStr2);
				
				stmt.executeUpdate();
				stmt2.executeUpdate();
				
				stmt.close();
				stmt2.close();
				
				First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "manageRequirementPanel");
				
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