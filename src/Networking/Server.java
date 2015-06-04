package Networking;

import Assets.GameObjects.Multiplayer.MPPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * */
public class Server
{
    public final static int PORT_NO = 8080;
    public static String HOST = "localhost";
    public static ServerSocket listener;
    public static List<Connection> clients;
    public static int clientIDs = 1;

    public Server() throws IOException {
        listener = new ServerSocket(PORT_NO);
        clients = new ArrayList<Connection>();
        System.out.println("listening on port " + PORT_NO);
    }

    public static void main(String[] args) {
        try {
            System.out.println("ChatServer starting");
            new Server().runServer();
        } catch (IOException ioe) {
            System.err.println("unable to create server socket");
        }
    }

    public void runServer() {

        try {
            while (true) {
                Socket socket = listener.accept();
                System.out.println("accepted connection");
                Connection con = new Connection(socket);

                synchronized(clients)
                {
                    clients.add(con);

                    for (int i = 0; i < clients.size(); i++) {
                        clients.get(i).send("Welcome! Player" + clientIDs);
                    }

                    clientIDs++;
                    con.start();

                }
            }
        } catch (IOException ioe) {
            System.err.println("I/O error: "+ioe.getMessage());
            return;
        }
    }
}


