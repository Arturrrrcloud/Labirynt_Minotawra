import java.awt.*;

public class Bat {

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

    public Bat(int startX, int startY, int endX, int endY){
        this.Xforreset = startX;
        this.Yforreset = startY;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.x = startX;
        this.y = startY;
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
        g.setColor(Color.RED);
        g.fillOval(x, y, size, size);
    }

}
