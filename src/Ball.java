import javax.swing.*;
import java.awt.*;

public class Ball extends JPanel {
    private final int SIZE = 10;
    private int x;
    private int y;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
