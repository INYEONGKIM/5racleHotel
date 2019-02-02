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
public class ShowComplainPanel extends JPanel {

   private JScrollPane centerPanel;
   private JPanel p2;

   public ShowComplainPanel() {

      setBackground(Color.WHITE);
      setLayout(new BorderLayout(0, 0));

      JPanel p1 = new JPanel();
      p1.setBackground(Color.WHITE);
      p1.setPreferredSize(new Dimension(0, 65));
      p1.setLayout(new BorderLayout(0, 0));

      JLabel manageReqLbl = new JLabel("Complains", JLabel.CENTER);
      manageReqLbl.setFont(new Font("Dialog", Font.BOLD, 30));
      manageReqLbl.setForeground(new Color(21, 78, 149));
      p1.add(manageReqLbl, BorderLayout.CENTER);

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

      JButton backBtn = new JButton("Back");
      backBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            First.card.show(First.contentsPanel, "customerPanel");
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
      JTable reqTable = null;
      try {
         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");

         String sqlStr = "select count(*) from tb_complain where CL_CUS_ID = '"
               + CustomerLoginPanel.getId() + "'"; // 첫번쨰 sql
         String tableHeader[] = { "Complain Number", "Content", "Status", "Staff" };
         PreparedStatement stmt = con.prepareStatement(sqlStr);
         ResultSet rs = stmt.executeQuery();
         rs.next();
         int num = rs.getInt("count(*)"); // 전체 사이즈를 가져옴

         String tableObject[][] = new String[num][4];

         String sqlStr1 = "select CL_CMPL_NUM,CL_CONTENT,CL_STATE,CL_STAFF from tb_complain where CL_CUS_ID = '"
               + CustomerLoginPanel.getId() + "'";
         PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
         ResultSet rs1 = stmt1.executeQuery();

         for (int i = 0; rs1.next(); i++) {
            // sql
            tableObject[i][0] = rs1.getString("CL_CMPL_NUM");
            tableObject[i][1] = rs1.getString("CL_CONTENT");
            String tmp = rs1.getString("CL_STATE");
            if (tmp.equals("N")) {
               tableObject[i][2] = "Not yet";
            } else if (tmp.equals("I")) {
               tableObject[i][2] = "처리 중";
            } else {
               tableObject[i][2] = "Complete";
            }
            tableObject[i][3] = rs1.getString("CL_STAFF");
         }

         reqTable = new JTable();
         reqTable.setModel(new DefaultTableModel(tableObject, tableHeader));
         reqTable.getTableHeader().setReorderingAllowed(false);
         reqTable.setFillsViewportHeight(true);

         reqTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         });

         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
         for (int i = 0; i < 4; i++) {
            reqTable.getColumnModel().getColumn(i).setResizable(false);
            reqTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
         }
         reqTable.getColumnModel().getColumn(1).setMinWidth(280);

         centerPanel.setViewportView(reqTable);

         rs1.next();
         stmt1.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return reqTable;
   }

}