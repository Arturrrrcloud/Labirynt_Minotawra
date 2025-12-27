import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bat {

    //game logic
    private final int Xforreset;
    private final int Yforreset;

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int x;
    private int y;

    private int size = 30;
    private int speed = 2;

    private boolean forward = true;

    //animation
    private static final int FRAME_W = 32;
    private static final int FRAME_H = 32;

    private int frameIndex = 0;
    private int frameCounter = 0;
    private int frameDelay = 6;

    private Image[] walkFrames;

    private enum State {WALK};
    private Bat.State state = Bat.State.WALK;

    public Bat(int startX, int startY, int endX, int endY){
        this.Xforreset = startX;
        this.Yforreset = startY;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.x = startX;
        this.y = startY;

        loadSprites();
    }

    private void loadSprites() {
        try {
            BufferedImage sheet =
                    ImageIO.read(getClass().getResource("/resources/Assets/32x32-bat-sprite.png"));

            // WALK — 3-й ряд (row = 2)
            walkFrames = new Image[4];
            int walkRow = 0;
            for (int i = 0; i < 4; i++) {
                walkFrames[i] = sheet.getSubimage(
                        i * FRAME_W,
                        walkRow * FRAME_H,
                        FRAME_W,
                        FRAME_H
                );
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAnimation() {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            frameIndex++;

            Image[] frames = walkFrames;
            frameIndex %= frames.length;
        }
    }

    public void move() {
        int targetX = forward ? endX : startX;
        int targetY = forward ? endY : startY;

        int dx = targetX - x;
        int dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            // ставимо точно на точку
            x = targetX;
            y = targetY;

            // змінюємо напрямок
            forward = !forward;
            return;
        }

        x += (int) (dx / distance * speed);
        y += (int) (dy / distance * speed);
    }

    public void reset() {
        x = Xforreset;
        y = Yforreset;
        forward = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void draw(Graphics g) {
        Image[] frames = walkFrames;
        Image frame = frames[frameIndex];

        g.drawImage(frame, x, y, size, size, null);
    }

}
