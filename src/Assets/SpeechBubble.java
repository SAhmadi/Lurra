package Assets;

import Main.References;
import Main.ResourceLoader;
import Main.Tutorial;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.charset.Charset;

/**
 * Sprechblase fuer Informationsausgabe auf dem Bildschirm
 *
 * @author Sirat
 * @version 0.9
 * */
public class SpeechBubble extends Rectangle
{

    // Sprechblasen Farbe
    private final Color WHITE_ALPHA = new Color(255, 255, 255, 210);

    // Sprechblasen Polster innen
    private byte padding;

    // Auszugebender Text
    private String printString;

    // Sprechblase anzeigen, verbergen
    private boolean printSpeechBubble;

    // Hilfstext
    private String infoText;

    /**
     * SpeechBubble         Konstruktor der SpeechBubble Klasse
     * */
    public SpeechBubble()
    {
        this.padding = 10;
        this.printSpeechBubble = false;

        this.infoText = "Enter...";
        Charset.forName("UTF-8").encode(infoText);

        setBounds(
                References.SCREEN_WIDTH / 2 - 500 / 2,
                References.SCREEN_HEIGHT / 4,
                500,
                ResourceLoader.textFieldFontBold.deriveFont(14f).getSize() + 2 * padding
        );
    }

    /**
     * createSpeechBubble       Initialisieren einer Sprechblase
     *
     * @param text              Text
     * */
    public void createSpeechBubble(String text)
    {
        printString = text;
        Charset.forName("UTF-8").encode(printString);

        printSpeechBubble = true;
    }

    /**
     * render       Zeichnen der Sprechblase
     *
     * @param g     Graphics Objekt
     * */
    public void render(Graphics2D g)
    {
        if (Tutorial.currentTut == Tutorial.TUT_CLOSE_TUT)
            printSpeechBubble = false;

        if (printSpeechBubble)
        {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setFont(ResourceLoader.textFieldFontBold.deriveFont(12f));

            g.setColor(WHITE_ALPHA);
            g.fillRect(super.x, super.y, super.width, super.height);

            g.setColor(Color.BLACK);

            if (printString.toCharArray().length > super.width/ResourceLoader.textFieldFontBold.deriveFont(14f).getSize())
            {
                setBounds(
                        References.SCREEN_WIDTH / 2 - 500 / 2,
                        References.SCREEN_HEIGHT/4,
                        500,
                        3*ResourceLoader.textFieldFontBold.deriveFont(14f).getSize() + 2*padding
                );
            }

            g.drawString(
                    printString,
                    super.x + padding / 2,
                    super.y + 2 * padding
            );

            // Hilfstext
            g.setFont(ResourceLoader.textFieldFontBold.deriveFont(10f));
            g.drawString(
                    infoText,
                    super.x + padding + g.getFontMetrics(ResourceLoader.textFieldFontBold.deriveFont(12f)).stringWidth(printString),
                    super.y + 2*padding
            );
        }
    }

    // EVENTS
    /**
     * keyPressed       Tastendruck
     *
     * @param e         Tastenevent
     * */
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) printSpeechBubble = !printSpeechBubble;
    }

}
