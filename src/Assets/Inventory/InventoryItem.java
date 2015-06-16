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
        if(this.texture == ResourceLoader.treeTrunkRoot ||
                this.texture == ResourceLoader.treeTrunk||
                this.texture == ResourceLoader.treeTrunkRight ||
                this.texture == ResourceLoader.treeTrunkLeft ||
                this.texture == ResourceLoader.treeTrunkTop)
        {
            this.name = "Holz";
        }

        // Baumkrone
        if(this.texture == ResourceLoader.leafStart ||
                this.texture == ResourceLoader.leaf ||
                this.texture == ResourceLoader.leafEnd)
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
        if(this.texture == ResourceLoader.copper)
        {
            this.name = "Kupfer";
        }

        // Silber
        if(this.texture == ResourceLoader.silver)
        {
            this.name = "Silber";
        }

        // Gold
        if(this.texture == ResourceLoader.gold)
        {
            this.name = "Gold";
        }
    }

}