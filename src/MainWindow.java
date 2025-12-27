import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MainWindow extends JPanel implements MouseMotionListener {

    private Image backgroundImage;

    private int seconds = 0;

    private int points = 100;

    private int CurrentLevel = 1;

    private Timer timeTimer;
    private Timer scoreTimer;
    private Timer moveTimer;

    private boolean gameOver = false;

    private GameOver gameoverpanel;
    private WinPanel gamewinpanel;
    private GameCompletePanel gamecompletepanel;

    private ArrayList<Bat> bats = new ArrayList<>();

    private ArrayList<Wall> walls = new ArrayList<>();

    private Player player;

    private Start start;
    private Finish finish;

    private Rectangle mazeBounds = new Rectangle(0, 0, 800, 750);

    public MainWindow() {

        //window parameters
        setPreferredSize(new Dimension(1024, 768));
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/resources/Assets/background.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Не вдалося завантажити фон");
        }

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

        //gamecompletepanel
        gamecompletepanel = new GameCompletePanel();
        gamecompletepanel.setLocation(
                1024 / 2 - 700 / 2,
                768 / 2 - 500 / 2
        );
        add(gamecompletepanel);


        // add buttons
        JButton Menu = new JButton("Menu");
        Menu.setBounds(820, 300, 150, 60);
        Menu.addActionListener(e -> {
            gameoverpanel.setVisible(false);
            gamewinpanel.setVisible(false);
            gamecompletepanel.setVisible(false);
            menuPanel.setVisible(!menuPanel.isVisible());
        });
        add(Menu);

        JButton Restart = new JButton("Restart");
        Restart.setBounds(820, 200, 150, 60);
        Restart.addActionListener(e -> {
            gameoverpanel.setVisible(false);
            gamewinpanel.setVisible(false);
            gamecompletepanel.setVisible(false);
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
            if (points <= 0){
                gameOver();
            }
            repaint();
        });
        scoreTimer.start();

        // timer for player ans bats movement
        moveTimer = new Timer(15, e -> {
            if (player != null) {
                player.move(walls, mazeBounds);
                player.updateAnimation();
            }
            for (Bat bat : bats) {
                bat.move();
                bat.updateAnimation();
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
            player = new Player(5, 140, 25);

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
            walls.add(new Wall(110, 90, 400, 30));
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
            player = new Player(665, 720, 25);
            // add start and finish
            start = new Start(630, 720, 80, 30);
            finish = new Finish(80, 0, 80, 30);

        }else if (level == 3){
            // add walls
            // horizontal borders
            walls.add(new Wall(0, 0, 250, 30));
            walls.add(new Wall(330, 0, 470, 30));
            walls.add(new Wall(0, 720, 630, 30));
            walls.add(new Wall(710, 720, 90, 30));
            // vertical borders
            walls.add(new Wall(0, 30, 30, 690));
            walls.add(new Wall(770, 30, 30, 690));
            //other walls
            walls.add(new Wall(220, 30, 30, 160));
            walls.add(new Wall(100, 80, 140, 30));
            walls.add(new Wall(100, 110, 30, 160));
            walls.add(new Wall(100, 320, 30, 340));
            walls.add(new Wall(130, 630, 580, 30));
            walls.add(new Wall(330, 30, 30, 50));
            walls.add(new Wall(330, 80, 380, 30));
            walls.add(new Wall(680, 110, 30, 520));
            walls.add(new Wall(190, 160, 410, 30));
            walls.add(new Wall(190, 250, 30, 170));
            walls.add(new Wall(190, 470, 30, 110));
            walls.add(new Wall(220, 550, 380, 30));
            walls.add(new Wall(600, 160, 30, 420));
            walls.add(new Wall(130, 390, 150, 30));
            walls.add(new Wall(420, 190, 30, 200));
            walls.add(new Wall(280, 320, 30, 150));
            walls.add(new Wall(310, 440, 220, 30));
            walls.add(new Wall(500, 260, 30, 180));
            walls.add(new Wall(220, 250, 130, 30));
            // add bats
            bats.add(new Bat(140, 120, 140, 360));
            bats.add(new Bat(45, 30, 45, 690));
            bats.add(new Bat(230, 285, 390, 285));
            //add player
            player = new Player(280, 10, 25);
            //add start and finish
            start = new Start(250, 0, 80, 30);
            finish = new Finish(630, 720, 80, 30);


        }

        resumeGame();
        repaint();
    }

    public void checkCollision() {

        for (Bat bat : bats) {
            if (player.getBounds().intersects(bat.getBounds())) {
                gameOver();
                break;

            } else if (player.getBounds().intersects(finish.getBounds()) && CurrentLevel != 3) {
                gameWin();
                break;

            } else if (player.getBounds().intersects(finish.getBounds()) && CurrentLevel == 3) {
                gameComplete();
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

    private void gameComplete() {

        gameOver = true;

        StopGame();
        gamecompletepanel.setVisible(true);
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

        if (player != null) {
            player.setTarget(e.getX(), e.getY());
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

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
        g.setColor(Color.WHITE);
        g.drawString("Time: " + seconds + " s", 830, 50);

        // paint score
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + points, 830, 150);

        // paint level number
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("Level: " + CurrentLevel, 830, 430);

        //paint bats
        for (Bat bat : bats) {
            bat.draw(g);
        }

    }

}
