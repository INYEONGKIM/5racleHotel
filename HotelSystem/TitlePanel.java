package HotelSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class TitlePanel extends JPanel {

    public TitlePanel() {
       
       setBackground(new Color(14, 76, 151));
       
       setLayout(new BorderLayout(0, 0));
       JLabel title = new JLabel("5racle Hotel", JLabel.CENTER);
       add(title, BorderLayout.CENTER);
       title.setForeground(Color.WHITE);
       title.setFont(new Font("Dialog", Font.BOLD, 25));
    }
   
}