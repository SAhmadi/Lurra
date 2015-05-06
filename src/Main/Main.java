package Main;

import javax.swing.*;
import java.awt.*;

/*
* Main Klasse
* */
public class Main {

  public static void main(String[] args) {
      JFrame gameFrame = new JFrame("Lurra");

      gameFrame.setContentPane(new GamePanel(gameFrame));

      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setResizable(false);
      gameFrame.setUndecorated(true);
      gameFrame.pack();
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
  }

}
