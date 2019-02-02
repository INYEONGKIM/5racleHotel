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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AdminLoginPanel extends JPanel {

	private JTextField idFld;
	private JPasswordField pwdFld;

	public AdminLoginPanel() {

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JPanel p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(0, 65));
		p1.setLayout(new BorderLayout(0, 0));

		JLabel loginLbl = new JLabel("Admin Login", JLabel.CENTER);
		loginLbl.setFont(new Font("Dialog", Font.BOLD, 30));
		loginLbl.setForeground(new Color(21, 78, 149));
		p1.add(loginLbl, BorderLayout.CENTER);

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
		centerPanel.setBounds(260, 80, 420, 210);
		p2.add(centerPanel);

		JLabel idLbl = new JLabel("ID:");
		idLbl.setBounds(70, 65, 120, 20);
		centerPanel.add(idLbl);

		JLabel pwdLbl = new JLabel("Password:");
		pwdLbl.setBounds(70, 125, 120, 20);
		centerPanel.add(pwdLbl);

		idFld = new JTextField();
		idFld.setBounds(210, 60, 150, 30);
		centerPanel.add(idFld);

		pwdFld = new JPasswordField();
		pwdFld.setBounds(210, 120, 150, 30);
		centerPanel.add(pwdFld);

		JPanel p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		p3.setPreferredSize(new Dimension(0, 65));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));

		JButton okBtn = new JButton("Sign In");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idFld.getText();
				String pwd = pwdFld.getText();

				if (id.equals("admin") || pwd.equals("admin")) {
					JOptionPane.showMessageDialog(null, "로그인에 성공하셨습니다.");
					First.contentsPanel.remove(First.contentsPanel.getComponentCount() - 1);
					First.contentsPanel.add("adminPanel", new AdminPanel());
					First.card.show(First.contentsPanel, "adminPanel");
				} else {
					JOptionPane.showMessageDialog(null, "돵신은 관리자가 아뉩니다.");
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
				First.contentsPanel.remove(First.contentsPanel.getComponentCount() - 1);
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

}
