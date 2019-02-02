package HotelSystem;
import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class StaffAdd extends JPanel {
   
   private JTextField textName;
   private JTextField textPhonenumber;
   private JTextField textDep;
   private JTextField textEmail;
   private JTextField textBirth;
   private JComboBox<String> typeCbox;
   
   
   private static StaffAdd staffadd;
   private JRadioButton maleBtn;
   private JRadioButton femaleBtn;
   private ButtonGroup radioBtn2;
   
   /**
    * @wbp.nonvisual location=396,401
    */
   //private final JButton btnAdd = new JButton("Add");
   //private final JLabel lblNewLabel_2 = new JLabel("New label");
   
   public StaffAdd(JPanel contentsPanel, CardLayout card) {
      //staffAdd = new StaffAdd(contentsPanel, card);
      addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
            clear();
         }
      });
      setBackground(Color.WHITE);
      setLayout(new BorderLayout(0, 0));
       
       JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
       p1.setPreferredSize(new Dimension(0, 65));
       p1.setLayout(new BorderLayout(0, 0));
       
    
       
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
       centerPanel.setBounds(150, 10, 680, 350);
       p2.add(centerPanel);
       
       
       
       JLabel lblName = new JLabel("*Name");
       centerPanel.add(lblName);
       lblName.setBounds(220, 80, 61, 16);
       
       textName = new JTextField();
       textName.setBounds(330, 80, 130, 26);
       centerPanel.add(textName);
       textName.setColumns(10);
       
       JLabel lblPhonenumber = new JLabel("*Phone number");
       lblPhonenumber.setBounds(220, 110, 95, 16);
       centerPanel.add(lblPhonenumber);
       
       textPhonenumber = new JTextField();
       textPhonenumber.setBounds(330, 110, 130, 26);
       centerPanel.add(textPhonenumber);
       textPhonenumber.setColumns(10);
       
       
       JLabel lblDep = new JLabel("*Departure");
       lblDep.setBounds(220, 140, 95, 16);
       centerPanel.add(lblDep);
       //--------------------------------------------
       ComboBoxModel<String> typeList = new DefaultComboBoxModel<String>(
            new String[] { "Information", "Management", "Cleaning", "Concierge", "IT Development",
                  "Products Devlopment", "Business Support", "Director" });

      typeCbox = new JComboBox<String>(typeList);
      typeCbox.setEditable(true);
      typeCbox.getEditor().getEditorComponent().setBackground(Color.WHITE);
      ((JTextField) typeCbox.getEditor().getEditorComponent()).setEditable(false);
      typeCbox.setBounds(330, 140, 130, 26);
      centerPanel.add(typeCbox);
       //--------------------------------------
//       textDep = new JTextField();
//       textDep.setBounds(143, 140, 130, 26);
//       centerPanel.add(textDep);
//       textDep.setColumns(10);
       
       JLabel lblEmail = new JLabel("*e_mail");
       lblEmail.setBounds(220, 170, 95, 16);
       centerPanel.add(lblEmail);
       
       textEmail = new JTextField();
       textEmail.setBounds(330, 170, 130, 26);
       centerPanel.add(textEmail);
       textEmail.setColumns(10);
      
       JLabel genderLbl = new JLabel("*Gender :");
        genderLbl.setBounds(220, 200, 120, 20);
        centerPanel.add(genderLbl);
        
        maleBtn = new JRadioButton("Male");
        maleBtn.setBorderPainted(false);
        maleBtn.setContentAreaFilled(false);
        maleBtn.setBounds(330, 200, 85, 30);
        centerPanel.add(maleBtn);
        
        femaleBtn = new JRadioButton("Female");
        femaleBtn.setBorderPainted(false);
        femaleBtn.setContentAreaFilled(false);
        femaleBtn.setBounds(400, 200, 85, 30);
        centerPanel.add(femaleBtn);
        ButtonGroup radioBtn2 = new ButtonGroup();
        radioBtn2.add(maleBtn);
        radioBtn2.add(femaleBtn);
        
        JLabel staffbirth = new JLabel("Birthday");
        staffbirth.setBounds(220, 230, 120, 20);
        centerPanel.add(staffbirth);
        
        textBirth= new JTextField();
        textBirth.setBounds(330, 230, 130, 26);
       centerPanel.add(textBirth);
       textBirth.setColumns(10);
      
       
       JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
       p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
       p3.setPreferredSize(new Dimension(0, 65));
       
       JButton sendBtn = new JButton("Add");
       sendBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             String staf_nm = textName.getText();
             String staf_phon = textPhonenumber.getText();
             String staf_dep = (String) typeCbox.getSelectedItem();
             int now_floor = 1, randNum = ((int)Math.random()*10);
             String gen, randNum2 = Integer.toString(randNum);
             if(maleBtn.isSelected())
                gen = "01";
             else
                gen = "02";
             String staffID = "18" + (typeCbox.getSelectedIndex()+10) + gen + randNum2;
             
             
             if(staf_nm.equals("") || staf_phon.equals("") || staf_dep.equals("") ||radioBtn2.getSelection() == null||textEmail.getText().equals("")){
                System.out.println(typeCbox.getSelectedIndex());
                System.out.println(femaleBtn.isSelected());
                JOptionPane.showMessageDialog(null, "필수사항을 입력해주세요!");
             }else{
                try {
                   Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
                   String sqlStr = "insert into TB_STAFF values('"+staffID+"','"+staf_dep+"','"+staf_nm+"','"+staf_phon+"',2800000,'N',"+now_floor+")";
                   PreparedStatement stmt = con.prepareStatement(sqlStr);
                   ResultSet rs = stmt.executeQuery();
                   rs.next();
                   
                   rs.close();
                   stmt.close();

                } catch (SQLException e1) {
                   e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "직원등록 완료");
                 card.show(contentsPanel, "manageStaffPanel");
             }
          }
       });
       
       sendBtn.setBorderPainted(false);
       sendBtn.setContentAreaFilled(false);
       sendBtn.setForeground(new Color(21, 78, 149));
       p3.add(sendBtn);
       
       JButton cancelBtn = new JButton("Back");
       cancelBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             card.show(contentsPanel, "manageStaffPanel");
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
   
   void clear() {
      
   }
}