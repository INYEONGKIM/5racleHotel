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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ManageStaffPanel extends JPanel {
   private static StaffAdd staffAdd;
   private JScrollPane centerPanel;
   private JPanel p2;

   public ManageStaffPanel() {
      addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
            centerPanel.setViewportView(make_table());// 갱신
         }
      });

      staffAdd = new StaffAdd(First.contentsPanel, First.card);
      setBackground(Color.WHITE);
      setLayout(new BorderLayout(0, 0));

      JPanel p1 = new JPanel();
      p1.setBackground(Color.WHITE);
      p1.setPreferredSize(new Dimension(0, 65));
      p1.setLayout(new BorderLayout(0, 0));

      JLabel manageStaffLbl = new JLabel("Staffs Management", JLabel.CENTER);
      manageStaffLbl.setFont(new Font("Dialog", Font.BOLD, 30));
      manageStaffLbl.setForeground(new Color(21, 78, 149));
      p1.add(manageStaffLbl, BorderLayout.CENTER);

      p2 = new JPanel() {
         public void paintComponent(Graphics g) {
            URL imageURL = getClass().getResource("images/background.jpg");
            Image img = new ImageIcon(imageURL).getImage();
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
         }
      };
      p2.setLayout(null);

      centerPanel = new JScrollPane();
      centerPanel.setBackground(Color.WHITE);
      centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
      centerPanel.setBounds(150, 10, 680, 350);
      p2.add(centerPanel);

      centerPanel.setViewportView(make_table());

      JPanel p3 = new JPanel();
      p3.setBackground(Color.WHITE);
      p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
      p3.setPreferredSize(new Dimension(0, 65));

      JButton btnAddition = new JButton("Addition");
      btnAddition.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            First.contentsPanel.add("staffAdd", staffAdd);
            First.card.show(First.contentsPanel, "staffAdd");
         }
      });
      btnAddition.setBorderPainted(false);
      btnAddition.setContentAreaFilled(false);
      btnAddition.setForeground(new Color(21, 78, 149));
      p3.add(btnAddition);
      
      JButton btnDelete = new JButton("Delete");
      btnDelete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String stf_id;
            while (true) {
               stf_id = JOptionPane.showInputDialog("삭제하실 직원의 ID를 입력해주세요.");
               if (stf_id.equals(""))
                  JOptionPane.showMessageDialog(null, "직원ID를 입력해주세요");
               else
                  break;
            }
            delStaff(stf_id);
            First.card.show(First.contentsPanel, "adminPanel");
            First.card.show(First.contentsPanel, "manageStaffPanel");
         }
      });

      btnDelete.setBorderPainted(false);
      btnDelete.setContentAreaFilled(false);
      btnDelete.setForeground(new Color(21, 78, 149));
      p3.add(btnDelete);

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

   } // 생성자

   JTable make_table() {
      JTable staffTable = null;
      try {
         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
         String sqlStr = "select count(*) from tb_staff";
         PreparedStatement stmt = con.prepareStatement(sqlStr);
         ResultSet rs = stmt.executeQuery();
         rs.next();
         int num = rs.getInt("count(*)"); // 전체 사이즈를 가져옴

         String tableHeader[] = { "Staff ID", "Name", "Department", "Phone No.", "Status", "Floor" };
         String tableObject[][] = new String[num][6];

         String sqlStr1 = "select CL_STAFF_ID, CL_STAFF_NM, CL_DEP, CL_STAFF_PHONE,"
               + "CL_WORKABLE,CL_now_floor from tb_staff order by cl_workable desc, cl_dep";
         PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
         ResultSet rs1 = stmt1.executeQuery();

         for (int i = 0; rs1.next(); i++) {

            tableObject[i][0] = rs1.getString("CL_STAFF_ID");
            tableObject[i][1] = rs1.getString("CL_STAFF_NM");
            tableObject[i][2] = rs1.getString("CL_DEP");
            tableObject[i][3] = rs1.getString("CL_STAFF_PHONE");
            tableObject[i][4] = rs1.getString("CL_WORKABLE");
            tableObject[i][5] = rs1.getString("CL_now_floor");
         }
         staffTable = new JTable();
         staffTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         });
         staffTable.getTableHeader().setReorderingAllowed(false);
         staffTable.setFillsViewportHeight(true);

         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         centerRenderer.setHorizontalAlignment(JLabel.CENTER);

         for (int i = 0; i < 6; i++) {
            staffTable.getColumnModel().getColumn(i).setResizable(false);
            staffTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
         }

         centerPanel.setViewportView(staffTable);
         rs1.close();
         stmt1.close();

      } catch (SQLException e) {
         e.printStackTrace();
      }

      return staffTable;
   }

   void delStaff(String stf_id) {
      try {
         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
         String sqlStr = "delete from tb_staff where CL_STAFF_ID = '" + stf_id + "'";
         PreparedStatement stmt = con.prepareStatement(sqlStr);
         ResultSet rs = stmt.executeQuery();
         rs.next();

         JOptionPane.showMessageDialog(null, "삭제 완료");
         rs.close();
         stmt.close();
      } catch (SQLException e1) {

         JOptionPane.showMessageDialog(null, "존재하지 않는 직원ID 입니다");
         e1.printStackTrace();
      }

   }

} // class