import javax.swing.*;
import java.awt.*;


public class MenuPanel extends JPanel {

    public MenuPanel(MainWindow mainwindow) {

        //parameters
        setLayout(null);
        setSize(500, 400);
        setBackground(new Color(0, 0, 0, 200));
        setVisible(false);

        //title
        JLabel title = new JLabel("Select Level");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(170, 290,200,25);
        add(title);

        JButton Level1 = new JButton("Level 1");
        Level1.setBounds(20,330,100,50);
        Level1.addActionListener(e -> {
            setVisible(false);
            mainwindow.loadLevel(1);
        });
        add(Level1);

        JButton Level2 = new JButton("Level 2");
        Level2.setBounds(140,330,100,50);
        Level2.addActionListener(e -> {
            setVisible(false);
            mainwindow.loadLevel(2);
        });
        add(Level2);

        JButton Level3 = new JButton("Level 3");
        Level3.setBounds(260,330,100,50);
        Level3.addActionListener(e -> {
            setVisible(false);
            mainwindow.loadLevel(3);
        });
        add(Level3);
    }

}
