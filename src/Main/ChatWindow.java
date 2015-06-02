package Main;

///**
// * Created by moham_000, Amin, Halit, Carola, Vanessa and Sirat
// */
//
//public class ChatWindow extends JFrame {
//
//    public static final String TITLE = "Lurra Chatroom";
//    private static ChatWindow instance;
//
//    //List of users connected
//    private JList listUsers;
//
//    //Chat text
//    private JTextArea textChat;
//
//    //User input
//    private JTextField fieldInput;
//    private JButton buttonSend;
//
//    //Network client object (networking module)
//    private Client client;
//
//    private DataOutputStream streamOut;
//
//
//    public ChatWindow() {
//        createView();
//        client = new Client("127.0.0.1", 1050);
//        client.connectToServer();
//
//        setTitle(TITLE);
//        setSize(600, 500);
//        setResizable(true);
//        setLocationRelativeTo(null);
//    }
//
//    private void createView() {
//        JPanel panel = new JPanel();
//        getContentPane().add(panel);
//        panel.setLayout(new BorderLayout());
//
//        listUsers = new JList();
//        JScrollPane listUsersSP = new JScrollPane(listUsers);
//        listUsersSP.setPreferredSize(new Dimension(200, 0));
//        panel.add(listUsersSP, BorderLayout.EAST);
//
//        JPanel panelChat = new JPanel(new BorderLayout());
//        panel.add(panelChat, BorderLayout.CENTER);
//
//        textChat = new JTextArea();
//        textChat.setEditable(false);
//        ((DefaultCaret) textChat.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//        JScrollPane textChatSP = new JScrollPane(textChat);
//        panelChat.add(textChatSP, BorderLayout.CENTER);
//
//        JPanel panelInput = new JPanel(new BorderLayout());
//        panel.add(panelInput, BorderLayout.SOUTH);
//        fieldInput = new JTextField();
//        panelInput.add(fieldInput, BorderLayout.CENTER);
//        buttonSend = new JButton("Send");
//        buttonSend.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Sende Nachricht an Network.Server
//                sendMessage(fieldInput.getText());
//            }
//        });
//        panelInput.add(buttonSend, BorderLayout.EAST);
//    }
//
//    private void sendMessage(String message) {
//        try {
//            streamOut = new DataOutputStream(client.socket.getOutputStream());
//        }
//        catch(IOException e) {}
//        try {
//            streamOut.writeUTF(message);
//        }
//        catch(IOException e) {}
//    }
//}