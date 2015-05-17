package Assets;

import Assets.Tiles.*;
import Main.ScreenDimensions;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/*
* TileMap - Spielfeld bestehend aus Tiles
* */
public class TileMap {

    private Assets assets;

    /*
    * TileMap
    * */
    // Position
    private int width;
    private int height;
    private double x;
    private double y;

    // Grenzen
    private int xmin, ymin;
    private int xmax, ymax;

    // Anzahl der Spalten und Reihen
    private int numberOfColumns, numberOfRows;
    private int puffer = 2;

    // Offset
    private int columnOffset, rowOffset;
    private int numberOfColumnsToDraw, numberOfRowsToDraw;

    /*
    * Tiles
    * */
    // Alle Tiles in einer ArrayList
    private ArrayList<ArrayList<Tile>> tiles;

    // WaterTile and LavaTile
    private BufferedImage waterTileTop;
    private BufferedImage waterTile;
    private BufferedImage lavaTileTop;
    private BufferedImage lavaTile;

    // GrasTile
    private BufferedImage grasTileRight;
    private BufferedImage grasTileRight_Destroyed;
    private BufferedImage grasTileLeft;
    private BufferedImage grasTileLeft_Destroyed;
    private BufferedImage grasTileBottom;
    private BufferedImage grasTileBottom_Destroyed;

    private BufferedImage grasTileBottomRightCorner;
    private BufferedImage grasTileBottomRightCorner_Destroyed;
    private BufferedImage grasTileBottomLeftCorner;
    private BufferedImage grasTileBottomLeftCorner_Destroyed;
    private BufferedImage grasTileTopRightCorner;
    private BufferedImage grasTileTopRightCorner_Destroyed;
    private BufferedImage grasTileTopLeftCorner;
    private BufferedImage grasTileTopLeftCorner_Destroyed;

    private BufferedImage grasTile;
    private BufferedImage grasTile_Destroyed;

    // DirtTile
    private BufferedImage dirtDark;
    private BufferedImage dirtDark_Destroyed;
    private BufferedImage dirtMidDark;
    private BufferedImage dirtMidDark_Destroyed;
    private BufferedImage dirt;
    private BufferedImage dirt_Destroyed;

    // StoneTile
    private BufferedImage stoneWhite1;
    private BufferedImage stoneWhite2;
    private BufferedImage stoneWhite3;
    private BufferedImage stoneWhiteLeafs;
    private BufferedImage stoneWhite_Destroyed;

    private BufferedImage stoneRed1;
    private BufferedImage stoneRed2;
    private BufferedImage stoneRed3;
    private BufferedImage stoneRedLeafs;
    private BufferedImage stoneRed_Destroyed;

    // Kupfer, Silber und Gold
    private BufferedImage copper;
    private BufferedImage copper_Destroyed;

    private BufferedImage silver;
    private BufferedImage silver_Destroyed;

    private BufferedImage gold;
    private BufferedImage gold_Destroyed;

    // Holz und Blatt
    private BufferedImage leafBottomLeftCorner;
    private BufferedImage leafBottom;
    private BufferedImage leafBottomRightCorner;
    private BufferedImage leafRight;
    private BufferedImage leafNormal;
    private BufferedImage leafLeft;
    private BufferedImage leafTopLeftCorner;
    private BufferedImage leafTop;
    private BufferedImage leafTopRightCorner;

    private BufferedImage treeTrunkRootLeft;
    private BufferedImage treeTrunkBottomLeft;
    private BufferedImage treeTrunkBottomRight;
    private BufferedImage treeTrunkRootRight;
    private BufferedImage treeTrunkRoundedCornerTopLeft;
    private BufferedImage treeTrunkNextToCorner;
    private BufferedImage treeTrunkHorizontalNormal;
    private BufferedImage treeTrunkRoundedCornerBottomRight;
    private BufferedImage treeTrunkVerticalNormal;
    private BufferedImage treeTrunkTopCenter;
    private BufferedImage treeTrunkTopLeft;
    private BufferedImage treeTrunkTopLeftEnd;
    private BufferedImage treeTrunkTopRightEnd;

    // Map - Pfad
    private String mapFilePath;

    // MouseClicked - Variablen
    private Rectangle selectedTileBounds;
    private Tile selectedTile;
    private Tile selectedTileForGravity;
    private Tile tmp;


    /*
    * Konstruktor
    * */
    public TileMap(Assets tileAssets, String mapFilePath) {
        this.assets = tileAssets;
        this.mapFilePath = mapFilePath;

        this.tiles = new ArrayList<ArrayList<Tile>>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = ScreenDimensions.WIDTH / Tile.WIDTH + this.puffer;
        this.numberOfRowsToDraw = ScreenDimensions.HEIGHT / Tile.HEIGHT + this.puffer;

        /*
        * Tiles aus dem TileSet ausschneiden
        * */
        // Wasser und Lava
        this.waterTileTop = tileAssets.getSubimage(0, 0, Tile.WIDTH, Tile.HEIGHT);
        this.waterTile = tileAssets.getSubimage(16, 0, Tile.WIDTH, Tile.HEIGHT);
        this.lavaTile = tileAssets.getSubimage(32, 0, Tile.WIDTH, Tile.HEIGHT);
        this.lavaTileTop = tileAssets.getSubimage(48, 0, Tile.WIDTH, Tile.HEIGHT);

        // Gras
        this.grasTileRight = tileAssets.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileRight_Destroyed = tileAssets.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileLeft = tileAssets.getSubimage(0, 32, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileLeft_Destroyed = tileAssets.getSubimage(16, 32, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileBottom = tileAssets.getSubimage(0, 48, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileBottom_Destroyed = tileAssets.getSubimage(16, 48, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileBottomRightCorner = tileAssets.getSubimage(0, 64, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileBottomRightCorner_Destroyed = tileAssets.getSubimage(16, 64, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileBottomLeftCorner = tileAssets.getSubimage(0, 80, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileBottomLeftCorner_Destroyed = tileAssets.getSubimage(16, 80, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileTopRightCorner = tileAssets.getSubimage(0, 96, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileTopRightCorner_Destroyed = tileAssets.getSubimage(16, 96, Tile.WIDTH, Tile.HEIGHT);

        this.grasTileTopLeftCorner = tileAssets.getSubimage(0, 112, Tile.WIDTH, Tile.HEIGHT);
        this.grasTileTopLeftCorner_Destroyed = tileAssets.getSubimage(16, 112, Tile.WIDTH, Tile.HEIGHT);

        this.grasTile = tileAssets.getSubimage(0, 128, Tile.WIDTH, Tile.HEIGHT);
        this.grasTile_Destroyed = tileAssets.getSubimage(16, 128, Tile.WIDTH, Tile.HEIGHT);

        // Erde
        this.dirtDark = tileAssets.getSubimage(0, 144, Tile.WIDTH, Tile.HEIGHT);
        this.dirtDark_Destroyed = tileAssets.getSubimage(16, 144, Tile.WIDTH, Tile.HEIGHT);
        this.dirtMidDark = tileAssets.getSubimage(0, 160, Tile.WIDTH, Tile.HEIGHT);
        this.dirtMidDark_Destroyed = tileAssets.getSubimage(16, 160, Tile.WIDTH, Tile.HEIGHT);
        this.dirt = tileAssets.getSubimage(0, 176, Tile.WIDTH, Tile.HEIGHT);
        this.dirt_Destroyed = tileAssets.getSubimage(16, 176, Tile.WIDTH, Tile.HEIGHT);

        // Stein
        this.stoneWhite1 = tileAssets.getSubimage(0, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhite2 = tileAssets.getSubimage(16, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhite3 = tileAssets.getSubimage(32, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhiteLeafs = tileAssets.getSubimage(48, 192, Tile.WIDTH, Tile.HEIGHT);
        this.stoneWhite_Destroyed = tileAssets.getSubimage(64, 192, Tile.WIDTH, Tile.HEIGHT);

        this.stoneRed1 = tileAssets.getSubimage(0, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRed2 = tileAssets.getSubimage(16, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRed3 = tileAssets.getSubimage(32, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRedLeafs = tileAssets.getSubimage(48, 208, Tile.WIDTH, Tile.HEIGHT);
        this.stoneRed_Destroyed = tileAssets.getSubimage(64, 208, Tile.WIDTH, Tile.HEIGHT);

        // Kupfer, Silber und Gold
        copper = tileAssets.getSubimage(0, 224, Tile.WIDTH, Tile.HEIGHT);
        copper_Destroyed = tileAssets.getSubimage(16, 224, Tile.WIDTH, Tile.HEIGHT);

        silver = tileAssets.getSubimage(0, 240, Tile.WIDTH, Tile.HEIGHT);
        silver_Destroyed = tileAssets.getSubimage(16, 240, Tile.WIDTH, Tile.HEIGHT);

        gold = tileAssets.getSubimage(0, 256, Tile.WIDTH, Tile.HEIGHT);
        gold_Destroyed = tileAssets.getSubimage(16, 256, Tile.WIDTH, Tile.HEIGHT);

        // Holz und Blatt
        leafBottomLeftCorner = tileAssets.getSubimage(0, 272, Tile.WIDTH, Tile.HEIGHT);
        leafBottom = tileAssets.getSubimage(16, 272, Tile.WIDTH, Tile.HEIGHT);
        leafBottomRightCorner = tileAssets.getSubimage(32, 272, Tile.WIDTH, Tile.HEIGHT);
        leafRight = tileAssets.getSubimage(48, 272, Tile.WIDTH, Tile.HEIGHT);
        leafNormal = tileAssets.getSubimage(64, 272, Tile.WIDTH, Tile.HEIGHT);
        leafLeft = tileAssets.getSubimage(80, 272, Tile.WIDTH, Tile.HEIGHT);
        leafTopLeftCorner = tileAssets.getSubimage(96, 272, Tile.WIDTH, Tile.HEIGHT);
        leafTop = tileAssets.getSubimage(112, 272, Tile.WIDTH, Tile.HEIGHT);
        leafTopRightCorner = tileAssets.getSubimage(128, 272, Tile.WIDTH, Tile.HEIGHT);

        treeTrunkRootLeft = tileAssets.getSubimage(0, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkBottomLeft = tileAssets.getSubimage(16, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkBottomRight = tileAssets.getSubimage(32, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkRootRight = tileAssets.getSubimage(48, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkRoundedCornerTopLeft = tileAssets.getSubimage(64, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkNextToCorner = tileAssets.getSubimage(80, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkHorizontalNormal = tileAssets.getSubimage(96, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkRoundedCornerBottomRight = tileAssets.getSubimage(112, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkVerticalNormal = tileAssets.getSubimage(128, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopCenter = tileAssets.getSubimage(144, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopLeft = tileAssets.getSubimage(160, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopLeftEnd = tileAssets.getSubimage(176, 288, Tile.WIDTH, Tile.HEIGHT);
        treeTrunkTopRightEnd = tileAssets.getSubimage(192, 288, Tile.WIDTH, Tile.HEIGHT);

        // Anzahl der Spalten in der Map-Datei
        try {
            Scanner scanner = new Scanner(new FileReader(mapFilePath));
            String line = scanner.nextLine();
            numberOfColumns = line.length();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    /*
    * update
    * */
    public void update() {
        // Schaue ob Tile-Texture existiert, wenn nicht loesche Tile aus der Liste
//        for(int row = 0; row < tiles.size(); row++) {
//            for(int column = 0; column < tiles.get(row).size(); column++) {
//                if(tiles.get(row).get(column) == null)
//                    tiles.remove(tiles.get(row).get(column));
//            }
//        }
    }

    /*
    * render
    *
    * @param graphics   - Graphics Objekt
    * */
    public void render(Graphics graphics) {
        // Aufrufen der render-Methode des durchlaufenen Tiles
        for(int row = rowOffset; row < rowOffset+numberOfRowsToDraw; row++) {
            if(row >= tiles.size())
                break;
            for(int column = columnOffset; column < columnOffset+numberOfColumns; column++) {
                if(column >= tiles.get(row).size())
                    break;
                tiles.get(row).get(column).setX( (int)x + column * Tile.WIDTH );
                tiles.get(row).get(column).setY( (int)y + row * Tile.HEIGHT );
                tiles.get(row).get(column).render(graphics);
        }
        }
    }

    /*
    * loadMap - Laden der Map-Dateien
    * */
    public void loadMap() {
        int x = 0;
        int y = 0;
        int row = 0;
        int column = 0;
        String[] numbers;
        int numberAsInt;
        String line;

        try {
            Scanner scanner = new Scanner(new FileReader(mapFilePath));

            // Lese Zeile für Zeile ein
            while (scanner.hasNextLine()) {
                tiles.add(new ArrayList<Tile>());
                line = scanner.nextLine();

                numbers = line.split("[,]");

                // Iterieren ueber die Spalten der Zeile
                for(String number : numbers) {
                    try {
                        numberAsInt = Integer.parseInt(number);
                        switch (numberAsInt) {
                            case 0:
                                tiles.get(row).add(new Tile(null, x, y, row, column, false, false, false));
                                column++;
                                break;
                            case Tile.WATERTILE_TOP:
                                tiles.get(row).add(new WaterTile(waterTileTop, x, y, row, column, true, false, false));
                                column++;
                                break;
                            case Tile.WATERTILE:
                                tiles.get(row).add(new WaterTile(waterTile, x, y, row, column, true, false, false) );
                                column++;
                                break;
                            case Tile.LAVALTILETOP:
                                tiles.get(row).add(new LavaTile(lavaTileTop, x, y, row, column, true, false, false));
                                column++;
                                break;
                            case Tile.LAVATILE:
                                tiles.get(row).add(new LavaTile(lavaTile, x, y, row, column, true, false, false));
                                column++;
                                break;
                            case Tile.GRASTILE_RIGHT:
                                tiles.get(row).add(new GrasTile(grasTileRight, x, y, row, column, true, true, true));
                                column++;
                            case Tile.GRASTILE_LEFT:
                                tiles.get(row).add(new GrasTile(grasTileLeft, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GRASTILE_BOTTOM:
                                tiles.get(row).add(new GrasTile(grasTileBottom, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GRASTILE_BOTTOMRIGHTCORNER:
                                tiles.get(row).add(new GrasTile(grasTileBottomRightCorner, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GRASTILE_BOTTOMLEFTCORNER:
                                tiles.get(row).add(new GrasTile(grasTileBottomLeftCorner, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GRASTILE_TOPRIGHTCORNER:
                                tiles.get(row).add(new GrasTile(grasTileTopRightCorner, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GRASTILE_TOPLEFTCORNER:
                                tiles.get(row).add(new GrasTile(grasTileTopLeftCorner, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GRASTILE:
                                tiles.get(row).add(new GrasTile(grasTile, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.DIRTDARK:
                                tiles.get(row).add(new DirtTile(dirtDark, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.DIRTMIDDARK:
                                tiles.get(row).add(new DirtTile(dirtMidDark, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.DIRT:
                                tiles.get(row).add(new DirtTile(dirt, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.STONEWHITE1:
                                tiles.get(row).add(new StoneTile(stoneWhite1, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONEWHITE2:
                                tiles.get(row).add(new StoneTile(stoneWhite2, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONEWHITE3:
                                tiles.get(row).add(new StoneTile(stoneWhite3, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONEWHITELEAF:
                                tiles.get(row).add(new StoneTile(stoneWhiteLeafs, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONERED1:
                                tiles.get(row).add(new StoneTile(stoneRed1, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONERED2:
                                tiles.get(row).add(new StoneTile(stoneRed2, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONERED3:
                                tiles.get(row).add(new StoneTile(stoneRed3, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.STONEREDLEAF:
                                tiles.get(row).add(new StoneTile(stoneRedLeafs, x, y, row, column, true, true, false));
                                column++;
                                break;
                            case Tile.COPPER:
                                tiles.get(row).add(new CopperTile(copper, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.SILVER:
                                tiles.get(row).add(new SilverTile(silver, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.GOLD:
                                tiles.get(row).add(new GoldTile(gold, x, y, row, column, true, true, true));
                                column++;
                                break;
                            case Tile.LEAFBOTTOM_LEFTCORNER:
                                tiles.get(row).add(new LeafTile(leafBottomLeftCorner, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFBOTTOM:
                                tiles.get(row).add(new LeafTile(leafBottom, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFBOTTOM_RIGHTCORNER:
                                tiles.get(row).add(new LeafTile(leafBottomRightCorner, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFRIGHT:
                                tiles.get(row).add(new LeafTile(leafRight, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFNORMAL:
                                tiles.get(row).add(new LeafTile(leafNormal, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFLEFT:
                                tiles.get(row).add(new LeafTile(leafLeft, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFTOP_LEFTCORNER:
                                tiles.get(row).add(new LeafTile(leafTopLeftCorner, x, y, row, column, true, false, true, true));
                                column++;;
                                break;
                            case Tile.LEAFTOP:
                                tiles.get(row).add(new LeafTile(leafTop, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.LEAFTOP_RIGHTCORNER:
                                tiles.get(row).add(new LeafTile(leafTopRightCorner, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_ROOTLEFT:
                                tiles.get(row).add(new WoodTile(treeTrunkRootLeft, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_BOTTOMLEFT:
                                tiles.get(row).add(new WoodTile(treeTrunkBottomLeft, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_BOTTOMRIGHT:
                                tiles.get(row).add(new WoodTile(treeTrunkBottomRight, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_ROOTRIGHT:
                                tiles.get(row).add(new WoodTile(treeTrunkRootRight, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_ROUNDEDCORNER_TOPLEFT:
                                tiles.get(row).add(new WoodTile(treeTrunkRoundedCornerTopLeft, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_NEXTTOCORNER:
                                tiles.get(row).add(new WoodTile(treeTrunkNextToCorner, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_HORIZONTALNORMAL:
                                tiles.get(row).add(new WoodTile(treeTrunkHorizontalNormal, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_ROUNDEDCORNER_BOTTOMRIGHT:
                                tiles.get(row).add(new WoodTile(treeTrunkRoundedCornerBottomRight, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_VERTICALNORMAL:
                                tiles.get(row).add(new WoodTile(treeTrunkVerticalNormal, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_TOPCENTER:
                                tiles.get(row).add(new WoodTile(treeTrunkTopCenter, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_TOPLEFT:
                                tiles.get(row).add(new WoodTile(treeTrunkTopLeft, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_TOPLEFTEND:
                                tiles.get(row).add(new WoodTile(treeTrunkTopLeftEnd, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            case Tile.TREETRUNK_TOPRIGHTEND:
                                tiles.get(row).add(new WoodTile(treeTrunkTopRightEnd, x, y, row, column, true, false, true, true));
                                column++;
                                break;
                            default:
                                tiles.get(row).add(new DirtTile(dirt, x, y, row, column, true, true, true));
                                column++;
                                break;
                        }
                        x += Tile.WIDTH;    // Erhoehe x Position fuer naechste Spalte
                    }
                    catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        continue;
                    }
                }
                // Reset, da naechste Zeile abgearbeitet wird
                x = 0;
                y += Tile.HEIGHT;

                column = 0;
                row++;
                numberOfRows = row;
            }
            scanner.close();

            width = numberOfColumns * Tile.WIDTH;
            height = numberOfRows * Tile.HEIGHT;

            xmin = ScreenDimensions.WIDTH - width;
            xmax = 0;
            ymin = ScreenDimensions.HEIGHT - height;
            ymax = 0;
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Getter and Setter
    * */
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public ArrayList<ArrayList<Tile>> getTiles() { return this.tiles; }

    public Tile getTile(int row, int column) {
        try {
            return tiles.get(row).get(column);
        }
        catch(IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public int getNumberOfColumns() { return this.numberOfColumns; }
    public int getNumberOfRows() { return this.numberOfRows; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;

        // Auf Grenzen prüfen
        if(this.x < this.xmin)
            this.x = this.xmin;
        if(this.x < this.ymin)
            this.y = this.ymin;
        if(this.x > this.xmax)
            this.x = this.xmax;
        if(this.y > this.ymax)
            this.y = this.ymax;

        // Offsets setzen
        this.columnOffset = (int)-this.x / Tile.WIDTH;
        this.rowOffset = (int)-this.y / Tile.HEIGHT;
    }

    /*
    * EventListener
    * */
    public void mouseClicked(MouseEvent e) {
        // Mauszeiger Klickpunkt
        Point point = e.getPoint();

        // Durchlaufe Liste und pruefe ob auf ein Tile geklickt wurde
        for(int row = 0; row < tiles.size(); row++) {
            for(int column = 0; column < tiles.get(row).size(); column++) {
                selectedTile = tiles.get(row).get(column);

                // Erstelle Rechteck mit der Groesse des ausgewaehlten Tile
                selectedTileBounds = new Rectangle(selectedTile.getX(), selectedTile.getY(), Tile.WIDTH, Tile.HEIGHT);

                // Pruefe ob Klickpunkt im Bereich des Rechtecks liegt
                if(selectedTileBounds.contains(point) && selectedTile.isCollidable) {

                    // Falls Tile zerstoerbar ist
                    if(selectedTile.isDestructible) {
                        // Falls Tile zerstoerbar ist loesche es

                        // Falls Tile von der Schwerkraft angezogen wird
                        if(getTile(selectedTile.getRow()-1, selectedTile.getColumn()) != null && getTile(selectedTile.getRow()-1, selectedTile.getColumn()).hasGravity) {
                            tmp = getTile(selectedTile.getRow()-1, selectedTile.getColumn());
                            selectedTile.delete();
                            selectedTile.setIsCollidable(false);
                            while(tmp != null && tmp.hasGravity) {
                                selectedTileForGravity = tmp;
                                selectedTileForGravity.setY(selectedTileForGravity.getY() + Tile.HEIGHT);
                                //selectedTileForGravity.setRow(selectedTileForGravity.getRow() + 1);
                                tmp = getTile(selectedTileForGravity.getRow()-1, selectedTileForGravity.getColumn());
                            }
                        }
                        else {
                            selectedTile.delete();
                            selectedTile.setIsCollidable(false);
                        }

                    }

                }

            }
        }


    }

}
