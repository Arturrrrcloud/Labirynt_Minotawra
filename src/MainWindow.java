import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MainWindow extends JPanel implements MouseMotionListener {

    private int seconds = 0;

    private int points = 100;

    private ArrayList<Wall> walls = new ArrayList<>();

    private Player player;

    private Rectangle mazeBounds = new Rectangle(0, 0, 780, 680);

    public MainWindow() {

        //window parameters
        setPreferredSize(new Dimension(1024, 768));
        setBackground(Color.WHITE);

        addMouseMotionListener(this);
        setLayout(null);

        // add player
        player = new Player(640, 620, 25);

        // add wales
        //horizontal border
        walls.add(new Wall(0, 0, 80, 30));
        walls.add(new Wall(160, 0, 620, 30));
        walls.add(new Wall(30, 650, 580, 30));
        walls.add(new Wall(680, 650, 100, 30));
        //vertical border
        walls.add(new Wall(0, 30, 30, 650));
        walls.add(new Wall(750, 30, 30, 650));
        //other wales
        walls.add(new Wall(600, 30, 30, 50));
        walls.add(new Wall(600, 80, 70, 30));
        walls.add(new Wall(670, 80, 30, 530));
        walls.add(new Wall(370, 580, 300, 30));
        walls.add(new Wall(30, 250, 50, 30));
        walls.add(new Wall(80, 80, 30, 530));
        walls.add(new Wall(110, 80, 400, 30));
        walls.add(new Wall(110, 580, 200, 30));
        walls.add(new Wall(110, 380, 70, 30));
        walls.add(new Wall(180, 300, 30, 230));
        walls.add(new Wall(210, 500, 420, 30));
        walls.add(new Wall(600, 310, 30, 200));
        walls.add(new Wall(400, 310, 30, 200));
        walls.add(new Wall(160, 190, 510, 30));
        walls.add(new Wall(500, 220, 30, 230));
        walls.add(new Wall(300, 220, 30, 230));

        // add buttons
        JButton Menu = new JButton("Menu");
        Menu.setBounds(820, 300, 150, 60);
        add(Menu);

        JButton Restart = new JButton("Restart");
        Restart.setBounds(820, 200, 150, 60);
        add(Restart);



        //  timer
        Timer timer = new Timer(1000, e -> {
            seconds++;
            repaint();
        });
        timer.start();

        //score calculation
        Timer score = new Timer(1000, e -> {
            if (seconds > 30 && seconds < 60) {
                points--;
            }
            else if (seconds > 60) {
                points = points - 3;
            }
            repaint();
        });
        score.start();
        timer.start();

        // timer for player movement
        Timer moveTimer = new Timer(15, e -> {
            player.move(walls, mazeBounds);
            repaint();
        });
        moveTimer.start();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.setTarget(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //paint wales
        for (Wall wall : walls) {
            wall.draw(g);
        }

        // paint player
        player.draw(g);

        // paint timer
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Time: " + seconds + " s", 830, 50);

        // paint score
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + points, 830, 150);

        // paint level number
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Level x", 830, 430);

    }

}
