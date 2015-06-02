//package Network;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Scanner;
//
///**
// * Created by Sirat on 02.06.2015.
// */
//public class ServerConnection implements Runnable {
//
//    public Socket socket;
//    private Scanner inputStream;
//    private PrintWriter outputStream;
//    public String message = "";
//
//    public ServerConnection(Socket socket) {
//        this.socket = socket;
//    }
//
//    public void checkConnection() {
//        if(!socket.isConnected()) {
//
//        }
//    }
//
//    @Override
//    public void run() {
//        try {
//
//            try {
//                inputStream = new Scanner(socket.getInputStream());
//                outputStream = new PrintWriter(socket.getOutputStream());
//
//                while(true) {
//                    checkConnection();
//
//                    if(!inputStream.hasNext())
//                        return;
//
//                    message = inputStream.nextLine();
//                    System.out.println("Network.Client sendet: " + message);
//
//                    for (int i = 1; i <= Server.ConnectionArray.size(); i++) {
//                        Socket tmpSocket = Server.ConnectionArray.get(i - 1);
//                        PrintWriter tmpOutputStream = new PrintWriter(tmpSocket.getOutputStream());
//
//                        tmpOutputStream.println("message");
//                        tmpOutputStream.flush();
//                        System.out.println("Gesendet an: " + tmpSocket.getLocalAddress().getHostName());
//                    }
//                }
//            }
//            finally {
//                socket.close();
//            }
//
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//}
