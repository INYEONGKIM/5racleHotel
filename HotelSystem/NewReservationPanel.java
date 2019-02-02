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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

@SuppressWarnings("serial")
public class NewReservationPanel extends JPanel {
	
	private DatePicker chkinDate;
	private DatePicker chkoutDate;
	private JComboBox<String> typeCbox;
	private JComboBox<Integer> countCbox;
	
	private Integer[] co_num = null;
	
	private Connection con;
	
	public NewReservationPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
    	JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel newRsvLbl = new JLabel("New Reservation", JLabel.CENTER);
    	newRsvLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	newRsvLbl.setForeground(new Color(21, 78, 149));
    	p1.add(newRsvLbl, BorderLayout.CENTER);
    	
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
    	centerPanel.setBounds(180, 10, 580, 350);
    	p2.add(centerPanel);
    	
    	JLabel chkinLbl = new JLabel("Check-In Date:");
    	chkinLbl.setBounds(90, 25, 120, 20);
		centerPanel.add(chkinLbl);
    	
    	JLabel chkoutLbl = new JLabel("Check-Out Date:");
    	chkoutLbl.setBounds(90, 65, 120, 20);
		centerPanel.add(chkoutLbl);
    	
    	JLabel typeLbl = new JLabel("Room Type:");
    	typeLbl.setBounds(90, 105, 120, 20);
		centerPanel.add(typeLbl);
    	
    	JLabel countLbl = new JLabel("Headcount:");
    	countLbl.setBounds(90, 145, 120, 20);
		centerPanel.add(countLbl);
		
		DatePickerSettings settings1 = new DatePickerSettings();
		settings1.setFormatForDatesCommonEra("uuuu-MM-dd");
		settings1.setColor(DateArea.DatePickerTextInvalidDate, Color.BLACK);
		settings1.setColor(DateArea.DatePickerTextValidDate, Color.BLACK);
		chkinDate = new DatePicker(settings1);
		chkinDate.getComponentToggleCalendarButton().setText("▼");
		chkinDate.getComponentDateTextField().setEditable(false);
		chkinDate.setBounds(280, 20, 190, 30);
		centerPanel.add(chkinDate);
		
		DatePickerSettings settings2 = new DatePickerSettings();
		settings2.setFormatForDatesCommonEra("uuuu-MM-dd");
		settings2.setColor(DateArea.DatePickerTextInvalidDate, Color.BLACK);
		settings2.setColor(DateArea.DatePickerTextValidDate, Color.BLACK);
		chkoutDate = new DatePicker(settings2);
		chkoutDate.getComponentToggleCalendarButton().setText("▼");
		chkoutDate.getComponentDateTextField().setEditable(false);
		chkoutDate.setBounds(280, 60, 190, 30);
		centerPanel.add(chkoutDate);
		
		String[] r_type = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
			String sqlStr1 = "select count(*) from tb_room_type";
			String sqlStr2 = "select cl_room_type from tb_room_type";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
	        PreparedStatement stmt2 = con.prepareStatement(sqlStr2);
	        ResultSet rs1 = stmt1.executeQuery();
	        ResultSet rs2 = stmt2.executeQuery();
	        
	        rs1.next();
	        r_type = new String[rs1.getInt("count(*)")];

	        for(int i=0; rs2.next(); i++)
				r_type[i] = rs2.getString("CL_ROOM_TYPE");
	        
	        stmt1.close();
	        stmt2.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		countCbox = new JComboBox<Integer>();
		centerPanel.add(countCbox);
		
		ComboBoxModel<String> typeList = new DefaultComboBoxModel<String>(r_type);
		typeCbox = new JComboBox<String>(typeList);
		typeCbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
						con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
						String sqlStr1 = "select cl_max from tb_room_type where cl_room_type = '" + typeCbox.getSelectedItem() + "'";
					PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			        ResultSet rs1 = stmt1.executeQuery();
			        rs1.next();
			        
			        System.out.println(sqlStr1);
			        
			        int max_num = rs1.getInt("CL_MAX");
			        co_num = new Integer[max_num];
			        for(int i = 0; i < max_num; i++)
			        	co_num[i] = i+1;
			        
			        stmt1.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				centerPanel.remove(countCbox);
				ComboBoxModel<Integer> countList = new DefaultComboBoxModel<Integer>(co_num);
				countCbox = new JComboBox<Integer>(countList);
				countCbox.setEditable(true);
				countCbox.getEditor().getEditorComponent().setBackground(Color.WHITE);
				((JTextField)countCbox.getEditor().getEditorComponent()).setEditable(false);
				countCbox.setBounds(280, 140, 190, 30);
				countCbox.setSelectedItem(null);
				centerPanel.add(countCbox);
				centerPanel.setVisible(false);
				centerPanel.setVisible(true);
			}
		});
		typeCbox.setEditable(true);
		typeCbox.getEditor().getEditorComponent().setBackground(Color.WHITE);
		((JTextField)typeCbox.getEditor().getEditorComponent()).setEditable(false);
		typeCbox.setBounds(280, 100, 190, 30);
		typeCbox.setSelectedIndex(0);
		centerPanel.add(typeCbox);
    	
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	p3.setPreferredSize(new Dimension(0, 65));
    	
    	JButton okBtn = new JButton("Reservation");
    	okBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    			String userId = CustomerLoginPanel.getId();
                String chkIn = chkinDate.getText();
                String chkOut = chkoutDate.getText();
                String roomType = (String) typeCbox.getSelectedItem();
                Integer headNum = (Integer) countCbox.getSelectedItem();
                Integer InDateCount, OutDateCount;

                InDateCount = DateCount(chkIn);
                OutDateCount = DateCount(chkOut);

                if (chkIn.equals("") || chkOut.equals("") || typeCbox.getSelectedItem() == null
                      || countCbox.getSelectedItem() == null) {
                   JOptionPane.showMessageDialog(null, "필수사항을 입력해주세요.");
                } else if (InDateCount >= OutDateCount) {
                   JOptionPane.showMessageDialog(null, "올바른 날짜를 지정해주세요.");
                } else {
                   try {
                      con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
                      String sqlStr = "insert into tb_reservation values(tb_reservation_seq.nextval,'" + userId
                            + "',null,'" + chkIn + "', '" + chkOut + "','N'," + headNum + ",'" + roomType + "')";
                      PreparedStatement stmt = con.prepareStatement(sqlStr);
                      stmt.executeUpdate();
                      JOptionPane.showMessageDialog(null, "예약이 완료되었습니다.");
                      stmt.close();
                      
                      First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
          				First.card.show(First.contentsPanel, "customerPanel");
                   } catch (SQLException e1) {
                      e1.getMessage();
                   }
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
    	
    	add(p1, BorderLayout.NORTH);
    	add(p2, BorderLayout.CENTER);
    	add(p3, BorderLayout.SOUTH);
	}
	
	Integer DateCount(String str) {
	      Integer count = 0;
	      String[] a = str.split("-");
	      try {
	         count += Integer.parseInt(a[0]) * 10000 + Integer.parseInt(a[1]) * 100 + Integer.parseInt(a[2]);
	      } catch (NumberFormatException e) {
	         e.getMessage();
	      }
	      return count;
	   }

}
