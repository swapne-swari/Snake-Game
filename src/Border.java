
import javax.swing.*;
import java.awt.*;

public class Border extends JPanel {
    private final int BORDER_SIZE = 10;
    private final int B_WIDTH;
    private final int B_HEIGHT;

    public Border(int width, int height) {
        this.B_WIDTH = width;
        this.B_HEIGHT = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawRect(0, 0, BORDER_SIZE, B_HEIGHT); // left
        g.drawRect(0, 0, B_WIDTH, BORDER_SIZE); // top
        g.drawRect(B_WIDTH - BORDER_SIZE, 0, BORDER_SIZE, B_HEIGHT); // right
        g.drawRect(0, B_HEIGHT - BORDER_SIZE, B_WIDTH, BORDER_SIZE); // bottom
    }
}
