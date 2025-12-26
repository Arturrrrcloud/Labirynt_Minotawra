import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MainWindow extends JPanel implements MouseMotionListener {

    private int seconds = 0;

    private int points = 100;

    private int CurrentLevel = 0;

    private Timer timeTimer;
    private Timer scoreTimer;
    private Timer moveTimer;

    private boolean gameOver = false;

    private GameOver gameoverpanel;

    private WinPanel gamewinpanel;

    private ArrayList<Bat> bats = new ArrayList<>();

    private ArrayList<Wall> walls = new ArrayList<>();

    private Player player;

    private Start start;
    private Finish finish;

    private Rectangle mazeBounds = new Rectangle(0, 0, 800, 750);

    public MainWindow() {

        //window parameters
        setPreferredSize(new Dimension(1024, 768));
        setBackground(Color.WHITE);

        addMouseMotionListener(this);
        setLayout(null);

        //menu
        MenuPanel menuPanel = new MenuPanel( this);
        menuPanel.setLocation(
                1024 / 2 - 500 / 2,
                768 / 2 - 400 / 2
        );
        add(menuPanel);

        //gameoverpanel
        gameoverpanel = new GameOver();
        gameoverpanel.setLocation(
                1024 / 2 - 400 / 2,
                768 / 2 - 250 / 2
        );
        add(gameoverpanel);

        //gamewinpanel
        gamewinpanel = new WinPanel(this);
        gamewinpanel.setLocation(
                1024 / 2 - 400 / 2,
                768 / 2 - 250 / 2
        );
        add(gamewinpanel);


        // add buttons
        JButton Menu = new JButton("Menu");
        Menu.setBounds(820, 300, 150, 60);
        Menu.addActionListener(e -> {
            gameoverpanel.setVisible(false);
            gamewinpanel.setVisible(false);
            menuPanel.setVisible(!menuPanel.isVisible());
        });
        add(Menu);

        JButton Restart = new JButton("Restart");
        Restart.setBounds(820, 200, 150, 60);
        Restart.addActionListener(e -> {
            gameoverpanel.setVisible(false);
            gamewinpanel.setVisible(false);
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
            if (player != null) {
                player.move(walls, mazeBounds);
            }
            for (Bat bat : bats) {
                bat.move();
            }

            checkCollision();

            repaint();
        });
        moveTimer.start();

    }

    public void loadLevel(int level) {

        CurrentLevel = level;
        restartLevel();
        walls.clear();
        bats.clear();

        if (level == 1){
            // add walls
            walls.add(new Wall(0, 0, 800, 30));
            walls.add(new Wall(0, 720, 800, 30));
            walls.add(new Wall(0, 30, 30, 70));
            walls.add(new Wall(0, 180, 30, 540));
            walls.add(new Wall(770, 30, 30, 550));
            walls.add(new Wall(770, 660, 30, 60));
            walls.add(new Wall(140, 30, 30, 580));
            walls.add(new Wall(280, 140, 30, 580));
            walls.add(new Wall(420, 30, 30, 250));
            walls.add(new Wall(420, 400, 30, 320));
            walls.add(new Wall(450, 400, 100, 30));
            walls.add(new Wall(550, 200, 120, 30));
            walls.add(new Wall(670, 30, 30, 550));

            //add bats
            bats.add(new Bat(85, 650, 85, 150));
            bats.add(new Bat(210, 200, 210, 500));
            bats.add(new Bat(350, 340, 500, 340));
            bats.add(new Bat(720, 50, 720, 650));

            //add player
            player = new Player(5, 140);

            //add start and finish
            start = new Start(0, 100, 30, 80);
            finish = new Finish(770, 580, 30, 80);

        } else if (level == 2){
            // add walls
            // horizontal borders
            walls.add(new Wall(0, 0, 80, 30));
            walls.add(new Wall(160, 0, 640, 30));
            walls.add(new Wall(0, 720, 630, 30));
            walls.add(new Wall(710, 720, 90, 30));
            // vertical borders
            walls.add(new Wall(0, 30, 30, 690));
            walls.add(new Wall(770, 30, 30, 690));
            // other walls
            walls.add(new Wall(620, 30, 30, 60));
            walls.add(new Wall(620, 90, 80, 30));
            walls.add(new Wall(700, 90, 30, 590));
            walls.add(new Wall(380, 650, 320, 30));
            walls.add(new Wall(30, 280, 50, 30));
            walls.add(new Wall(80, 90, 30, 590));
            walls.add(new Wall(110, 90, 420, 30));
            walls.add(new Wall(110, 650, 220, 30));
            walls.add(new Wall(110, 420, 70, 30));
            walls.add(new Wall(180, 350, 30, 250));
            walls.add(new Wall(210, 570, 440, 30));
            walls.add(new Wall(620, 350, 30, 250));
            walls.add(new Wall(420, 350, 30, 250));
            walls.add(new Wall(160, 190, 540, 30));
            walls.add(new Wall(520, 220, 30, 260));
            walls.add(new Wall(320, 220, 30, 260));
            // add bats
            bats.add(new Bat(40, 685, 740, 685));
            bats.add(new Bat(375, 530, 375, 240));
            bats.add(new Bat(50, 45, 585, 45));
            // add player
            player = new Player(665, 720);
            // add start and finish
            start = new Start(630, 720, 80, 30);
            finish = new Finish(80, 0, 80, 30);

        }else if (level == 3){


        }

        resumeGame();
        repaint();
    }

    public void checkCollision() {

        for (Bat bat : bats) {
            if (player.getBounds().intersects(bat.getBounds())) {
                gameOver();
                break;
            } else if (player.getBounds().intersects(finish.getBounds())) {
                gameWin();
                break;
            }
        }
    }

    public void StopGame(){

        // stop all timers
        timeTimer.stop();
        scoreTimer.stop();
        moveTimer.stop();

    }

    public void gameOver() {

        gameOver = true;

        StopGame();
        gameoverpanel.setVisible(true);
    }

    private void gameWin() {

        gameOver = true;

        StopGame();
        gamewinpanel.updateStats(seconds, points);
        gamewinpanel.setVisible(true);
    }

    public void nextLevel() {
        loadLevel(CurrentLevel + 1);
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
        if (player != null) {
            player.reset();
        }
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
        if (player != null) {
            player.setTarget(e.getX(), e.getY());
        }

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

        //paint start and finish
        if (start != null) {
            start.draw(g);
        }
        if (finish != null) {
            finish.draw(g);
        }

        // paint player
        if (player != null) {
            player.draw(g);
        }

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
        g.drawString("Level: " + CurrentLevel, 830, 430);

        //paint bats
        for (Bat bat : bats) {
            bat.draw(g);
        }

    }

}
