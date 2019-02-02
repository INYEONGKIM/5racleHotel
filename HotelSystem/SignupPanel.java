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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

@SuppressWarnings("serial")
public class SignupPanel extends JPanel {
	
	private JPanel p2;
	
	private JTextField idFld;
	private JPasswordField pwdFld;
	private JPasswordField confirmpwdFld;
	private JTextField nameFld;
	private JRadioButton maleBtn;
	private JRadioButton femaleBtn;
	private DatePicker birthDate;
	private JTextField phoneFld;
	private JTextField emailFld;
	private JButton idchkBtn;
	private ButtonGroup radioBtn;
	
	private Connection con;
	
	public SignupPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel signupLbl = new JLabel("Sign Up", JLabel.CENTER);
    	signupLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	signupLbl.setForeground(new Color(21, 78, 149));
    	p1.add(signupLbl, BorderLayout.CENTER);
    	
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
    	centerPanel.setBounds(200, 10, 540, 350);
    	p2.add(centerPanel);
    	
    	JLabel idLbl = new JLabel("ID :");
    	idLbl.setBounds(90, 25, 120, 20);
		centerPanel.add(idLbl);
		
		JLabel pwdLbl = new JLabel("Create Password :");
		pwdLbl.setBounds(90, 65, 120, 20);
		centerPanel.add(pwdLbl);
		
		JLabel confirmpwdLbl = new JLabel("Confirm Password :");
		confirmpwdLbl.setBounds(90, 105, 120, 20);
		centerPanel.add(confirmpwdLbl);
		
		JLabel nameLbl = new JLabel("Name :");
		nameLbl.setBounds(90, 145, 120, 20);
		centerPanel.add(nameLbl);
		
		JLabel genderLbl = new JLabel("Gender :");
		genderLbl.setBounds(90, 185, 120, 20);
		centerPanel.add(genderLbl);
		
		JLabel birthLbl = new JLabel("Date Of Birth :");
		birthLbl.setBounds(90, 225, 120, 20);
		centerPanel.add(birthLbl);
		
		JLabel phoneLbl = new JLabel("Phone No. :");
		phoneLbl.setBounds(90, 265, 120, 20);
		centerPanel.add(phoneLbl);
		
		JLabel emailLbl = new JLabel("E-mail :");
		emailLbl.setBounds(90, 305, 120, 20);
		centerPanel.add(emailLbl);
		
		idFld = new JTextField();
		idFld.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				idchkBtn.setForeground(new Color(220, 20, 60));
				idchkBtn.setText("Check");
			}
		});
		idFld.setBounds(280, 20, 120, 30);
		centerPanel.add(idFld);
		
		idchkBtn = new JButton("Check");
		idchkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idchkBtn.getText().equals("Check")) {
					if(idFld.getText().equals("")) {
						JOptionPane.showMessageDialog(p2, "ID를 입력하세요.", "ID", JOptionPane.ERROR_MESSAGE);
					}
					else {
						// id check part
						try {
		    				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
		    				String sqlStr1 = "select cl_cus_id from tb_customer";   
		    				PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
		                    ResultSet rs = stmt1.executeQuery();
		                    
		                    for(; rs.next(); ) {
		                    	if(idFld.getText().equals(rs.getString("cl_cus_id"))) {
		                    		JOptionPane.showMessageDialog(p2, "이미 가입된 ID 입니다.", "ID", JOptionPane.ERROR_MESSAGE);
		                    		return;
		                    	}
		                    }
		                    JOptionPane.showMessageDialog(p2, "ID 사용이 가능합니다.", "ID", JOptionPane.INFORMATION_MESSAGE);
		    			}catch (SQLException e1) {
		    				e1.printStackTrace();
		    			}
						
						idchkBtn.setForeground(new Color(21, 78, 149));
						idchkBtn.setText("Ok");
					}
				}
				
			}
		});
		idchkBtn.setFont(new Font("Dialog", Font.BOLD, 12));
		idchkBtn.setBounds(400, 20, 70, 29);
		idchkBtn.setForeground(new Color(220, 20, 60));
		centerPanel.add(idchkBtn);
		
		pwdFld = new JPasswordField();
		pwdFld.setBounds(280, 60, 190, 30);
		centerPanel.add(pwdFld);
		
		confirmpwdFld = new JPasswordField();
		confirmpwdFld.setBounds(280, 100, 190, 30);
		centerPanel.add(confirmpwdFld);
		
		nameFld = new JTextField();
		nameFld.setBounds(280, 140, 190, 30);
		centerPanel.add(nameFld);
		
		maleBtn = new JRadioButton("Male");
		maleBtn.setBorderPainted(false);
		maleBtn.setContentAreaFilled(false);
		maleBtn.setBounds(280, 180, 85, 30);
		centerPanel.add(maleBtn);
		femaleBtn = new JRadioButton("Female");
		femaleBtn.setBorderPainted(false);
		femaleBtn.setContentAreaFilled(false);
		femaleBtn.setBounds(375, 180, 90, 30);
		centerPanel.add(femaleBtn);
		radioBtn = new ButtonGroup();
		radioBtn.add(maleBtn);
		radioBtn.add(femaleBtn);
		
		DatePickerSettings settings = new DatePickerSettings();
		settings.setFormatForDatesCommonEra("uuuu-MM-dd");
		settings.setColor(DateArea.DatePickerTextInvalidDate, Color.BLACK);
		settings.setColor(DateArea.DatePickerTextValidDate, Color.BLACK);
		birthDate = new DatePicker(settings);
		birthDate.getComponentToggleCalendarButton().setText("▼");
		birthDate.getComponentDateTextField().setEditable(false);
		birthDate.setBounds(280, 220, 190, 30);
		centerPanel.add(birthDate);
		
		phoneFld = new JTextField();
		phoneFld.setBounds(280, 260, 190, 30);
		centerPanel.add(phoneFld);
		
		emailFld = new JTextField();
		emailFld.setBounds(280, 300, 190, 30);
		centerPanel.add(emailFld);
    	
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton okBtn = new JButton("Sign Up");
    	okBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
    			if(check())
    				return;
    			
    			//DB Input part
    			String id = idFld.getText();
    			String staff = null;
				String name = nameFld.getText();
				String gender = "";
				if(maleBtn.isSelected()) gender = "m";
				else if(femaleBtn.isSelected()) gender = "f";
				String date = birthDate.getDate().toString();
				String phone = phoneFld.getText();
				String email = emailFld.getText();
                String pwd = pwdFld.getText();
                
                try {
                	con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
                    String sqlStr = "insert into tb_customer values('"+id+"','"+staff+"','"+name+"','"+gender+"','"+date+"','"+phone+"','"+email+"','"+pwd+"')";   
                    PreparedStatement stmt = con.prepareStatement(sqlStr);
                    stmt.executeUpdate();
                    stmt.close();
                } catch(SQLException e2){
                	e2.getMessage();
                }
    			
    			JOptionPane.showMessageDialog(p2, "Thank you for join us!", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
    			First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "mainPanel");
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
	
	@SuppressWarnings("deprecation")
	boolean check() {
		if(!idchkBtn.getText().equals("Ok")) {
			JOptionPane.showMessageDialog(p2, "아이디 중복 체크를 확인하세요.", "ID", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(radioBtn.getSelection() == null || nameFld.getText().equals("") || birthDate.getText().equals("") || phoneFld.getText().equals("") || emailFld.getText().equals("") || pwdFld.getPassword().length == 0 || confirmpwdFld.getPassword().length == 0) {
			JOptionPane.showMessageDialog(p2, "모든 항목을 채워주세요.", "Sign Up", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(!pwdFld.getText().equals(confirmpwdFld.getText())) {
			JOptionPane.showMessageDialog(p2, "비밀번호가 일치하지 않습니다.", "Password", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

}
