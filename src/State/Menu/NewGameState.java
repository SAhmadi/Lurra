package State.Menu;

import GameSaves.GameData.GameData;
import GameSaves.PlayerData.PlayerData;
import GameSaves.PlayerData.PlayerDataSave;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.Level.Level1State;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Neues Spiel Menu
 *
 * @author Sirat
 * */
public class NewGameState extends State
{

    // Maximale Namenslaenge
    private final int MAXSIZE = 20;

    // Resources
    private ImageIcon startGameButton, startGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    // Eingabefeld
    private JTextField nameTextField;
    private char[] letters;
    private boolean validInput;

    // Buttons
    private JButton startGameBtn;
    private JButton backBtn;

    /**
     * NewGameState     Konstruktor der NewGameState-Klasse
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Spielinhaltsflaeche
     * @param stateManager  Zustandsmanager
     * */
    public NewGameState(Graphics graphics, GamePanel gamePanel, StateManager stateManager)
    {
        super(gamePanel, graphics, stateManager);

        this.startGameButton = ResourceLoader.startGameButton;
        this.startGameButtonPressed = ResourceLoader.startGameButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Initialisieren der Buttons
        init();
    }

    /**
     * init         Initialisieren des Menus
     * */
    @Override
    public void init()
    {
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
                ResourceLoader.menuIlandBackground.getWidth(null),
                ResourceLoader.menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                ResourceLoader.menuTitleImage,
                References.SCREEN_WIDTH/2 - ResourceLoader.menuTitleImage.getWidth(null)/2,
                References.SCREEN_HEIGHT/4,
                ResourceLoader.menuTitleImage.getWidth(),
                ResourceLoader.menuTitleImage.getHeight(),
                null
        );

        // Initialisieren der Buttons
        nameTextField = new JTextField("Wie lautet dein Name?");
        startGameBtn = new JButton(startGameButton);
        backBtn = new JButton(backButton);

        // ACTIONSLISTENERS
        nameTextField.addFocusListener(
                new FocusListener()
                {
                    @Override
                    public void focusGained(FocusEvent e)
                    {
                        nameTextField.setText("");
                        nameTextField.setBackground(Color.WHITE);
                        nameTextField.setForeground(Color.BLACK);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {}
                }
        );

        startGameBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On"))
            {
                Sound.elevatorSound.stop();
                Sound.elevatorSound.close();
                Sound.gameSound.play();
                Sound.gameSound.continues();
            }

            // Pruefe ob Eingabe nur Buchstaben enthaelt mittels ASCII
            letters = nameTextField.getText().toCharArray();

            for (char letter : letters)
            {
                if ((letter >= 65 && letter <= 90) || (letter >= 97 && letter <= 122))
                    validInput = true;
                else
                {
                    validInput = false;
                    break;
                }
            }

            if (!validInput)
            {
                nameTextField.setBackground(Color.RED);
                nameTextField.setForeground(Color.WHITE);
                nameTextField.setText("Nur Buchstaben!");
            }
            else if (nameTextField.getText().length() > MAXSIZE)
            {
                nameTextField.setBackground(Color.RED);
                nameTextField.setForeground(Color.WHITE);
                nameTextField.setText("Maximal " + MAXSIZE + " Buchstaben!");
            }
            else if (nameTextField.getText().contains("Save") || nameTextField.getText().contains("Inventory") || nameTextField.getText().contains("Level"))
            {
                nameTextField.setBackground(Color.RED);
                nameTextField.setForeground(Color.WHITE);
                nameTextField.setText("Save, Inventory oder Level im Namen nicht erlaubt!");
            }
            else
            {
                nameTextField.setBackground(Color.GREEN);
                nameTextField.setForeground(Color.WHITE);

                try
                {
                    try
                    {
                        File saveFile = new File(nameTextField.getText() + "Save.xml");
                        File levelFile = new File(nameTextField.getText() + "Level.xml");

//                        if (!saveFile.getParentFile().exists())
//                            if (saveFile.getParentFile().mkdir())
//                                System.out.println("Directory erstellt!");
//                            else
//                                System.out.println("Directory fehlgeschlafen!");
//
//                        if (!levelFile.getParentFile().exists())
//                            if (levelFile.getParentFile().mkdir())
//                                System.out.println("Directory erstellt!");
//                            else
//                                System.out.println("Directory fehlgeschlafen!");

                        if (!saveFile.exists() && !levelFile.exists())
                        {
                            if (saveFile.createNewFile()) System.out.println("Speicherdatei erfolgreich angelegt!");
                            else System.out.println("Fehler Speicherdatei nicht angelegt!");

                            if (levelFile.createNewFile()) System.out.println("Leveldatei erfolgreich angelegt");
                            else System.out.println("Fehler Leveldatei nicht angelegt!");

                            // Speicher Namen und Chartacter-Auswahl
                            PlayerData.name = nameTextField.getText();
                            PlayerData.gender = GameData.gender;
                            PlayerData.currentLevel = "1";
                            PlayerDataSave.XMLSave(PlayerData.name);

                            gamePanel.remove(nameTextField);
                            gamePanel.remove(backBtn);
                            gamePanel.remove(startGameBtn);

                            graphics.clearRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
                            gamePanel.revalidate();
                            gamePanel.repaint();

                            stateManager.getGameStates().pop();
                            stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager, false), StateManager.LEVEL1STATE);
                        }
                        else
                        {
                            nameTextField.setBackground(Color.RED);
                            nameTextField.setForeground(Color.WHITE);
                            nameTextField.setText(nameTextField.getText() + " existiert bereits!");
                        }
                    }
                    catch (NullPointerException ex) { ex.printStackTrace(); }

                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });

        backBtn.addActionListener(e ->
        {
            // Spiele Sound
            if (GameData.isSoundOn.equals("On")) Sound.backButtonSound.play();

            gamePanel.remove(startGameBtn);
            gamePanel.remove(nameTextField);
            gamePanel.remove(backBtn);

            gamePanel.revalidate();
            gamePanel.repaint();

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new PlayLocalMenuState(graphics, gamePanel, stateManager), StateManager.PLAYLOCALMENUSTATE);
        });

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Namen Eingabefeld
        nameTextField.setBounds(
                References.SCREEN_WIDTH/ 2 - ResourceLoader.menuTitleImage.getWidth() / 2,
                References.SCREEN_HEIGHT / 2 - startGameButton.getIconHeight() / 2,
                ResourceLoader.menuTitleImage.getWidth(),
                ResourceLoader.textFieldFont.getSize() + 20
        );
        nameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setBackground(Color.WHITE);
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setFont(ResourceLoader.textFieldFont);

        gamePanel.add(nameTextField);

        // Spielstarten
        startGameBtn.setBounds(
                References.SCREEN_WIDTH/2 - startGameButton.getIconWidth()/2,
                References.SCREEN_HEIGHT/2 + startGameButton.getIconHeight(),
                startGameButton.getIconWidth(),
                startGameButton.getIconHeight()
        );
        startGameBtn.setBorderPainted(false);
        startGameBtn.setFocusPainted(false);
        startGameBtn.setContentAreaFilled(false);
        startGameBtn.setOpaque(false);
        startGameBtn.setPressedIcon(startGameButtonPressed);
        startGameBtn.setVisible(true);
        gamePanel.add(startGameBtn);

        // Zurueck Button
        backBtn.setBounds(
                References.SCREEN_WIDTH/2 - backButton.getIconWidth()/2,
                References.SCREEN_HEIGHT/2 + startGameButton.getIconHeight() + backButton.getIconHeight() + backButton.getIconHeight()/2,
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
