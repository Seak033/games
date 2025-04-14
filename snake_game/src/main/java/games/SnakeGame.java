package games;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;

    private static final int gameWidth = 580;
    private static final int gameHeight = 440;

    private static final int x1 = 100;
    private static final int y1 = 60;
    private static int tileSize = 20;
    private static FontMetrics metrics;

    private Image backgroundImage;

    public class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int highscore;

    // Snake (start)
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Food logic
    Tile food;
    Random random;

    // Game logic
    Timer gameLoop;
    int directionX; // Snake speeds X
    int directionY; // Snake speeds Y
    boolean gameOver = false;
    boolean gameStarted = false;

    public SnakeGame(Image backgroundImage) {
        this.backgroundImage = backgroundImage;

        JFrame frame = new JFrame("Snake Game");
        frame.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        frame.setBackground(Color.BLACK);
        this.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        directionX = 1;
        directionY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();

        List<Integer> scores = HighScoreManager.loadHighscore();
        this.highscore = scores.isEmpty() ? 0 : scores.get(0);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Game area
        Graphics2D g2d = (Graphics2D) g;
        // g2d.setColor(Color.WHITE);
        // g2d.drawRect(x1, y1, gameWidth, gameHeight);

        // // Grid lines
        // g2d.setColor(Color.LIGHT_GRAY);
        // for (int i = 0; i < gameWidth / tileSize; i++) {
        // g.drawLine(x1 + i * tileSize, y1, x1 + i * tileSize, y1 + gameHeight);
        // }

        // for (int i = 0; i < gameHeight / tileSize; i++) {
        // g.drawLine(x1, y1 + i * tileSize, x1 + gameWidth, y1 + i * tileSize);
        // }

        // Food
        g2d.setColor(new Color(255, 165, 60));
        g2d.fill3DRect(x1 + food.x * tileSize, y1 + food.y * tileSize, tileSize, tileSize, true);

        // Snake head
        g2d.setColor(Color.GREEN.darker());
        g2d.fill3DRect(x1 + snakeHead.x * tileSize, y1 + snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g2d.fill3DRect(x1 + snakePart.x * tileSize, y1 + snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Highscore
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 15));
        String scoreText = "Score: " + snakeBody.size();
        String highscoreText = "Highscore: " + highscore;
        g2d.drawString(scoreText, x1 + 10, y1 + 20);
        g2d.drawString(highscoreText, x1 + 10, y1 + 40);

        // Game over pop-up screen
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 50));
            String gameOverText = "Game Over";
            metrics = g2d.getFontMetrics();
            int x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(gameOverText)) / 2;
            int y = DEFAULT_SCREEN_HEIGHT / 3;
            g2d.drawString(gameOverText, x, y);

            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            String gameOverScore = "Your score is: " + snakeBody.size();
            metrics = g2d.getFontMetrics();
            x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(gameOverScore)) / 2;
            y = DEFAULT_SCREEN_HEIGHT / 2;
            g2d.drawString(gameOverScore, x, y);

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            String restartText = "Press 'R' to restart";
            metrics = g2d.getFontMetrics();
            x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(restartText)) / 2;
            y = DEFAULT_SCREEN_HEIGHT + (y1 + gameHeight - DEFAULT_SCREEN_HEIGHT - y1);
            g2d.drawString(restartText, x, y);

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            String backMessage = "Press 'H' to go back to Home";
            metrics = g2d.getFontMetrics();
            x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(backMessage)) / 2;
            y = DEFAULT_SCREEN_HEIGHT + (y1 + gameHeight - DEFAULT_SCREEN_HEIGHT - y1) + 100;
            g2d.drawString(backMessage, x, y);

        }

        // Press space bar to start
        if (!gameStarted) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            String startMessage = "Press 'SPACE' to start";
            metrics = g2d.getFontMetrics();
            int x = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(startMessage)) / 2;
            int y = DEFAULT_SCREEN_HEIGHT - (DEFAULT_SCREEN_HEIGHT / 2);
            g2d.drawString(startMessage, x, y);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            String backMessage = "Press 'H' to go back to Home";
            metrics = g2d.getFontMetrics();
            int xPlace = (DEFAULT_SCREEN_WIDTH - metrics.stringWidth(backMessage)) / 2;
            int yPlace = DEFAULT_SCREEN_HEIGHT - (DEFAULT_SCREEN_HEIGHT / 3);
            g2d.drawString(backMessage, xPlace, yPlace);
        }
    }

    // Generates random food on the game area and not colliding with snake
    public void placeFood() {
        boolean foodPlaced = false;
        while (!foodPlaced) {
            food.x = random.nextInt(gameWidth / tileSize);
            food.y = random.nextInt(gameHeight / tileSize);

            foodPlaced = true;
            if (collision(snakeHead, food)) {
                foodPlaced = false;
            } else {
                for (Tile snakePart : snakeBody) {
                    if (collision(snakePart, food)) {
                        foodPlaced = false;
                        break;
                    }
                }
            }
        }

    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // When the snake eats food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // move Snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake head
        snakeHead.x += directionX;
        snakeHead.y += directionY;

        // Game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // Snake eats itself
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= gameWidth || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize >= gameHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            move();
            repaint();
            if (gameOver) {
                gameLoop.stop();

                HighScoreManager.saveHighscore(snakeBody.size());
                List<Integer> scores = HighScoreManager.loadHighscore();
                highscore = scores.isEmpty() ? 0 : scores.get(0);

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameLoop.start();
            gameStarted = true;
            return;
        }

        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_H) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            Home homescreen = new Home();
            homescreen.homePage();
        }

        if (gameStarted) {
            if (e.getKeyCode() == KeyEvent.VK_UP && directionY != 1) {
                directionX = 0;
                directionY = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && directionY != -1) {
                directionX = 0;
                directionY = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && directionX != 1) {
                directionX = -1;
                directionY = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && directionX != -1) {
                directionX = 1;
                directionY = 0;
            }

        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            gameOver = false;
            snakeHead = new Tile(5, 5);
            snakeBody.clear();
            placeFood();
            directionX = 1;
            directionY = 0;
            gameLoop.start();
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_H) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            Home homescreen = new Home();
            homescreen.homePage();
        }

    }

    // NOT NEEDED
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
