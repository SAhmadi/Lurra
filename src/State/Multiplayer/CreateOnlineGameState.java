//package State.Multiplayer;
//
//import GameSaves.GameData.GameData;
//import Main.GamePanel;
//import Main.ResourceLoader;
//import Main.ScreenDimensions;
//import Main.Sound;
//import Network.Client;
//import Network.Server;
//import State.Menu.PlayOnlineMenuState;
//import State.State;
//import State.StateManager;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//
///*
//* MenuState - Spielmenu
//* */
//public class CreateOnlineGameState extends State {
//
//    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
//    protected GamePanel gamePanel;
//    protected Graphics graphics;
//    protected StateManager stateManager;
//
//    // Schriftart
//    private int textFieldFontSize;
//
//    // Resources
//    private BufferedImage menuBackground;
//    private BufferedImage menuIlandBackground;
//    private BufferedImage menuTitleImage;
//
//    private ImageIcon continueGameButton, continueGameButtonPressed;
//    private ImageIcon backButton, backButtonPressed;
//
//    /*
//    * Menu Buttons
//    * */
//    private JTextField nameTextField;
//    private JTextField ipAdressTextField;
//    private JTextField portTextField;
//
//    /* Textfelder */
//    private final int MAXSIZE = 20;
//    private char[] nameInput;
//    private String nameInputAsString;
//    private String ipAdressInput;
//    private int portInput;
//    private boolean validInput;
//
//    private JButton continueGameBtn;
//    private JButton backBtn;
//
//    /*
//    * Konstruktor - Initialisieren
//    * */
//    public CreateOnlineGameState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
//        this.graphics = graphics;
//        this.gamePanel = gamePanel;
//        this.stateManager = stateManager;
//
//        // Schriftart Initialisieren
//        this.textFieldFontSize = ResourceLoader.textFieldFont.getSize();
//
//        // Resource Initialisieren
//        this.menuBackground = ResourceLoader.menuBackground;
//        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
//        this.menuTitleImage = ResourceLoader.menuTitleImage;
//
//        this.continueGameButton = ResourceLoader.continueGameButton;
//        this.continueGameButtonPressed = ResourceLoader.continueGameButtonPressed;
//
//        this.backButton = ResourceLoader.backButton;
//        this.backButtonPressed = ResourceLoader.backButtonPressed;
//
//        // Initialisieren der Buttons
//        init();
//    }
//
//    /*
//    * init - Eigentliches Initialisieren
//    * Hinzufuegen und Positionieren der Buttons
//    * */
//    @Override
//    public void init() {
//        // Zeichne Himmel
//        graphics.drawImage(menuBackground, 0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT, null);
//
//        // Zeichne Insel
//        graphics.drawImage(
//                menuIlandBackground,
//                (ScreenDimensions.WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
//                (ScreenDimensions.HEIGHT / 2) - (menuIlandBackground.getHeight(null) / 2),
//                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
//                null
//        );
//
//        // Zeichne Title
//        graphics.drawImage(
//                menuTitleImage,
//                ScreenDimensions.WIDTH/2 - menuTitleImage.getWidth(null)/2,
//                ScreenDimensions.HEIGHT/4,
//                menuTitleImage.getWidth(), menuTitleImage.getHeight(),
//                null
//        );
//
//        // Initialisieren der Buttons
//        nameTextField = new JTextField("Wie lautet dein Name?");
//        ipAdressTextField = new JTextField("Bitte eine IP-Adresse eingeben");
//        portTextField = new JTextField("Bitte einen Port eingeben");
//
//        continueGameBtn = new JButton(continueGameButton);
//        backBtn = new JButton(backButton);
//
//        /*
//        * Button Listeners
//        * Aendert Sichtbarkeit der Buttons, die im Ober-/Unter-Menu sichbar sein sollen
//        * */
//        nameTextField.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                nameTextField.setText("");
//                nameTextField.setBackground(Color.WHITE);
//                nameTextField.setForeground(Color.BLACK);
//            }
//            @Override
//            public void focusLost(FocusEvent e) {}
//        });
//
//        ipAdressTextField.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                ipAdressTextField.setText("");
//                ipAdressTextField.setBackground(Color.WHITE);
//                ipAdressTextField.setForeground(Color.BLACK);
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {}
//        });
//
//        portTextField.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                portTextField.setText("");
//                portTextField.setBackground(Color.WHITE);
//                portTextField.setForeground(Color.BLACK);
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {}
//        });
//
//        continueGameBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Spiele Sound
//                if(GameData.isSoundOn.equals("On"))
//                    Sound.diamondSound.play();
//
//                /* Namen - Textfeld ueberpruefen */
//                nameInput = nameTextField.getText().toCharArray();
//                for (char letter : nameInput) {
//                    if( (letter >= 65 && letter <= 90) || (letter >= 97 && letter <= 122) ) {
//                        validInput = true;
//                    }
//                    else {
//                        validInput = false;
//                        break;
//                    }
//                }
//                if(validInput) {
//                    nameInputAsString = nameInput.toString();
//                }
//
//                /* IP-Adresse - Textfeld ueberpruefen */
//                // TODO ueberpruefen ob gueltige IP-Adresse eingegeben wurde
//                ipAdressInput = ipAdressTextField.getText();
//
//                /* Port - Textfeld ueberpruefen */
//                portInput = Integer.parseInt(portTextField.getText());
//                //Server.PORT = (portInput > 1023) ? portInput : 8080;
//
//                if(!validInput) {
//                    nameTextField.setBackground(Color.RED);
//                    nameTextField.setForeground(Color.WHITE);
//                    nameTextField.setText("Nur Buchstaben!");
//                }
//                else if(nameTextField.getText().length() > MAXSIZE) {
//                    nameTextField.setBackground(Color.RED);
//                    nameTextField.setForeground(Color.WHITE);
//                    nameTextField.setText("Maximal " + MAXSIZE + " Buchstaben!");
//                }
//                else {
//                    // Eingabe ist gueltig
//                    nameTextField.setBackground(Color.GREEN);
//                    nameTextField.setForeground(Color.WHITE);
//                    ipAdressTextField.setBackground(Color.GREEN);
//                    ipAdressTextField.setForeground(Color.WHITE);
//                    portTextField.setBackground(Color.GREEN);
//                    portTextField.setForeground(Color.WHITE);
//
//
//                    // Erstelle neues Spiel
//                    //Server server = new Server(portInput);
//
//                    //Client client;
//                    //client = new Client(ipAdressInput, Server.port);
//
//
//
//                    gamePanel.remove(nameTextField);
//                    gamePanel.remove(ipAdressTextField);
//                    gamePanel.remove(portTextField);
//                    gamePanel.remove(backBtn);
//                    gamePanel.remove(continueGameBtn);
//
//                    graphics.clearRect(0, 0, ScreenDimensions.WIDTH, ScreenDimensions.HEIGHT);
//
//                    gamePanel.revalidate();
//                    gamePanel.repaint();
//                }
//            }
//        });
//
//        backBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Spiele Sound
//                if (GameData.isSoundOn.equals("On"))
//                    Sound.diamondSound.play();
//
//                gamePanel.remove(nameTextField);
//                gamePanel.remove(ipAdressTextField);
//                gamePanel.remove(portTextField);
//                gamePanel.remove(backBtn);
//
//                gamePanel.revalidate();
//                gamePanel.repaint();
//
//                stateManager.getGameStates().pop();
//                stateManager.setActiveState(new PlayOnlineMenuState(graphics, gamePanel, stateManager), stateManager.PLAYONLINEMENUSTATE);
//            }
//        });
//
//        /*
//        * Hinzufuegen und Positionieren der Buttons
//        * */
//
//        // Kein Layout, um Buttons selbst zu positionieren
//        gamePanel.setLayout(null);
//
//        /*
//        * Anpassen der Textfelder und Buttons
//        * */
//        nameTextField.setBounds(
//                ScreenDimensions.WIDTH / 2 - menuTitleImage.getWidth() / 2,
//                ScreenDimensions.HEIGHT / 2 - backButton.getIconHeight() / 2,
//                menuTitleImage.getWidth(),
//                textFieldFontSize + 20
//        );
//        nameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
//        nameTextField.setHorizontalAlignment(JTextField.CENTER);
//        nameTextField.setBackground(Color.WHITE);
//        nameTextField.setForeground(Color.BLACK);
//        nameTextField.setFont(ResourceLoader.textFieldFont);
//        gamePanel.add(nameTextField);
//
//
//        ipAdressTextField.setBounds(
//                ScreenDimensions.WIDTH / 2 - menuTitleImage.getWidth() / 2,
//                ScreenDimensions.HEIGHT / 2 - backButton.getIconHeight() / 2 + (textFieldFontSize + 20 + 5),
//                menuTitleImage.getWidth(),
//                textFieldFontSize + 20
//        );
//        ipAdressTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
//        ipAdressTextField.setHorizontalAlignment(JTextField.CENTER);
//        ipAdressTextField.setBackground(Color.WHITE);
//        ipAdressTextField.setForeground(Color.BLACK);
//        ipAdressTextField.setFont(ResourceLoader.textFieldFont);
//        gamePanel.add(ipAdressTextField);
//
//
//        portTextField.setBounds(
//                ScreenDimensions.WIDTH / 2 - menuTitleImage.getWidth() / 2,
//                ScreenDimensions.HEIGHT / 2 - backButton.getIconHeight() / 2 + 2*(textFieldFontSize + 20 + 5),
//                menuTitleImage.getWidth(),
//                textFieldFontSize + 20
//        );
//        portTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
//        portTextField.setHorizontalAlignment(JTextField.CENTER);
//        portTextField.setBackground(Color.WHITE);
//        portTextField.setForeground(Color.BLACK);
//        portTextField.setFont(ResourceLoader.textFieldFont);
//        gamePanel.add(portTextField);
//
//        // Buttons
//        continueGameBtn.setBounds(
//                (ScreenDimensions.WIDTH / 2 - continueGameButton.getIconWidth() / 2) - continueGameButton.getIconWidth()/2 - 25,
//                ScreenDimensions.HEIGHT / 2 - continueGameButton.getIconHeight() / 2 + 3 * (textFieldFontSize + 20 + 5),
//                backButton.getIconWidth(),
//                backButton.getIconHeight()
//        );
//        continueGameBtn.setBorderPainted(false);
//        continueGameBtn.setFocusPainted(false);
//        continueGameBtn.setContentAreaFilled(false);
//        continueGameBtn.setOpaque(false);
//        continueGameBtn.setPressedIcon(continueGameButtonPressed);
//        continueGameBtn.setVisible(true);
//        gamePanel.add(continueGameBtn);
//
//        backBtn.setBounds(
//                (ScreenDimensions.WIDTH/2 - backButton.getIconWidth()/2) + backButton.getIconWidth()/2 + 25,
//                ScreenDimensions.HEIGHT / 2 - backButton.getIconHeight() / 2 + 3*(textFieldFontSize + 20 + 5),
//                backButton.getIconWidth(),
//                backButton.getIconHeight()
//        );
//        backBtn.setBorderPainted(false);
//        backBtn.setFocusPainted(false);
//        backBtn.setContentAreaFilled(false);
//        backBtn.setOpaque(false);
//        backBtn.setPressedIcon(backButtonPressed);
//        backBtn.setVisible(true);
//        gamePanel.add(backBtn);
//
//    }
//
//    /*
//    * update
//    * */
//    @Override
//    public void update() {}
//
//    /*
//    * render
//    * */
//    @Override
//    public void render(Graphics g) {}
//
//    /*
//    * EventListeners
//    * */
//    @Override
//    public void keyPressed(KeyEvent e) {}
//    @Override
//    public void keyReleased(KeyEvent e) {}
//
//    @Override
//    public void mouseClicked(MouseEvent e) {}
//    @Override
//    public void mousePressed(MouseEvent e) {}
//    @Override
//    public void mouseReleased(MouseEvent e) {}
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//    @Override
//    public void mouseExited(MouseEvent e) {}
//
//    @Override
//    public void mouseWheelMoved(MouseWheelEvent e) {
//
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//
//    }
//
//}
