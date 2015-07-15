package Assets.Crafting;

import Main.References;

import java.util.Map;

/**
 * Rezepte die gemixt werden koennen
 *
 * @author Sirat, Carola
 * */
public class Recipe
{

    // Rezepte
    public static byte[] recipes = {
            References.TORCH, References.WOODEN_BOARD, References.AXE, References.SWORD, References.ARMOR_ION,
            References.ARMOR_IRONMAN, References.ARMOR_CAP, References.BURGER, References.POTION, References.TNT,
            References.BLUEROCK_OFF, References.BATTERY, References.SWITCH_OFF, References.NANDL, References.NANDR,
            References.STONE_RED
    };

    /**
     * initRecipes          Initialisieren der Rezepte
     *
     * @param id            Karten ID
     * @param ingredients   Zutatenliste
     * */
    public static void initRecipes(byte id, Map<Byte, Byte> ingredients)
    {
        // Fackel
        if (id == References.TORCH)
        {
            ingredients.put(References.WOOD, (byte) 3);
            ingredients.put(References.GRAS, (byte) 3);
            ingredients.put(References.STONE, (byte) 1);
        }
        // Holzbrett
        else if (id == References.WOODEN_BOARD)
        {
            ingredients.put(References.WOOD, (byte) 3);
        }
        // Ziegelstein
        else if (id == References.STONE_RED)
        {
            ingredients.put(References.DIRT, (byte) 1);
        }
        // Axt
        else if (id == References.AXE)
        {
            ingredients.put(References.DIRT, (byte) 5);
            ingredients.put(References.GRAS, (byte) 5);
        }
        // Schwert
        else if (id == References.SWORD)
        {
            ingredients.put(References.DIRT, (byte) 10);
            ingredients.put(References.ION, (byte) 1);
            ingredients.put(References.COPPER, (byte) 1);
        }
        // Eisenruestung
        else if (id == References.ARMOR_ION)
        {
            ingredients.put(References.STONE, (byte) 4);
            ingredients.put(References.ION, (byte) 5);
        }
        // Ironman Ruestung
        else if (id == References.ARMOR_IRONMAN)
        {
            ingredients.put(References.RUBY, (byte) 5);
            ingredients.put(References.GOLD, (byte) 4);
        }
        // Ironman Ruestung
        else if (id == References.ARMOR_CAP)
        {
            ingredients.put(References.SAPHIRE, (byte) 5);
            ingredients.put(References.SILVER, (byte) 4);
        }
        // Burger
        else if (id == References.BURGER)
        {
            ingredients.put(References.DIRT, (byte) 4);

        }
        // Heiltrank
        else if (id == References.POTION)
        {
            ingredients.put(References.DIRT, (byte) 1);
            ingredients.put(References.ION, (byte) 1);
            ingredients.put(References.ICE, (byte) 1);
        }
        // TNT
        else if (id == References.TNT)
        {
            ingredients.put(References.DIRT, (byte) 3);
            //ingredients.put(References.ION, (byte) 2);
        }
        // Bluerock
        else if (id == References.BLUEROCK_OFF)
        {
            //ingredients.put(References.DIRT, (byte) 1);
            ingredients.put(References.COPPER, (byte) 5);
        }
        // Batterie
        else if (id == References.BATTERY)
        {
            //ingredients.put(References.DIRT, (byte) 1);
            ingredients.put(References.DIAMOND, (byte) 9);
        }
        // Schalter
       /* else if (id == References.SWITCH_OFF)
        {
            //ingredients.put(References.DIRT, (byte) 1);
            ingredients.put(References.COPPER, (byte) 6);
        }*/
        // NANDL
        else if (id == References.NANDL)
        {
            //ingredients.put(References.DIRT, (byte) 1);
            ingredients.put(References.COPPER, (byte) 9);
        }
        // NANDR
        else if (id == References.NANDR)
        {
            //ingredients.put(References.DIRT, (byte) 1);
            ingredients.put(References.COPPER, (byte) 9);
        }
    }

}