
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener {
    private final int DELAY = 150;
    private final int SIZE = 10;
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int BORDER_SIZE = 10;
    private final int INITIAL_X = 100;
    private final int INITIAL_Y = 100;

    private int[] x = new int[100];
    private int[] y = new int[100];
    private int bodyParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;

    public Snake() {
        initBoard();
    }

    private void initBoard() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setFocusable(true);
        addKeyListener(new TAdapter());

        placeApple();

        for (int i = 0; i < bodyParts; i++) {
            x[i] = INITIAL_X - i * SIZE;
            y[i] = INITIAL_Y;
        }

        timer = new Timer(DELAY, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, SIZE, SIZE);

            g.setColor(Color.green);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.fillRect(x[i], y[i], SIZE, SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], SIZE, SIZE);
                }
            }
            drawBorder(g);
        } else {
            gameOver(g);
        }
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(0, 0, BORDER_SIZE, B_HEIGHT); // left
        g.drawRect(0, 0, B_WIDTH, BORDER_SIZE); // top
        g.drawRect(B_WIDTH - BORDER_SIZE, 0, BORDER_SIZE, B_HEIGHT); // right
        g.drawRect(0, B_HEIGHT - BORDER_SIZE, B_WIDTH, BORDER_SIZE); // bottom
    }

    private void placeApple() {
        int r = (int) (Math.random() * (B_WIDTH / SIZE));
        appleX = r * SIZE;

        r = (int) (Math.random() * (B_HEIGHT / SIZE));
        appleY = r * SIZE;
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - SIZE;
                break;
            case 'D':
                y[0] = y[0] + SIZE;
                break;
            case 'L':
                x[0] = x[0] - SIZE;
                break;
            case 'R':
                x[0] = x[0] + SIZE;
                break;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            placeApple();
        }
    }

    private void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] >= B_WIDTH || y[0] < 0 || y[0] >= B_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString("Apples Eaten: " + applesEaten, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 + 20);
    }

    public void start() {
        running = true;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && direction != 'R') {
                direction = 'L';
            }

            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && direction != 'L') {
                direction = 'R';
            }

            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && direction != 'D') {
                direction = 'U';
            }

            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && direction != 'U') {
                direction = 'D';
            }
        }
    }
}
