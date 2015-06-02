package Assets.Crafting;

import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
import Assets.World.Tile;
import Assets.World.TileMap;
import Main.ScreenDimensions;

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
                    (ScreenDimensions.WIDTH/2) - ((craftBenchLength * (craftCellSize + craftCellSpacing))/2) + (x * (craftCellSize + craftCellSpacing)),
                    ScreenDimensions.HEIGHT - (craftCellSize + craftBorderSpacing) - (craftBenchHeight * (craftCellSize + craftCellSpacing)) + (y * (craftCellSize + craftCellSpacing)) - yOffset,
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
                    (ScreenDimensions.WIDTH/2) - ((productBenchLength * (craftCellSize + craftCellSpacing))/2) + (x * (craftCellSize + craftCellSpacing)) + (craftCellSize*2+craftCellSpacing+craftBorderSpacing),
                    ScreenDimensions.HEIGHT - (craftCellSize + craftBorderSpacing) - (productBenchHeight * (craftCellSize + craftCellSpacing)) + (y * (craftCellSize + craftCellSpacing)) - (yOffset*2+craftCellSize + craftBorderSpacing +craftCellSpacing),
                    craftCellSize,
                    craftCellSize
            ));

            x++;
            if(x == productBenchLength) {
                x = 0;
                y++;
            }
        }

        craftBench[0].name = "Erde";
        craftBench[0].setTileImage();

        craftBench[2].name = "Gras";
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
                    TileMap.mouseX - Tile.WIDTH / 2,
                    TileMap.mouseY - Tile.HEIGHT / 2,
                    Tile.WIDTH * 2,
                    Tile.HEIGHT * 2,
                    null
            );
        }
    }


    public void checkRecipes() {
        if(productBench[0].name.equals("null")) {

            // Sand herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Wasser") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Sand")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Sand")) {   // Die klappen noch nicht ganz, kleiner Fehler
                    productBench[0].name = "Sand";
                    productBench[0].setTileImage();
                    System.out.println("Sand hergestellt");
                }
            }

            // Eisen herstellen
            if(craftBench[0].name.equals("Stein") && craftBench[1].name.equals("Lava") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisen")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisen")) {
                    productBench[0].name = "Eisen";
                    productBench[0].setTileImage();
                    System.out.println("Eisen hergestellt");
                }
            }

            /*
            // Diamant herstellen
            if(craftBench[0].name.equals("") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Diamant")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Diamant")) {
                    productBench[0].name = "Diamant";
                    productBench[0].setTileImage();
                    System.out.println("Diamant hergestellt");
                }
            }

            // Smaragd herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Smaragd")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Smaragd")) {
                    productBench[0].name = "Smaragd";
                    productBench[0].setTileImage();
                    System.out.println("Smaragd hergestellt");
                }
            }

            // Rubin herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Rubin")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Rubin")) {
                    productBench[0].name = "Rubin";
                    productBench[0].setTileImage();
                    System.out.println("Rubin hergestellt");
                }
            }

            // Saphir herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Saphir")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Saphir")) {
                    productBench[0].name = "Saphir";
                    productBench[0].setTileImage();
                    System.out.println("Saphir hergestellt");
                }
            }

            // Gold herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Gold")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Gold")) {
                    productBench[0].name = "Gold";
                    productBench[0].setTileImage();
                    System.out.println("Gold hergestellt");
                }
            }
            */

            // Silber herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Lava") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Silber")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Silber")) {
                    productBench[0].name = "Silber";
                    productBench[0].setTileImage();
                    System.out.println("Silber hergestellt");
                }
            }

            // Kupfer herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Blatt") && craftBench[2].name.equals("Lava") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Kupfer")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Kupfer")) {
                    productBench[0].name = "Kupfer";
                    productBench[0].setTileImage();
                    System.out.println("Kupfer hergestellt");
                }
            }

            // Eis herstellen
            else if(craftBench[0].name.equals("Wasser") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eis")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eis")) {
                    productBench[0].name = "Eis";
                    productBench[0].setTileImage();
                    System.out.println("Eis hergestellt");
                }
            }

            // Axt herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Eisen") && craftBench[3].name.equals("Eisen") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Axt")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Axt")) {
                    productBench[0].name = "Axt";
                    productBench[0].setTileImage();
                    System.out.println("Axt hergestellt");
                }
            }

            // Pfeil herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Pfeil")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Pfeil")) {
                    productBench[0].name = "Pfeil";
                    productBench[0].setTileImage();
                    System.out.println("Pfeil hergestellt");
                }
            }

            // Bogen herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Stein") && craftBench[3].name.equals("Stein") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Bogen")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Bogen")) {
                    productBench[0].name = "Bogen";
                    productBench[0].setTileImage();
                    System.out.println("Bogen hergestellt");
                }
            }

            // Werkbank herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Holz") && craftBench[3].name.equals("Holz") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Werkbank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Werkbank")) {
                    productBench[0].name = "Werkbank";
                    productBench[0].setTileImage();
                    System.out.println("Werkbank hergestellt");
                }
            }

            // Fackel herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Blatt") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Fackel")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Fackel")) {
                    productBench[0].name = "Fackel";
                    productBench[0].setTileImage();
                    System.out.println("Fakel hergestellt");
                }
            }

            // Steinhacke herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Stein") && craftBench[3].name.equals("Stein") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Steinhacke")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Steinhacke")) {
                    productBench[0].name = "Steinhacke";
                    productBench[0].setTileImage();
                    System.out.println("Steinhacke hergestellt");
                }
            }

            // Holzschwert herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Holzschwert")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Holzschwert")) {
                    productBench[0].name = "Holzschwert";
                    productBench[0].setTileImage();
                    System.out.println("Holzschwert hergestellt");
                }
            }

            // Edelschwert herstellen
            else if(craftBench[0].name.equals("Kupfer") && craftBench[1].name.equals("Silber") && craftBench[2].name.equals("Gold") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Edelschwert")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Edelschwert")) {
                    productBench[0].name = "Edelschwert";
                    productBench[0].setTileImage();
                    System.out.println("Edelschwert hergestellt");
                }
            }

            // Eisenschwert herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisenschwert")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisenschwert")) {
                    productBench[0].name = "Eisenschwert";
                    productBench[0].setTileImage();
                    System.out.println("Eisenschwert hergestellt");
                }
            }

            // Glas herstellen
            else if(craftBench[0].name.equals("Sand") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Eisen") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Glas")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Glas")) {
                    productBench[0].name = "Glas";
                    productBench[0].setTileImage();
                    System.out.println("Glas hergestellt");
                }
            }

            // Steinofen herstellen
            else if(craftBench[0].name.equals("Stein") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Holz") && craftBench[3].name.equals("Holz") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Steinofen")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Steinofen")) {
                    productBench[0].name = "Steinofen";
                    productBench[0].setTileImage();
                    System.out.println("Steinofen hergestellt");
                }
            }

            // Steinbank herstellen
            else if(craftBench[0].name.equals("Stein") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Stein") && craftBench[3].name.equals("Stein") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Steinbank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Steinbank")) {
                    productBench[0].name = "Steinbank";
                    productBench[0].setTileImage();
                    System.out.println("Steinbank hergestellt");
                }
            }

            // Holzruestung herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Blatt") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Holzruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Holzruestung")) {
                    productBench[0].name = "Holzruestung";
                    productBench[0].setTileImage();
                    System.out.println("Holzruestung hergestellt");
                }
            }

            // Eisenruestung herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Kupfer") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisenruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisenruestung")) {
                    productBench[0].name = "Eisenruestung";
                    productBench[0].setTileImage();
                    System.out.println("Eisenruestung hergestellt");
                }
            }

            // Edelruestung herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Kupfer") && craftBench[2].name.equals("Silber") && craftBench[3].name.equals("Gold") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Edelruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Edelruestung")) {
                    productBench[0].name = "Edelruestung";
                    productBench[0].setTileImage();
                    System.out.println("Edelruestung hergestellt");
                }
            }

            // Silberruestung herstellen
            else if(craftBench[0].name.equals("Silber") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("Kupfer") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Silberruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Silberruestung")) {
                    productBench[0].name = "Silberruestung";
                    productBench[0].setTileImage();
                    System.out.println("Silberruestung hergestellt");
                }
            }

            // Goldruestung herstellen
            else if(craftBench[0].name.equals("Gold") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("Kupfer") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Goldruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Goldruestung")) {
                    productBench[0].name = "Goldruestung";
                    productBench[0].setTileImage();
                    System.out.println("Goldruestung hergestellt");
                }
            }

            // Blauen Genesungstrank herstellen
            else if(craftBench[0].name.equals("Saphir") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Blauer Genesungstrank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Blauer Genesungstrank")) {
                    productBench[0].name = "Blauer Genesungstrank";
                    productBench[0].setTileImage();
                    System.out.println("Blauen Genesungstrank hergestellt");
                }
            }

            // Roten Genesungstrank herstellen
            else if(craftBench[0].name.equals("Rubin") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Roter Genesungstrank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Roter Genesungstrank")) {
                    productBench[0].name = "Roter Genesungstrank";
                    productBench[0].setTileImage();
                    System.out.println("Roten Genesungstrank hergestellt");
                }
            }

            // Gift herstellen
            else if(craftBench[0].name.equals("Smaragd") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("Glas") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Gift")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Gift")) {
                    productBench[0].name = "Gift";
                    productBench[0].setTileImage();
                    System.out.println("Gift hergestellt");
                }
            }

            // Schwarzpulver herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("Sand") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Schwarzpulver")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Schwarzpulver")) {
                    productBench[0].name = "Schwarzpulver";
                    productBench[0].setTileImage();
                    System.out.println("Schwarzpulver hergestellt");
                }
            }

            // TNT herstellen
            else if(craftBench[0].name.equals("Schwarzpulver") && craftBench[1].name.equals("Schwarzpulver") && craftBench[2].name.equals("Eisen") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("TNT")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("TNT")) {
                    productBench[0].name = "TNT";
                    productBench[0].setTileImage();
                    System.out.println("TNT hergestellt");
                }
            }

            // Granate herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Schwarzpulver") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Granate")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Granate")) {
                    productBench[0].name = "Granate";
                    productBench[0].setTileImage();
                    System.out.println("Granate hergestellt");
                }
            }

            // Pistole herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("Kupfer") && craftBench[3].name.equals("Kupfer") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Pistole")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Pistole")) {
                    productBench[0].name = "Pistole";
                    productBench[0].setTileImage();
                    System.out.println("Pistole hergestellt");
                }
            }

            // Eisenkugel herstellen
            else if(craftBench[0].name.equals("Schwarzpulver") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisenkugel")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisenkugel")) {
                    productBench[0].name = "Eisenkugel";
                    productBench[0].setTileImage();
                    System.out.println("Eisenkugel hergestellt");
                }
            }

            // Kupferkugel herstellen
            else if(craftBench[0].name.equals("Schwarzpulver") && craftBench[1].name.equals("Kupfer") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Kupferkugel")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Kupferkugel")) {
                    productBench[0].name = "Kupferkugel";
                    productBench[0].setTileImage();
                    System.out.println("Kupferkugel hergestellt");
                }
            }

            // Diamantruestung herstellen
            else if(craftBench[0].name.equals("Diamant") && craftBench[1].name.equals("Rubin") && craftBench[2].name.equals("Saphir") && craftBench[3].name.equals("Smaragd") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Diamantruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Diamantruestung")) {
                    productBench[0].name = "Diamantruestung";
                    productBench[0].setTileImage();
                    System.out.println("Diamantruestung hergestellt");
                }
            }

            else {
                productBench[0].name = "null";
                productBench[0].setTileImage();
            }
        }
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
                    if(Inventory.invBar[i].contains(TileMap.mouseX, TileMap.mouseY)) {
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
                    if(Inventory.invDrawer[i].contains(TileMap.mouseX, TileMap.mouseY)) {
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
                    if(craftBench[i].contains(TileMap.mouseX, TileMap.mouseY)) {
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
                    if(productBench[i].contains(TileMap.mouseX, TileMap.mouseY)) {
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
