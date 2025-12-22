import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MainWindow extends JPanel implements MouseMotionListener {

    private int seconds = 0;

    private int points = 100;

    private int Level = 1;

    private Timer timeTimer;
    private Timer scoreTimer;
    private Timer moveTimer;

    private boolean gameOver = false;

    private GameOver gameoverpanel;

    private ArrayList<Bat> bats = new ArrayList<>();

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
        player = new Player(640, 620);

        //menu
        MenuPanel menuPanel = new MenuPanel( this);
        menuPanel.setLocation(
                1024 / 2 - 500 / 2,
                768 / 2 - 400 / 2
        );
        add(menuPanel);

        //gameoverpanel
        gameoverpanel = new GameOver(this);
        gameoverpanel.setLocation(
                1024 / 2 - 400 / 2,
                768 / 2 - 250 / 2
        );
        add(gameoverpanel);


        // add buttons
        JButton Menu = new JButton("Menu");
        Menu.setBounds(820, 300, 150, 60);
        Menu.addActionListener(e -> {
            gameoverpanel.setVisible(false);
            menuPanel.setVisible(!menuPanel.isVisible());
        });
        add(Menu);

        JButton Restart = new JButton("Restart");
        Restart.setBounds(820, 200, 150, 60);
        Restart.addActionListener(e -> {
            gameoverpanel.setVisible(false);
            restartLevel();
            resumeGame();
                });
        add(Restart);

        //timer
        timeTimer = new Timer(1000, e -> {
            seconds++;
            repaint();
        });
        timeTimer.start();

        //score calculation
        scoreTimer = new Timer(1000, e -> {
            if (seconds > 30 && seconds < 60) {
                points--;
            }
            else if (seconds > 60) {
                points = points - 3;
            }
            repaint();
        });
        scoreTimer.start();

        // timer for player ans bats movement
        moveTimer = new Timer(15, e -> {
            player.move(walls, mazeBounds);
            for (Bat bat : bats) {
                bat.move();
            }

            checkCollision();

            repaint();
        });
        moveTimer.start();

    }

    public void loadLevel(int level) {
        restartLevel();
        walls.clear();

        if (level == 1){
            Level = level;
            //add walls
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

            //add bats
            bats.add(new Bat(40, 615, 715, 615));



        } else if (level == 2){
            Level = level;

        }else if (level == 3){
            Level = level;
        }
        repaint();
    }

    public void checkCollision() {
        for (Bat bat : bats) {
            if (player.getBounds().intersects(bat.getBounds())) {
                gameOver();
                break;
            }
        }
    }

    public void gameOver() {

        gameOver = true;

        // stop all timers
        timeTimer.stop();
        scoreTimer.stop();
        moveTimer.stop();

        gameoverpanel.setVisible(true);
        gameoverpanel.repaint();
    }

    public void resumeGame() {

        gameOver = false;

        timeTimer.start();
        scoreTimer.start();
        moveTimer.start();
    }


    public void restartLevel() {

        seconds = 0;
        points = 100;
        player.reset();
        for (Bat bat : bats) {
            bat.reset();
        }
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println("Mouse position: x=" + mouseX + ", y=" + mouseY);
        player.setTarget(e.getX(), e.getY());

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println("Mouse dragged: x=" + mouseX + ", y=" + mouseY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //paint level
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
        g.drawString("Level: " + Level, 830, 430);

        //paint bats
        for (Bat bat : bats) {
            bat.draw(g);
        }

    }

}
