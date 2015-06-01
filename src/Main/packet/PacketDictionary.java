package Main.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moham_000, Amin and Halit
 */
public class PacketDictionary {

    private static final Map<PacketType, Class<? extends Packet>> PACKET_DICTIONARY =
            new HashMap<PacketType, Class<? extends Packet>>();

    static {
        PACKET_DICTIONARY.put(PacketType.CONNECT, ConnectPacket.class);
    }

    public static Packet translatePacketType(PacketType type, String[] data) {
        Class clazz = PACKET_DICTIONARY.get(type);
        if(clazz != null) {
            try {
                return (Packet) clazz.getConstructor(String[].class).newInstance((Object) data);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}