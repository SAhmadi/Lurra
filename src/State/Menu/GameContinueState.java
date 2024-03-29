package State.Menu;

import GameSaves.GameData.GameData;
import GameSaves.PlayerData.PlayerData;
import GameSaves.PlayerData.PlayerDataLoad;
import GameSaves.TilemapData.TilemapDataLoad;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.Level.Level1State;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Spielfortsetzen Menu
 *
 * @author Sirat
 * */
public class GameContinueState extends State
{

    private ImageIcon continueGameButton, continueGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    // Liste aller Speicherstaende
    private JScrollPane scrollPane;
    private JList playerSavesList;
    private String playerSavesFilename;
    private String[] playerSaves;
    private ArrayList<String> playerNameSaves;
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
        super(gamePanel, graphics, stateManager);

        this.continueGameButton = ResourceLoader.continueGameButton;
        this.continueGameButtonPressed = ResourceLoader.continueGameButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Einlesen aller Speicherstaende
        try
        {
            //this.fileCounter = new File("").list().length;

            playerNameSaves = new ArrayList<>();
            Files.walk(Paths.get("")).forEach(filePath -> {
                if (Files.isRegularFile(filePath))
                {
                    playerSavesFilename = filePath.getFileName().toString();    // Dateinamen speichern

                    if (playerSavesFilename.contains("Save.xml"))
                    {
                        playerSavesFilename = playerSavesFilename.substring(0, playerSavesFilename.lastIndexOf(".") - "Save".length());
                        playerNameSaves.add(playerSavesFilename);
                    }
                }
            });

            playerSaves = new String[playerNameSaves.size()];
            for (int i = 0; i < playerNameSaves.size(); i++)
            {
                playerSaves[i] = playerNameSaves.get(i);
            }
        } catch (NullPointerException | IOException ex) { ex.printStackTrace(); }

        // Initialisieren der Buttons
        init();
    }

    /**
     * init         Initialisieren des Menus
     * */
    @Override
    public void init() {
        // Zeichne Himmel
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint gp = new GradientPaint(0, 0, References.NEON_GREEN, 0, References.SCREEN_HEIGHT, References.LIGHT_BLUE);
        g2d.setPaint(gp);

        g2d.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        //graphics.drawImage(menuBackground, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                ResourceLoader.menuIlandBackground,
                (References.SCREEN_WIDTH / 2) - (ResourceLoader.menuIlandBackground.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 2) - (ResourceLoader.menuIlandBackground.getHeight(null) / 2),
                ResourceLoader.menuIlandBackground.getWidth(null), ResourceLoader.menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                ResourceLoader.menuTitleImage,
                (References.SCREEN_WIDTH/2) - (ResourceLoader.menuTitleImage.getWidth(null)/2),
                (References.SCREEN_HEIGHT/4),
                ResourceLoader.menuTitleImage.getWidth(null), ResourceLoader.menuTitleImage.getHeight(null),
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
            {
                Sound.continueButtonSound.play();
                Sound.elevatorSound.stop();
                Sound.elevatorSound.close();
                Sound.gameSound.play();
                Sound.gameSound.continues();
            }

            // Ausgewaehlter Spielstand, lese alle Daten aus der passenden Datei
            selected = playerSaves[playerSavesList.getSelectedIndex()];
            PlayerDataLoad.XMLRead(selected);
            TilemapDataLoad.XMLRead(PlayerData.name);

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
                Sound.backButtonSound.play();

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

        if(playerSaves.length > 0)
        {
            // ScrollPane
            scrollPane.setBounds(
                    References.SCREEN_WIDTH/ 2 - ResourceLoader.menuTitleImage.getWidth() / 2 - continueGameButton.getIconWidth() / 2,
                    References.SCREEN_HEIGHT / 2 - ResourceLoader.maleCharacterButtonActive.getIconHeight() / 2,
                    ResourceLoader.menuTitleImage.getWidth() + continueGameButton.getIconWidth(),
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
    public void render(Graphics2D g) {}

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
