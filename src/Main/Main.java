package Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Startklasse der Spiels. Erstellt das Spielfenster und Spielfeldgroesse
 *
 * @author Vanessa, Amin, Mo, Sirat, Carola, Halit
 */
public class Main
{
    public  static JFrame gameFrame = new JFrame("Lurra");
    public static GamePanel gamePanel = new GamePanel(gameFrame);
    /**
     * Bildschrimgroesse wird erfasst und Spiel wird gestartet
     * */
    public static void main(String[] args)
    {
        // Bildschirmgroesse
        //References.SCREEN_WIDTH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        //References.SCREEN_HEIGHT = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        // Spielfenster


        gameFrame.setContentPane(gamePanel);
        gameFrame.setBackground(Color.BLACK);

        gameFrame.setResizable(false);
        gameFrame.setUndecorated(false);

        try {
            InOut.writeinfile(800);
            InOut.writeinfile(500);
        } catch (IOException e) {
            e.printStackTrace();
        }


        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

}


