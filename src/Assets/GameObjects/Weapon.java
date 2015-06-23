package Assets.GameObjects;

import java.awt.image.BufferedImage;

/**
 * Erstellen von Waffen
 *
 * @author Sirat, Halit
 * */
public class Weapon
{

    /* Waffen IDs */
    public static final int PICKE_ID = 1;
    public static final int AXE_ID = 2;
    public static final int HAMMER_ID = 3;
    public static final int PURPLE_GUN_ID = 4;

    /* Waffen Schadem */
    public static final int PICKE_DAMAGE = 1;
    public static final int AXE_DAMAGE = 4;
    public static final int HAMMER_DAMAGE = 3;
    public static final int PURPLE_GUN_DAMAGE = 5;

    /* Waffen Reichweite */
    public static final int PICKE_RANGE = 2;
    public static final int AXE_RANGE = 1;
    public static final int HAMMER_RANGE = 2;
    public static final int PURPLE_GUN_RANGE = 150;


    private BufferedImage texture;
    private String name;
    private int id;

    private int damage;
    private int range;

    /**
     * Konstruktor der Klasse Weapon
     * */
    public Weapon(BufferedImage texture, String name, int id, int damage, int range)
    {
        this.texture = texture;
        this.name = name;
        this.id = id;

        this.damage = damage;
        this.range = range;
    }

    public static int getAxeId() {
        return AXE_ID;
    }

    public static int getPickeId() {
        return PICKE_ID;
    }

    public static int getHammerId() {
        return HAMMER_ID;
    }

    public static int getPurpleGunId() {
        return PURPLE_GUN_ID;
    }
}