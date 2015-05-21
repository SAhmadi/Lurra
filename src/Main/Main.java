package Main;

import javax.swing.*;
import java.awt.*;

/*
* Main - Erstellen des Spielfensters und Inhaltsflaeche setzen
* F�r den Gruppenleiter : bitte den res ordner als recourse folder markieren!!!
* */
public class Main {

  public static void main(String[] args) {
      JFrame gameFrame = new JFrame("Lurra");
      gameFrame.setContentPane(new GamePanel(gameFrame));
      gameFrame.setBackground(Color.BLACK);

      gameFrame.setResizable(false);
      gameFrame.setUndecorated(true);

      LevelSave.XMLSave(args);
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

      // Bildschirm und Inhaltsgröße
      ScreenDimensions.SCREEN_WIDTH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      ScreenDimensions.SCREEN_HEIGHT = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
      ScreenDimensions.WIDTH = (int)ScreenDimensions.SCREEN_WIDTH;
      ScreenDimensions.HEIGHT = (int)(ScreenDimensions.SCREEN_WIDTH * 9) / 16;  // 16 zu 9 Format

      gameFrame.pack();
      //gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
  }

}
