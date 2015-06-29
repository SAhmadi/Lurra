package State.Menu;

import GameSaves.GameData.GameData;
import Main.*;
import GameSaves.PlayerData.PlayerData;
import GameSaves.PlayerData.PlayerDataLoad;
import State.Level.Level1State;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Spielfortsetzen Menu
 *
 * @author Sirat
 * */
public class GameContinueState extends State
{

    // Inhaltsflaeche, Graphics Objekt und Zustandsmanager
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon continueGameButton, continueGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    // Liste aller Speicherstaende
    private JScrollPane scrollPane;
    private JList playerSavesList;
    private String playerSavesFilename;
    private String[] playerSaves;
    private int fileCounter;
    private String selected;

    // Buttons
    private JButton continueGameBtn;
    private JButton backBtn;

    /**
     * GameContinueState        Konstruktor der GameContinueState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public GameContinueState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.continueGameButton = ResourceLoader.continueGameButton;
        this.continueGameButtonPressed = ResourceLoader.continueGameButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Einlesen aller Speicherstaende
        try
        {
            this.fileCounter = new File("res/xml/playerSaves/").list().length;

            if(this.fileCounter > 0)
            {
                fileCounter = 0;

                // Schaue wie viele Dateien im Ordner sind und setze dementsprechend Array
                playerSaves = new String[new File("res/xml/playerSaves/").list().length];

                // Durchlaufen des Ordners
                Files.walk(Paths.get("res/xml/playerSaves/")).forEach(filePath ->
                {
                    if (Files.isRegularFile(filePath))
                    {
                        playerSavesFilename = filePath.getFileName().toString();    // Dateinamen speichern

                        if(!playerSavesFilename.contains("Inventory"))
                        {
                            playerSavesFilename = playerSavesFilename.substring(0, playerSavesFilename.lastIndexOf("."));
                            playerSaves[fileCounter] = playerSavesFilename; // Dateinamen in Array einfuegen
                            fileCounter++;
                        }
                    }
                });
            }

        } catch (IOException | NullPointerException ex) { System.out.println("Error: " + ex.getMessage()); }

        // Initialisieren der Buttons
        init();
    }

    /**
     * init         Initialisieren des Menus
     * */
    @Override
    public void init() {
        // Zeichne Himmel
        graphics.drawImage(menuBackground, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                menuIlandBackground,
                (References.SCREEN_WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 2) - (menuIlandBackground.getHeight(null) / 2),
                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                menuTitleImage,
                (References.SCREEN_WIDTH/2) - (menuTitleImage.getWidth(null)/2),
                (References.SCREEN_HEIGHT/4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        // Initialisieren der Buttons
        scrollPane = new JScrollPane();
        playerSavesList = new JList(playerSaves);
        continueGameBtn = new JButton(continueGameButton);
        backBtn = new JButton(backButton);

        // BUTTONLISTENERS
        continueGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            // Ausgewaehlter Spielstand, lese alle Daten aus der passenden Datei
            selected = playerSaves[playerSavesList.getSelectedIndex()];
            PlayerDataLoad.XMLRead(selected);


            gamePanel.remove(playerSavesList);
            gamePanel.remove(backBtn);
            gamePanel.remove(scrollPane);
            playerSaves = null;
            gamePanel.remove(continueGameBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            if(PlayerData.currentLevel.equals("1")) // Welches Level soll gestartet werden.
                stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager, true), StateManager.LEVEL1STATE);   // Starte Level 1
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On"))
                Sound.diamondSound.play();

            gamePanel.remove(continueGameBtn);
            gamePanel.remove(playerSavesList);
            playerSaves = null;
            gamePanel.remove(scrollPane);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new PlayLocalMenuState(graphics, gamePanel, stateManager), StateManager.PLAYLOCALMENUSTATE);
        });

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        if(fileCounter > 0)
        {
            // ScrollPane
            scrollPane.setBounds(
                    References.SCREEN_WIDTH/ 2 - menuTitleImage.getWidth() / 2 - continueGameButton.getIconWidth() / 2,
                    References.SCREEN_HEIGHT / 2 - ResourceLoader.maleCharacterButtonActive.getIconHeight() / 2,
                    menuTitleImage.getWidth() + continueGameButton.getIconWidth(),
                    ResourceLoader.maleCharacterButtonActive.getIconHeight()
            );
            scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            scrollPane.getHorizontalScrollBar().setVisible(false);
            scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));

            // Spielerdaten-Liste
            playerSavesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            playerSavesList.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
            DefaultListCellRenderer listRenderer = (DefaultListCellRenderer) playerSavesList.getCellRenderer();
            listRenderer.setHorizontalAlignment(JLabel.CENTER);
            playerSavesList.setFixedCellWidth(scrollPane.getWidth());
            playerSavesList.setFixedCellHeight(32);
            playerSavesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            playerSavesList.setVisibleRowCount(0);

            playerSavesList.setBackground(Color.WHITE);
            playerSavesList.setForeground(Color.BLACK);
            playerSavesList.setFont(ResourceLoader.textFieldFont);
            playerSavesList.setSelectionBackground(Color.GREEN);
            playerSavesList.setSelectionForeground(Color.WHITE);
            playerSavesList.setFocusable(false);

            // Hinzufuegen der Liste im ScrollPane
            scrollPane.setViewportView(playerSavesList);
            scrollPane.setVisible(true);
            gamePanel.add(scrollPane);
        }


        // Spiel-Fortsezten Button
        continueGameBtn.setBounds(
                References.SCREEN_WIDTH/2 - continueGameButton.getIconWidth() - continueGameButton.getIconWidth()/4,
                References.SCREEN_HEIGHT/2 + continueGameButton.getIconHeight() + continueGameButton.getIconHeight() + continueGameButton.getIconHeight()/2,
                continueGameButton.getIconWidth(),
                continueGameButton.getIconHeight()
        );
        continueGameBtn.setBorderPainted(false);
        continueGameBtn.setFocusPainted(false);
        continueGameBtn.setContentAreaFilled(false);
        continueGameBtn.setOpaque(false);
        continueGameBtn.setPressedIcon(continueGameButtonPressed);
        continueGameBtn.setVisible(true);
        gamePanel.add(continueGameBtn);

        // Zurueck Button
        backBtn.setBounds(
                References.SCREEN_WIDTH/2 + backButton.getIconWidth()/4,
                References.SCREEN_HEIGHT/2 + continueGameButton.getIconHeight() + continueGameButton.getIconHeight() + continueGameButton.getIconHeight()/2,
                backButton.getIconWidth(),
                backButton.getIconHeight()
        );
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setOpaque(false);
        backBtn.setPressedIcon(backButtonPressed);
        backBtn.setVisible(true);
        gamePanel.add(backBtn);
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics g) {}

    // EVENTS
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}
