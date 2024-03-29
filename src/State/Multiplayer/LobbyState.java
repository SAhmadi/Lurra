package State.Multiplayer;

import Assets.GameObjects.Multiplayer.MPPlayer;
import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.GamePanel;
import Main.References;
import Main.ResourceLoader;
import Main.Sound;
import Networking.Server;
import State.Level.MPLevelState;
import State.Menu.MenuState;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


/*
* Level1State - Erstes Level
* */
public class LobbyState extends State
{

    // Resources
    public static boolean goldRushSelected = false;

    /*
    * NETZWERK
    * */
    public static String playerName;
    public int clientId = 0;
    public static ArrayList<MPPlayer> players = new ArrayList<MPPlayer>();
    public static ArrayList<String> playerNames = new ArrayList<String>();
    public static Socket socket;

    public static volatile BufferedReader br;
    public static PrintWriter pw;

    public boolean isSpectator = false;
    public TileMap tileMap = new TileMap(20, false);

    // Spieler-Liste
    public static JList<String> playerList;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;
    private DefaultListCellRenderer ListRenderer; // Zentrieren des Textes

    // Chat und Eingabefeld
    private JTextArea chatAreaField;
    private JScrollPane chatAreaScrollPane;
    private JTextField chatInputField;
    private String messageToSend;
    private JButton sendBtn;

    // Namen aendern
    private JButton changeNameBtn;
    private JButton deathMatchBtn;
    private JButton goldRushBtn;

    // Lobby verlassen
    private JButton exitBtn;

    // Spieler entfernen
    private JButton removePlayerBtn;

    // Zufallswelt
    private JButton randomWorldBtn;

    // Spiel starten
    private JButton startGameBtn;


    /**
     * LobbyState           Konstruktor der Klasse LobbyState
     *
     * @param graphics      Graphics Objekt
     * @param gamePanel     Inhaltsflaeche
     * @param stateManager  Zustandsmanager
     * @param playerName    Spielername
     * @param isSpectator   Ist Client Zuschauer
     * */
    public LobbyState(Graphics graphics, GamePanel gamePanel, StateManager stateManager, String playerName, boolean isSpectator)
    {
        super(gamePanel, graphics, stateManager);

        LobbyState.playerName = playerName;
        this.isSpectator = isSpectator;

        JFrame f = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
        f.setTitle(playerName);

        init();
        System.out.println("Lobby Inititalized");

        // TileMap
        tileMap.setPosition(0, 0);
    }


    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
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
                (References.SCREEN_WIDTH / 2) - (ResourceLoader.menuTitleImage.getWidth(null) / 2),
                (References.SCREEN_HEIGHT/ 4),
                ResourceLoader.menuTitleImage.getWidth(null),
                ResourceLoader.menuTitleImage.getHeight(null),
                null
        );

        /*
        * SPIELR - LISTE
        * */
        scrollPane = new JScrollPane();
        listModel = new DefaultListModel<String>();
        playerList = new JList<String>(listModel);

        // ScrollPane
        scrollPane.setBounds(
                References.SCREEN_WIDTH - References.SCREEN_WIDTH / 6,
                0,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT - References.SCREEN_HEIGHT / 13
        );
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scrollPane.getHorizontalScrollBar().setVisible(false);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));

        // Spielerdaten-Liste
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerList.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        ListRenderer = (DefaultListCellRenderer)playerList.getCellRenderer();
        ListRenderer.setHorizontalAlignment(JLabel.CENTER);
        playerList.setFixedCellWidth(scrollPane.getWidth());
        playerList.setFixedCellHeight(32);
        playerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        playerList.setVisibleRowCount(0);

        playerList.setBackground(new Color(10, 10, 10, 210));
        playerList.setForeground(Color.WHITE);
        playerList.setFont(ResourceLoader.textFieldFont);
        playerList.setSelectionBackground(Color.GREEN);
        playerList.setSelectionForeground(Color.BLACK);
        playerList.setFont(ResourceLoader.textFieldFont);
        playerList.setFocusable(false);

        // Hinzufuegen der Liste im ScrollPane
        scrollPane.setViewportView(playerList);
        scrollPane.setVisible(true);
        gamePanel.add(scrollPane);


/*
//        * CHAT - TEXTFELD
//        * */

        chatAreaScrollPane = new JScrollPane();
        chatAreaScrollPane.getHorizontalScrollBar().setVisible(false);
        chatAreaScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        chatAreaScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        chatAreaScrollPane.setOpaque(false);
        chatAreaScrollPane.getViewport().setOpaque(false);

        chatAreaField = new JTextArea();
        chatAreaScrollPane.setBounds(
                (References.SCREEN_WIDTH - References.SCREEN_WIDTH / 6) - References.SCREEN_WIDTH/ 4,
                0,
                References.SCREEN_WIDTH/ 4,
                References.SCREEN_HEIGHT - References.SCREEN_HEIGHT / 13
        );
        chatAreaField.setBackground(new Color(10, 10, 10, 200));
        chatAreaField.setFont(ResourceLoader.textFieldFont.deriveFont(12f));
        chatAreaField.setForeground(Color.WHITE);
        chatAreaField.setFont(ResourceLoader.textFieldFont);
        chatAreaField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(10, 10, 10, 0)), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        chatAreaField.setEditable(false);
        chatAreaField.setLineWrap(true);
        chatAreaField.setWrapStyleWord(true);
        chatAreaField.setAutoscrolls(true);
        chatAreaField.setVisible(true);

        //players.add(new MPPlayer(43, 43, 20, 25, 0.5, 5, 8.0, 20.0, null, playerName, Server.clientIDs));
        //playerNames.add(playerName);

        //chatAreaScrollPane.setVerticalScrollBar;
        chatAreaScrollPane.setViewportView(chatAreaField);
        chatAreaScrollPane.setVisible(true);

        listModel.addElement("(Admin) " + playerName);
        gamePanel.add(chatAreaScrollPane);


//        * CHAT - EINGABEFELD
//        * */

        chatInputField = new JTextField();
        chatInputField.setBounds(
                (References.SCREEN_WIDTH - References.SCREEN_WIDTH / 6) - References.SCREEN_WIDTH / 4,
                References.SCREEN_HEIGHT - References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 4,
                References.SCREEN_HEIGHT / 13
        );
        chatInputField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        chatInputField.setFont(ResourceLoader.textFieldFont.deriveFont(12f));
        chatInputField.setBackground(Color.WHITE);
        chatInputField.setForeground(Color.BLACK);
        chatInputField.setFont(ResourceLoader.textFieldFont);
        chatInputField.setVisible(true);
        gamePanel.add(chatInputField);

        sendBtn = new JButton("Senden");
        sendBtn.setBounds(
                References.SCREEN_WIDTH - References.SCREEN_WIDTH/ 6,
                References.SCREEN_HEIGHT - References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        sendBtn.setBackground(Color.WHITE);
        sendBtn.setBorderPainted(false);
        sendBtn.setOpaque(true);
        sendBtn.setForeground(new Color(40, 40, 40));
        sendBtn.setFont(ResourceLoader.textFieldFont);
        sendBtn.setVisible(true);
        gamePanel.add(sendBtn);


/*
//        * SPIELDATEN-EINSTELLUNGEN
//        * */

        // Namen ändern
        changeNameBtn = new JButton("Namen aendern");
        changeNameBtn.setBounds(
                0,
                0,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        changeNameBtn.setBackground(new Color(10, 10, 10, 180));
        changeNameBtn.setBorderPainted(false);
        changeNameBtn.setOpaque(true);
        changeNameBtn.setForeground(Color.WHITE);
        changeNameBtn.setFont(ResourceLoader.textFieldFont);
        changeNameBtn.setVisible(true);
        gamePanel.add(changeNameBtn);

        // Lobby verlassen
        exitBtn = new JButton("Spiel verlassen");
        exitBtn.setBounds(
                0,
                References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        exitBtn.setBackground(new Color(10, 10, 10, 180));
        exitBtn.setBorderPainted(false);
        exitBtn.setOpaque(true);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(ResourceLoader.textFieldFont);
        exitBtn.setVisible(true);
        gamePanel.add(exitBtn);

        // Spieler entfernen -> Nur sichtbar fuer Admin (receivePlayers-Methode)
        randomWorldBtn = new JButton("Zufallswelt");
        randomWorldBtn.setBounds(
                0,
                2 * References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        randomWorldBtn.setBackground(new Color(10, 10, 10, 180));
        randomWorldBtn.setBorderPainted(false);
        randomWorldBtn.setOpaque(true);
        randomWorldBtn.setForeground(Color.WHITE);
        randomWorldBtn.setFont(ResourceLoader.textFieldFont);

        // Spieler entfernen -> Nur sichtbar fuer Admin (receivePlayers-Methode)
        removePlayerBtn = new JButton("Spieler entfernen");
        removePlayerBtn.setBounds(
                0,
                3 * References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        removePlayerBtn.setBackground(new Color(10, 10, 10, 180));
        removePlayerBtn.setBorderPainted(false);
        removePlayerBtn.setOpaque(true);
        removePlayerBtn.setForeground(Color.WHITE);
        removePlayerBtn.setFont(ResourceLoader.textFieldFont);

        // Spiel starten
        startGameBtn = new JButton("Spiel starten");
        startGameBtn.setBounds(
                0,
                References.SCREEN_HEIGHT - References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        startGameBtn.setBackground(Color.WHITE);
        startGameBtn.setForeground(Color.BLACK);
        startGameBtn.setBorderPainted(false);
        startGameBtn.setOpaque(true);
        startGameBtn.setFont(ResourceLoader.textFieldFont);




        deathMatchBtn = new JButton("Deathmatch");
        deathMatchBtn.setBounds(
                0,
                4* References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        deathMatchBtn.setBackground(Color.WHITE);
        deathMatchBtn.setForeground(Color.BLACK);
        deathMatchBtn.setBorderPainted(false);
        deathMatchBtn.setOpaque(true);
        deathMatchBtn.setFont(ResourceLoader.textFieldFont);



        goldRushBtn = new JButton("Goldrush");
        goldRushBtn.setBounds(
                0,
                5* References.SCREEN_HEIGHT / 13,
                References.SCREEN_WIDTH / 6,
                References.SCREEN_HEIGHT / 13
        );
        goldRushBtn.setBackground(Color.WHITE);
        goldRushBtn.setForeground(Color.BLACK);
        goldRushBtn.setBorderPainted(false);
        goldRushBtn.setOpaque(true);
        goldRushBtn.setFont(ResourceLoader.textFieldFont);
        gamePanel.add(goldRushBtn);


/*
        * LISTENERS
        * */

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageToSend = chatInputField.getText().trim();
                pw.println("msgToSend:" + playerNames.get(clientId - 1) + ":" + messageToSend);

                chatInputField.setText("");
            }
        });

        sendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sendBtn.setBackground(Color.GREEN);
                sendBtn.setForeground(Color.BLACK);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendBtn.setBackground(Color.WHITE);
                sendBtn.setForeground(new Color(40, 40, 40));
            }
        });

        changeNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientId == 1) {
                    if(playerList.getSelectedValue() != null) {
                        String newPlayerName = JOptionPane.showInputDialog("Ändere " + playerList.getSelectedValue() + "'s Namen:");
                        if(newPlayerName != null) {
                            if(playerList.getSelectedValue().contains("(Admin)")) {
                                pw.println("plNameChange:" + playerList.getSelectedValue().substring(8) + ":" + newPlayerName);

                            }
                            else {
                                pw.println("plNameChange:" + playerList.getSelectedValue() + ":" + newPlayerName);
                            }
                        }
                    }
                    else {
                        gamePanel.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.RED));
                    }
                }
                else if(clientId != 1) {
                    String newPlayerName = JOptionPane.showInputDialog("Ändere deinen Namen:");
                    if(newPlayerName != null) {
                        pw.println("plNameChange:" + playerName + ":" + newPlayerName);
                    }
                }
            }
        });

        changeNameBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeNameBtn.setBackground(Color.GREEN);
                changeNameBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gamePanel.getRootPane().setBorder(BorderFactory.createEmptyBorder());

                changeNameBtn.setBackground(new Color(10, 10, 10, 180));
                changeNameBtn.setForeground(Color.WHITE);
            }
        });


        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("plExit");   // TODO
            }
        });

        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitBtn.setBackground(Color.GREEN);
                exitBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gamePanel.getRootPane().setBorder(BorderFactory.createEmptyBorder());

                exitBtn.setBackground(new Color(10, 10, 10, 180));
                exitBtn.setForeground(Color.WHITE);
            }
        });


        randomWorldBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientId == 1) {
                    // TODO
                    if(playerList.getSelectedValue() != null && playerList.getSelectedIndex() > 0) {

                        if(playerNames.indexOf(playerList.getSelectedValue()) > 0) {
                            pw.println("rmPl:" + playerNames.indexOf(playerList.getSelectedValue()) + ":" + playerList.getSelectedValue());       // rmPl:ID:Spielername
                        }
                    }
                    else {
                        gamePanel.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.RED));
                    }

                }

            }
        });

        randomWorldBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                randomWorldBtn.setBackground(Color.GREEN);
                randomWorldBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gamePanel.getRootPane().setBorder(BorderFactory.createEmptyBorder());

                randomWorldBtn.setBackground(new Color(10, 10, 10, 180));
                randomWorldBtn.setForeground(Color.WHITE);
            }
        });


        removePlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientId == 1) {

                    if(playerList.getSelectedValue() != null && playerList.getSelectedIndex() > 0) {

                        if(playerNames.indexOf(playerList.getSelectedValue()) > 0) {
                            pw.println("rmPl:" + playerNames.indexOf(playerList.getSelectedValue()) + ":" + playerList.getSelectedValue());       // rmPl:ID:Spielername
                        }
                    }
                    else {
                        gamePanel.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.RED));
                    }

                }

            }
        });

        removePlayerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removePlayerBtn.setBackground(Color.GREEN);
                removePlayerBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gamePanel.getRootPane().setBorder(BorderFactory.createEmptyBorder());

                removePlayerBtn.setBackground(new Color(10, 10, 10, 180));
                removePlayerBtn.setForeground(Color.WHITE);
            }
        });

        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientId == 1) {
                    if (GameData.isSoundOn.equals("On")) {
                        Sound.elevatorSound.stop();
                        Sound.elevatorSound.close();
                        Sound.gameSound.play();
                        Sound.gameSound.continues();
                    }
                    pw.println("strtGame");
                }
            }
        });

        startGameBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startGameBtn.setBackground(Color.GREEN);
                startGameBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gamePanel.getRootPane().setBorder(BorderFactory.createEmptyBorder());

                startGameBtn.setBackground(Color.WHITE);
                startGameBtn.setForeground(Color.BLACK);
            }
        });

        goldRushBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clientId == 1) {
                    if (GameData.isSoundOn.equals("On")) {
                        Sound.elevatorSound.stop();
                        Sound.elevatorSound.close();
                        Sound.gameSound.play();
                        Sound.gameSound.continues();
                    }

                    goldRushSelected = true;
                    pw.println("strtGame");

                }
            }
        });

        goldRushBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                goldRushBtn.setBackground(Color.GREEN);
                goldRushBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gamePanel.getRootPane().setBorder(BorderFactory.createEmptyBorder());

                goldRushBtn.setBackground(Color.WHITE);
                goldRushBtn.setForeground(Color.BLACK);
            }
        });



/*
        * NETZWERK
        * */

        try {
            socket = new Socket(Server.HOST, Server.PORT);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(isr);
            pw = new PrintWriter(socket.getOutputStream(), true);

            if (isSpectator) {
                pw.println("pName:#" + playerName);
            }
            else {
                pw.println("pName:" + playerName);

            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        multiplayerThread();

    }

    public void multiplayerThread() {
        new Thread() {

            @Override
            public void run() {
                String line;
                try {
                    while ((line = br.readLine()) != null) {

                        //System.out.println("** Line from Server was: " + line);

                        /*
                        * Gruesse Spieler
                        * */
                        receivePlayers(line);


/*
                        * Empfange Spielernamen - Liste
                        * */
                        receivePlayerNames(line);

/*
                        * Empfange aktuellste Spielernamen-Liste, nachdem Spielernamen geaendert wurden
                        * */
                        receiveChangedNames(line);


/*
                        * Empfange Chat-Nachricht
                        * */

                        receiveMessage(line);

/*
                        *
                        * */
                        receivePlayerExit(line);

/*
                        *
                        * */
                        receiveRandomWorld(line);


/*
                        * Empfange aktuellste Spielernamen-Liste, nachdem Spieler geloescht wurde
                        * Sender geloeschten Spieler zurueck zum Menu
                        * */
                        receiveRemovedNames(line);
                        sendPlayerBackToMenu(line);
/*
                        *
                        * */
                        receiveStartGame(line);
                    }
                }
                catch (IOException ioe) {
                }
            }

        }.start();
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics2D g) {}

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



    /**
     *
     * */

    private void receivePlayers(String line) {
        if(line.contains("Welcome! Player")) {
            line = line.split(":")[1];
            if(clientId == 0) {
                clientId = Integer.parseInt(line);
            }

            if(clientId == 1) {
                randomWorldBtn.setVisible(true);
                gamePanel.add(randomWorldBtn);

                removePlayerBtn.setVisible(true);
                gamePanel.add(removePlayerBtn);

                startGameBtn.setVisible(true);
                gamePanel.add(startGameBtn);

                deathMatchBtn.setVisible(true);
                gamePanel.add(deathMatchBtn);

                goldRushBtn.setVisible(true);
                gamePanel.add(goldRushBtn);


            }
            //System.out.println("////////////// " + this.clientId);
        }

    }


    /**
     * receivePlayerNames       Empfange die Namen aller verbundenen Spieler
     * @param line              Packet des Servers, der Form -> #Pl:AnzSpieler:Name1;Name2;...
     * */

    private void receivePlayerNames(String line) {
        if(line.contains("#Pl")) {
            //System.out.println("Line Contains PLAYERNAME");

            line = line.split(":")[3];

            // Resete alles
            listModel.removeAllElements();
            for (int p = 0; p < players.size(); p++) {
                try {
                    players.remove(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            String[] allPlayerNames = line.split(";");
            for (int i = 0; i < allPlayerNames.length; i++) {
                System.out.println(allPlayerNames[i]);
                if(!allPlayerNames[i].contains("#"))
                    players.add(new MPPlayer(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap, allPlayerNames[i], i));
                playerNames.add(allPlayerNames[i]);
                playerName = allPlayerNames[i];

                if(i == 0) {    // Erstes Element (Admin) vorsetzen
                    listModel.addElement("(Admin) " + allPlayerNames[i]);
                    continue;
                }
                else
                {
                    listModel.addElement(allPlayerNames[i]);
                }
                //System.out.println("############### Selected Player: " + playerList.getSelectedValue());
            }
        }
    }


    /**
     *
     * */

    private void receiveChangedNames(String line) {
        if(line.contains("changedPlys")) {
            //System.out.println("Line Contains PLAYERNAME");

            line = line.split(":")[1];

            // Resete alles
            listModel.removeAllElements();
            for (int p = 0; p < players.size(); p++) {
                try {
                    players.remove(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            for (int n = 0; n < playerNames.size(); n++) {
                playerNames.remove(n);
            }

            String[] allPlayerNames = line.split(";");
            for (int i = 0; i < allPlayerNames.length; i++) {
                if(!allPlayerNames[i].contains("#"))
                    players.add(new MPPlayer(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap, allPlayerNames[i], i));
                playerNames.add(allPlayerNames[i]);
                playerName = allPlayerNames[i];

                if(i == 0) {    // Erstes Element (Admin) vorsetzen
                    listModel.addElement("(Admin) " + allPlayerNames[i]);
                    continue;
                }
                listModel.addElement(allPlayerNames[i]);
            }

        }
    }


    /**
     *
     * */

    private void receiveMessage(String line) {
        if(line.contains("msgToReceive")) {
            String transmitter = line.split(":")[1];
            String message = line.split(":")[2];

            chatAreaField.append(transmitter + " - " + message + "\n\n");
            //System.out.println(clientId);
        }
    }


    /**
     *
     * */

    private void receivePlayerExit(String line) {}


    /**
     *
     * */

    private void receiveRandomWorld(String line) {}


    /**
     *
     * */

    private void receiveRemovedNames(String line) {
        if(line.contains("rmPlys")) {
            int rmClientId = Integer.parseInt(line.split(":")[1]);
            line = line.split(":")[2];


            // Resete alles
            listModel.removeAllElements();
            for (int p = 0; p < players.size(); p++) {
                try {
                    players.remove(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            for (int n = 0; n < playerNames.size(); n++) {
                playerNames.remove(n);
            }


            if(clientId > rmClientId)
                clientId--;

            String[] allPlayerNames = line.split(";");
            for (int i = 0; i < allPlayerNames.length; i++) {
                if(!allPlayerNames[i].contains("#"))
                    players.add(new MPPlayer(22, 41, 16, 16, 0.5, -5.0, 8.0, -20.0, tileMap, allPlayerNames[i], i));
                playerNames.add(allPlayerNames[i]);
                playerName = allPlayerNames[i];

                if(i == 0) {    // Erstes Element (Admin) vorsetzen
                    listModel.addElement("(Admin) " + allPlayerNames[i]);
                    continue;
                }
                listModel.addElement(allPlayerNames[i]);
            }


            //
            //exitPlayer(rmClientId);

        }
    }

    /**
     * sendPlayerBackToMenu
     *
     * @param line      Text
     * */
    public void sendPlayerBackToMenu(String line)
    {
        if (line.contains("backToMenu"))
        {
            int id = Integer.parseInt(line.split(":")[1]);

            if (clientId == id)
            {
                gamePanel.remove(playerList);
                listModel.removeAllElements();

                gamePanel.remove(scrollPane);
                ListRenderer.removeAll();

                gamePanel.remove(chatAreaField);
                gamePanel.remove(chatAreaScrollPane);
                gamePanel.remove(chatInputField);
                gamePanel.remove(chatInputField);
                gamePanel.remove(sendBtn);
                gamePanel.remove(changeNameBtn);
                gamePanel.remove(exitBtn);

                stateManager.getGameStates().pop();
                stateManager.setActiveState(new MenuState(graphics, gamePanel, stateManager), stateManager.MENUSTATE);

            }
        }
    }


    /**
     *
     * */
    private void receiveStartGame(String line) {
        if(line.contains("strtGame")) {
            gamePanel.remove(playerList);
            listModel.removeAllElements();

            gamePanel.remove(scrollPane);
            ListRenderer.removeAll();

            gamePanel.remove(chatAreaField);
            gamePanel.remove(chatAreaScrollPane);
            gamePanel.remove(chatInputField);
            gamePanel.remove(chatInputField);
            gamePanel.remove(sendBtn);
            gamePanel.remove(changeNameBtn);
            gamePanel.remove(exitBtn);

            //System.out.println("**************** " + clientId);

            if(clientId == 1) {
                gamePanel.remove(randomWorldBtn);
                gamePanel.remove(startGameBtn);
                gamePanel.remove(removePlayerBtn);
                gamePanel.remove(goldRushBtn);
                gamePanel.remove(deathMatchBtn);
            }

            //System.out.println("LOBBY ALL PLAYERS SIZE: " + players.size());

            stateManager.getGameStates().pop();
            stateManager.setActiveState(new MPLevelState(graphics, gamePanel, stateManager, players, clientId, isSpectator), stateManager.MPLEVELSTATE);
        }
    }
}

