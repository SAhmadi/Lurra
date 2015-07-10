package Assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Laden des Animations-Set eines Objekts
 *
 * @author Sirat
 * */
public class Assets
{

    // Bild-Set
    private static BufferedImage assetsSet;

    /**
     * Assets           Konstruktor der Assets-Klasse
     *
     * @param resPath   Ressource-Pfad des Sets
     * */
    public Assets(String resPath)
    {
        try { assetsSet = ImageIO.read(getClass().getResourceAsStream(resPath)); }
        catch (IOException ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    /**
     * getSubimage      Ausschneiden eines einzelnen Bildes aus dem Set
     *
     * @param x         x-Koordinate Anfangspunkt
     * @param y         y-Koordinate Anfangspunkt
     * @param width     Breite des Bildes
     * @param height    Höhe des Bildes
     *
     * @return          Das ausgeschnittene Bild
     * */
    public BufferedImage getSubimage(int x, int y, int width, int height) { return assetsSet.getSubimage(x, y, width, height); }

}