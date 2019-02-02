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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class CustomerPanel extends JPanel {
	
	private JPanel menuPanel;

	public CustomerPanel() {
		menuPanel = new JPanel();
		
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				menuPanel.setVisible(false);
			}
		});
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(null);
    	
    	JLabel adminLbl = new JLabel("Customer Menu", JLabel.CENTER);
    	adminLbl.setBounds(359, 11, 226, 42);
    	adminLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	adminLbl.setForeground(new Color(21, 78, 149));
    	p1.add(adminLbl);
    	
    	JLabel idLbl1 = new JLabel("Signed in as ");
    	idLbl1.setBounds(750, 25, 75, 15);
    	idLbl1.setForeground(new Color(21, 78, 149));
    	p1.add(idLbl1);
    	
    	JLabel idLbl2 = new JLabel(CustomerLoginPanel.getId());
    	idLbl2.setBounds(825, 24, 100, 15);
    	idLbl2.setFont(new Font("Dialog", Font.BOLD, 15));
    	idLbl2.setForeground(new Color(220, 20, 60));
    	p1.add(idLbl2);
    	
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
    	centerPanel.setBounds(102, 30, 735, 255);
    	p2.add(centerPanel);
    	
    	menuPanel.setBackground(Color.WHITE);
    	menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 7));
    	menuPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
    	menuPanel.setBounds(102, 295, 735, 46);
    	menuPanel.setVisible(false);
    	p2.add(menuPanel);
    	
    	JButton makeRsvBtn = new JButton("New Reservation");
    	makeRsvBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("newReservationPanel", new NewReservationPanel());
				First.card.show(First.contentsPanel, "newReservationPanel");
    		}
    	});
    	makeRsvBtn.setForeground(new Color(21, 78, 149));
    	makeRsvBtn.setContentAreaFilled(false);
    	
    	JButton checkRsvBtn = new JButton("Check Reservation");
    	checkRsvBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("checkReservationPanel", new CheckReservationPanel());
				First.card.show(First.contentsPanel, "checkReservationPanel");
    		}
    	});
    	checkRsvBtn.setContentAreaFilled(false);
    	checkRsvBtn.setForeground(new Color(21, 78, 149));
    	
    	JButton modifyBtn = new JButton("Modify Profile");
    	modifyBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("modifyProfilePanel", new ModifyProfilePanel());
				First.card.show(First.contentsPanel, "modifyProfilePanel");
    		}
    	});
    	modifyBtn.setContentAreaFilled(false);
    	modifyBtn.setForeground(new Color(21, 78, 149));
    	
    	JButton historyBtn = new JButton("Service Usage History");
    	historyBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("serviceHistoryPanel", new ServiceHistoryPanel());
				First.card.show(First.contentsPanel, "serviceHistoryPanel");
    		}
    	});
    	historyBtn.setContentAreaFilled(false);
    	historyBtn.setForeground(new Color(21, 78, 149));
    	
    	JButton compBtn = new JButton("Add Complain");
    	compBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("newComplainPanel", new NewComplainPanel());
    			First.card.show(First.contentsPanel, "newComplainPanel");
    		}
    	});
    	compBtn.setContentAreaFilled(false);
    	compBtn.setForeground(new Color(21, 78, 149));
    	
    	JButton showCompBtn = new JButton("Show Complain");
    	showCompBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("showComplainPanel", new ShowComplainPanel());
    			First.card.show(First.contentsPanel, "showComplainPanel");
    		}
    	});
    	showCompBtn.setContentAreaFilled(false);
    	showCompBtn.setForeground(new Color(21, 78, 149));
    	
    	JButton reservationBtn = new JButton("Reservations");
    	reservationBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menuPanel.setVisible(false);
    			menuPanel.removeAll();
    			menuPanel.add(makeRsvBtn);
    			menuPanel.add(checkRsvBtn);
    			menuPanel.setVisible(true);
    		}
    	});
    	URL imageURL = getClass().getResource("images/reservation2.png");
    	ImageIcon img = new ImageIcon(imageURL);
		reservationBtn.setIcon(img);
		reservationBtn.setFont(new Font("Dialog", Font.BOLD, 20));
		reservationBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		reservationBtn.setVerticalTextPosition(SwingConstants.TOP);
		reservationBtn.setContentAreaFilled(false);
    	reservationBtn.setForeground(new Color(21, 78, 149));
    	reservationBtn.setBounds(15, 15, 225, 225);
    	centerPanel.add(reservationBtn);
    	
    	JButton profileBtn = new JButton("Profile");
    	profileBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menuPanel.setVisible(false);
    			menuPanel.removeAll();
    			menuPanel.add(modifyBtn);
    			menuPanel.setVisible(true);
    		}
    	});
    	imageURL = getClass().getResource("images/profile.png");
    	img = new ImageIcon(imageURL);
		profileBtn.setIcon(img);
		profileBtn.setFont(new Font("Dialog", Font.BOLD, 20));
		profileBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		profileBtn.setVerticalTextPosition(SwingConstants.TOP);
    	profileBtn.setContentAreaFilled(false);
    	profileBtn.setForeground(new Color(21, 78, 149));
    	profileBtn.setBounds(255, 15, 225, 225);
    	centerPanel.add(profileBtn);
    	
    	JButton serviceBtn = new JButton("Services");
    	serviceBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menuPanel.setVisible(false);
    			menuPanel.removeAll();
    			menuPanel.add(historyBtn);
    			menuPanel.add(compBtn);
    			menuPanel.add(showCompBtn);
    			menuPanel.setVisible(true);
    		}
    	});
    	imageURL = getClass().getResource("images/service.png");
    	img = new ImageIcon(imageURL);
    	serviceBtn.setIcon(img);
    	serviceBtn.setFont(new Font("Dialog", Font.BOLD, 20));
		serviceBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		serviceBtn.setVerticalTextPosition(SwingConstants.TOP);
    	serviceBtn.setContentAreaFilled(false);
    	serviceBtn.setForeground(new Color(21, 78, 149));
    	serviceBtn.setBounds(495, 15, 225, 225);
    	centerPanel.add(serviceBtn);
    	
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