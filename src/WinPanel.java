import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {

    private JLabel timeLabel;
    private JLabel scoreLabel;

    public WinPanel(MainWindow mainwindow) {
        setLayout(null);
        setSize(400, 250);
        setBackground(new Color(0, 0, 0, 220));
        setVisible(false);

        JLabel title = new JLabel("LEVEL COMPLETE", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(50, 20, 300, 40);
        add(title);

        // time result
        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timeLabel.setBounds(50, 80, 300, 30);
        add(timeLabel);

        // score result
        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreLabel.setBounds(50, 120, 300, 30);
        add(scoreLabel);

        JButton nextLevel = new JButton("Next Level");
        nextLevel.setFont(new Font("Arial", Font.BOLD, 18));
        nextLevel.setBounds(100, 170, 200, 45);
        nextLevel.addActionListener(e -> {
            setVisible(false);
            mainwindow.nextLevel();
        });
        add(nextLevel);

    }

    public void updateStats(int seconds, int score) {
        timeLabel.setText("Time: " + seconds + " s");
        scoreLabel.setText("Score: " + score);
    }
}


