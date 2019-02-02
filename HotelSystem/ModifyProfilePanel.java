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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

@SuppressWarnings("serial")
public class ModifyProfilePanel extends JPanel {
   
	private JTextField idFld;
	private JPasswordField pwdFld;
	private JPasswordField confirmpwdFld;
	private JTextField nameFld;
	private JRadioButton maleBtn;
	private JRadioButton femaleBtn;
	private ButtonGroup radioBtn;
	private DatePicker birthDate;
	private JTextField phoneFld;
	private JTextField emailFld;
	
	private JPanel p2;
	private String old_pwd = null;
	
	public Connection con;
	
	public ModifyProfilePanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
       
		JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(0, 65));
		p1.setLayout(new BorderLayout(0, 0));
       
		JLabel modifyLbl = new JLabel("Modify Profile", JLabel.CENTER);
		modifyLbl.setFont(new Font("Dialog", Font.BOLD, 30));
		modifyLbl.setForeground(new Color(21, 78, 149));
		p1.add(modifyLbl, BorderLayout.CENTER);
       
		p2 = new JPanel() {
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
		centerPanel.setBounds(180, 10, 580, 350);
		p2.add(centerPanel);
       
		JLabel idLbl = new JLabel("ID :");
		idLbl.setBounds(120, 25, 120, 20);
		centerPanel.add(idLbl);
      
		JLabel pwdLbl = new JLabel("Create Password :");
		pwdLbl.setBounds(120, 65, 120, 20);
		centerPanel.add(pwdLbl);
      
		JLabel confirmpwdLbl = new JLabel("Confirm Password :");
		confirmpwdLbl.setBounds(120, 105, 120, 20);
		centerPanel.add(confirmpwdLbl);

		JLabel nameLbl = new JLabel("Name :");
		nameLbl.setBounds(120, 145, 120, 20);
		centerPanel.add(nameLbl);

		JLabel genderLbl = new JLabel("Gender :");
		genderLbl.setBounds(120, 185, 120, 20);
		centerPanel.add(genderLbl);

		JLabel birthLbl = new JLabel("Date Of Birth :");
		birthLbl.setBounds(120, 225, 120, 20);
		centerPanel.add(birthLbl);

		JLabel phoneLbl = new JLabel("Phone No. :");
		phoneLbl.setBounds(120, 265, 120, 20);
		centerPanel.add(phoneLbl);

		JLabel emailLbl = new JLabel("E-mail :");
		emailLbl.setBounds(120, 305, 120, 20);
		centerPanel.add(emailLbl);

		idFld = new JTextField();
		idFld.setBounds(320, 20, 150, 30);
		idFld.setEnabled(false);
		centerPanel.add(idFld);

		pwdFld = new JPasswordField();
		pwdFld.setBounds(320, 60, 150, 30);
		centerPanel.add(pwdFld);

		confirmpwdFld = new JPasswordField();
		confirmpwdFld.setBounds(320, 100, 150, 30);
		centerPanel.add(confirmpwdFld);

		nameFld = new JTextField();
		nameFld.setBounds(320, 140, 150, 30);
		centerPanel.add(nameFld);

		maleBtn = new JRadioButton("Male");
		maleBtn.setBorderPainted(false);
		maleBtn.setContentAreaFilled(false);
		maleBtn.setBounds(320, 180, 75, 30);
		centerPanel.add(maleBtn);
		femaleBtn = new JRadioButton("Female");
		femaleBtn.setBorderPainted(false);
		femaleBtn.setContentAreaFilled(false);
		femaleBtn.setBounds(395, 180, 90, 30);
		centerPanel.add(femaleBtn);
		radioBtn = new ButtonGroup();
		radioBtn.add(maleBtn);
		radioBtn.add(femaleBtn);
      
		DatePickerSettings settings = new DatePickerSettings();
		settings.setFormatForDatesCommonEra("uuuu-MM-dd");
		settings.setColor(DateArea.DatePickerTextInvalidDate, Color.BLACK);
		settings.setColor(DateArea.DatePickerTextValidDate, Color.BLACK);
		birthDate = new DatePicker(settings);
		birthDate.getComponentToggleCalendarButton().setText("¡å");
		birthDate.getComponentDateTextField().setEditable(false);
		birthDate.setBounds(320, 220, 150, 30);
		centerPanel.add(birthDate);

		phoneFld = new JTextField();
		phoneFld.setBounds(320, 260, 150, 30);
		centerPanel.add(phoneFld);

		emailFld = new JTextField();
		emailFld.setBounds(320, 300, 150, 30);
		centerPanel.add(emailFld);
		
		// µ¥ÀÌÅÍ ºÒ·¯¿À±â
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
            String sqlStr = "select * from tb_customer where cl_cus_id='"+CustomerLoginPanel.getId()+"'";   
            PreparedStatement stmt = con.prepareStatement(sqlStr);
            ResultSet rs1 = stmt.executeQuery();
            rs1.next();
            
            idFld.setText(rs1.getString("cl_cus_id"));
            old_pwd = rs1.getString("cl_pwd");
            pwdFld.setText(old_pwd);
            confirmpwdFld.setText(rs1.getString("cl_pwd"));
            nameFld.setText(rs1.getString("CL_CUM_NM"));
            if(rs1.getString("CL_GENDER").equals("m"))
            	maleBtn.setSelected(true);
            else
            	femaleBtn.setSelected(true);
            phoneFld.setText(rs1.getString("CL_PHONE"));
            emailFld.setText(rs1.getString("cl_email"));
            birthDate.setText(rs1.getString("CL_BIRTH"));
            
            stmt.close();
		}             
		catch(SQLException e2){
			e2.getMessage();
		}
       
		JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
		p3.setPreferredSize(new Dimension(0, 65));
       
		JButton okBtn = new JButton("Modify");
		okBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(check())
    				return;
				
				String id = idFld.getText();
				String name = nameFld.getText();
				String gender = "";
				if(maleBtn.isSelected()) gender = "m";
				else if(femaleBtn.isSelected()) gender = "f";
				String date = birthDate.getDate().toString();
				String phone = phoneFld.getText();
				String email = emailFld.getText();
				String new_pwd = pwdFld.getText();
				
				try {
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
					String sqlStr = "update tb_customer set  CL_CUM_NM ='"+name+"',CL_GENDER = '"+gender+"',CL_BIRTH = '"+date+"',CL_PHONE = '"+phone+"',"
									+ "CL_EMAIL = '"+email+"',CL_PWD = '"+new_pwd+"' where  CL_CUS_ID = '"+id+"'";
   					PreparedStatement stmt = con.prepareStatement(sqlStr);
   					stmt.executeUpdate();
					stmt.close();
				}
				catch(SQLException e2){
					e2.getMessage();
				}
				
				if(old_pwd.equals(new_pwd)) {
					JOptionPane.showMessageDialog(p2, "Your profile is changed.", "Modify", JOptionPane.INFORMATION_MESSAGE);
					
					First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
					First.card.show(First.contentsPanel, "customerPanel");
				}
				else {
					JOptionPane.showMessageDialog(p2, "Your profile and password is changed.\nPlease sign in again.", "Modify", JOptionPane.INFORMATION_MESSAGE);
	                
					First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
					First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
					First.card.show(First.contentsPanel, "mainPanel");
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
				First.card.show(First.contentsPanel, "customerPanel");
			}
		});
		cancelBtn.setBorderPainted(false);
		cancelBtn.setContentAreaFilled(false);
		cancelBtn.setForeground(new Color(21, 78, 149));
		p3.add(cancelBtn);
       
		JButton deleteBtn = new JButton("Delete Account");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String buttons[] = {"Ok", "Cancel"};
				int sel = JOptionPane.showOptionDialog(p2, "Do you want to delete your account?", "Delete account", 0, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[0]);
				if(sel == 0) {
					
					try {
						con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
						String sqlStr = "delete tb_customer where CL_CUS_ID='" + CustomerLoginPanel.getId() + "'";
						
						System.out.println(sqlStr);
						
						PreparedStatement stmt = con.prepareStatement(sqlStr);
						stmt.executeUpdate();
						stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(p2, "Å»ÅðµÊ", "Delete account", JOptionPane.INFORMATION_MESSAGE);
					
					First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
					First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
					First.card.show(First.contentsPanel, "mainPanel");
					
				}
			}
		});
		deleteBtn.setBorderPainted(false);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setForeground(new Color(220, 20, 60));
		p3.add(deleteBtn);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
	}
	
	@SuppressWarnings("deprecation")
	boolean check() {
		if(radioBtn.getSelection() == null || nameFld.getText().equals("") || birthDate.getText().equals("") || phoneFld.getText().equals("") || emailFld.getText().equals("") || pwdFld.getPassword().length == 0 || confirmpwdFld.getPassword().length == 0) {
			JOptionPane.showMessageDialog(p2, "Please enter in all the blanks.", "Modify", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(!pwdFld.getText().equals(confirmpwdFld.getText())) {
			JOptionPane.showMessageDialog(p2, "Please confirm password.", "Password", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

}