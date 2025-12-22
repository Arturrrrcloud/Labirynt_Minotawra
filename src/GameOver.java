import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {

    public GameOver(MainWindow mainwindow) {
        setLayout(new BorderLayout());
        setSize(400, 250);
        setBackground(new Color(0, 0, 0, 220));
        setVisible(false);

        JLabel title = new JLabel("GAME OVER", SwingConstants.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial", Font.BOLD, 48));

        add(title, BorderLayout.CENTER);
    }
}

