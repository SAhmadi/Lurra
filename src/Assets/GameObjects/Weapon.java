package Assets.GameObjects;

import java.awt.image.BufferedImage;

/**
 * Erstellen von Waffen
 *
 * @author Sirat, Halit
 * */
public class Weapon
{
    // Waffen Schaden
    public static final byte PICK_DAMAGE = 1;
    public static final byte AXE_DAMAGE = 4;
    public static final byte HAMMER_DAMAGE = 3;
    public static final byte SWORD_DAMAGE = 10;
    public static final byte PURPLE_GUN_DAMAGE = 5;

    // Waffen Reichweite
    public static final byte PURPLE_GUN_RANGE = 127;

    // Waffe
    private BufferedImage texture;
    private String name;
    private byte id;

    private byte damage;
    private byte range;

    /**
     * Weapon       Konstruktor der Weapon-Klasse
     *
     * @param texture   Texture der Waffe
     * @param name      Name der Waffe
     * @param id        ID der Waffe
     * @param damage    Zufuegbarer Schaden der Waffe
     * @param range     Reichweite der Waffe
     * */
    public Weapon(BufferedImage texture, String name, byte id, byte damage, byte range)
    {
        this.texture = texture;
        this.name = name;
        this.id = id;

        this.damage = damage;
        this.range = range;
    }

}
