package LurraServer.Network;

import LurraServer.*;
import LurraServer.Network.packet.ConnectPacket;
import LurraServer.Network.packet.Packet;
import LurraServer.Network.packet.PacketDictionary;
import LurraServer.Network.packet.PacketType;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by moham_000, Amin and Halit
 */
public class NetworkServer { private ServerSocket socket;
    private boolean running = false;
    private int port;

    public NetworkServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            socket = new ServerSocket(port);
            LServer.getInstance().log("Server socket initialized on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                LServer.getInstance().log("Warten auf Clients...");

                while(running) {
                    try {
                        final Socket client = socket.accept();
                        LServer.getInstance().log("Client hat sich verbunden! " + client.getRemoteSocketAddress());

                        //Read individual client data
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                boolean error = false;
                                while(!error && client.isConnected()) {
                                    try {
                                        DataInputStream input = new DataInputStream(client.getInputStream());
                                        String rawData = input.readUTF();
                                        String[] data = rawData.trim().split(";");
                                        PacketType type = PacketType.valueOf(data[0]);
                                        Packet packet = PacketDictionary.translatePacketType(type, data);

                                        LServer.getInstance().log("Client:\n\t" + ((ConnectPacket) packet).i_username);

                                    } catch(EOFException e) {
                                        error = true;
                                        LServer.getInstance().log("Client " + client.getRemoteSocketAddress()
                                                + " hat sich ausgeloggt!");
                                    } catch (IOException e) {
                                        error = true;
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        running = true;
    }

    public void stopServer() {
        running = false;
    }
}

