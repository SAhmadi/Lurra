package Assets.Crafting;

import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
import Main.References;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by Sirat on 31.05.2015.
 */
public class Crafting extends Rectangle implements KeyListener, MouseListener {

    public static int craftBenchLength = 3;
    public static int craftBenchHeight = 3;
    public static int productBenchLength = 1;
    public static int productBenchHeight = 1;

    public static int craftCellSize = 48;
    public static int craftCellSpacing = 5;
    public static int craftBorderSpacing = 5;


    public static Cell[] craftBench = new Cell[craftBenchLength * craftBenchHeight];
    public static Cell[] productBench = new Cell[productBenchLength * productBenchHeight];

    public static boolean isHolding = false;
    public static boolean isCraftBenchOpen = false;

    public static String holdingName = "null";
    public BufferedImage isHoldingTileImage;

    private int yOffset = 150;

    public Crafting() {
        // Crafting Fenster
        int x = 0, y = 0;
        for(int i = 0; i < craftBench.length; i++) {
            craftBench[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((craftBenchLength * (craftCellSize + craftCellSpacing))/2) + (x * (craftCellSize + craftCellSpacing)),
                    References.SCREEN_HEIGHT - (craftCellSize + craftBorderSpacing) - (craftBenchHeight * (craftCellSize + craftCellSpacing)) + (y * (craftCellSize + craftCellSpacing)) - yOffset,
                    craftCellSize,
                    craftCellSize
            ));

            x++;
            if(x == craftBenchLength) {
                x = 0;
                y++;
            }
        }

        // Produkt Cell
        for(int i = 0; i < productBench.length; i++) {
            productBench[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH/2) - ((productBenchLength * (craftCellSize + craftCellSpacing))/2) + (x * (craftCellSize + craftCellSpacing)) + (craftCellSize*2+craftCellSpacing+craftBorderSpacing),
                    References.SCREEN_HEIGHT - (craftCellSize + craftBorderSpacing) - (productBenchHeight * (craftCellSize + craftCellSpacing)) + (y * (craftCellSize + craftCellSpacing)) - (yOffset*2+craftCellSize + craftBorderSpacing +craftCellSpacing),
                    craftCellSize,
                    craftCellSize
            ));

            x++;
            if(x == productBenchLength) {
                x = 0;
                y++;
            }
        }

        craftBench[0].name = "null";
        craftBench[0].setTileImage();

        craftBench[2].name = "null";
        craftBench[2].setTileImage();

        productBench[0].name = "null";
        productBench[0].setTileImage();
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);

//        for(int i = 0; i < invBar.length; i++) {
//            boolean isSelected = false;
//            if(i == selected) {
//                isSelected = true;
//            }
//            invBar[i].render(g, isSelected);
//        }

        if(isCraftBenchOpen) {
            for(int i = 0; i < craftBench.length; i++) {
                craftBench[i].render(g, false);
            }

            for(int i = 0; i < productBench.length; i++) {
                productBench[i].render(g, false);
            }
        }

        if(isHolding) {
            g.drawImage(
                    isHoldingTileImage,
                    References.MOUSE_X - References.TILE_SIZE / 2,
                    References.MOUSE_Y - References.TILE_SIZE / 2,
                    References.TILE_SIZE * 2,
                    References.TILE_SIZE * 2,
                    null
            );
        }
    }


    /**
     *
     * */
    public void checkRecipes() {
        Recipe.checkRecipes(craftBench, productBench, holdingName);
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_F) {
            if(isCraftBenchOpen) {
                isCraftBenchOpen = false;
            }
            else {
                isCraftBenchOpen = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(isCraftBenchOpen) {
                for(int i = 0; i < Inventory.invBar.length; i++) {
                    if(Inventory.invBar[i].contains(References.MOUSE_X, References.MOUSE_Y)) {
                        if(Inventory.invBar[i].name != "null" && !isHolding) {
                            holdingName = Inventory.invBar[i].name;
                            isHoldingTileImage = Inventory.invBar[i].tileImage;
                            Inventory.invBar[i].name = "null";
                            Inventory.invBar[i].setTileImage();

                            isHolding = true;
                        }
                        else if(isHolding && Inventory.invBar[i].name.equals("null")) {
                            Inventory.invBar[i].name = holdingName;
                            Inventory.invBar[i].setTileImage();

                            isHolding = false;
                        }
                        else if(isHolding && !Inventory.invBar[i].name.equals("null")) {
                            String tmpName = Inventory.invBar[i].name;
                            BufferedImage tmpImage = Inventory.invBar[i].tileImage;

                            Inventory.invBar[i].name = holdingName;
                            Inventory.invBar[i].setTileImage();

                            holdingName = tmpName;
                            isHoldingTileImage = tmpImage;
                        }
                    }
                }

                for(int i = 0; i < Inventory.invDrawer.length; i++) {
                    if(Inventory.invDrawer[i].contains(References.MOUSE_X, References.MOUSE_Y)) {
                        if(Inventory.invDrawer[i].name != "null" && !isHolding) {
                            holdingName = Inventory.invDrawer[i].name;
                            isHoldingTileImage = Inventory.invDrawer[i].tileImage;
                            Inventory.invDrawer[i].name = "null";
                            Inventory.invDrawer[i].setTileImage();

                            isHolding = true;
                        }
                        else if(isHolding && Inventory.invDrawer[i].name.equals("null")) {
                            Inventory.invDrawer[i].name = holdingName;
                            Inventory.invDrawer[i].setTileImage();

                            isHolding = false;
                        }
                        else if(isHolding && !Inventory.invDrawer[i].name.equals("null")) {
                            String tmpName = Inventory.invDrawer[i].name;
                            BufferedImage tmpImage = Inventory.invDrawer[i].tileImage;

                            Inventory.invDrawer[i].name = holdingName;
                            Inventory.invDrawer[i].setTileImage();

                            holdingName = tmpName;
                            isHoldingTileImage = tmpImage;
                        }
                    }
                }


                for(int i = 0; i < craftBench.length; i++) {
                    if(craftBench[i].contains(References.MOUSE_X, References.MOUSE_Y)) {
                        if(craftBench[i].name != "null" && !isHolding) {
                            holdingName = craftBench[i].name;
                            isHoldingTileImage = craftBench[i].tileImage;
                            craftBench[i].name = "null";
                            craftBench[i].setTileImage();

                            isHolding = true;
                        }
                        else if(isHolding && craftBench[i].name.equals("null")) {
                            craftBench[i].name = holdingName;
                            craftBench[i].setTileImage();

                            isHolding = false;
                        }
                        else if(isHolding && !craftBench[i].name.equals("null")) {
                            String tmpName = craftBench[i].name;
                            BufferedImage tmpImage = craftBench[i].tileImage;

                            craftBench[i].name = holdingName;
                            craftBench[i].setTileImage();

                            holdingName = tmpName;
                            isHoldingTileImage = tmpImage;
                        }
                    }
                }

                for(int i = 0; i < productBench.length; i++) {
                    if(productBench[i].contains(References.MOUSE_X, References.MOUSE_Y)) {
                        if(productBench[i].name != "null" && !isHolding) {
                            holdingName = productBench[i].name;
                            isHoldingTileImage = productBench[i].tileImage;
                            productBench[i].name = "null";
                            productBench[i].setTileImage();

                            isHolding = true;
                        }
                        else if(isHolding && productBench[i].name.equals("null")) {
                            productBench[i].name = holdingName;
                            productBench[i].setTileImage();

                            isHolding = false;
                        }
                        else if(isHolding && !productBench[i].name.equals("null")) {
                            String tmpName = productBench[i].name;
                            BufferedImage tmpImage = productBench[i].tileImage;

                            productBench[i].name = holdingName;
                            productBench[i].setTileImage();

                            holdingName = tmpName;
                            isHoldingTileImage = tmpImage;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
