package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* PauseMenu
* */
public class PauseMenu extends JFrame {

    // Pause Fenster
    public static JButton returnButton;
    private JButton saveButton;
    private JButton exitButton;
    public static AtomicBoolean paused;

    public PauseMenu() {
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        this.setContentPane(new JLabel(new ImageIcon(ResourceLoader.menuBackground)));

        paused = new AtomicBoolean(false);

        // Initialisiere Pause Fenster
        returnButton = new JButton("Fortsetzen");
        saveButton = new JButton("Speichern");
        exitButton = new JButton("Spiel beenden");

        returnButton.setBorderPainted(false);
        returnButton.setBackground(Color.BLACK);
        returnButton.setFocusPainted(false);
        returnButton.setForeground(Color.WHITE);

        saveButton.setBorderPainted(false);
        saveButton.setBackground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.setForeground(Color.WHITE);

        exitButton.setBorderPainted(false);
        exitButton.setBackground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.WHITE);

        returnButton.setBounds(
                (ScreenDimensions.WIDTH - 200 * 3) / 4,
                ScreenDimensions.HEIGHT / 2 - 50 / 2,
                200,
                50
        );

        saveButton.setBounds(
                ((ScreenDimensions.WIDTH - 200 * 3) / 2) + 200,
                ScreenDimensions.HEIGHT / 2 - 50 / 2,
                200,
                50
        );

        exitButton.setBounds(
                2 * ((ScreenDimensions.WIDTH - 200 * 3) / 4 + 200) + ((ScreenDimensions.WIDTH - 200 * 3) / 4),
                ScreenDimensions.HEIGHT / 2 - 50 / 2,
                200,
                50
        );

        // Event fuer Exit-Button
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(returnButton);
        this.add(saveButton);
        this.add(exitButton);
    }

}
