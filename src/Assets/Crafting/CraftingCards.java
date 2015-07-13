package Assets.Crafting;

import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
import Main.References;
import Main.ResourceLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Alle moegliche vordefinierten Craftingrezepte
 *
 * @author Sirat
 * @version 0.9
 * */
public class CraftingCards extends Rectangle
{

    // Karten
    private int x;
    private int y;
    private int width;
    private int height;

    private byte id;
    private BufferedImage texture;
    private Map<Byte, Byte> ingredients;
    private Map.Entry pair;

    private ArrayList<Boolean> ingredientIsCraftable;
    private boolean isCraftable;

    /**
     * CraftingCards        Konstruktor der CraftingCards Klasse
     *
     * @param id            ID
     * */
    public CraftingCards(byte id)
    {
        this.width = this.height = 4 * References.TILE_SIZE;
        this.x = References.SCREEN_WIDTH/2 - this.width/2;
        this.y = References.SCREEN_HEIGHT/2 - this.height/2;
        super.setBounds(this.x, this.y, this.width, this.height);

        this.id = id;
        this.texture = References.getTextureById(id);
        this.ingredients = new HashMap<>();
        Recipe.initRecipes(id, ingredients);

        this.ingredientIsCraftable = new ArrayList<>();
        this.isCraftable = false;
    }

    /**
     *
     * */
    public void update()
    {
        ingredientIsCraftable.clear();

        for (Object o : ingredients.entrySet())
        {
            Map.Entry pair = (Map.Entry) o;
            checkIfCraftable(pair);
        }

        for (boolean ingredient : ingredientIsCraftable)
        {
            if (ingredient) isCraftable = true;
            else { isCraftable = false; break; }
        }
    }


    /**
     * render       Zeichnen der Craftingkarten
     *
     * @param g     Graphics Objekt
     * */
    public void render(Graphics2D g)
    {
        if (isCraftable)
            g.setColor(new Color(0, 255, 0, 150));
        else
            g.setColor(new Color(255, 255, 255, 150));

        g.fillRect(super.x - width / 2, super.y - height / 2, 2 * super.width, 2 * super.height + 2 * References.TILE_SIZE);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(ResourceLoader.textFieldFontBold.deriveFont(25f));
        g.drawImage(texture, super.x, super.y, super.width, super.height, null);

        Iterator iterator = ingredients.entrySet().iterator();
        byte ingredientCounter = 0;
        try
        {
            while (iterator.hasNext())
            {
                Map.Entry pair = (Map.Entry) iterator.next();

                // Zeichnen der Zutaten Texture
                g.drawImage(References.getTextureById((byte) pair.getKey()), (super.x - width / 2) + ingredientCounter * 2 * References.TILE_SIZE, super.y + super.height + 2 * References.TILE_SIZE, 2 * References.TILE_SIZE, 2 * References.TILE_SIZE, null);

                g.setColor(Color.WHITE);
                // Fuer bessere Lesbarkeit, Schriftfarbe aendern bei folgenden Tiles
                if ((byte) pair.getKey() == References.ICE || (byte) pair.getKey() == References.DIAMOND || (byte) pair.getKey() == References.STONE
                        || (byte) pair.getKey() == References.GOLD || (byte) pair.getKey() == References.SILVER)
                    g.setColor(Color.BLACK);

                // Zeichnen der Zutaten Anzahl
                g.drawString(
                        pair.getValue().toString(),
                        (super.x - width / 2) + ingredientCounter*2*References.TILE_SIZE + g.getFontMetrics(g.getFont()).stringWidth(pair.getValue().toString())/2,
                        super.y + super.height + 2 * References.TILE_SIZE + 2*g.getFontMetrics(g.getFont()).getHeight()/3
                );

                ingredientCounter++;
            }
        }
        catch (ConcurrentModificationException ignored) {}

        g.setFont(ResourceLoader.textFieldFont.deriveFont(30f));
        g.setColor(Color.WHITE);
    }

    /**
     * checkIfCraftable         Pruefen ob alle Zutaten vorhanden sind
     *
     * @param pair              Zutatendaten
     * */
    private void checkIfCraftable(Map.Entry pair)
    {
        boolean tmpCraftable = false;
        for (Cell cell : Inventory.invBar)
        {
            if ((byte) pair.getKey() == cell.getId())
            {
                if (cell.getCount() >= (byte) pair.getValue())
                    tmpCraftable = true;
                else
                    isCraftable = false;
            }
            else
                isCraftable = false;
        }

        if (!tmpCraftable)
        {
            for (Cell cell : Inventory.invDrawer)
            {
                if ((byte) pair.getKey() == cell.getId())
                {
                    if (cell.getCount() >= (byte) pair.getValue())
                        tmpCraftable = true;
                    else
                        isCraftable = false;
                }
                else
                    isCraftable = false;
            }
        }

        ingredientIsCraftable.add(tmpCraftable);
    }

    /**
     * mouseClicked         Falls linke Maustaste gedrueckt wird
     *
     * @param e             Mausevent
     * */
    public void mouseClicked(MouseEvent e)
    {
        if (isCraftable)
        {
            try
            {
                boolean addedToInventory = false;
                for (Object o : ingredients.entrySet())
                {
                    Map.Entry pair = (Map.Entry) o;

                    boolean foundIngredient = false;
                    for (Cell cell : Inventory.invBar)
                    {
                        if ((byte) pair.getKey() == cell.getId())
                        {
                            if (cell.getCount() >= (byte) pair.getValue())
                            {
                                // Verringe Tile im Inventarleiste
                                cell.setCount(cell.getCount() - (byte) pair.getValue());

                                // Loesche Tile aus Inventar, falls keine mehr vorhanden
                                if (cell.getCount() < 1)
                                {
                                    cell.setId((byte) 0);
                                    cell.setTileImage(null);
                                    cell.setCount(0);
                                }

                                // Fuege neues Rezept in Inventar
                                if (!addedToInventory)
                                {
                                    Inventory.addToInventory(id);
                                    addedToInventory = true;
                                }

                                foundIngredient = true;
                            }
                        }
                    }

                    if (!foundIngredient)
                    {
                        for (Cell cell : Inventory.invDrawer)
                        {
                            if ((byte) pair.getKey() == cell.getId())
                            {
                                if (cell.getCount() >= (byte) pair.getValue())
                                {
                                    // Verringe Tile im Inventardrawer
                                    cell.setCount(cell.getCount() - (byte) pair.getValue());

                                    // Loesche Tile aus Inventar, falls keine mehr vorhanden
                                    if (cell.getCount() < 1)
                                    {
                                        cell.setId((byte) 0);
                                        cell.setTileImage(null);
                                        cell.setCount(0);
                                    }

                                    // Fuege neues Rezept in Inventar
                                    if (!addedToInventory)
                                    {
                                        Inventory.addToInventory(id);
                                        addedToInventory = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (ConcurrentModificationException ignored) {}
        }
    }

}






