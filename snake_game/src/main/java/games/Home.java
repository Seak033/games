package games;

import java.awt.*;
import java.awt.event.*;
import java.io.*;


import javax.imageio.ImageIO;
import javax.swing.*;


public class Home extends JPanel implements ActionListener {
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;
    private static JButton startButton;
    private static JButton highScore;
    private static JFrame frame;
    private static ImagePanel backgroundImage;
    

     public void homePage() {
        frame = new JFrame();
        frame.setTitle("Home - Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        frame.setContentPane(layeredPane);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Background image
        backgroundImage = new ImagePanel("src\\main\\resources\\images\\snake-background.jpg");
        layeredPane.add(backgroundImage, Integer.valueOf(0));
        backgroundImage.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // Welcome text
        JLabel label = new JLabel("Welcome to my Snake Game!");
        label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        label.setForeground(Color.decode("#000000"));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);

        centerPanel.add(Box.createVerticalStrut(50));

        // Start game button
        startButton = new CustomButton("Start Game", "src\\main\\resources\\images\\snake-home.png");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setFocusable(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);  
        startButton.addActionListener(e -> {
            frame.setVisible(false); 
            openSnakeGame(backgroundImage.getImage());
        });
        centerPanel.add(startButton);

        centerPanel.add(Box.createVerticalStrut(25));

        // Highscore button
        highScore = new CustomButton("Highscore", "src\\main\\resources\\images\\high-score.png");
        highScore.setFont(new Font("Arial", Font.BOLD, 20));
        highScore.setFocusable(false);
        highScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScore.addActionListener(e -> {
            frame.setVisible(false);
            openHighScore(backgroundImage.getImage());
        });
        centerPanel.add(highScore);

        centerPanel.setBounds(0, DEFAULT_SCREEN_HEIGHT / 3, frame.getWidth(), frame.getHeight());
        layeredPane.add(centerPanel, Integer.valueOf(1));

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                backgroundImage.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });

        frame.setVisible(true);
    }



    public static class CustomButton extends JButton {
        private Image image;

        public CustomButton(String text, String imagePath) {
            super(text);
            try {
                image = ImageIO.read(new File(imagePath));
                if (image == null) {
                    System.err.println("Image not found at path: " + imagePath);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }

            setIcon(new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
            setHorizontalAlignment(SwingConstants.LEFT);
            setHorizontalTextPosition(SwingConstants.RIGHT);
            setIconTextGap(10);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(getHeight(), getHeight(), Image.SCALE_AREA_AVERAGING);
                g.drawImage(scaledImage, 10, 0, this);
            }
        }
    }

    public static class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String imagePath) {
            try {
                image = ImageIO.read(new File(imagePath));
                if (image == null) {
                    System.err.println("Error: Image not found at path: " + imagePath);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        public Image getImage() {
            return image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void openSnakeGame(Image backgroundImage) {
        JFrame gameFrame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame(backgroundImage);
        gameFrame.add(game);
        gameFrame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
        game.requestFocus();
    }
    
    private void openHighScore(Image backgroundImage) {
        JFrame highScoreFrame = new JFrame("Snake Game - HighScores");
        Highscore highscore = new Highscore(backgroundImage);
        highScoreFrame.add(highScore);
        highScoreFrame.revalidate();
        highScoreFrame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            frame.setVisible(false);
            openSnakeGame(backgroundImage.getImage());
        }
    }
}