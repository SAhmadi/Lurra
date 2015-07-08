package Assets.World;

import Main.References;

import java.awt.*;

/**
 * Hintergrund eines Levels
 *
 * @author Sirat
 * */
public class Background
{

    // Himmelzeit
    public static boolean isDay;

    // Farbe
    public static int opacity = 0;

    public static int red = 10;
    public static int green = 10;
    public static int blue = 10;

    public int dayFrame = 0;
    public int dayTimer = 500;

    /**
      Background        Konstruktor der Background-Klasse
     * */
    public Background()
    {
        isDay = true;
    }

    /**
     * update       Aktualisieren der Abdaemmerung
     * */
    public void update()
    {
        if (dayFrame >= dayTimer)
        {
            isDay = !isDay;
            dayFrame = 0;
        }
        else
            dayFrame++;

        if (isDay)
        {
            opacity += dayFrame/200;
            if (opacity > 200)
                opacity = 200;
        }
        else
        {
            opacity -= dayFrame/200;
            if (opacity < 0)
                opacity = 0;
        }
    }

    /**
     * render       Zeichnen der Abdaemmerung
     *
     * @param g     Graphics Objekt
     * */
    public void render(Graphics g)
    {
        g.setColor(new Color(red, green, blue, opacity));
        g.fillRect(0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
    }

}
