package Networking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server
 * */
public class Server {

    public static boolean isServerRunning = false;
    public static int PORT = 8080;
    public static String HOST = "localhost";
    public static ServerSocket listener;
    public static List<Connection> clients;

    public static ArrayList<String> playerNames = new ArrayList<>();
    public static int clientIDs = 1;
    public static int numberOfPlayers;

    public static JFrame serverFrame;
    private static JTextField ipAddressTextField;
    private static JTextField portTextField;
    private static JButton connectBtn;
    private static JButton exitBtn;
    private static boolean validInput = false;


     /**
      * Initialisieren des Servers
      *
      * @param args     Konsole Argumente
      * */
    public static void main(String[] args)
    {
        /*
        * Fenster
        * */
        serverFrame = new JFrame("Server Offline");
        serverFrame.setPreferredSize(new Dimension(400, 160));
        serverFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serverFrame.setResizable(false);
        serverFrame.setUndecorated(false);
        serverFrame.getContentPane().setBackground(Color.WHITE);
        serverFrame.getContentPane().setForeground(new Color(105, 105, 105));
        serverFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(80, 80, 80)));

        serverFrame.setLayout(null);

        /*
        * IP-Adresse, Port und Verbinden-Button
        * */
        ipAddressTextField = new JTextField("IP-Adresse");
        ipAddressTextField.setBounds(10, 10, 380, 30);
        ipAddressTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        ipAddressTextField.setHorizontalAlignment(JTextField.CENTER);
        ipAddressTextField.setBackground(new Color(253, 253, 253));
        ipAddressTextField.setForeground(new Color(105, 105, 105));
        ipAddressTextField.setFont(new Font("Arial", Font.BOLD, 12));
        ipAddressTextField.setVisible(true);

        portTextField = new JTextField("Port");
        portTextField.setBounds(10, 50, 380, 30);
        portTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // null funktioniert hier nicht!
        portTextField.setHorizontalAlignment(JTextField.CENTER);
        portTextField.setBackground(new Color(253, 253, 253));
        portTextField.setForeground(new Color(105, 105, 105));
        portTextField.setFont(new Font("Arial", Font.BOLD, 12));
        portTextField.setVisible(true);

        connectBtn = new JButton("Verbinden");
        connectBtn.setFont(new Font("Arial", Font.BOLD, 14));
        connectBtn.setForeground(new Color(105, 105, 105));
        connectBtn.setBackground(Color.WHITE);
        connectBtn.setBounds(10, 90, 150, 40);
        connectBtn.setContentAreaFilled(false);
        connectBtn.setBorderPainted(false);
        connectBtn.setOpaque(false);
        connectBtn.setVisible(true);

        exitBtn = new JButton("Beenden");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setForeground(new Color(105, 105, 105));
        exitBtn.setBackground(Color.WHITE);
        exitBtn.setBounds(220, 90, 150, 40);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setBorderPainted(false);
        exitBtn.setOpaque(false);
        exitBtn.setVisible(true);


        ipAddressTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ipAddressTextField.setText("");
                ipAddressTextField.setBackground(new Color(253, 253, 253));
                ipAddressTextField.setForeground(new Color(105, 105, 105));
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        portTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                portTextField.setText("");
                portTextField.setBackground(new Color(253, 253, 253));
                portTextField.setForeground(new Color(105, 105, 105));
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });

        connectBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                connectBtn.setForeground(new Color(18, 78, 163));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                connectBtn.setForeground(new Color(105, 105, 105));
            }
        });

        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitBtn.setContentAreaFilled(true);
                exitBtn.setForeground(new Color(18, 78, 163));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBtn.setForeground(new Color(105, 105, 105));
            }
        });

        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ipAddressTextField.equals("") || !ipAddressTextField.getText().contains(" ") || !portTextField.getText().equals("")) {
                    try {
                        HOST = ipAddressTextField.getText().trim();
                        PORT = Integer.parseInt(portTextField.getText().trim());
                        validInput = true;
                        serverFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GREEN));
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        validInput = false;
                        serverFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
                    }
                }
                else {
                    HOST = "localhost";
                    ipAddressTextField.setText("Eingabe wiederholen...");

                    PORT = 8080;
                    portTextField.setText("Eingabe wiederholen...");
                    validInput = false;
                }


                if(validInput) {
                    serverFrame.setTitle("Server Online");

                    try {
                        listener = new ServerSocket(PORT);
                        clients = new ArrayList<Connection>();
                        System.out.println("listening on port " + PORT);

                        System.out.println("ChatServer starting");
                        new Server().runServer();

                    } catch (Exception ex) {
                        serverFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));

                        try {
                            listener.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        ex.printStackTrace();
                    }
                }
                else {
                    serverFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
                }
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        serverFrame.add(ipAddressTextField);
        serverFrame.add(portTextField);
        serverFrame.add(connectBtn);
        serverFrame.add(exitBtn);

        serverFrame.pack();
        serverFrame.setLocationRelativeTo(null);
        serverFrame.setVisible(true);
    }



    public void runServer() {
        isServerRunning = true;

        try {
            while (isServerRunning) {
                Socket socket = listener.accept();

                System.out.println("accepted connection");
                Connection con = new Connection(socket, clientIDs);

                synchronized(clients)
                {
                    clients.add(con);

                    for (int i = 0; i < clients.size(); i++) {
                        clients.get(i).send("Welcome! Player:" + clientIDs);
                        //clients.get(i).send("#players:" + Integer.toString(clients.size()) );
                    }

                    clientIDs++;
                    numberOfPlayers++;
                    System.out.println(numberOfPlayers);
                    con.start();

                }
            }
        } catch (IOException ioe) {
            System.err.println("I/O error: "+ioe.getMessage());
        }
    }
}


