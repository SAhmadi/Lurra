package Main.packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moham_000, Amin und Halit
 */
public abstract class Packet {

    private List<String> dataList = new ArrayList<String>();
    private PacketType packetType;

    //Read the incoming data from the same packet
    public Packet(String[] rawData) {
        for(String segment : rawData) {
            dataList.add(segment);
        }
    }

    //Outgoing packet constructor
    public Packet(PacketType type) {
        this.packetType = type;
    }

    protected String getData(int index) {
        return dataList.get(index);
    }

    //Outgoing packet methods
    protected void addData(String data) {
        addData(data, 0);
    }

    protected void addData(String data, int index) {
        dataList.add(index, data);
    }

    protected abstract void indexOutgoingData();

    public String getOutgoingData() {
        //Request the child packets to create the raw data list to be sent to the client
        dataList.clear();
        indexOutgoingData();
        return compileOutgoingData();
    }

    protected String compileOutgoingData() {
        StringBuffer buffer = new StringBuffer(packetType.name()).append(";");
        //CONNECT;
        for(int i = dataList.size() - 1; i >= 0; i--) {
            String data = dataList.get(i);
            buffer.append(data).append(";");
        }
        return buffer.toString();
    }
}