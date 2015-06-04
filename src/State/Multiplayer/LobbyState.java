package State.Multiplayer;

import Networking.Server;
import Assets.GameObjects.Multiplayer.MPPlayer;
import Assets.World.TileMap;
import Main.GamePanel;
import Main.ResourceLoader;
import Main.ScreenDimensions;
import Networking.Connection;
import State.State;
import State.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
public class LobbyState extends State {

    // Inhaltsflaeche, Graphics-Obj und Zustands-Manger
    protected GamePanel gamePanel;
    protected Graphics graphics;
    protected StateManager stateManager;

    // Resources
    private BufferedImage menuBackground;
    private BufferedImage menuIlandBackground;
    private BufferedImage menuTitleImage;

    // Netzwerk
    public String playerName;
    public ArrayList<MPPlayer> players = new ArrayList<MPPlayer>();
    public Socket socket;
    public volatile BufferedReader br;
    public PrintWriter pw;

    // Chat
    private JTextField textField;
    private String messageToSend;
    private JButton sendBtn;
    private JLabel label;

    private JList playerList;
    private DefaultListModel listModel;
    private JScrollPane scrollPane;
    private DefaultListCellRenderer ListRenderer; // Zentrieren des Textes



    /*
    * Konstruktor - Initialisieren
    * */
    public LobbyState(Graphics graphics, GamePanel gamePanel, StateManager stateManager, String playerName) {
        this.gamePanel = gamePanel;
        this.graphics = graphics;
        this.stateManager = stateManager;

        // Resource Initialisieren
        this.menuBackground = ResourceLoader.menuBackground;
        this.menuIlandBackground = ResourceLoader.menuIlandBackground;
        this.menuTitleImage = ResourceLoader.menuTitleImage;

        this.playerName = playerName;

        init();
        System.out.println("Lobby Inititalized");
    }

    /*
    * Eigentliches Initialisieren
    * */
    @Override
    public void init() {
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
                (ScreenDimensions.WIDTH / 2) - (menuTitleImage.getWidth(null) / 2),
                (ScreenDimensions.HEIGHT / 4),
                menuTitleImage.getWidth(null), menuTitleImage.getHeight(null),
                null
        );

        players.add(new MPPlayer(43, 43, 20, 25, 0.5, 5, 8.0, 20.0, null, playerName, Server.clientIDs));
        System.out.println("Added Player  to list");
        System.out.println(playerName);


        /*
        * CHAT
        * */
        textField = new JTextField();
        textField.setBounds(
                ScreenDimensions.WIDTH / 2 - 40 - 100,
                ScreenDimensions.HEIGHT / 2 - 20,
                80,
                40
        );
        textField.setVisible(true);
        gamePanel.add(textField);

        sendBtn = new JButton("Senden");
        sendBtn.setBounds(
                ScreenDimensions.WIDTH / 2 + 50 + 40,
                ScreenDimensions.HEIGHT / 2 - 15,
                100,
                30
        );
        sendBtn.setVisible(true);
        gamePanel.add(sendBtn);

        label = new JLabel("Going to change");
        label.setBounds(
                ScreenDimensions.WIDTH / 2,
                ScreenDimensions.HEIGHT / 2 - 50,
                100,
                30
        );
        label.setVisible(true);
        gamePanel.add(label);


        scrollPane = new JScrollPane();
        listModel = new DefaultListModel();
        playerList = new JList(listModel);

        // ScrollPane
        scrollPane.setBounds(
                ScreenDimensions.WIDTH - ScreenDimensions.WIDTH/3 - 20,
                20,
                ScreenDimensions.WIDTH/3,
                ScreenDimensions.HEIGHT - ScreenDimensions.HEIGHT/8
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

        playerList.setBackground(Color.WHITE);
        playerList.setForeground(Color.BLACK);
        playerList.setFont(ResourceLoader.textFieldFont);
        playerList.setSelectionBackground(Color.GREEN);
        playerList.setSelectionForeground(Color.WHITE);
        playerList.setFocusable(false);

        // Hinzufuegen der Liste im ScrollPane
        scrollPane.setViewportView(playerList);
        scrollPane.setVisible(true);
        gamePanel.add(scrollPane);

        /*
        * ACTIONLISTENERS
        * */
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageToSend = textField.getText().trim();
                pw.println(messageToSend);
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

            pw.println("pName:" + playerName);
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
        new Thread(){

            @Override
            public void run() {
                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        boolean newClient = true;

                        System.out.println("** Line from Server was: " + line);

                        if(line.contains("pName:")) {
                            System.out.println("Line Contains PLAYERNAME");
                            for (int i = 0; i < players.size(); i++) {
                                String lineTrimmed = line.split(":")[1];
                                String[] allPlayerNames = lineTrimmed.split(";");

                                for (int j = 0; j < allPlayerNames.length; i++) {
                                    if(!listModel.contains(allPlayerNames[j])) {
                                        listModel.addElement(allPlayerNames[j]);

                                    }
                                }
                            }

                        }

//                        try {
//                            for (MPPlayer player: Server.players){
//                                if (line.startsWith(player.playerName)){
//                                    if (line.startsWith(players.get(0).playerName)){
//                                        //Do nothing
//                                        newClient = false;
//                                    } else {
//                                        label.setText(line);
//                                        System.out.println("Chnaged label");
//                                        newClient = false;
//                                    }
//                                }
//
//                                label.setText(line);
//                                System.out.println(line);
//                            }
//
////                            if (newClient && !line.contains("Welcome")){
////                                players.add(
////                                        new MPPlayer(43, 43, 20, 25, 0.5, 5, 8.0, 20.0, null, "Player"+Server.clientIDs, Server.clientIDs)
////                                );
////                            }
//                        } catch(NullPointerException ex) {
//                            ex.printStackTrace();
//                        }


                    }
                }
                catch (IOException ioe) {
                }
            }

        }.start();
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
    public void render(Graphics g) {
    }

    /*
    * EventListener
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
    public void mouseWheelMoved(MouseWheelEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     *
     * Getter und Setter Methoden
     *
     * */

}
