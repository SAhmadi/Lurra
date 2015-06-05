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
    private String allNames = "";
    private String tmpLine = "";

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

                /*
                * Gesamte Spielernamens-Liste jedem Client senden
                * */
                sendPlayerNames(line);

                /*
                * Client hat eine Nachricht in Chat-Fenster geschrieben
                * */
                sendMessage(line);

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
                Server.numberOfPlayers--;
                System.out.println("Client Removed");
                if(Server.clients.size() <= 0) {
                    System.exit(0);
                }
            }
        }

    }

    public void send(String message) {
        pw.println(message);
    }


    /**
     * sendPlayerNames          Aktuellste Liste der Spielernamen senden
     * @param line              Paket vom Client, der Form -> pName:Name1;Name2;...
     * */
    private void sendPlayerNames(String line) {
        if(line.contains("pName:")) {
            allNames = "";
            line = line.split(":")[1];  // Loeschen des Prefix: pName
            Server.playerNames.add(line);

            for (String s : Server.playerNames) {
                allNames = allNames + s + ";";  // Alle Namen verketten: Name1;Name2;Name3;...
            }
            System.out.println("AllNames: " + allNames);
            System.out.println("PlayerNamesList: " + Server.playerNames.size());

            /* Senden der Namen an alle verbundenen Clients */
            line = allNames;
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("#Pl:" + Server.playerNames.size() + ":allPl:" + line);  // #Pl:4:Name1;Name2;...
            }
        }
        else {
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send(line);
            }
        }
    }

    /**
     *
     * */
    private void sendMessage(String line) {
        if(line.contains("msgToSend")) {
            tmpLine = line.split(":")[1];   // Spielername des Senders
            line = line.split(":")[2];

            /* Senden der Nachricht an alle verbundenen Clients */
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("msgToSend:" + tmpLine + ":" + line);  // msgToSend:Spielersender:Nachricht
            }
        }
    }
}