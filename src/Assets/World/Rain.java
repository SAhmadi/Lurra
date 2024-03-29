package Assets.World;

import Main.References;

import java.awt.*;
import java.util.Random;

/**
 * Erzeugen von Regen
 *
 * @author Sirat
 * */
public class Rain
{
    // zufaelliger Regen
    private boolean isRaining;

    private int x;
    private int y;

    private byte width = 1;
    private byte height = 5;

    /**
     * Rain                 Konstruktor der Rain-Klasse
     * */
    public Rain()
    {
        this.isRaining = false;
    }

    /**
     * render               Zeichnen des Regens
     *
     * @param g             Graphics Objekt
     * */
    public void render(Graphics g)
    {
        if (isRaining && new Random().nextInt(1000) < 30)
            isRaining = false;

        if (!isRaining && new Random().nextInt(1000) < 50)
            isRaining = true;

        if (isRaining)
        {
            x = new Random().nextInt(References.SCREEN_WIDTH - width);
            y = new Random().nextInt(References.SCREEN_HEIGHT - References.SCREEN_HEIGHT/3 - height);

            g.setColor(new Color(230, 230, 230));
            g.fillRect(x, y, width, height);
        }
    }

}
