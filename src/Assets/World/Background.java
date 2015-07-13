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
    public int dayTimer = 4150;

    private float counter;

    /**
      Background        Konstruktor der Background-Klasse
     * */
    public Background()
    {
        isDay = true;
        counter = 0;
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
            counter += 0.5;
            if (counter >= 10.0)
            {
                opacity++;
                counter = 0;
            }

            if (opacity > 200) opacity = 200;
        }
        else
        {

            counter += 0.5;
            if (counter >= 10.0)
            {
                opacity--;
                counter = 0;
            }

            if (opacity < 0) opacity = 0;
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
