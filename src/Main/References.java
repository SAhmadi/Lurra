package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    // Exceptions
    public static boolean SHOW_EXCEPTION = true;

    // Game Over
    public static boolean GAME_OVER = false;

    // Menu Hintergrundverlauf
    public static Color NEON_GREEN = new Color(0 , 255, 84);
    public static Color LIGHT_BLUE = new Color(55 , 255, 215);
    public static Color WHITE_BLUE = new Color(201 , 250, 255);

    // Inventory
    public static final byte INVENTORY_BAR_CELL_SIZE = 48;

    // Statusbar
    public static final int HEALTH_CELL_WIDTH = 150;
    public static final int HEALTH_CELL_HEIGHT = 22;

    public static final int ENERGY_CELL_WIDTH = 74;
    public static final int ENERGY_CELL_HEIGHT = 30;

    public static final int THIRST_CELL_WIDTH = 110;
    public static final int THIRST_CELL_HEIGHT = 26;

    // Mouse Position
    public static int MOUSE_X;
    public static int MOUSE_Y;

    // Tiles
    public static final int TILE_SIZE = 16;

    // Tile IDs
    public static final byte DIRT = 1;
    public static final byte GRAS = 2;
    public static final byte WOOD = 3;
    public static final byte LEAF = 4;
    public static final byte ION = 5;
    public static final byte COPPER = 6;
    public static final byte SILVER = 7;
    public static final byte GOLD = 8;
    public static final byte RUBY = 9;
    public static final byte SAPHIRE = 10;
    public static final byte SMARAGD = 11;
    public static final byte DIAMOND = 12;
    public static final byte SAND = 13;
    public static final byte ICE = 14;
    public static final byte BLUEROCK_ON = 15;
    public static final byte BLUEROCK_OFF = 16;
    public static final byte BATTERY = 17;
    public static final byte NANDL = 18;
    public static final byte NANDR = 19;
    public static final byte STONE = 20;
    public static final byte STONE_RED = 21;
    public static final byte WATER = 22;

    public static final byte BURGER = 31;
    public static final byte POTION = 32;
    public static final byte WOODEN_BOARD = 33;
    public static final byte SWITCH_OFF = 34;
    public static final byte SWITCH_ON = 35;
    public static final byte TORCH = 36;
    public static final byte TNT = 37;


    public static final byte PICK = 41;
    public static final byte AXE = 42;
    public static final byte HAMMER = 43;
    public static final byte PURPLE_GUN = 44;
    public static final byte SWORD = 45;

    public static final byte ARMOR_ION = 51;
    public static final byte ARMOR_IRONMAN = 52;
    public static final byte ARMOR_CAP = 53;

    // Verbaubar Tile IDs
    public static final byte[] SHEETINGABLE_TILES = { STONE, STONE_RED, SAND, BATTERY, BLUEROCK_OFF, SWITCH_OFF, NANDR, NANDL};

    // GETTER UND SETTER
    /**
     * getTextures      Rueckgabe der passenden Texture
     *
     * @param id        Id
     * @return          Bild
     * */
    public static BufferedImage getTextureById(byte id)
    {
        if (id == References.DIRT) return ResourceLoader.dirt;
        else if (id == References.GRAS) return ResourceLoader.grasWithFlower;
        else if (id == References.WOOD) return ResourceLoader.treeTrunkRoot;
        else if (id == References.LEAF) return ResourceLoader.leaf;
        else if (id == References.ION) return ResourceLoader.ion;
        else if (id == References.COPPER) return ResourceLoader.copper;
        else if (id == References.SILVER) return ResourceLoader.silver;
        else if (id == References.GOLD) return ResourceLoader.gold;
        else if (id == References.RUBY) return ResourceLoader.ruby;
        else if (id == References.SAPHIRE) return ResourceLoader.saphire;
        else if (id == References.SMARAGD) return ResourceLoader.smaragd;
        else if (id == References.DIAMOND) return ResourceLoader.diamond;
        else if (id == References.SAND) return ResourceLoader.sandTop;
        else if (id == References.ICE) return ResourceLoader.iceTop;
        else if (id == References.STONE) return ResourceLoader.stoneWhiteLeafs;
        else if (id == References.STONE_RED) return ResourceLoader.stoneRedLeafs;
        else if (id == References.WATER) return ResourceLoader.water;

        else if (id == References.TORCH) return ResourceLoader.torch;
        else if (id == References.WOODEN_BOARD) return ResourceLoader.woodenBoard;
        else if (id == References.AXE) return ResourceLoader.axe;
        else if (id == References.PICK) return ResourceLoader.stonePick;
        else if (id == References.HAMMER) return ResourceLoader.hammer;
        else if (id == References.SWORD) return ResourceLoader.sword;
        else if (id == References.PURPLE_GUN) return ResourceLoader.gunPurple;
        else if (id == References.ARMOR_ION) return ResourceLoader.ionIced;    // TODO
        else if (id == References.ARMOR_IRONMAN) return ResourceLoader.rubyIced;    // TODO
        else if (id == References.ARMOR_CAP) return ResourceLoader.saphireIced; // TODO
        else if (id == References.BURGER) return ResourceLoader.burger;
        else if (id == References.POTION) return ResourceLoader.healthPotion;
        else if (id == References.TNT) return ResourceLoader.tnt;
        else if (id == References.BLUEROCK_OFF) return ResourceLoader.bluerockOff;
        else if (id == References.BLUEROCK_ON) return ResourceLoader.bluerockOn;
        else if (id == References.BATTERY) return ResourceLoader.battery;
        else if (id == References.SWITCH_OFF) return ResourceLoader.switchOff;
        else if (id == References.SWITCH_ON) return ResourceLoader.switchOn;
        else if (id == References.NANDL) return ResourceLoader.NANDL;
        else if (id == References.NANDR) return ResourceLoader.NANDR;
        else return null;
    }

}
