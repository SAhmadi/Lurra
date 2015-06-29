package Assets.GameObjects;

import java.awt.image.BufferedImage;

/**
 * Animation der Spielobjekte
 *
 * @author Sirat
 * */
public class Animation
{

    // Animation
    private BufferedImage[] frames;
    private int activeFrame;
    private boolean wasPlayed;

    // Dauer
    private long startTime;
    private long frameHoldTime;


    /**
     * Animation        Konstruktor der Animation-Klasse
     * */
    public Animation()
    {
        this.activeFrame = 0;
        this.wasPlayed = false;
    }

    /**
     * init             Initialisieren der Animation
     *
     * @param frames    Bildsequenz
     * */
    public void init(BufferedImage[] frames)
    {
        this.frames = frames;
        this.activeFrame = 0;
        this.wasPlayed = false;
        this.startTime = System.nanoTime();
    }

    /**
     * update           Aktualisieren der Animation
     * */
    public void update()
    {
        if(frameHoldTime < 0) return;

        long deltaTime = System.nanoTime() - startTime;
        deltaTime /= 1000000;

        if(deltaTime > frameHoldTime)
        {
            activeFrame++;
            startTime = System.nanoTime();
        }

        if(activeFrame == frames.length)
        {
            activeFrame = 0;
            wasPlayed = true;
        }
    }

    // GETTER UND SETTER
    /**
     * getActiveFrame           Rueckgabe der aktuellen Bild-ID
     * @return int              Bild-ID
     * */
    public int getActiveFrame() { return this.activeFrame; }

    /**
     * setActiveFrame           Setzen der aktuellen Bild-ID
     * @param activeFrame       Aktuelle Bild-ID
     * */
    public void setActiveFrame(int activeFrame) { this.activeFrame = activeFrame; }

    /**
     * getActiveFrameImage      Rueckgabe des aktuellen Bildes
     * @return BufferedImage    Aktuelles Bild
     * */
    public BufferedImage getActiveFrameImage() { return this.frames[this.activeFrame]; }

    /**
     * setFrameHoldTime         Setzen der Haltezeit eines Bildes
     * @param frameHoldTime     Haltezeit in ms
     * */
    public void setFrameHoldTime(long frameHoldTime) { this.frameHoldTime = frameHoldTime; }

    /**
     * getWasPlayed             Rückgabe, ob Bildsequenz bereits einmal durchlaufen wurde
     * @return boolean          Wert, ob bereits einmal durchlaufen wurde
     * */
    public boolean getWasPlayed() { return this.wasPlayed; }

    /**
     * setWasPlayed             Setzen, ob Bildsequenz bereits einmal durchlaufen wurde
     * @param wasPlayed         Wert, ob einmal durchlaufen wurde
     * */
    public void setWasPlayed(boolean wasPlayed) { this.wasPlayed = wasPlayed; }

}




