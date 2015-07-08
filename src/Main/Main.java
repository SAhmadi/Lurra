package Main;

import GameSaves.GameData.GameData;

import javax.swing.*;
import java.awt.*;

/**
 * Startklasse der Spiels. Erstellt das Spielfenster und Spielfeldgroesse
 *
 * @author Sirat, Amin, Mo
 */
public class Main
{
    /**
     * Bildschrimgroesse wird erfasst und Spiel wird gestartet
     * */
    public static void main(String[] args)
    {
        // Bildschirmgroesse
        //References.SCREEN_WIDTH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        //References.SCREEN_HEIGHT = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        // Spielfenster

        JFrame gameFrame = new JFrame(References.TITLE);
        GamePanel gamePanel = new GamePanel(gameFrame);

        if(GameData.isSoundOn.equals("On"))
        {
            Sound.elevatorSound.play();
            Sound.elevatorSound.continues();
        }

        gameFrame.setContentPane(gamePanel);
        gameFrame.setBackground(Color.BLACK);

        gameFrame.setResizable(false);
        gameFrame.setUndecorated(true);

        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

}


