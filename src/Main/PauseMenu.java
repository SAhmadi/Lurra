package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* PauseMenu
* */
public class PauseMenu extends JFrame {

    PausePanel panel;

    // Pause Fenster
    public static JButton returnBtn;
    private JButton mainMenuBtn;
    private JButton saveBtn;
    private JButton exitBtn;
    public static AtomicBoolean paused;

    private ImageIcon returnButton, returnButtonPressed;
    private ImageIcon mainMenuButton, mainMenuButtonPressed;
    private ImageIcon saveButton, saveButtonPressed;
    private ImageIcon exitButton, exitButtonPressed;


    public PauseMenu() {
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.panel = new PausePanel();
        this.setContentPane(panel);

        paused = new AtomicBoolean(false);

        this.returnButton = ResourceLoader.continueGameButton;
        this.returnButtonPressed = ResourceLoader.continueGameButtonPressed;
        this.mainMenuButton = ResourceLoader.mainMenuButton;
        this.mainMenuButtonPressed = ResourceLoader.mainMenuButtonPressed;
        this.saveButton = ResourceLoader.saveButton;
        this.saveButtonPressed = ResourceLoader.saveButtonPressed;
        this.exitButton = ResourceLoader.closeButton;
        this.exitButtonPressed = ResourceLoader.closeButtonPressed;

        // Initialisiere Pause Fenster
        this.returnBtn = new JButton(this.returnButton);
        this.mainMenuBtn = new JButton(this.mainMenuButton);
        this.saveBtn = new JButton(this.saveButton);
        this.exitBtn = new JButton(this.exitButton);

        // Fortsetzen
        this.returnBtn.setBounds(
                (ScreenDimensions.WIDTH - returnButton.getIconWidth() * 4) / 5,
                ScreenDimensions.HEIGHT / 2 - returnButton.getIconHeight() / 2,
                returnButton.getIconWidth(),
                returnButton.getIconHeight()
        );
        this.returnBtn.setBorderPainted(false);
        this.returnBtn.setFocusPainted(false);
        this.returnBtn.setContentAreaFilled(false);
        this.returnBtn.setOpaque(false);
        this.returnBtn.setPressedIcon(returnButtonPressed);
        this.returnBtn.setVisible(true);
        this.panel.add(this.returnBtn);

        // Hauptmenu
        this.mainMenuBtn.setBounds(
                2*((ScreenDimensions.WIDTH - mainMenuButton.getIconWidth() * 4) / 5) + mainMenuButton.getIconWidth(),
                ScreenDimensions.HEIGHT / 2 - mainMenuButton.getIconHeight() / 2,
                mainMenuButton.getIconWidth(),
                mainMenuButton.getIconHeight()
        );
        this.mainMenuBtn.setBorderPainted(false);
        this.mainMenuBtn.setFocusPainted(false);
        this.mainMenuBtn.setContentAreaFilled(false);
        this.mainMenuBtn.setOpaque(false);
        this.mainMenuBtn.setPressedIcon(mainMenuButtonPressed);
        this.mainMenuBtn.setVisible(true);
        this.panel.add(this.mainMenuBtn);

        // Speichern
        this.saveBtn.setBounds(
                2 * ((ScreenDimensions.WIDTH - saveButton.getIconWidth() * 4) / 5 + saveButton.getIconWidth()) + ((ScreenDimensions.WIDTH - saveButton.getIconWidth() * 4) / 5),
                ScreenDimensions.HEIGHT / 2 - saveButton.getIconHeight() / 2,
                saveButton.getIconWidth(),
                saveButton.getIconHeight()
        );
        this.saveBtn.setBorderPainted(false);
        this.saveBtn.setFocusPainted(false);
        this.saveBtn.setContentAreaFilled(false);
        this.saveBtn.setOpaque(false);
        this.saveBtn.setPressedIcon(saveButtonPressed);
        this.saveBtn.setVisible(true);
        this.panel.add(this.saveBtn);

        // Beenden Button
        this.exitBtn.setBounds(
                3 * ((ScreenDimensions.WIDTH - exitButton.getIconWidth() * 4) / 5 + exitButton.getIconWidth()) + ((ScreenDimensions.WIDTH - exitButton.getIconWidth() * 4) / 5),
                ScreenDimensions.HEIGHT / 2 - exitButton.getIconHeight() / 2,
                exitButton.getIconWidth(),
                exitButton.getIconHeight()
        );
        this.exitBtn.setBorderPainted(false);
        this.exitBtn.setFocusPainted(false);
        this.exitBtn.setContentAreaFilled(false);
        this.exitBtn.setOpaque(false);
        this.exitBtn.setPressedIcon(exitButtonPressed);
        this.exitBtn.setVisible(true);
        this.panel.add(this.exitBtn);

        // Event fuer Exit-Button
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}

/**
 *
 *
 *
 * */
class PausePanel extends JComponent {

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(ResourceLoader.menuBackground, 0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, null);
    }

}


