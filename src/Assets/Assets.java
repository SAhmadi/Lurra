package Assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
* Assets - Laden der im Spiel verwendeten Bilder
* */
public class Assets {
    
    // Bild-Set
    private BufferedImage assetsSet;


    /*
    * Konstruktor - Initialisieren
    * 
    * @param resPath    - Resource Pfad des Bild-Sets
    * */
    public Assets(String resPath) {
        try {
            this.assetsSet = ImageIO.read(getClass().getResourceAsStream(resPath));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
    * getSubimage - Ausschneiden eines einzelnen Bildes aus dem Set
    *
    * @param x          - x-Koordinate Anfangspunkt
    * @param y          - y-Koordinate Anfangspunkt
    * @param width      - Breite des Bildes
    * @param height     - Höhe des Bildes
    * */
    public BufferedImage getSubimage(int x, int y, int width, int height) {
        return assetsSet.getSubimage(x, y, width, height);
    }

    /////////////// Setter und Getter //////////////
    /*
    * getAssetsSet - Rückgabe des Bild-Sets
    * */
    public BufferedImage getAssetsSet() { return this.assetsSet; }

}
