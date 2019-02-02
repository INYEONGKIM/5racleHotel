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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ServiceHistoryPanel extends JPanel {

	private String tableObject[][];
	private int num;

	public ServiceHistoryPanel() {
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
			}
		});

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JPanel p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(0, 65));
		p1.setLayout(new BorderLayout(0, 0));

		JLabel usageHistLb1 = new JLabel("Service Usage History", JLabel.CENTER);
		usageHistLb1.setFont(new Font("Dialog", Font.BOLD, 30));
		usageHistLb1.setForeground(new Color(21, 78, 149));
		p1.add(usageHistLb1, BorderLayout.CENTER);

		JPanel p2 = new JPanel() {
			public void paintComponent(Graphics g) {
				URL imageURL = getClass().getResource("images/background.jpg");
				Image img = new ImageIcon(imageURL).getImage();
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		p2.setLayout(null);

		JScrollPane centerPanel = new JScrollPane();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
		centerPanel.setBounds(100, 10, 740, 350);
		p2.add(centerPanel);
		// -----------------------------------------------
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
			String id = CustomerLoginPanel.getId();
			String sqlStr = "select count(*) from tb_service where CL_CUS_ID = '" + id + "'";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			num = rs.getInt("count(*)"); // 전체 사이즈를 가져옴

			String tableHeader[] = { "Facility", "Usage Details", "Price", "Date of use" };
			String[][] Object = new String[num][4];
			String sqlStr1 = "select CL_FC_NM, CL_SER_NM, CL_FC_PRICE, CL_USE_DATE from tb_service where CL_CUS_ID = '"
					+ id + "' order by CL_USE_DATE";
			PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
			ResultSet rs1 = stmt1.executeQuery();

			for (int i = 0; rs1.next(); i++) {
				Object[i][0] = rs1.getString("CL_FC_NM");
				Object[i][1] = rs1.getString("CL_SER_NM");
				Object[i][2] = rs1.getString("CL_FC_PRICE");
				Object[i][3] = rs1.getString("CL_USE_DATE").substring(0, 10);
			}
			JTable rsvTable = new JTable();
			rsvTable.setModel(new DefaultTableModel(Object, tableHeader) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});

			// ------------------------------------------------
			rsvTable.getTableHeader().setReorderingAllowed(false);
			rsvTable.setFillsViewportHeight(true);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);

			for (int i = 0; i < 4; i++) {
				rsvTable.getColumnModel().getColumn(i).setResizable(false);
				rsvTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}

			centerPanel.setViewportView(rsvTable);

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
			rs.next();
			stmt.close();

			rs1.next();
			stmt1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void load() {

		tableObject = new String[4][4];

		tableObject[0][0] = "1";
		tableObject[0][1] = "2";
		tableObject[0][2] = "3";
		tableObject[0][3] = "4";
	}

}
