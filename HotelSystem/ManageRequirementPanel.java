package HotelSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ManageRequirementPanel extends JPanel {
   
   private Connection con;
	private JScrollPane centerPanel;
	private static String select_req;
	private static String select_room;
   
   public ManageRequirementPanel() {
      addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
        	 centerPanel.setViewportView(make_table());
         }
      });
      
      setBackground(Color.WHITE);
      setLayout(new BorderLayout(0, 0));
       
       JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
       p1.setPreferredSize(new Dimension(0, 65));
       p1.setLayout(new BorderLayout(0, 0));
       
       JLabel manageReqLbl = new JLabel("Requirements Management", JLabel.CENTER);
       manageReqLbl.setFont(new Font("Dialog", Font.BOLD, 30));
       manageReqLbl.setForeground(new Color(21, 78, 149));
       p1.add(manageReqLbl, BorderLayout.CENTER);
       
       JPanel p2 = new JPanel() {
          public void paintComponent(Graphics g) {
             URL imageURL = getClass().getResource("images/background.jpg");
             Image img = new ImageIcon(imageURL).getImage();
             g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
          }
       };
       p2.setLayout(null);
       
       centerPanel = new JScrollPane(); centerPanel.setBackground(Color.WHITE);
       centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
       centerPanel.setBounds(92, 10, 780, 350);
       p2.add(centerPanel);
       
       centerPanel.setViewportView(make_table());
            
       JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
       p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
       p3.setPreferredSize(new Dimension(0, 65));
       
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
              
   } //생성자
   
   JTable make_table() {
	   JTable reqTable = null;
	   
	   try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
			String sqlStr = "select count(*) from tb_complain natural join tb_reservation where cl_state != 'O'";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int num = rs.getInt("count(*)"); //전체 사이즈를 가져옴
			
			String tableHeader[] = {"Complain No.", "Customer ID", "Room No.", "Content", "Staff", "State"};
			
			String tableObject[][] = new String[num][6];
			
			String sqlStr1 = "select cl_cmpl_num, cl_cus_id, cl_room_num, cl_content, cl_staff, cl_state "
					+ "from tb_complain natural join tb_reservation "
					+ "where cl_state != 'O' "
					+ "order by cl_cmpl_num";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();
			
			 for(int i=0; rs1.next(); i++) {
	             tableObject[i][0] = rs1.getString("cl_cmpl_num");
	             tableObject[i][1] = rs1.getString("cl_cus_id");
	             tableObject[i][2] = rs1.getString("cl_room_num");
	             tableObject[i][3] = rs1.getString("cl_content");
	             tableObject[i][4] = rs1.getString("cl_staff");
	             tableObject[i][5] = rs1.getString("cl_state");
	          }
			
			 reqTable = new JTable();
			 reqTable.setModel(new DefaultTableModel(tableObject, tableHeader) {
	    		public boolean isCellEditable(int row, int column) {
	    			if (column == 5) return true;
	    			return false;
	    		}
	    	});
			 reqTable.setFillsViewportHeight(true);
	    	
	    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        for(int i = 0; i < 5; i++) {
	        	reqTable.getColumnModel().getColumn(i).setResizable(false);
	        	reqTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
	        reqTable.getColumnModel().getColumn(2).setMaxWidth(70);
	        reqTable.getColumnModel().getColumn(3).setMinWidth(300);
	        reqTable.getColumnModel().getColumn(5).setMaxWidth(90);
	        reqTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer2());
	        reqTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor2(new JTextField()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqTable;
   }
   
   public static void set_selected_req(String req_num) {
		select_req = req_num;
	}
	public static String get_selected_req() {
		return select_req;
	}
	public static void set_selected_room(String room) {
		select_room = room;
	}
	public static String get_selected_room() {
		return select_room;
	}

} //class

@SuppressWarnings("serial")
class ButtonRenderer2 extends JButton implements TableCellRenderer {
   public ButtonRenderer2() {
       setOpaque(true);
   }
   
   public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {
	   if(obj.equals("N")) {
			setText("Deploy");
			return this;
		}
		else if(obj.equals("I")) {
			setText("Return");
			return this;
		}
		else {
			setText("OK");
			return this;
		}
	}
}

@SuppressWarnings("serial")
class ButtonEditor2 extends DefaultCellEditor {
   protected JButton btn;
   private String lbl;
   private Boolean clicked;
   private JTable reqTable;
   private int target_row;
   
   private Connection con;
   
   public ButtonEditor2(JTextField txt) {
      super(txt);
      btn = new JButton();
      btn.setOpaque(true);
      btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
         }
      });
   }

   public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {
	   lbl = (obj == null) ? "" : obj.toString();
		btn.setText(lbl);
		clicked = true;
		reqTable = table;new String("Deploy");
		target_row = row;
		return btn;
   }

   public Object getCellEditorValue() {
	   if(clicked && reqTable.getModel().getValueAt(target_row, 5).equals("N")) {
			// 배치
			String buttons[] = {"Ok", "Cancel"};
			int sel = JOptionPane.showOptionDialog(First.contentsPanel, "직원을 배치하시겠습니까?", "Take", 0, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if(sel == 0) {
				ManageRequirementPanel.set_selected_req(reqTable.getModel().getValueAt(target_row, 0).toString());
				ManageRequirementPanel.set_selected_room(reqTable.getModel().getValueAt(target_row, 2).toString());

				First.contentsPanel.add("selectStaffPanel", new SelectStaffPanel());
				First.card.show(First.contentsPanel, "selectStaffPanel");
			}
		}
		else if(clicked && reqTable.getModel().getValueAt(target_row, 5).equals("I")) {
			// 복귀
			String buttons[] = {"Ok", "Cancel"};
			int sel = JOptionPane.showOptionDialog(First.contentsPanel, "민원처리를 완료하시겠습니까?", "Return", 0, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
			if(sel == 0) {
				String staff_id = reqTable.getModel().getValueAt(target_row, 4).toString();
				
				try {
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hotel","hotel");
					String sqlStr = "update tb_complain set CL_state = 'O' where cl_staff = '" + staff_id + "'";
					String sqlStr2 = "update tb_staff set CL_workable = 'Y' where cl_staff_id = " + staff_id;
					
					System.out.println(sqlStr);
					System.out.println(sqlStr2);
					
					PreparedStatement stmt = con.prepareStatement(sqlStr);
					PreparedStatement stmt2 = con.prepareStatement(sqlStr2);
					
					stmt.executeUpdate();
					stmt2.executeUpdate();
					
					stmt.close();
					stmt2.close();
					
					First.card.show(First.contentsPanel, "adminPanel");
					First.card.show(First.contentsPanel, "manageRequirementPanel");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		clicked=false;
		return new String(lbl);
   }
   
   public boolean stopCellEditing() {
      clicked = false;
      return super.stopCellEditing();
   }
   
   protected void fireEditingStopped() {
      super.fireEditingStopped();
   }
}