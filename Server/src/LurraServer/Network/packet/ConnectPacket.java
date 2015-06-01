package LurraServer.Network.packet;

/**
 * Created by MrDeathJockey on 18/01/15.
 */
public class ConnectPacket extends Packet {

    //Incoming constructor
    public String i_username;
    public ConnectPacket(String[] rawData) {
        super(rawData);

        i_username = getData(1);
    }

    //Outgoing constructor
    private String o_username;
    public ConnectPacket(String username) {
        super(PacketType.CONNECT);

        this.o_username = username;
    }

    @Override
    protected void indexOutgoingData() {
        addData(o_username);
    }
}