package Assets.Crafting;

import Assets.Inventory.Inventory;
import Main.Tutorial;

import java.awt.*;
import java.awt.event.*;

/**
 * Mixen der Abbauobjekten zu neuen Objekten
 *
 * @author Sirat
 * @version 1.0
 * */
public class Crafting extends Rectangle implements KeyListener, MouseListener, MouseWheelListener
{

    // Crafting
    public static CraftingCards[] craftingCards = new CraftingCards[ Recipe.recipes.length ];

    public static boolean isCraftBenchOpen = false;
    public static int selected = 0;

    /**
     * Crafting         Konstruktor der Crafting-Klasse
     * */
    public Crafting()
    {
        int recipeIndex = 0;
        for(int card = 0; card < craftingCards.length; card++)
        {
            if (recipeIndex >= Recipe.recipes.length) recipeIndex = 0;
            craftingCards[card] = new CraftingCards(Recipe.recipes[recipeIndex]);
            recipeIndex++;
        }
    }

    /**
     * update       Updaten der Craftingkarten
     * */
    public void update()
    {
        if (isCraftBenchOpen)
            craftingCards[selected].update();
    }

    /**
     * render       Zeichnen der Craftingkarten
     *
     * @param g     Graphics Objekt
     * */
    public void render(Graphics2D g)
    {
        if (isCraftBenchOpen)
            craftingCards[selected].render(g);
    }


    // EVENTS
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F) {
            isCraftBenchOpen = !isCraftBenchOpen;
            Tutorial.solveTut(Tutorial.TUT_CRAFT);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (isCraftBenchOpen && !Inventory.isDrawerOpen)
            if (e.getButton() == MouseEvent.BUTTON1)
                craftingCards[selected].mouseClicked(e);

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        // Runter
        if(e.getWheelRotation() < 0)
        {
            if(selected > 0) selected--;
            else selected = craftingCards.length - 1;
        }
        // Hoch
        else if(e.getWheelRotation() > 0)
        {
            if(selected < craftingCards.length - 1) selected++; // nach unten
            else selected = 0;
        }
    }
}
