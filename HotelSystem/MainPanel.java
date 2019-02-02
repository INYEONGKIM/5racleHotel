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

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	public MainPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setLayout(null);
    	p1.setPreferredSize(new Dimension(0, 65));
    	
    	JButton joinBtn = new JButton("Sign Up");
    	joinBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("signupPanel", new SignupPanel());
    			First.card.show(First.contentsPanel, "signupPanel");
    		}
    	});
    	joinBtn.setBorderPainted(false);
    	joinBtn.setContentAreaFilled(false);
    	joinBtn.setBounds(708, 21, 77, 23);
    	joinBtn.setForeground(new Color(21, 78, 149));
    	p1.add(joinBtn);
    	
    	JButton oracleBtn = new JButton("About 5racle");
    	oracleBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("introductionPanel", new IntroductionPanel());
    			First.card.show(First.contentsPanel, "introductionPanel");
    		}
    	});
    	oracleBtn.setBorderPainted(false);
    	oracleBtn.setContentAreaFilled(false);
    	oracleBtn.setBounds(808, 21, 106, 23);
    	oracleBtn.setForeground(new Color(21, 78, 149));
    	p1.add(oracleBtn);
    	
    	JPanel p2 = new JPanel() {
    		public void paintComponent(Graphics g) {
    			URL imageURL = getClass().getResource("images/background.jpg");
    			Image img = new ImageIcon(imageURL).getImage();
    			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    		}
    	};
    	p2.setLayout(null);
    	
    	JLabel welcomeLbl = new JLabel("Welcome!!!", JLabel.CENTER);
    	welcomeLbl.setFont(new Font("Dialog", Font.PLAIN, 94));
    	welcomeLbl.setBounds(172, 35, 600, 300);
    	welcomeLbl.setForeground(Color.WHITE);
    	p2.add(welcomeLbl);
    	
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton adminBtn = new JButton("Admin Login");
    	adminBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("adminLoginPanel", new AdminLoginPanel());
    			First.card.show(First.contentsPanel, "adminLoginPanel");
    		}
    	});
    	adminBtn.setBorderPainted(false);
    	adminBtn.setContentAreaFilled(false);
    	adminBtn.setForeground(new Color(21, 78, 149));
    	p3.add(adminBtn);
    	
    	JButton customerBtn = new JButton("Customer Login");
    	customerBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.add("customerLoginPanel", new CustomerLoginPanel());
    			First.card.show(First.contentsPanel, "customerLoginPanel");
    		}
    	});
    	customerBtn.setBorderPainted(false);
    	customerBtn.setContentAreaFilled(false);
    	customerBtn.setForeground(new Color(21, 78, 149));
    	p3.add(customerBtn);
    	
    	add(p1, BorderLayout.NORTH);
    	add(p2, BorderLayout.CENTER);
    	add(p3, BorderLayout.SOUTH);
    }

}
