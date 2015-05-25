package Main;

import javax.swing.*;
import java.awt.*;

/*
* Main - Erstellen des Spielfensters und Inhaltsflaeche setzen
* */
public class Main {

  public static void main(String[] args) {
      //
      ScreenDimensions.SCREEN_WIDTH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      ScreenDimensions.SCREEN_HEIGHT = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

      // Bildschirm und Inhaltsgroesse
      ScreenDimensions.WIDTH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      ScreenDimensions.HEIGHT = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

      JFrame gameFrame = new JFrame("Lurra");
      gameFrame.setContentPane(new GamePanel(gameFrame));
      gameFrame.setBackground(Color.BLACK);

      gameFrame.setResizable(false);
      gameFrame.setUndecorated(true);

      //LevelSave.XMLSave(args);
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

      gameFrame.pack();
      //gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
  }

}
