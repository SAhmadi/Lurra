package Main;

import java.awt.*;

/**
 * Referenzvariabeln des Spiels
 *
 * @author Sirat
 * */
public class References
{

    // Spieldaten
    public static String TITLE = "Lurra";

    // Bildschirmgroesse
    public static int SCREEN_WIDTH = 1024;
    public static int SCREEN_HEIGHT = 576;

    // Menu Hintergrundverlauf
    public static Color NEON_GREEN = new Color(0 , 255, 84);
    public static Color LIGHT_BLUE = new Color(55 , 255, 215);
    public static Color WHITE_BLUE = new Color(201 , 250, 255);

    // Tiles
    public static final int TILE_SIZE = 16;

    public static final byte INVENTORY_BAR_CELL_SIZE = 48;

    public static final int HEALTH_CELL_WIDTH = 150;
    public static final int HEALTH_CELL_HEIGHT = 22;

    public static final int ENERGY_CELL_WIDTH = 74;
    public static final int ENERGY_CELL_HEIGHT = 30;

    public static final int THIRST_CELL_WIDTH = 110;
    public static final int THIRST_CELL_HEIGHT = 26;

    // Mouse Position
    public static int MOUSE_X;
    public static int MOUSE_Y;

    // Exceptions
    public static boolean SHOW_EXCEPTION = true;

    // Game Over
    public static boolean GAME_OVER = false;

}
