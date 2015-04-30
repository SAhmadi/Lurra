import javax.sound.sampled.*;
import java.io.*;

/**
 *  
 * 
 * @author Amin Oulad; Mo Cobay Hafid 
 * @version  30042015 
 * Die Datei unbedingt im selben Verzeichnis speichern!
 */

public class Sound { 
    
    public Sound() {
    }

public static void playDiamondSound() {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("bling.wav"));
            AudioFormat af     = audioInputStream.getFormat();
            int size      = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio       = new byte[size];
            DataLine.Info info      = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            
           
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(af, audio, 0, size);
                clip.start();
           
        }catch(Exception e){ e.printStackTrace(); }
        
    }
}