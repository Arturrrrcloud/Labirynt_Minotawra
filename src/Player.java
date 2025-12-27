import java.awt.*;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player {

    //game logic
    private final int Xforreset;
    private final int Yforreset;
    private int x;
    private int y;
    private int size;
    private int targetX;
    private int targetY;
    private int speed = 3;

    //animation
    private static final int FRAME_W = 16;
    private static final int FRAME_H = 16;

    private int frameIndex = 0;
    private int frameCounter = 0;
    private int frameDelay = 6;

    private Image[] idleFrames;
    private Image[] walkFrames;

    private enum State { IDLE, WALK }
    private State state = State.IDLE;


    public Player(int startX, int startY, int size) {
        this.Xforreset = startX;
        this.Yforreset = startY;
        this.size = size;
        this.x = startX;
        this.y = startY;
        this.targetX = startX;
        this.targetY = startY;

        loadSprites();
    }

    public void setTarget(int mouseX, int mouseY) {
        this.targetX = mouseX - size / 2;
        this.targetY = mouseY - size / 2;
    }

    public void move(ArrayList<Wall> walls, Rectangle mazeBounds) {
        int dx = targetX - x;
        int dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < 1) return;

        int newX = x + (int) (dx / distance * speed);
        int newY = y + (int) (dy / distance * speed);
        Rectangle newRect = new Rectangle(newX, newY, size, size);

        boolean collision = false;
        for (Wall wall : walls) {
            if (newRect.intersects(wall.getBounds())) {
                collision = true;
                break;
            }
        }

        if (!collision && mazeBounds.contains(newRect)) {
            x = newX;
            y = newY;
        }
    }

    public void updateAnimation() {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            frameIndex++;

            Image[] frames = (state == State.WALK) ? walkFrames : idleFrames;
            frameIndex %= frames.length;
        }
    }

    private void loadSprites() {
        try {
            BufferedImage sheet =
                    ImageIO.read(getClass().getResource("/resources/Assets/Player16x16.png"));

            // WALK — 3-й ряд (row = 2)
            walkFrames = new Image[4];
            int walkRow = 2;
            for (int i = 0; i < 4; i++) {
                walkFrames[i] = sheet.getSubimage(
                        i * FRAME_W,
                        walkRow * FRAME_H,
                        FRAME_W,
                        FRAME_H
                );
            }

            // IDLE — 4-й ряд (row = 3)
            idleFrames = new Image[4];
            int idleRow = 3;
            for (int i = 0; i < 4; i++) {
                idleFrames[i] = sheet.getSubimage(
                        i * FRAME_W,
                        idleRow * FRAME_H,
                        FRAME_W,
                        FRAME_H
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        x = Xforreset;
        y = Yforreset;
        targetX = Xforreset;
        targetY = Yforreset;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void draw(Graphics g) {

        Image[] frames = (state == State.WALK) ? walkFrames : idleFrames;
        Image frame = frames[frameIndex];

        g.drawImage(frame, x, y, size, size, null);
    }

}

