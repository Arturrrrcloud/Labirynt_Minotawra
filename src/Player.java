import java.awt.*;
import java.util.ArrayList;

public class Player {

    private int x;
    private int y;
    private int size;
    private int targetX;
    private int targetY;
    private int speed = 3;

    public Player(int startX, int startY, int size) {
        this.x = startX;
        this.y = startY;
        this.targetX = startX;
        this.targetY = startY;
        this.size = size;
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

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}

