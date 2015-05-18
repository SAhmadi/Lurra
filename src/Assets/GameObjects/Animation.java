package Assets.GameObjects;

import java.awt.image.BufferedImage;

/*
* Animation - Animation der Spielobjekte
* */
public class Animation {

    private BufferedImage[] frames;
    private int activeFrame;
    private boolean wasPlayed;

    private long startTime;
    private long frameHoldTime;
    private long deltaTime;


    /*
    * Konstruktor - Initialisieren
    * */
    public void Animation() {
        this.activeFrame = 0;
        this.wasPlayed = false;
    }

    /*
    * init - Initialisieren der aktiven Bildsequenz
    * */
    public void init(BufferedImage[] frames) {
        this.frames = frames;
        this.activeFrame = 0;
        this.wasPlayed = false;
        this.startTime = System.nanoTime();
    }

    /*
    * update - Update der aktiven Bildsequenz
    * */
    public void update() {
        if(frameHoldTime < 0)
            return;
        deltaTime = System.nanoTime() - startTime;
        deltaTime /= 1000000;

        if(deltaTime > frameHoldTime) {
            activeFrame++;
            startTime = System.nanoTime();
        }

        if(activeFrame == frames.length) {
            activeFrame = 0;
            wasPlayed = true;
        }
    }

    //////////// Setter und Getter ////////////
    /*
    * setActiveFrame - Setzen des aktiven Bildes-ID
    *
    * @param activeFrame    - aktives Bild-ID
    * */
    public void setActiveFrame(int activeFrame) { this.activeFrame = activeFrame; }

    /*
    * getActiveFrame - Rückgabe des aktiven Bildes-ID
    * */
    public int getActiveFrame() { return this.activeFrame; }

    /*
    * setFrameHoldTime - Setzen der Haltezeit eines Bildes
    *
    * @param frameHoldTime      - Haltezeit in Millisekunden
    * */
    public void setFrameHoldTime(long frameHoldTime) { this.frameHoldTime = frameHoldTime; }

    /*
    * getActiveFrameImage - Rückgabe des aktiven Bildes
    * */
    public BufferedImage getActiveFrameImage() { return this.frames[this.activeFrame]; }

    /*
    * getWasPlayed - Rückgabe, ob Bildsequenz bereits einmal durchlaufen wurde
    * */
    public boolean getWasPlayed() { return this.wasPlayed; }

}




