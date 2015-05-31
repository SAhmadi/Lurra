package State.Menu;

import Main.*;
import GameData.GameData;
import PlayerData.PlayerData;
import PlayerData.PlayerDataSave;
import State.Level.Level1State;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
* MenuState - Spielmenu
* */
public class NewGameState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Schriftart
    private int textFieldFontSize;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon startGameButton, startGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    /*
    * Menu Buttons
    * */
    private JTextField nameTextField;
    private final int MAXSIZE = 20;
    private char[] letters;
    private boolean validInput;

    private JButton startGameBtn;
    private JButton backBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public NewGameState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Schriftart Initialisieren
        this.textFieldFontSize = ResourceLoader.textFieldFont.getSize();

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.startGameButton = ResourceLoader.startGameButton;
        this.startGameButtonPressed = ResourceLoader.startGameButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Initialisieren der Buttons
        init();
    }

    /*
    * init - Eigentliches Initialisieren
    * Hinzufuegen und Positionieren der Buttons
    * */
    @Override
    public void init() {
        // Zeichne Himmel
        graphics.drawImage(menuBackground, 0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, null);

        // Zeichne Insel
        graphics.drawImage(
                menuIlandBackground,
                (ScreenDimensions.WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
                (ScreenDimensions.HEIGHT / 2) - (menuIlandBackground.getHeight(null) / 2),
                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                menuTitleImage,
                ScreenDimensions.WIDTH/2 - menuTitleImage.getWidth(null)/2,
                ScreenDimensions.HEIGHT/4,
                menuTitleImage.getWidth(), menuTitleImage.getHeight(),
                null
        );

        // Initialisieren der Buttons
        nameTextField = new JTextField("Wie lautet dein Name?");
        startGameBtn = new JButton(startGameButton);
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        nameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                nameTextField.setText("");
                nameTextField.setBackground(Color.WHITE);
                nameTextField.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });

        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                // Pruefe ob Eingabe nur Buchstaben enthaelt mittels ASCII
                letters = nameTextField.getText().toCharArray();
                for (char letter : letters) {
                    if( (letter >= 65 && letter <= 90) || (letter >= 97 && letter <= 122) ) {
                        validInput = true;
                    }
                    else {
                        validInput = false;
                        break;
                    }
                }


                if(!validInput) {
                    nameTextField.setBackground(Color.RED);
                    nameTextField.setForeground(Color.WHITE);
                    nameTextField.setText("Nur Buchstaben!");
                }
                else if(nameTextField.getText().length() > MAXSIZE) {
                    nameTextField.setBackground(Color.RED);
                    nameTextField.setForeground(Color.WHITE);
                    nameTextField.setText("Maximal " + MAXSIZE + " Buchstaben!");
                }
                else {
                    nameTextField.setBackground(Color.GREEN);
                    nameTextField.setForeground(Color.WHITE);
                    try {
                        File saveFile = new File("res/xml/playerSaves/" + nameTextField.getText() + ".xml");
                        File levelFile = new File("res/xml/playerLevelSaves/" + nameTextField.getText() + ".txt");

                        if(!saveFile.getParentFile().exists())
                            saveFile.getParentFile().mkdir();

                        if(!levelFile.getParentFile().exists())
                            levelFile.getParentFile().mkdir();

                        if(!saveFile.exists() && !levelFile.exists()) {
                            saveFile.createNewFile();
                            levelFile.createNewFile();

                            // Speicher Namen und Chartacter-Auswahl
                            PlayerData.name = nameTextField.getText();
                            PlayerData.gender = GameData.gender;
                            PlayerData.currentLevel = "1";
                            PlayerDataSave.XMLSave(PlayerData.name);

                            gamePanel.remove(nameTextField);
                            gamePanel.remove(backBtn);
                            gamePanel.remove(startGameBtn);

                            graphics.clearRect(0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);

                            gamePanel.revalidate();
                            gamePanel.repaint();

                            // Pushe Level1State -> Starte Level 1
                            stateManager.getGameStates().pop();
                            stateManager.setActiveState(new Level1State(graphics, gamePanel, stateManager ,false), 1);
                        }
                        else {
                            nameTextField.setBackground(Color.RED);
                            nameTextField.setForeground(Color.WHITE);
                            nameTextField.setText(nameTextField.getText() + " existiert bereits!");
                        }
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if (GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                gamePanel.remove(startGameBtn);
                gamePanel.remove(nameTextField);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new PlayLocalMenuState(graphics, gamePanel, stateManager), -5);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Lokal-Spielen Button
        nameTextField.setBounds(
                ScreenDimensions.WIDTH / 2 - menuTitleImage.getWidth() / 2,
                ScreenDimensions.HEIGHT / 2 - startGameButton.getIconHeight() / 2,
                menuTitleImage.getWidth(), textFieldFontSize + 20
        );
        nameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setBackground(Color.WHITE);
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setFont(ResourceLoader.textFieldFont);

        gamePanel.add(nameTextField);

        // Online-Spielen Button
        startGameBtn.setBounds(
                ScreenDimensions.WIDTH/2 - startGameButton.getIconWidth()/2,
                ScreenDimensions.HEIGHT/2 + startGameButton.getIconHeight(),
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

        // Beenden Button
        backBtn.setBounds(
                ScreenDimensions.WIDTH/2 - backButton.getIconWidth()/2,
                ScreenDimensions.HEIGHT/2 + startGameButton.getIconHeight() + backButton.getIconHeight() + backButton.getIconHeight()/2,
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

    /*
    * update
    * */
    @Override
    public void update() {}

    /*
    * render
    * */
    @Override
    public void render(Graphics g) {}

    /*
    * EventListeners
    * */
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
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
