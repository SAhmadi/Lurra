package Assets;

import com.sun.deploy.ui.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
* Assets - Laden der im Spiel verwendeten Bilder
* */
public class Assets {

    private BufferedImage assetsSet;

    public Assets(String resPath) {
        try {
            this.assetsSet = ImageIO.read(getClass().getResourceAsStream(resPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public BufferedImage getSubimage(int x, int y, int width, int height) {
        return assetsSet.getSubimage(x, y, width, height);
    }

}
