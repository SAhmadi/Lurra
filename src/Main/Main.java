package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Startklasse der Spiels. Erstellt das Spielfenster und Spielfeldgroesse
 *
 * @author Vanessa, Amin, Mo, Sirat, Carola, Halit
 */
public class Main
{

    /**
     * Bildschrimgroesse wird erfasst und Spiel wird gestartet
     * */
    public static void main(String[] args)
    {
        // Bildschirmgroesse
        //ScreenDimensions.WIDTH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        //ScreenDimensions.HEIGHT = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        // Spielfenster
        JFrame gameFrame = new JFrame("Lurra");
        GamePanel gamePanel = new GamePanel(gameFrame);
        gameFrame.setContentPane(gamePanel);
        gameFrame.setBackground(Color.BLACK);

        gameFrame.setResizable(false);
        gameFrame.setUndecorated(false);

        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //gameFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

}


