package Assets.GameObjects;

import java.awt.image.BufferedImage;

/**
 * Erstellen von Waffen
 *
 * @author Sirat, Halit
 * */
public class Weapon
{
    // Waffen IDs
    public static final int PICKE_ID = 1;
    public static final int AXE_ID = 2;
    public static final int HAMMER_ID = 3;
    public static final int PURPLE_GUN_ID = 4;

    // Waffen Schaden
    public static final int PICKE_DAMAGE = 1;
    public static final int AXE_DAMAGE = 4;
    public static final int HAMMER_DAMAGE = 3;
    public static final int PURPLE_GUN_DAMAGE = 5;

    // Waffen Reichweite
    public static final int PICKE_RANGE = 2;
    public static final int AXE_RANGE = 1;
    public static final int HAMMER_RANGE = 2;
    public static final int PURPLE_GUN_RANGE = 150;

    // Waffe
    private BufferedImage texture;
    private String name;
    private int id;

    private int damage;
    private int range;

    /**
     * Weapon       Konstruktor der Weapon-Klasse
     *
     * @param texture   Texture der Waffe
     * @param name      Name der Waffe
     * @param id        ID der Waffe
     * @param damage    Zufuegbarer Schaden der Waffe
     * @param range     Reichweite der Waffe
     * */
    public Weapon(BufferedImage texture, String name, int id, int damage, int range)
    {
        this.texture = texture;
        this.name = name;
        this.id = id;

        this.damage = damage;
        this.range = range;
    }

}
