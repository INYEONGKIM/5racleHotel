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
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class IntroductionPanel extends JPanel {
	
	public IntroductionPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
    	
		JPanel p1 = new JPanel(); p1.setBackground(Color.WHITE);
    	p1.setPreferredSize(new Dimension(0, 65));
    	p1.setLayout(new BorderLayout(0, 0));
    	
    	JLabel introLbl = new JLabel("Developed By", JLabel.CENTER);
    	introLbl.setFont(new Font("Dialog", Font.BOLD, 30));
    	introLbl.setForeground(new Color(21, 78, 149));
    	p1.add(introLbl, BorderLayout.CENTER);
    	
    	JPanel p2 = new JPanel() {
    		public void paintComponent(Graphics g) {
    			URL imageURL = getClass().getResource("images/background.jpg");
    			Image img = new ImageIcon(imageURL).getImage();
    			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    		}
    	};
    	p2.setLayout(null);
    	
    	JPanel centerPanel = new JPanel() {
    		public void paintComponent(Graphics g) {
    			URL imageURL = getClass().getResource("images/front_back.jpg");
    			Image img = new ImageIcon(imageURL).getImage();
    			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    		}
    	};
    	centerPanel.setBackground(Color.WHITE);
    	centerPanel.setLayout(null);
    	centerPanel.setBorder(new LineBorder(new Color(195, 213, 233), 2, true));
    	centerPanel.setBounds(180, 10, 580, 350);
    	p2.add(centerPanel);
    	
    	JLabel frontLbl = new JLabel("<HTML>Hwang Gyu-hee&emsp;&emsp;Lee Chung-ik<br>Lee Ju-yeon&emsp;&emsp;&emsp;&nbsp;&nbsp;Yun Sung-ho</HTML>", JLabel.CENTER);
    	frontLbl.setFont(new Font("Dialog", Font.BOLD, 15));
    	frontLbl.setForeground(new Color(71, 203, 251));
    	frontLbl.setBounds(10, 260, 270, 100);
    	centerPanel.add(frontLbl);
    	
    	JLabel backLbl = new JLabel("<HTML>Kim Hee-chan&emsp;&emsp;Kim In-yeong<br>&emsp;&emsp;&emsp;&emsp;&nbsp;Lim Joon-hwi</HTML>", JLabel.CENTER);
    	backLbl.setFont(new Font("Dialog", Font.BOLD, 15));
    	backLbl.setForeground(new Color(44, 55, 87));
    	backLbl.setBounds(300, 260, 270, 100);
    	centerPanel.add(backLbl);
    	
    	JPanel p3 = new JPanel(); p3.setBackground(Color.WHITE);
    	p3.setPreferredSize(new Dimension(0, 65));
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 18));
    	
    	JButton backBtn = new JButton("Back");
    	backBtn.setForeground(new Color(21, 78, 149));
    	backBtn.setBorderPainted(false);
    	backBtn.setContentAreaFilled(false);
    	backBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			First.contentsPanel.remove(First.contentsPanel.getComponentCount()-1);
    			First.card.show(First.contentsPanel, "mainPanel");
    		}
    	});
    	p3.add(backBtn);
    	
    	add(p1, BorderLayout.NORTH);
    	add(p2, BorderLayout.CENTER);
    	add(p3, BorderLayout.SOUTH);
    }

}
