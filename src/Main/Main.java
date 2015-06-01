package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Startklasse der Spiels. Erstellt das Spielfenster und Spielfeldgroesse
 *
 * @author Vanessa, Carola, Amin, Mo, Sirat, Halit
 */
public class Main {

    /**
     * main             Main-Methode. Erfassen der Bildschirmgroesse.
     *
     * @param args      Standard Main-Argumente
     * */
    public static void main(String[] args) {

        // Bildschirmgroesse
        ScreenDimensions.WIDTH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        ScreenDimensions.HEIGHT = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        // Spielfenster
        JFrame gameFrame = new JFrame("Lurra");
        GamePanel gamePanel = new GamePanel(gameFrame);
        gameFrame.setContentPane(gamePanel);
        gameFrame.setBackground(Color.BLACK);

        gameFrame.setResizable(false);
        gameFrame.setUndecorated(true);

        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

}
