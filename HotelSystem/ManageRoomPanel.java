package HotelSystem;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ManageRoomPanel extends JPanel {
	
	private Connection con;
	
	public ManageRoomPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel manageRoomLbl = new JLabel("Rooms Management", JLabel.CENTER);
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
		JTable roomTable = null;
	
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
			String sqlStr = "select count(*) from TB_ROOM";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int num = rs.getInt("count(*)"); //전체 사이즈를 가져옴
			
			String tableHeader[] = {"Room No.", "Room Type", "Status"};
			String tableObject[][] = new String[num][3];
			
			String sqlStr1 = "select * from TB_ROOM order by CL_ROOM_NUM";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();
			
			for(int i=0; rs1.next(); i++) {
				tableObject[i][0] = rs1.getString("CL_ROOM_NUM");
				tableObject[i][1] = rs1.getString("CL_ROOM_TYPE");
				tableObject[i][2] = rs1.getString("CL_ABLE");
	         }
			
			roomTable = new JTable();
			roomTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
	    		public boolean isCellEditable(int row, int column) {
	    			return false;
	    		}
	    	});
			roomTable.setFillsViewportHeight(true);
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        for(int i = 0; i < 3; i++) {
	        	roomTable.getColumnModel().getColumn(i).setResizable(false);
	        	roomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomTable;
	}

}