package Main;

import javax.sound.sampled.*;

/**
 * Absppielen, Stoppen und Schließen der Spielsounds
 * Sound -      Spielsounds
 * */
public class Sound {

    private Clip clip;
    //private static  Volume volume = Volume.LOW;

    public static Sound elevatorSound;
    public static Sound diamondSound;

    /**
     * Sound -      Konstruktor der Sound Klasse
     */
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Volume -     Lautstaerken
     */
    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    /**
     * play -       Abspielen der Clips
     */
    public void play() {
        if (clip == null)
            return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * stop -       Stoppen der Clips
     */
    public void stop() {
        if (clip.isRunning())
            clip.stop();
    }

    /**
     * continues -  Abspielen in einer Endlos-Schleife
     */
    public void continues() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    /**
     * close -      Schließen des Clips
     */
    public void close() {
        stop();
        clip.close();
    }
}

