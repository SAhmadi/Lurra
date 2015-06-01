package Main;

import Main.packet.*;


import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by moham_000, Amin, Halit and Vanessa
 */
public class Client {

    public Socket socket;
    private String ipAddress;
    private int serverPort;

    public Client(String ipAddress, int serverPort) {
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;
    }

    public void connectToServer() {
        try {
            socket = new Socket(ipAddress, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String nickname = JOptionPane.showInputDialog(null, "Tippe Name um Spiel zu starten: ", " ", JOptionPane.QUESTION_MESSAGE);
        try {
            sendPacket(new ConnectPacket(nickname));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Client listens for server requests/responses
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        String rawData = input.readUTF();
                        String[] data = rawData.trim().split(";");
                        PacketType type = PacketType.valueOf(data[0]);
                        Packet packet = PacketDictionary.translatePacketType(type, data);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void sendPacket(Packet packet) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeUTF(packet.getOutgoingData());
    }
}