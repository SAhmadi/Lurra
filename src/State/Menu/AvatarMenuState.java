package State.Menu;

import GameSaves.GameData.GameData;
import GameSaves.GameData.GameDataSave;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
* MenuState - Spielmenu
* */
public class AvatarMenuState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon maleCharacterButtonUnactive, maleCharacterButtonActive;
    private ImageIcon femaleCharacterButtonUnactive, femaleCharacterButtonActive;
    private ImageIcon backButton, backButtonPressed;

    /*
    * Menu Buttons
    * */
    private JButton maleCharacterBtn;
    private JButton femaleCharacterBtn;
    private JButton backBtn;

    /*
    * Konstruktor - Initialisieren
    * */
    public AvatarMenuState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.maleCharacterButtonUnactive = ResourceLoader.maleCharacterButtonUnactive;
        this.maleCharacterButtonActive = ResourceLoader.maleCharacterButtonActive;

        this.femaleCharacterButtonUnactive = ResourceLoader.femaleCharacterButtonUnactive;
        this.femaleCharacterButtonActive = ResourceLoader.femaleCharacterButtonActive;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Initialisieren der Buttons
        this.init();
    }

    /*
    * init - Eigentliches Initialisieren
    * Hinzufuegen und Positionieren der Buttons
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
                (References.SCREEN_WIDTH / 2) - (menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );


        // Initialisieren der Buttons
        maleCharacterBtn = new JButton();
        femaleCharacterBtn = new JButton();
        backBtn = new JButton(backButton);

        /*
        * Button Listeners
        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
        * */
        maleCharacterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                if(GameData.gender.equals("Male")) {
                    maleCharacterBtn.setIcon(maleCharacterButtonUnactive);
                    femaleCharacterBtn.setIcon(femaleCharacterButtonActive);
                    GameData.gender = "Female";
                    GameDataSave.XMLSave();
                }
                else if(GameData.gender.equals("Female")) {
                    maleCharacterBtn.setIcon(maleCharacterButtonActive);
                    femaleCharacterBtn.setIcon(femaleCharacterButtonUnactive);
                    GameData.gender = "Male";
                    GameDataSave.XMLSave();
                }
            }
        });

        femaleCharacterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.diamondSound.play();

                if(GameData.gender.equals("Female")) {
                    femaleCharacterBtn.setIcon(femaleCharacterButtonUnactive);
                    maleCharacterBtn.setIcon(maleCharacterButtonActive);
                    GameData.gender = "Male";
                    GameDataSave.XMLSave();
                }
                else if(GameData.gender.equals("Male")) {
                    femaleCharacterBtn.setIcon(femaleCharacterButtonActive);
                    maleCharacterBtn.setIcon(maleCharacterButtonUnactive);
                    GameData.gender = "Female";
                    GameDataSave.XMLSave();
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if (GameData.isSoundOn.equals("On"))
                    Sound.backButtonSound.play();

                gamePanel.remove(maleCharacterBtn);
                gamePanel.remove(femaleCharacterBtn);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                // Pushe StartMenu -> Starte SettingsMenuState
                stateManager.getGameStates().pop();
                stateManager.setActiveState(new SettingsMenuState(graphics, gamePanel, stateManager), -2);
            }
        });

        /*
        * Hinzufuegen und Positionieren der Buttons
        * */

        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);

        // Male-Character Button
        // In den Grenzen des MenuTitleImage positionieren
        maleCharacterBtn.setBounds(
                References.SCREEN_WIDTH / 2 - menuTitleImage.getWidth() / 2,
                References.SCREEN_HEIGHT / 2 - maleCharacterButtonActive.getIconHeight() / 2,
                maleCharacterButtonActive.getIconWidth(),
                maleCharacterButtonActive.getIconHeight()
        );
        maleCharacterBtn.setBorderPainted(false);
        maleCharacterBtn.setFocusPainted(false);
        maleCharacterBtn.setContentAreaFilled(true);
        maleCharacterBtn.setOpaque(true);

        if(GameData.gender.equals("Male")) {
            maleCharacterBtn.setIcon(maleCharacterButtonActive);
        }
        else {
            maleCharacterBtn.setIcon(maleCharacterButtonUnactive);
        }
        maleCharacterBtn.setVisible(true);
        gamePanel.add(maleCharacterBtn);

        // Female-Character Button
        femaleCharacterBtn.setBounds(
                References.SCREEN_WIDTH/ 2 + menuTitleImage.getWidth() / 2 - femaleCharacterButtonActive.getIconWidth(),
                References.SCREEN_HEIGHT / 2 - femaleCharacterButtonActive.getIconHeight() / 2,
                femaleCharacterButtonActive.getIconWidth(),
                femaleCharacterButtonActive.getIconHeight()
        );
        femaleCharacterBtn.setBorderPainted(false);
        femaleCharacterBtn.setFocusPainted(false);
        femaleCharacterBtn.setContentAreaFilled(false);
        femaleCharacterBtn.setOpaque(true);

        if(GameData.gender.equals("Female")) {
            femaleCharacterBtn.setIcon(femaleCharacterButtonActive);
        }
        else {
            femaleCharacterBtn.setIcon(femaleCharacterButtonUnactive);
        }
        femaleCharacterBtn.setVisible(true);
        gamePanel.add(femaleCharacterBtn);

        // Beenden Button
        backBtn.setBounds(
                References.SCREEN_WIDTH/2 - backButton.getIconWidth()/2,
                References.SCREEN_HEIGHT/2 + maleCharacterButtonActive.getIconHeight() - backButton.getIconHeight()/2,
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
