package games;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Test_game extends JPanel implements ActionListener, KeyListener {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 700;
    private static final int gameWidth = 300;
    private static final int gameHeight = 600;
    private static JFrame frame;

    private static final int tileSize = 30;
    private static final int x1 = 60;
    private static final int y1 = 30;

    private static Timer gameLoop;
    private int directionX;
    private int directionY;

    boolean leftCollision, rightCollision, bottomCollision;

    public void gameFrame() {
        frame = new JFrame("Welcome to my world of TETRIS");
        frame.setVisible(true);
        frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Test_game());
        frame.addKeyListener(this);
        frame.setFocusable(true);

        Figures.randomFigure();

        directionX = 0;
        directionY = 1;
        gameLoop = new Timer(500, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;

        // Gameplay screen
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4f));
        g2d.drawRect(x1, y1, gameWidth, gameHeight);

        // Next figure screen
        int x = (x1 + gameWidth) + 40;
        int y = (y1 + gameHeight) - 150;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4f));
        g2d.drawRect(x, y, gameWidth / 2, gameHeight / 4);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g2d.drawString("NEXT", x + 40, y + -10);

        // Grid lines
        // g2d.setColor(Color.WHITE);
        // for (int i = 0; i < gameWidth / tileSize; i++) {
        // g2d.drawLine(x1 + i * tileSize, y1, x1 + i * tileSize, y1 + gameHeight);
        // }
        // for (int i = 0; i < gameHeight / tileSize; i++) {
        // g2d.drawLine(x1, y1 + i * tileSize, x1 + gameWidth, y1 + i * tileSize);
        // }

        int[][] shape = Figures.getCurrentFigure();
        Figures.Tile shapePosition = Figures.getShapePosition();
        Color figureColor = Figures.getFigureColor();

        // Figure color
        g2d.fill3DRect(x1, y1, WIDTH, HEIGHT, true);
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int xPos = x1 + (shapePosition.x + j) * tileSize;
                    int yPos = y1 + (shapePosition.y + i) * tileSize;
                    g2d.setColor(figureColor);
                    g2d.fill3DRect(xPos, yPos, tileSize, tileSize, true);
                    repaint();
                }
            }
        }

    }

    public void checkCollision() {

        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
    }

    public void move() {
        Figures.Tile shapePosition = Figures.getShapePosition();
        if (shapePosition.x > gameWidth) {

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            Figures.Tile shapePosition = Figures.getShapePosition();
            shapePosition.x += 1;
            repaint();
        } else if (key == KeyEvent.VK_LEFT) {
            Figures.Tile shapePosition = Figures.getShapePosition();
            shapePosition.x += -1;
            repaint();

        } else if (key == KeyEvent.VK_UP) {
            // Make the shape rotate

        } else if (key == KeyEvent.VK_DOWN) {
            // Make the shape move faster down
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveVertically();
        move();
        repaint();
    }

    private void moveVertically() {
        Figures.Tile shapePosition = Figures.getShapePosition();
        shapePosition.y += directionY;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}