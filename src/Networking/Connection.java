package Networking;

import java.io.*;
import java.net.Socket;

/**
 * Clientverbindung
 *
 * @author Sirat
 * */
public class Connection extends Thread {
    private volatile BufferedReader br;
    private volatile PrintWriter pw;

    public static Socket socket;
    public static int id;
    private String allNames = "";
    private String tmpLine = "";

    Connection(Socket s, int id) throws IOException {
        this.socket = s;
        this.id = id;

        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        pw = new PrintWriter(s.getOutputStream(), true);
    }

    @Override
    public void run() {
        String line;
        try {

            /* Senden der TileMap */

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

                /*
                * Geaenderte Spielernamen scicken
                * */
                sendPlayerNameChange(line);

                /*
                *
                * */
                sendPlayerExit(line);

                /*
                *
                * */
                sendRandomWorld(line);

                /*
                *
                * */
                sendPlayerRemoveNames(line);

                /*
                *
                * */
                sendStartGame(line);

                /*
                *
                * */
                sendPlayerMove(line);
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

    /**
     * send             Senden von Nachrichten
     *
     * @param message   Nachricht
     * */
    public void send(String message) {
        pw.println(message);
    }


    /**
     * sendPlayerNames          Aktuellste Liste der Spielernamen senden
     * @param line              Packet vom Client, der Form -> pName:Name1;Name2;...
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
     * sendPlayerNameChange     Nachricht, Spieler Namensaenderung
     *
     * @param line              Nachricht
     * */
    private void sendPlayerNameChange(String line) {
        if(line.contains("plNameChange")) {
            String prevName = line.split(":")[1];
            line = line.split(":")[2];

            //Server.playerNames.add(line);

            // Ueberpruefe ob in der Liste
            for(int p = 0; p < Server.playerNames.size(); p++) {
                if(Server.playerNames.get(p).equals(prevName)) {
                    Server.playerNames.set(p, line);
                }
            }

            // Check updated Names-List
            for (String n : Server.playerNames) {
                System.out.println("*********** " + n);
            }

            allNames = "";
            for (String s : Server.playerNames) {
                allNames = allNames + s + ";";  // Alle Namen verketten: Name1;Name2;Name3;...
            }
            System.out.println("AllNames: " + allNames);
            System.out.println("PlayerNamesList: " + Server.playerNames.size());

            /* Senden der Namen an alle verbundenen Clients */
            line = allNames;
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("changedPlys:" + line);  // changedPlys:Name1;Name2;...
            }

        }
    }

    /**
     * sendMessage              Senden der Chat-Nachricht
     *
     * @param line              Packet vom CLient, der Form -> msgToSend:Spielersender:Nachricht
     * */
    private void sendMessage(String line) {
        if(line.contains("msgToSend")) {
            tmpLine = line.split(":")[1];   // Spielername des Senders
            line = line.split(":")[2];

            /* Senden der Nachricht an alle verbundenen Clients */
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("msgToReceive:" + tmpLine + ":" + line);  // msgToReceive:Spielersender:Nachricht
            }
        }
    }

    /**
     * sendPlayerExit   Nachricht, sende Spieler hat verlassen
     *
     * @param line      Nachricht
     * */
    private void sendPlayerExit(String line) {
        if(line.contains("plExit")) {
            int clientID = Integer.parseInt(line.split(":")[1]);
            line = line.split(":")[2];  // Spielername des zu entfernenden Spielers

            for(int i = 0; i < Server.playerNames.size(); i++) {
                if(Server.playerNames.get(i).equals(line)) {

                    for (int p = 0; p < Server.clients.size(); p++)
                    {
                        backToMenu(clientID);
                    }

                    Server.playerNames.remove(i);
                    //Server.clients.get(i).exit();
                    Server.clients.remove(i);
                    pw.println("exitClnt");
                    Server.clientIDs--;
                    Server.numberOfPlayers--;
                }
            }

            String allNames = "";
            for (String s : Server.playerNames) {
                allNames = allNames + s + ";";  // Alle Namen verketten: Name1;Name2;Name3;...
            }

            line = allNames;
            /* Senden des entfernten Spielernamen und seine ID */
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("rmPlys:" + clientID + ":" + line);  // rmPlys:rmIndex:Name1;Name2;...
            }
        }
    }

    /**
     * sendRandomWorld  Nachricht, sende Zufallswelt
     *
     * @param line      Nachricht
     * */
    private void sendRandomWorld(String line) {
        if(line.contains("rndWorld")) {
            /* Senden der Nachricht an alle verbundenen Clients */
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("rndWorld:" + line);  // rndWorld:randomWorld-ID
            }
        }
    }


    /**
     * sendRemovePlayer         Aktuellste Liste der Spielernamen senden, nachdem Spieler entfernt wurde
     *
     * @param line              Packet vom Client, der Form -> rmPlys:ID:Name
     * */
    private void sendPlayerRemoveNames(String line) {
        if(line.contains("rmPl")) {
            int clientID = Integer.parseInt(line.split(":")[1]);
            line = line.split(":")[2];  // Spielername des zu entfernenden Spielers

            for(int i = 0; i < Server.playerNames.size(); i++) {
                if(Server.playerNames.get(i).equals(line)) {

                    for (int p = 0; p < Server.clients.size(); p++)
                    {
                        backToMenu(clientID);
                    }

                    Server.playerNames.remove(i);
                    //Server.clients.get(i).exit();
                    Server.clients.remove(i);
                    pw.println("exitClnt");
                    Server.clientIDs--;
                    Server.numberOfPlayers--;
                }
            }

            String allNames = "";
            for (String s : Server.playerNames) {
                allNames = allNames + s + ";";  // Alle Namen verketten: Name1;Name2;Name3;...
            }

            line = allNames;
            /* Senden des entfernten Spielernamen und seine ID */
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send("rmPlys:" + clientID + ":" + line);  // rmPlys:rmIndex:Name1;Name2;...
            }
        }
    }

    /**
     * backToMenu       Nachricht, zurueck ins Menu
     *
     * @param id        ID des Clienten
     * */
    public static void backToMenu(int id)
    {
        for (int i = 0; i < Server.clients.size(); i++) {
            Server.clients.get(i).send("backToMenu:" + id);  // backToMenu:ID
        }
    }

    /**
     * sendStartGame    Nachricht, starte Spiel
     *
     * @param line      Nachricht
     * */
    private void sendStartGame(String line) {
        if(line.contains("strtGame")) {
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send(line);
            }
        }
    }

    /**
     * sendPlayerMove   Nachricht, Spielerbewegungdaten
     *
     * @param line      Nachricht
     * */
    private void sendPlayerMove(String line) {
        if(line.contains("plyMove")) {
            for (int i = 0; i < Server.clients.size(); i++) {
                Server.clients.get(i).send(line);
            }
        }
    }

}













