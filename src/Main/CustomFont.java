package Main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Benutzerdefinierte Schrift
 *
 * @author Sirat
 */
public class CustomFont
{

    /**
     * createCustomFont     Erstellen der benutzerdefinierten Schriftart
     *
     * @param filename      Dateiname als True-Type-Format
     * @return Font         Rueckgabe der neu erzeugten Schrift
     */
    public static Font createCustomFont(String filename)
    {
        try
        {
            InputStream inputStream = CustomFont.class.getResourceAsStream("/font/" + filename);

            // Erstelle neue Schrift
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);

            // Registriere neuerstellte Schrift
            //GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, inputStream));
        }
        catch (FontFormatException | IOException ex) { ex.printStackTrace(); }

        return null;
    }

}
