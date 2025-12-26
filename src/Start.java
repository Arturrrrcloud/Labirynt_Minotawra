import java.awt.*;

public class Start {

    private Rectangle rect;

    public Start(int x, int y, int width, int height) {
        rect = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLUE);
        g2.fillRect(rect.x, rect.y, rect.width, rect.height);

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth("START");
        int textHeight = fm.getAscent();

        int textX = rect.x + (rect.width - textWidth) / 2;
        int textY = rect.y + (rect.height + textHeight) / 2 - 4;

        g2.drawString("START", textX, textY);
    }

    public Rectangle getBounds() {
        return rect;
    }
}
