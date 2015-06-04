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
    public static int id;

    Connection(Socket s, int id) throws IOException {
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream(), true);
        this.socket = s;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("test");
        String line;
        try {
            while ((line = br.readLine()) != null){
                System.out.println("Line: " + line);

                if(line.contains("pName:")) {
                    line = line.split(":")[1];  // Loeschen des Codes

                    Server.playerNames.add(line);
                    for (int i = 0; i < Server.clients.size(); i++) {
                        String allNames  = "";
                        for (String n : Server.playerNames) {
                            allNames = allNames + n + ";";
                        }

                        Server.clients.get(i).send("allPl:" + line);
                    }
                }
                else {
                    for (int i = 0; i < Server.clients.size(); i++) {
                        Server.clients.get(i).send(line);
                    }
                }
            }
        }
        catch (IOException ioe) {
            System.err.println("I/O error: "+ioe.getMessage());
        }
        finally {
            synchronized(Server.clients)
            {
                Server.clients.remove(this);
                Server.clientIDs--;
                System.out.println("Client Removed");
            }
        }

    }

    public void send(String message) {
        pw.println(message);
    }

}