package Assets.Inventory;

import Main.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * Definition eines Inventar-Elements
 * InventoryItem -      Inventar-Elemente
 *
 * */
public class InventoryItem extends Rectangle
{
    public String name;
    public int count;
    public BufferedImage texture;

    /**
     *
     *  InventoryItem -         Konstruktor der Klasse InventoryItem
     *  @param texture          Bild des Items
     *
     * */
    public InventoryItem(Rectangle size, BufferedImage texture, String name)
    {
        this.setBounds(size);
        this.count = 1;
        this.texture = texture;
        this.name = name;
    }

    /**
     *
     * */
    private void setName()
    {
        // Baumstamm
        if(this.texture == ResourceLoader.treeTrunkRootLeft ||
                this.texture == ResourceLoader.treeTrunkBottomLeft ||
                this.texture == ResourceLoader.treeTrunkBottomRight ||
                this.texture == ResourceLoader.treeTrunkRootRight ||
                this.texture == ResourceLoader.treeTrunkRoundedCornerTopLeft ||
                this.texture == ResourceLoader.treeTrunkNextToCorner ||
                this.texture == ResourceLoader.treeTrunkHorizontalNormal ||
                this.texture == ResourceLoader.treeTrunkRoundedCornerBottomRight ||
                this.texture == ResourceLoader.treeTrunkVerticalNormal ||
                this.texture == ResourceLoader.treeTrunkTopCenter ||
                this.texture == ResourceLoader.treeTrunkTopLeft ||
                this.texture == ResourceLoader.treeTrunkTopLeftEnd ||
                this.texture == ResourceLoader.treeTrunkTopRightEnd)
        {
            this.name = "Holz";
        }

        // Baumkrone
        if(this.texture == ResourceLoader.greenLeafBottomLeftCorner ||
                this.texture == ResourceLoader.greenLeafBottom ||
                this.texture == ResourceLoader.greenLeafBottomRightCorner ||
                this.texture == ResourceLoader.greenLeafRight ||
                this.texture == ResourceLoader.greenLeafNormal ||
                this.texture == ResourceLoader.greenLeafLeft ||
                this.texture == ResourceLoader.greenLeafTopLeftCorner ||
                this.texture == ResourceLoader.greenLeafTop ||
                this.texture == ResourceLoader.greenLeafTopRightCorner)
        {
            this.name = "Blatt";
        }

        // Erde
        if(this.texture == ResourceLoader.dirt ||
                this.texture == ResourceLoader.dirtMidDark ||
                this.texture == ResourceLoader.dirtDark)
        {
            this.name = "Erde";
        }

        // Gras
        if(this.texture == ResourceLoader.gras)
        {
            this.name = "Gras";
        }

        // Kupfer
        if(this.texture == ResourceLoader.copperCrystal || this.texture == ResourceLoader.copper)
        {
            this.name = "Kupfer";
        }

        // Silber
        if(this.texture == ResourceLoader.silverCrystal || this.texture == ResourceLoader.silver)
        {
            this.name = "Silber";
        }

        // Gold
        if(this.texture == ResourceLoader.goldCrystal || this.texture == ResourceLoader.gold)
        {
            this.name = "Gold";
        }
    }

}