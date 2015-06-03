package Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Benutzerdefinierte Schrift
 *
 * @author Sirat
 */
public class CustomFont {

    /**
     * createCustomFont     Erstellen der benutzerdefinierten Schriftart
     *
     * @param filename      Dateiname als True-Type-Format
     * @param size          Schriftgroesse
     * @return Font         Rueckgabe der neu erzeugten Schrift
     */
    public static Font createCustomFont(String filename, float size) {

        String path = "res/font/" + filename;

        try {
            // Erstelle neue Schrift
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);

            // Registriere neuerstellte Schrift
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(path)));

            return customFont;

        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
