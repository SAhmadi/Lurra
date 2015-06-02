package Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

///**
// * Network.Server
// */
//public class Server {
//
//    public static ArrayList<Socket> ConnectionArray;
//    public static ArrayList<String> CurrentUsers;
//    public static int userID;
//
//    public static int PORT = 8080;  // Standard Port, kann ueberschrieben werden
//
//    public static boolean isServerRunning = false;
//
//    /**
//     *
//     * */
//    public Server() {
//        this.ConnectionArray = new ArrayList<Socket>();
//        this.CurrentUsers = new ArrayList<String>();
//    }
//
//    public static void main(String[] args) {
//        try {
//            ServerSocket serverSocket = new ServerSocket(8080);
//            System.out.print("Warten auf Clients...");
//
//            isServerRunning = true;
//            while(isServerRunning) {
//                Socket socket = serverSocket.accept();
//                ConnectionArray.add(socket);
//                System.out.print("Client hat sich verbunden: " + socket.getLocalAddress().getHostName());
//
//                addUserName(socket);
//
//
//                ServerConnection serverConnection = new ServerConnection(socket);
//                Thread serverThread = new Thread(serverConnection);
//                serverThread.start();
//            }
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    private static void addUserName(Socket socket) {
//        try {
//            Scanner input = new Scanner(socket.getInputStream());
//            String userName = input.nextLine();
//
//            CurrentUsers.add(userName);
//
////            for (int i = 1; i <= ConnectionArray.size(); i++) {
////                Socket tmpSocket = ConnectionArray.get(i - 1);
////                PrintWriter outputStream = new PrintWriter(tmpSocket.getOutputStream());
////
////                outputStream.println("#?!" + CurrentUsers);
////                outputStream.flush();
////            }
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//}

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class Server {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}