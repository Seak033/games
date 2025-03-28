package games;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Highscore extends JPanel implements KeyListener {
    private Image backgroundImage;
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;

    public Highscore(Image backgroundImage) {
        this.backgroundImage = backgroundImage;

        JFrame frame = new JFrame("Snake Game - Highscore");
        frame.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        frame.setBackground(Color.BLACK);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(this);
        frame.setVisible(true);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_H) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            Home homescreen = new Home();
            homescreen.homePage();
        }
    }






    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
