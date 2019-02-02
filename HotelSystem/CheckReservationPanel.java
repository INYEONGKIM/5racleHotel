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
public class CheckReservationPanel extends JPanel {

	private JScrollPane centerPanel;
	private JPanel p2;
	private int num;

	public CheckReservationPanel() {
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				centerPanel.setViewportView(make_table());// 갱신
			}
		});

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JPanel p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(0, 65));
		p1.setLayout(new BorderLayout(0, 0));

		JLabel cancelRsvLbl = new JLabel("Cancel Reservations", JLabel.CENTER);
		cancelRsvLbl.setFont(new Font("Dialog", Font.BOLD, 30));
		cancelRsvLbl.setForeground(new Color(21, 78, 149));
		p1.add(cancelRsvLbl, BorderLayout.CENTER);

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
		centerPanel.setBounds(100, 10, 740, 350);
		p2.add(centerPanel);

		centerPanel.setViewportView(make_table());

		JPanel p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
		p3.setPreferredSize(new Dimension(0, 65));

		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				First.contentsPanel.remove(First.contentsPanel.getComponentCount() - 1);
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
		JTable rsvTable = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
			String id = CustomerLoginPanel.getId();
			String sqlStr = "select count(*) from tb_reservation where CL_CUS_ID = '" + id + "' and CL_SIGN = 'N'";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			num = rs.getInt("count(*)");

			String tableHeader[] = { "Reservation No.", "Customer ID", "Check-In Date", "Check-Out Date", "Headcount",
					"Room Type", "Room No.", "Cancel" };
			String[][] Object = new String[num][8];

			String sqlStr1 = "select CL_RS_NUM, CL_CUS_ID, CL_CHECK_IN, CL_CHECK_OUT,"
					+ "CL_CO_NUM,CL_ROOM_NUM,CL_ROOM_TYPE,CL_SIGN from tb_reservation where CL_CUS_ID = '" + id
					+ "' and CL_SIGN = 'N' order by CL_SIGN, CL_CHECK_IN";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();

			for (int i = 0; rs1.next(); i++) {
				Object[i][0] = rs1.getString("CL_RS_NUM");
				Object[i][1] = rs1.getString("CL_CUS_ID");
				Object[i][2] = rs1.getString("CL_CHECK_IN").substring(0, 10);
				Object[i][3] = rs1.getString("CL_CHECK_OUT").substring(0, 10);
				Object[i][4] = rs1.getString("CL_CO_NUM");
				Object[i][5] = rs1.getString("CL_ROOM_TYPE");

				if (rs1.getString("CL_ROOM_NUM") == null) {
					Object[i][6] = "미배정";
				} else {
					Object[i][6] = rs1.getString("CL_ROOM_NUM");
				}

				Object[i][7] = rs1.getString("CL_SIGN");
			}

			rsvTable = new JTable();
			rsvTable.setModel(new DefaultTableModel(Object, tableHeader) {
				public boolean isCellEditable(int row, int column) {
					if (column == 7)
						return true;
					return false;
				}
			});

			rsvTable.getTableHeader().setReorderingAllowed(false);
			rsvTable.setFillsViewportHeight(true);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);

			for (int i = 0; i < 7; i++) {
				rsvTable.getColumnModel().getColumn(i).setResizable(false);
				rsvTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
			rsvTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer3());
			rsvTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor3(new JTextField()));

			centerPanel.setViewportView(rsvTable);
			rs.next();
			stmt.close();

			rs1.next();
			stmt1.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsvTable;
	}

}

@SuppressWarnings("serial")
class ButtonRenderer3 extends JButton implements TableCellRenderer {
	public ButtonRenderer3() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row,
			int col) {
		if (obj.equals("N")) {
			setText("Cancel");
			return this;
		} else {
			setText("OK");
			return this;
		}
	}
}

@SuppressWarnings("serial")
class ButtonEditor3 extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private Boolean clicked;
	private JTable rsvTable;
	private int target_row;

	public ButtonEditor3(JTextField txt) {
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
		rsvTable = table;
		target_row = row;
		// System.out.println(rsvTable.getValueAt(row, 0));
		return btn;
	}

	public Object getCellEditorValue() {
		if (rsvTable.getModel().getValueAt(target_row, 7).equals("ok"))
			return new String("ok");
		if (clicked) {
			String buttons[] = { "Ok", "Cancel" };
			int sel = JOptionPane.showOptionDialog(null, "예약 취소 하시겠습니까?", "예약취소", 0, JOptionPane.INFORMATION_MESSAGE,
					null, buttons, buttons[0]);
			if (sel == 0) {
				try {
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel",
							"hotel");
					String reserNum = (String) rsvTable.getValueAt(target_row, 0);
					String sqlStr = "delete from tb_reservation where CL_RS_NUM = " + reserNum;
					PreparedStatement stmt = con.prepareStatement(sqlStr);
					ResultSet rs = stmt.executeQuery();
					rs.next();
					JOptionPane.showMessageDialog(null, "취소 완료.");
					rs.close();
					stmt.close();

					First.card.show(First.contentsPanel, "customerPanel");
					First.card.show(First.contentsPanel, "checkReservationPanel");

				} catch (SQLException e1) {
					e1.getMessage();
				}

			}
		}
		clicked = false;
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