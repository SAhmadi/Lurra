package Assets.Inventory;

import Assets.World.TileMap;
import Main.References;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Eine Zelle im Inventar oder Crafting
 *
 * @author Sirat
 * */
public class Cell extends Rectangle
{

    // Zelle
    public String name = "null";
    public BufferedImage tileImage;
    public int count = 0;
    public static boolean wasEaten;

    /**
     * Cell         Konstruktor der Cell-Klasse
     *
     * @param size  Zellgroesse
     * */
    public Cell(Rectangle size)
    {
        this.setBounds(size);
        this.setTileImage();
    }

    /**
     * setTileImage     Setzen der Texture
     * */
    public void setTileImage()
    {
        switch (name) {
            case "Erde":
                tileImage = ResourceLoader.dirt;
                break;
            case "Gras":
                tileImage = ResourceLoader.gras;
                break;
            case "Gold":
                tileImage = ResourceLoader.gold;
                break;
            case "Silber":
                tileImage = ResourceLoader.silver;
                break;
            case "Kupfer":
                tileImage = ResourceLoader.copper;
                break;
            case "Eisen":
                tileImage = ResourceLoader.ion;
                break;
            case "Rubin":
                tileImage = ResourceLoader.ruby;
                break;
            case "Saphire":
                tileImage = ResourceLoader.saphire;
                break;
            case "Smaragd":
                tileImage = ResourceLoader.smaragd;
                break;
            case "Diamant":
                tileImage = ResourceLoader.diamond;
                break;
            case "Eis":
                tileImage = ResourceLoader.ice;
                break;
            case "Picke":
                tileImage = ResourceLoader.stonePick;
                break;
            case "Axt":
                tileImage = ResourceLoader.axe;
                break;
            case "Hammer":
                tileImage = ResourceLoader.hammer;
                break;
            case "Schleimpistole":
                tileImage = ResourceLoader.gunPurple;
                break;
            case "Burger":
                tileImage = ResourceLoader.burger;
                this.wasEaten = false;
                break;
            case "Rottrank":
                tileImage = ResourceLoader.healthPotion;
                this.wasEaten =false;
                break;
            case "null":
                tileImage = null;
                break;
        }
    }

    /**
     * setName      Setzen des Namen
     * */
    public void setName()
    {
        if(Arrays.asList(TileMap.dirtOnlyTextures).contains(tileImage))
            name = "Erde";
        else if(Arrays.asList(TileMap.grasOnlyTextures).contains(tileImage))
            name = "Gras";
        else if(tileImage == ResourceLoader.gold)
            name = "Gold";
        else if(tileImage == ResourceLoader.silver)
            name = "Silber";
        else if(tileImage == ResourceLoader.copper)
            name = "Kupfer";
        else if(tileImage == ResourceLoader.ion)
            name = "Eisen";
        else if(tileImage == ResourceLoader.ruby)
            name = "Rubin";
        else if(tileImage == ResourceLoader.saphire)
            name = "Saphire";
        else if(tileImage == ResourceLoader.smaragd)
            name = "Smaragd";
        else if(tileImage == ResourceLoader.diamond)
            name = "Diamant";
        else if(tileImage == ResourceLoader.stonePick)
            name = "Picke";
        else if(tileImage == ResourceLoader.axe)
            name = "Axt";
        else if(tileImage == ResourceLoader.hammer)
            name = "Hammer";
        else if(tileImage == ResourceLoader.gunPurple)
            name = "Schleimpistole";
        else if (tileImage == ResourceLoader.burger)
            name = "Burger";
        else if (tileImage == ResourceLoader.healthPotion)
            name = "Rottrank";
        else
            name = "null";
    }

    /**
     * render               Zeichnen der Zelle
     *
     * @param g             Graphics Objekt
     * @param isSelected    Wert, ob Zelle gerade ausgewaehlt ist
     * */
    public void render(Graphics g, boolean isSelected)
    {
        g.drawImage(ResourceLoader.inventoryBarCellUnselected, super.x, super.y, super.width, super.height, null);

        if(Inventory.isDrawerOpen && this.contains(References.MOUSE_X, References.MOUSE_Y))
        {
            g.setColor(Color.BLACK);
            g.fillRect(super.x, super.y, super.width, super.height);
        }

        if(isSelected)
            g.drawImage(ResourceLoader.inventoryBarCellSelected, super.x, super.y, super.width, super.height, null);

        if(!name.equals("null"))
        {
            g.drawImage(
                    tileImage,
                    super.x + References.TILE_SIZE / 2,
                    super.y + References.TILE_SIZE / 2,
                    References.TILE_SIZE * 2,
                    References.TILE_SIZE * 2,
                    null
            );

            g.setColor(Color.BLACK);
            g.setFont(ResourceLoader.inventoryItemFont);
            g.drawString(
                    Integer.toString(count),
                    super.x + 2*References.TILE_SIZE,
                    super.y + 2*References.TILE_SIZE
            );
        }
    }

}
