package Main;

import javax.sound.sampled.*;

/**
 * Absppielen, Stoppen und Schließen der Spielsounds
 *
 * @author Mo, Amin
 */
public class Sound
{

    private Clip clip;

    // Spielsounds
    public static Sound elevatorSound;
    public static Sound diamondSound;
    public static Sound boomSound;
    public static Sound walkSound;
    public static Sound jumpSound;
    public static Sound metalSound;
    public static Sound earthSound;
    public static Sound desertSound;
    public static Sound jungleSound;
    public static Sound alaskaSound;
    public static Sound woodSound;
    public static Sound explosionSound;
    public static Sound startButtonSound;
    public static Sound settingButtonSound;
    public static Sound localButtonSound;
    public static Sound onlineButtonSound;
    public static Sound backButtonSound;
    public static Sound avatarButtonSound;
    public static Sound continueButtonSound;
    public static Sound newGameButtonSound;
    public static Sound soundButtonSound;
    public static Sound createButtonSound;
    public static Sound joinButtonSound;
    public static Sound spectateButtonSound;
    public static Sound endStartButtonSound;
    public static Sound onButtonSound;
    public static Sound offButtonSound;
    public static Sound gameSound;


    /**
     * Sound            Konstruktor der Sound-Klasse
     *
     * @param filename  Dateiname
     */
    public Sound(String filename)
    {

        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sound/" + filename));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * play         Abspielen des Clips
     * */
    public void play()
    {
        if (clip == null)
            return;

        stop();
        clip.setFramePosition(0);
        clip.start();
    }



    /**
     * stop         Stoppen des Clips
     * */
    public void stop()
    {
        if (clip.isRunning())
        {
            clip.stop();

        }
    }

    /**
     * continues    Fortsetzen des Clips
     * */
    public void continues()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    /**
     * close        Schliessen des Clips
     * */
    public void close()
    {
        stop();
        clip.close();
    }

}