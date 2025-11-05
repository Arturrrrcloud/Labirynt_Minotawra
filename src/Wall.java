import java.awt.*;

public class Wall {
    private Rectangle rect;

    public Wall(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle getBounds() {
        return rect;
    }
}
