package HotelSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class First extends JFrame {

	public static JPanel contentsPanel;
	public static CardLayout card;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					First frame = new First();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private First() {
		setResizable(false);
		setTitle("5racle Hotel");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 950, 630);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);

		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setBounds(0, 0, 944, 100);
		panel.add(titlePanel);

		contentsPanel = new JPanel();
		contentsPanel.setBounds(0, 100, 944, 501);
		panel.add(contentsPanel);
		card = new CardLayout(0, 0);
		contentsPanel.setLayout(card);

		contentsPanel.add("mainPanel", new MainPanel());

//		JButton btnTest = new JButton("test");
//		btnTest.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println(contentsPanel.getComponentCount());
//				card.next(contentsPanel);
//			}
//		});
//		titlePanel.add(btnTest, BorderLayout.SOUTH);
//
//		JButton button = new JButton("temp_main");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				card.show(contentsPanel, "mainPanel");
//			}
//		});
//		titlePanel.add(button, BorderLayout.EAST);
	}

}