package Main;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Abspielen, Stoppen und Schließen der Spielsounds
 *
 * @author Mo, Amin
 */
public class Sound
{

    private Clip clip;

    // Spielsounds
    public static Sound elevatorSound;
    public static Sound waterSound;
    public static Sound boomSound;
    public static Sound jumpSound;
    public static Sound metalSound;
    public static Sound earthSound;
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
    public static Sound themeButtonSound;
    public static Sound ironManButtonSound;
    public static Sound gameSound;
    public static Sound eatSound;
    public static Sound drinkSound;
    public static Sound heartBeatSound;
    public static Sound killSound;
    public static Sound ironManJumpSound;
    public static Sound ironManShootSound;
    public static Sound jarvisSound;
    public static Sound jarvisDeadSound;
    public static Sound hulkButtonSound;
    public static Sound hulkJumpSound;
    public static Sound hulkClapSound;
    public static Sound hulkBreathSound;
    public static Sound hulkDeathSound;
    public static Sound captainAmericaButtonSound;
    public static Sound captainAmericaJumpSound;
    public static Sound captainAmericaThrowSound;
    public static Sound captainAmericaEnoughSound;
    public static Sound captainAmericaDeathSound;
    public static Sound thorButtonSound;
    public static Sound thorJumpSound;
    public static Sound mjoelmirSound;
    public static Sound thorDeathSound;
    public static Sound blackWidowButtonSound;
    public static Sound blackWidowJumpSound;
    public static Sound blackWidowDeathSound;
    public static Sound gunShot;
    public static Sound specialButtonSound;
    public static Sound specialShootSound;
    public static Sound specialJumpSound;
    public static Sound specialDeathSound;
    public static Sound specialHeartBeatSound;
    public static Sound normalButtonSound;
    public static Sound tickSound;
    public static Sound beepSound;
    public static Sound tntSound;

    /**
     * Sound            Konstruktor der Sound-Klasse
     *
     * @param filename  Dateiname
     */
    public Sound(String filename)
    {
        try
        {
            InputStream audioSrc = getClass().getResourceAsStream("/sound/" + filename);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sound/" + filename));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);

        } catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    /**
     * play         Abspielen des Clips
     * */
    public void play()
    {
        if (clip == null) return;

        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * stop         Stoppen des Clips
     * */
    public void stop()
    {
        if (clip.isRunning()) clip.stop();
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