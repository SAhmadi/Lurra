package Main;

import javax.sound.sampled.*;
import java.io.*;

/*
* Sound - Spielsounds
* */
public class Sound  {

    private Clip clip;
    private static  Volume volume = Volume.LOW;

    public static Sound elevatorSound;
    public static Sound diamondSound;

    /*
    * Konstruktor - Initialisieren
    * */
    public Sound(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sound/" + filename));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Lautstaerken
    * */
    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    /*
    * play - Abspielen der Clips
    * */
    public  void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /*
    * stop - Stoppen der Clips
    * */
    public void stop() {
        if(clip.isRunning())
            clip.stop();
    }

    /*
    * close - Schliessen des Clips
    * */
    public void close () {
        stop();
        clip.close();
    }

}