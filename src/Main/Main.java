package Main;

import javax.swing.*;

/*
*
* */
public class Main {

  public static void main(String[] args) {
      JFrame gameFrame = new JFrame("Lurra");
      gameFrame.setContentPane(new GamePanel(gameFrame));
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setResizable(false);
      gameFrame.pack();
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
  }

}
