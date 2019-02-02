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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AdminPanel extends JPanel {
	
	public AdminPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel adminLbl = new JLabel("Admin Menu", JLabel.CENTER);
    	adminLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	adminLbl.setForeground(new Color(21, 78, 149));
    	p1.add(adminLbl, BorderLayout.CENTER);
    	
    	JPanel p2 = new JPanel() {
    		public void paintComponent(Graphics g) {
    			URL imageURL = getClass().getResource("images/background.jpg");
    			Image img = new ImageIcon(imageURL).getImage();
    			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    		}
    	};
    	p2.setLayout(null);
    	
    	JPanel centerPanel = new JPanel(); centerPanel.setBackground(Color.WHITE);
    	centerPanel.setLayout(null);
    	centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
    	centerPanel.setBounds(295, 10, 350, 350);
    	p2.add(centerPanel);
    	
    	JButton reservationBtn = new JButton("Reservations");
    	reservationBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("manageReservationPanel", new ManageReservationPanel());
    			First.card.show(First.contentsPanel, "manageReservationPanel");
    		}
    	});
    	URL imageURL = getClass().getResource("images/reservation.png");
    	ImageIcon img = new ImageIcon(imageURL);
		reservationBtn.setIcon(img);
		reservationBtn.setFont(new Font("Dialog", Font.BOLD, 16));
		reservationBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		reservationBtn.setVerticalTextPosition(SwingConstants.TOP);
		reservationBtn.setContentAreaFilled(false);
    	reservationBtn.setForeground(new Color(21, 78, 149));
    	reservationBtn.setBounds(10, 10, 160, 160);
    	centerPanel.add(reservationBtn);
    	
    	JButton roomBtn = new JButton("Rooms");
    	roomBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("manageRoomPanel", new ManageRoomPanel());
    			First.card.show(First.contentsPanel, "manageRoomPanel");
    		}
    	});
    	imageURL = getClass().getResource("images/room.png");
    	img = new ImageIcon(imageURL);
		roomBtn.setIcon(img);
		roomBtn.setFont(new Font("Dialog", Font.BOLD, 16));
		roomBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		roomBtn.setVerticalTextPosition(SwingConstants.TOP);
    	roomBtn.setContentAreaFilled(false);
    	roomBtn.setForeground(new Color(21, 78, 149));
    	roomBtn.setBounds(180, 10, 160, 160);
    	centerPanel.add(roomBtn);
    	
    	JButton staffBtn = new JButton("Staffs");
    	staffBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("manageStaffPanel", new ManageStaffPanel());
    			First.card.show(First.contentsPanel, "manageStaffPanel");
    		}
    	});
    	imageURL = getClass().getResource("images/staff.png");
    	img = new ImageIcon(imageURL);
    	staffBtn.setIcon(img);
    	staffBtn.setFont(new Font("Dialog", Font.BOLD, 16));
		staffBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		staffBtn.setVerticalTextPosition(SwingConstants.TOP);
    	staffBtn.setContentAreaFilled(false);
    	staffBtn.setForeground(new Color(21, 78, 149));
    	staffBtn.setBounds(10, 180, 160, 160);
    	centerPanel.add(staffBtn);
    	
    	JButton complainBtn = new JButton("Requirement");
    	complainBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("manageRequirementPanel", new ManageRequirementPanel());
    			First.card.show(First.contentsPanel, "manageRequirementPanel");
    		}
    	});
    	imageURL = getClass().getResource("images/requirement.png");
    	img = new ImageIcon(imageURL);
    	complainBtn.setIcon(img);
    	complainBtn.setFont(new Font("Dialog", Font.BOLD, 16));
		complainBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		complainBtn.setVerticalTextPosition(SwingConstants.TOP);
    	complainBtn.setContentAreaFilled(false);
    	complainBtn.setForeground(new Color(21, 78, 149));
    	complainBtn.setBounds(180, 180, 160, 160);
    	centerPanel.add(complainBtn);
    	
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton signoutBtn = new JButton("Sign Out");
    	signoutBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "mainPanel");
    		}
    	});
    	signoutBtn.setBorderPainted(false);
    	signoutBtn.setContentAreaFilled(false);
    	signoutBtn.setForeground(new Color(21, 78, 149));
    	p3.add(signoutBtn);
    	
    	add(p1, BorderLayout.NORTH);
    	add(p2, BorderLayout.CENTER);
    	add(p3, BorderLayout.SOUTH);
	} 

}