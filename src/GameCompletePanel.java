import javax.swing.*;
import java.awt.*;

public class GameCompletePanel extends JPanel {

    public GameCompletePanel() {
        setLayout(new BorderLayout());
        setSize(700, 500);
        setBackground(new Color(0, 0, 0, 220));
        setVisible(false);

        JLabel title = new JLabel("GAME COMPLETE", SwingConstants.CENTER);
        title.setForeground(Color.GREEN);
        title.setFont(new Font("Arial", Font.BOLD, 68));
        add(title, BorderLayout.CENTER);

    }

}


