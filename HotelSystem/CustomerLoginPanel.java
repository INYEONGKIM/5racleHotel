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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class CustomerLoginPanel extends JPanel {
	
	private JTextField idFld;
	private JPasswordField pwdFld;
	private static String userID = "";
	
	public Connection con;
	
	public CustomerLoginPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
		JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel loginLbl = new JLabel("Customer Login", JLabel.CENTER);
    	loginLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	loginLbl.setForeground(new Color(21, 78, 149));
    	p1.add(loginLbl, BorderLayout.CENTER);
    	
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
    	centerPanel.setBounds(260, 80, 420, 210);
    	p2.add(centerPanel);
    	
    	JLabel idLbl = new JLabel("ID:");
    	idLbl.setBounds(70, 65, 120, 20);
		centerPanel.add(idLbl);
		
		JLabel pwdLbl = new JLabel("Password:");
		pwdLbl.setBounds(70, 115, 120, 20);
		centerPanel.add(pwdLbl);
		
		idFld = new JTextField();
		idFld.setBounds(210, 60, 150, 30);
		centerPanel.add(idFld);
		
		pwdFld = new JPasswordField();
		pwdFld.setBounds(210, 110, 150, 30);
		centerPanel.add(pwdFld);
		
		JButton forgetBtn = new JButton("Forgot ID/Password?");
    	forgetBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			JOptionPane.showMessageDialog(null, "찾기 개발중");
    		}
    	});
    	forgetBtn.setBorderPainted(false);
    	forgetBtn.setContentAreaFilled(false);
    	forgetBtn.setBounds(225, 160, 152, 23);
    	forgetBtn.setForeground(new Color(220, 20, 60));
    	centerPanel.add(forgetBtn);
    	
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setPreferredSize(new Dimension(0, 65));
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	
    	JButton okBtn = new JButton("Sign In");
    	okBtn.addActionListener(new ActionListener() {
    		@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
    			
                String id = idFld.getText();
                String pwd = pwdFld.getText();
                
                if(id.equals("") || pwd.equals("")) {
    				JOptionPane.showMessageDialog(p2, "Please enter ID and password.", "Sign In", JOptionPane.ERROR_MESSAGE);
    				return;
                }

                try {
                	con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");

            		String sqlStr = "select CL_CUS_ID, CL_PWD from tb_customer";
            		PreparedStatement stmt = con.prepareStatement(sqlStr);
            		ResultSet rs = stmt.executeQuery();

            		for (; rs.next(); ) {
            			if (id.equals(rs.getString("CL_CUS_ID")) && pwd.equals(rs.getString("CL_PWD"))) {
            				userID = id;
            				JOptionPane.showMessageDialog(p2, "You are signed in as \'" + userID + "\'.", "Sign In", JOptionPane.INFORMATION_MESSAGE);

            				First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
            				First.contentsPanel.add("customerPanel", new CustomerPanel());
            				First.card.show(First.contentsPanel, "customerPanel");
            				return;
            			}
            		}
            		JOptionPane.showMessageDialog(p2, "Sign in failed.", "Sign In", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e2) {
                	e2.getMessage();
                }
    		}
    	});
    	okBtn.setBorderPainted(false);
    	okBtn.setContentAreaFilled(false);
    	okBtn.setForeground(new Color(21, 78, 149));
    	p3.add(okBtn);
    	
    	JButton cancelBtn = new JButton("Cancel");
    	cancelBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "mainPanel");
    		}
    	});
    	cancelBtn.setBorderPainted(false);
    	cancelBtn.setContentAreaFilled(false);
    	cancelBtn.setForeground(new Color(21, 78, 149));
    	p3.add(cancelBtn);
    	
    	add(p1, BorderLayout.NORTH);
    	add(p2, BorderLayout.CENTER);
    	add(p3, BorderLayout.SOUTH);
    }
	
	public static String getId() {
	      return userID;
	}

}
