package State.Multiplayer;

import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import State.Menu.PlayOnlineMenuState;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;


public class JoinOnlineGameState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Schriftart
    private int textFieldFontSize;

    // Resources
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    private ImageIcon continueGameButton, continueGameButtonPressed;
    private ImageIcon backButton, backButtonPressed;

    private JTextField nameTextField;
    private JTextField ipAdressTextField;
    private JTextField portTextField;


    private final int MAXSIZE = 20;
    private char[] nameInput;
    private String ipAdressInput;
    private int portInput;
    private boolean validInput;

    private JButton continueGameBtn;
    private JButton backBtn;


    public JoinOnlineGameState(Graphics graphics, GamePanel gamePanel, StateManager stateManager) {
        this.graphics = graphics;
        this.gamePanel = gamePanel;
        this.stateManager = stateManager;

        // Schriftart Initialisieren
        this.textFieldFontSize = ResourceLoader.textFieldFont.getSize();

        // Resource Initialisieren
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.continueGameButton = ResourceLoader.continueGameButton;
        this.continueGameButtonPressed = ResourceLoader.continueGameButtonPressed;

        this.backButton = ResourceLoader.backButton;
        this.backButtonPressed = ResourceLoader.backButtonPressed;

        // Initialisieren der Buttons
        init();
    }

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
                menuIlandBackground,
                (References.SCREEN_WIDTH / 2) - (menuIlandBackground.getWidth(null) / 2),
                (References.SCREEN_HEIGHT / 2) - (menuIlandBackground.getHeight(null) / 2),
                menuIlandBackground.getWidth(null), menuIlandBackground.getHeight(null),
                null
        );

        // Zeichne Title
        graphics.drawImage(
                menuTitleImage,
                References.SCREEN_WIDTH/2 - menuTitleImage.getWidth(null)/2,
                References.SCREEN_HEIGHT/4,
                menuTitleImage.getWidth(), menuTitleImage.getHeight(),
                null
        );

        // Initialisieren der Buttons
        nameTextField = new JTextField("Wie lautet dein Name?");
        ipAdressTextField = new JTextField("Bitte eine IP-Adresse eingeben");
        portTextField = new JTextField("Bitte einen Port eingeben");

        continueGameBtn = new JButton(continueGameButton);
        backBtn = new JButton(backButton);

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

        ipAdressTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ipAdressTextField.setText("");
                ipAdressTextField.setBackground(Color.WHITE);
                ipAdressTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });

        portTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                portTextField.setText("");
                portTextField.setBackground(Color.WHITE);
                portTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });

        continueGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if(GameData.isSoundOn.equals("On"))
                    Sound.continueButtonSound.play();

                String nameAsString = nameTextField.getText();
                nameInput = nameTextField.getText().toCharArray();
                for (char letter : nameInput) {
                    if( (letter >= 65 && letter <= 90) || (letter >= 97 && letter <= 122) ) {
                        validInput = true;
                    }
                    else {
                        validInput = false;
                        break;
                    }
                }

                // TODO ueberpruefen ob gueltige IP-Adresse eingegeben wurde
                ipAdressInput = ipAdressTextField.getText();

                portInput = Integer.parseInt(portTextField.getText());
                //Server.PORT = (portInput > 1023) ? portInput : 8080;

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
                    boolean isPortListening = false;
                    Socket checkSocket = null;
                    try {
                        checkSocket = new Socket(ipAdressInput, portInput);
                        isPortListening = true;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        isPortListening = false;
                    }
                    finally {
                        try {
                            checkSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if(isPortListening) {
                        // Eingabe ist gueltig
                        nameTextField.setBackground(Color.GREEN);
                        nameTextField.setForeground(Color.WHITE);
                        ipAdressTextField.setBackground(Color.GREEN);
                        ipAdressTextField.setForeground(Color.WHITE);
                        portTextField.setBackground(Color.GREEN);
                        portTextField.setForeground(Color.WHITE);

                        // Initialisiere globale Variablen
                        GamePanel.USER_NAME = nameInput.toString();
                        GamePanel.IP_ADDRESS = ipAdressInput;
                        GamePanel.PORT = portInput;

                        gamePanel.remove(nameTextField);
                        gamePanel.remove(ipAdressTextField);
                        gamePanel.remove(portTextField);
                        gamePanel.remove(backBtn);
                        gamePanel.remove(continueGameBtn);

                        graphics.clearRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

                        gamePanel.revalidate();
                        gamePanel.repaint();

                        stateManager.getGameStates().pop();
                        stateManager.setActiveState(new LobbyState(graphics, gamePanel, stateManager, nameAsString, false), stateManager.LOBBY_STATE);
                    }
                    else {
                        ipAdressTextField.setBackground(Color.RED);
                        ipAdressTextField.setForeground(Color.WHITE);
                        portTextField.setBackground(Color.RED);
                        portTextField.setForeground(Color.WHITE);

                        ipAdressTextField.setText("Verbindung fehlgeschlagen!");
                        portTextField.setText("Verbindung fehlgeschlagen!");
                    }

                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spiele Sound
                if (GameData.isSoundOn.equals("On"))
                    Sound.backButtonSound.play();

                gamePanel.remove(nameTextField);
                gamePanel.remove(ipAdressTextField);
                gamePanel.remove(portTextField);
                gamePanel.remove(continueGameBtn);
                gamePanel.remove(backBtn);

                gamePanel.revalidate();
                gamePanel.repaint();

                stateManager.getGameStates().pop();
                stateManager.setActiveState(new PlayOnlineMenuState(graphics, gamePanel, stateManager), stateManager.PLAYONLINEMENUSTATE);
            }
        });


        // Kein Layout, um Buttons selbst zu positionieren
        gamePanel.setLayout(null);


        nameTextField.setBounds(
                References.SCREEN_WIDTH / 2 - menuTitleImage.getWidth() / 2,
                References.SCREEN_HEIGHT / 2 - backButton.getIconHeight() / 2,
                menuTitleImage.getWidth(),
                textFieldFontSize + 20
        );
        nameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setBackground(Color.WHITE);
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setFont(ResourceLoader.textFieldFont);
        gamePanel.add(nameTextField);


        ipAdressTextField.setBounds(
                References.SCREEN_WIDTH / 2 - menuTitleImage.getWidth() / 2,
                References.SCREEN_HEIGHT / 2 - backButton.getIconHeight() / 2 + (textFieldFontSize + 20 + 5),
                menuTitleImage.getWidth(),
                textFieldFontSize + 20
        );
        ipAdressTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        ipAdressTextField.setHorizontalAlignment(JTextField.CENTER);
        ipAdressTextField.setBackground(Color.WHITE);
        ipAdressTextField.setForeground(Color.BLACK);
        ipAdressTextField.setFont(ResourceLoader.textFieldFont);
        gamePanel.add(ipAdressTextField);


        portTextField.setBounds(
                References.SCREEN_WIDTH / 2 - menuTitleImage.getWidth() / 2,
                References.SCREEN_HEIGHT / 2 - backButton.getIconHeight() / 2 + 2*(textFieldFontSize + 20 + 5),
                menuTitleImage.getWidth(),
                textFieldFontSize + 20
        );
        portTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        portTextField.setHorizontalAlignment(JTextField.CENTER);
        portTextField.setBackground(Color.WHITE);
        portTextField.setForeground(Color.BLACK);
        portTextField.setFont(ResourceLoader.textFieldFont);
        gamePanel.add(portTextField);

        // Buttons
        continueGameBtn.setBounds(
                (References.SCREEN_WIDTH / 2 - continueGameButton.getIconWidth() / 2) - continueGameButton.getIconWidth()/2 - 25,
                References.SCREEN_HEIGHT / 2 - continueGameButton.getIconHeight() / 2 + 3 * (textFieldFontSize + 20 + 5),
                backButton.getIconWidth(),
                backButton.getIconHeight()
        );
        continueGameBtn.setBorderPainted(false);
        continueGameBtn.setFocusPainted(false);
        continueGameBtn.setContentAreaFilled(false);
        continueGameBtn.setOpaque(false);
        continueGameBtn.setPressedIcon(continueGameButtonPressed);
        continueGameBtn.setVisible(true);
        gamePanel.add(continueGameBtn);

        backBtn.setBounds(
                (References.SCREEN_WIDTH/2 - backButton.getIconWidth()/2) + backButton.getIconWidth()/2 + 25,
                References.SCREEN_HEIGHT / 2 - backButton.getIconHeight() / 2 + 3*(textFieldFontSize + 20 + 5),
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
