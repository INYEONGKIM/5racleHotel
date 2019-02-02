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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class NewComplainPanel extends JPanel {
	private JTextField textField;
	public Connection con;

	public NewComplainPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JPanel p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(0, 65));
		p1.setLayout(new BorderLayout(0, 0));

		JLabel newCmpLbl = new JLabel("New Complain", JLabel.CENTER);
		newCmpLbl.setFont(new Font("Dialog", Font.BOLD, 30));
		newCmpLbl.setForeground(new Color(21, 78, 149));
		p1.add(newCmpLbl, BorderLayout.CENTER);

		JPanel p2 = new JPanel() {
			public void paintComponent(Graphics g) {
				URL imageURL = getClass().getResource("images/background.jpg");
				Image img = new ImageIcon(imageURL).getImage();
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		p2.setLayout(null);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(null);
		centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
		centerPanel.setBounds(192, 65, 560, 240);
		p2.add(centerPanel);

		JLabel compLbl = new JLabel("What's the problem?");
		compLbl.setFont(new Font("Dialog", Font.BOLD, 16));
		compLbl.setBounds(190, 70, 180, 30);
		centerPanel.add(compLbl);

		textField = new JTextField("Please write it in less than 50 characters.", 50);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				textField.setForeground(Color.BLACK);
				textField.setText("");
			}

			public void focusLost(FocusEvent e) {
				String text = textField.getText();
				if (text.length() > 50)
					text = text.substring(0, 50);
				textField.setText(text);
			}
		});
		textField.setBounds(90, 150, 380, 30);
		centerPanel.add(textField);

		JPanel p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
		p3.setPreferredSize(new Dimension(0, 65));

		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = textField.getText();
				try {
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hotel", "hotel");
				
					String sqlStr1 = "select count(*) from tb_reservation where cl_room_num is not null and cl_cus_id = '"
							+ CustomerLoginPanel.getId() + "'";
					PreparedStatement stmt1 = con.prepareStatement(sqlStr1);
					ResultSet rs = stmt1.executeQuery();
					rs.next();
					int num = rs.getInt("count(*)");
					if(num < 1) {
						JOptionPane.showMessageDialog(p2, "not yet!!", "Alert",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					String sqlStr = "insert into tb_complain values(tb_complain_seq.nextval,'"
							+ CustomerLoginPanel.getId() + "','" + content + "','N',null)";
					PreparedStatement stmt = con.prepareStatement(sqlStr);
					stmt.executeUpdate();
					stmt.close();
				} catch (SQLException e2) {
					e2.getMessage();
				}

				JOptionPane.showMessageDialog(p2, "민원이 접수되었습니다. 신속히 처리해드리겠습니다.", "Complain",
						JOptionPane.INFORMATION_MESSAGE);

				First.contentsPanel.remove(First.contentsPanel.getComponentCount() - 1);
				First.card.show(First.contentsPanel, "customerPanel");
			}
		});
		sendBtn.setBorderPainted(false);
		sendBtn.setContentAreaFilled(false);
		sendBtn.setForeground(new Color(21, 78, 149));
		p3.add(sendBtn);

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				First.contentsPanel.remove(First.contentsPanel.getComponentCount() - 1);
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

	void clear() {

	}
}