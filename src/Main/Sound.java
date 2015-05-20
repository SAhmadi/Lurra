package Main;

import Assets.Tile;

import javax.sound.sampled.*;
import java.applet.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
* Sound - Spielsounds
* */
public class Sound extends Applet {

    public static boolean isSoundOn;
    public static Clip elevatorClip;

    public static void getIsSoundOn() {
        Scanner scanner;
        boolean line;

        try {
            scanner = new Scanner(new FileReader("res/sound/soundSettings.txt"));
            line = scanner.nextBoolean();

            if(line == true)
                isSoundOn = true;
            else
                isSoundOn = false;

            scanner.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void setIsSoundOn() {
        try {
            File file = new File("res/sound/soundSettings.txt");

            if (file.exists()) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                if(isSoundOn == true)
                    bw.write("true");
                else {
                    bw.write("false");
                    System.out.print("false wurde in file geschrieben!");
                }

                bw.close();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }


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

    /*
    * playElevatorSound - Abspielen der Startmusik
    * */
    public static void initElevatorSound() {
        // Datei-Pfad
        String elevatorSoundPath = "res/sound/elevator.wav";

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(elevatorSoundPath));
            AudioFormat af = audioInputStream.getFormat();

            int size = (int)(af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];

            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            elevatorClip = (Clip) AudioSystem.getLine(info);
            elevatorClip.isRunning();
            elevatorClip.open(af, audio, 0, size);
            //elevatorClip.start();

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playElevatorSound() {
        if(isSoundOn) {
            elevatorClip.start();
            while(true)
                elevatorClip.loop(10);
        }
    }

    public static void stopElevatorSound() {
        if(!isSoundOn) {
            elevatorClip.stop();
            elevatorClip.loop(0);
            elevatorClip.flush();
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