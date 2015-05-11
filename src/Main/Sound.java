package Main;

import javax.sound.sampled.*;
import java.io.*;

/*
* Sound - Spielsounds
* */
public class Sound {

    /*
    * playDiamondSound - Abspielen des Diamond-Sounds
    * */
    public static void playDiamondSound() {
        // Datei-Pfad
        String diamondSoundPath = "res/sound/bling.wav";

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(diamondSoundPath));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int)(af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            
           
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);
            clip.start();
           
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void playElevatoSround() {
        // Datei-Pfad
        String elevatorSoundPath = "res/sound/elevator.wav";

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(elevatorSoundPath));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int)(af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);


            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);
            clip.start();

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}