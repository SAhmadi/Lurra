package Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/*
*
* */
public class CustomFont {

    public static Font createCustomFont(String filename, float size) {
        String path = "res/font/" + filename;

        try {
            // Erstelle neues Font
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);


            // Registriere neuerstelltes Font, damit man es nutzen kann
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(path)));

            return customFont;

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
