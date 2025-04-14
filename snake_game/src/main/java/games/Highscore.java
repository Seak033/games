package games;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

public class Highscore extends JPanel implements KeyListener {
    private Image backgroundImage;
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;

    List<Integer> topScores;

    public Highscore(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.topScores = HighScoreManager.loadHighscore();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Top 10 Highscore", 260, 125);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        List<Integer> scores = HighScoreManager.loadHighscore();
        for (int i = 0; i < scores.size(); i++) {
            String entry = (i + 1) + ". " + scores.get(i);
            g.drawString(entry, 350, 170 + i * 30);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_H) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            Home homescreen = new Home();
            homescreen.homePage();
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            HighScoreManager.resetHighscores();
            topScores = HighScoreManager.loadHighscore();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
