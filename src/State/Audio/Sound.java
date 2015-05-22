package State.Audio;

import javax.sound.sampled.*;
import java.io.*;

/*
* Sound - Spielsounds
* */

public class Sound  {
    public Clip clip;
    public Sound (String s) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s));
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


    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }
    public static  Volume volume = Volume.LOW;


    /*
    * playDiamondSound - Abspielen des Diamond-Sounds
    * */
   /* public static void playDiamondSound() {
        // Datei-Pfad
        String diamondSoundPath = "res/sound/bling.wav";

       /* try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(diamondSoundPath));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);


            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    //}

    /*
    * playElevatorSound - Abspielen der Startmusik
    * */
    public  void play() {
        if (clip == null) return;
            stop();
        clip.setFramePosition(0);
        clip.start();
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if(clip.isRunning())
            clip.stop();
    }

    public void close () {
        stop();
        clip.close();
    }

   /* public static void playElevatorSound() {

        // Datei-Pfad
        String elevatorSoundPath = "res/sound/elevator.wav";

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(elevatorSoundPath));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.isRunning();
            clip.open(af, audio, 0, size);

            if (volume != Volume.MUTE) {
                if (clip.isRunning())
                    clip.stop();
                clip.setFramePosition(0);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {clip.stop();}


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/





}





