package Main;

import GameSaves.GameData.GameDataSave;
import GameSaves.InventoryData.InventoryDataSave;
import GameSaves.PlayerData.PlayerData;
import GameSaves.PlayerData.PlayerDataSave;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Pause-Menu des Spiels
 *
 * @author Halit, Sirat, Amin, Mo
 * */
public class PauseMenu extends JFrame
{

    // Inhaltsflaeche
    PausePanel panel;

    // Pausefenster
    public static JButton returnBtn;
    private JButton mainMenuBtn;
    private JButton saveBtn;
    private JButton exitBtn;

    private ImageIcon returnButton, returnButtonPressed;
    private ImageIcon mainMenuButton, mainMenuButtonPressed;
    private ImageIcon saveButton, saveButtonPressed;
    private ImageIcon exitButton, exitButtonPressed;

    public static AtomicBoolean paused;

    /**
     * PauseMenu            Konstruktor der Klasse-PauseMenu
     *
     * @param graphics      Graphics Objekt des Hauptfensters
     * @param gamePanel     Hauptfenster
     * @param stateManager  Statemanager des Hauptfensters
     * */
    public PauseMenu(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Initialisieren der Inhaltsflaeche
        this.panel = new PausePanel();
        this.setContentPane(panel);

        paused = new AtomicBoolean(false);

        // Initialisieren der Button-Bilder
        this.returnButton = ResourceLoader.continueGameButton;
        this.returnButtonPressed = ResourceLoader.continueGameButtonPressed;
        this.mainMenuButton = ResourceLoader.mainMenuButton;
        this.mainMenuButtonPressed = ResourceLoader.mainMenuButtonPressed;
        this.saveButton = ResourceLoader.saveButton;
        this.saveButtonPressed = ResourceLoader.saveButtonPressed;
        this.exitButton = ResourceLoader.closeButton;
        this.exitButtonPressed = ResourceLoader.closeButtonPressed;

        // Initialisieren der Buttons
        returnBtn = new JButton(this.returnButton);
        this.mainMenuBtn = new JButton(this.mainMenuButton);
        this.saveBtn = new JButton(this.saveButton);
        this.exitBtn = new JButton(this.exitButton);

        // Anpassen des Fortsetzen-Buttons
        returnBtn.setBounds(
                (References.SCREEN_WIDTH - returnButton.getIconWidth() * 4) / 5,
                References.SCREEN_HEIGHT / 2 - returnButton.getIconHeight() / 2,
                returnButton.getIconWidth(),
                returnButton.getIconHeight()
        );
        returnBtn.setBorderPainted(false);
        returnBtn.setFocusPainted(false);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setOpaque(false);
        returnBtn.setPressedIcon(returnButtonPressed);
        returnBtn.setVisible(true);
        this.panel.add(returnBtn);

        // Anpassen des Hauptmenu-Buttons
        this.mainMenuBtn.setBounds(
                2*((References.SCREEN_WIDTH - mainMenuButton.getIconWidth() * 4) / 5) + mainMenuButton.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - mainMenuButton.getIconHeight() / 2,
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

        // Anpassen des Speichern-Buttons
        this.saveBtn.setBounds(
                2 * ((References.SCREEN_WIDTH - saveButton.getIconWidth() * 4) / 5 + saveButton.getIconWidth()) + ((References.SCREEN_WIDTH - saveButton.getIconWidth() * 4) / 5),
                References.SCREEN_HEIGHT / 2 - saveButton.getIconHeight() / 2,
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

        // Anpassen des Beenden-Buttons
        this.exitBtn.setBounds(
                3 * ((References.SCREEN_WIDTH - exitButton.getIconWidth() * 4) / 5 + exitButton.getIconWidth()) + ((References.SCREEN_WIDTH - exitButton.getIconWidth() * 4) / 5),
                References.SCREEN_HEIGHT / 2 - exitButton.getIconHeight() / 2,
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

        /* ACTIONLISTENER */
        saveBtn.addActionListener(e -> {
            GameDataSave.XMLSave();
            PlayerDataSave.XMLSave(PlayerData.name);
            //TileMap.levelSave(TileMap.tiles, PlayerData.name);
            InventoryDataSave.XMLSave(PlayerData.name);
        });

        exitBtn.addActionListener(e -> System.exit(0));

        //Funktion muss noch geschriebenben werden
        mainMenuBtn.addActionListener(e -> {
            // TODO zurueck zum Menu
        });
    }

}

/**
 * Inhaltsflaeche des Pause-Menu
 *
 * @author Sirat
 * */
class PausePanel extends JComponent
{

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint gp = new GradientPaint(0, 0, References.NEON_GREEN, 0, References.SCREEN_HEIGHT, References.LIGHT_BLUE);
        g2d.setPaint(gp);

        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
    }

}


