package Network;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

///**
// * Created by Sirat on 02.06.2015.
// */
//public class Client implements Runnable {
//
//    private Socket socket;
//    private Scanner inputStream;
//    private PrintWriter outputStream;
//
//    public Client(Socket socket) {
//        this.socket = socket;
//    }
//
//    public void send(String message) {
//        // TODO username mit ausgeben. Beispiel: Sirat - Hello World
//        outputStream.println(message);
//        outputStream.flush();
//    }
//
//
//    @Override
//    public void run() {
//        try {
//
//            try {
//                inputStream = new Scanner(socket.getInputStream());
//                outputStream = new PrintWriter(socket.getOutputStream());
//                outputStream.flush();
//
//                String message = inputStream.nextLine();
//
//                while(true) {
//                    if(inputStream.hasNext()) {
//                        System.out.println(message + "\n");
//                    }
//                }
//
//            } finally {
//                socket.close();
//            }
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//}


/**
 * Trivial client for the date server.
 */
public class Client {

    /**
     * Runs the client as an application.  First it displays a dialog
     * box asking for the IP address or hostname of a host running
     * the date server, then connects to it and displays the date that
     * it serves.
     */
    public static void main(String[] args) throws IOException {
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of a machine that is\n" +
                        "running the date service on port 9090:");
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
    }
}