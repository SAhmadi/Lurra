package Networking;

import Assets.GameObjects.Multiplayer.MPPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by sirat on 04.06.15.
 */
public class Connection extends Thread {
    private volatile BufferedReader br;
    private volatile PrintWriter pw;
    public static Socket socket;

    Connection(Socket s) throws IOException {
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream(), true);
        this.socket = s;
    }

    @Override
    public void run() {
        System.out.println("test");
        String line;
        try {
            while ((line = br.readLine()) != null){
                System.out.println("Line - " + line);
                line = line + " - MODIFIED_BY_SERVER";

                for (int i = 0; i < Server.clients.size(); i++) {
                    Server.clients.get(i).send(line);
                }

                System.out.println("#ofPlayers: " + Server.clients.size());
            }
        }
        catch (IOException ioe) {
            System.err.println("I/O error: "+ioe.getMessage());
        }
        finally {
            synchronized(Server.clients)
            {
                Server.clients.remove(this);
                System.out.println("Client Removed");
            }
        }

    }

    public void send(String message) {
        pw.println(message);
    }

}