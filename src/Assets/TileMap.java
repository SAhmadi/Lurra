package Assets;

import Assets.Inventory.Inventory;
import Assets.Tiles.*;
import GameData.GameData;
import Main.ResourceLoader;
import Main.ScreenDimensions;
import PlayerData.PlayerData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.awt.image.BufferedImageDevice;

import javax.script.ScriptEngine;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/*
* TileMap - Spielfeld bestehend aus Tiles
* */
public class TileMap {
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
    public int numberOfColumns, numberOfRows;
    private int puffer = 2;

    // Offset
    private int columnOffset, rowOffset;
    private int numberOfColumnsToDraw, numberOfRowsToDraw;
    private int rowGenerateStartPoint;

    /*
    * Tiles
    * */
    private ArrayList<ArrayList<Tile>> tiles;

    // Map - Pfad
    private String mapFilePath;

    // MouseClicked - Variablen
    private Rectangle selectedTileBounds;
    private Tile selectedTile;
    private Tile selectedTileForGravity;
    private Tile tmp;


    //


    /*
    * Konstruktor
    * */
    public TileMap(String mapFilePath, int numberOfColumns, int numberOfRows) {
        this.mapFilePath = mapFilePath;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.rowGenerateStartPoint = (ScreenDimensions.HEIGHT/Tile.HEIGHT)/2;

        this.tiles = new ArrayList<ArrayList<Tile>>();

        // Anzahl Spalten und Reihe zum Zeichnen
        this.numberOfColumnsToDraw = ScreenDimensions.WIDTH / Tile.WIDTH + this.puffer;
        this.numberOfRowsToDraw = ScreenDimensions.HEIGHT / Tile.HEIGHT + this.puffer;

        width = numberOfColumns * Tile.WIDTH;
        height = numberOfRows * Tile.HEIGHT;

        xmin = ScreenDimensions.WIDTH - width;
        ymin = ScreenDimensions.HEIGHT - height;
        xmax = 0;
        ymax = 0;
    }

    /*
    * update
    * */
    public void update() {}

    /*
    * render
    *
    * @param graphics   - Graphics Objekt
    * */
    public void render(Graphics graphics) {
        // Aufrufen der render-Methode des durchlaufenen Tiles
        for(int row = rowOffset; row < rowOffset+numberOfRowsToDraw; row++) {
            if(row >= tiles.size()) break;

            for(int column = columnOffset; column < columnOffset+numberOfColumnsToDraw; column++) {
                try {
                    if(column >= tiles.get(row).size()) break;

                    tiles.get(row).get(column).setX( (int)x + column * Tile.WIDTH );
                    tiles.get(row).get(column).setY( (int)y + row * Tile.HEIGHT );
                    tiles.get(row).get(column).setRow((int)y * Tile.HEIGHT);
                    tiles.get(row).get(column).setColumn((int)x * Tile.WIDTH);
                    tiles.get(row).get(column).render(graphics);

                }
                catch(IndexOutOfBoundsException ex) {

                }

            }
        }
    }

    /*
    * createLevel - Zufälliges Erstellen der Tiles
    * */
    /*public void createLevel(int skyOffset) {
        //int[] earthInRow = RandomLevel.generateEarth();
        Random rand = new Random();
        int[] earthTilesID = {131, 118, 144};

        boolean[] earthAboveWasPlaced = new boolean[numberOfColumns];
        boolean loopAgain = false;

        Charset charset = Charset.forName("UTF-8");
        String s;

        try {
            File file = new File(mapFilePath);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for(int row = 0; row < numberOfRows; row++) {

                for (int column = 0; column < numberOfColumns; column++) {
                    if(row > ScreenDimensions.HEIGHT/100*3) {
                        // Link Oben
                        try {
                            if(tiles.get(row-1).get(column-1).getTexture() == dirt) {
                                tiles.get(row).get(column).setTexture(dirt);
                                tiles.get(row).get(column).setIsCollidable(true);
                                tiles.get(row).get(column).setHasGravity(true);
                                tiles.get(row).get(column).setIsDestructible(true);
                            }
                        }
                        catch(Exception ex) {}

                        //
                        try {
                            if(tiles.get(row-1).get(column+1).getTexture() == dirt) {
                                tiles.get(row).get(column).setTexture(dirt);
                                tiles.get(row).get(column).setIsCollidable(true);
                                tiles.get(row).get(column).setHasGravity(true);
                                tiles.get(row).get(column).setIsDestructible(true);
                            }
                        }
                        catch(Exception ex) {}

                        //
                        try {
                            if(tiles.get(row-1).get(column).getTexture() == dirt) {
                                tiles.get(row).get(column).setTexture(dirt);
                                tiles.get(row).get(column).setIsCollidable(true);
                                tiles.get(row).get(column).setHasGravity(true);
                                tiles.get(row).get(column).setIsDestructible(true);
                            }
                        }
                        catch(Exception ex) {}

                        if(new Random().nextInt(100) < 5) {
                            tiles.get(row).get(column).setTexture(dirt);
                            tiles.get(row).get(column).setIsCollidable(true);
                            tiles.get(row).get(column).setHasGravity(true);
                            tiles.get(row).get(column).setIsDestructible(true);
                        }
                    }
                }

            }


            bw.close();
            //System.out.println("Erfolgreich!");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }*/


    /**
     *
     *
     *
     * */
    public void generateMap(int flatness) {
        BufferedImage[] earthTextures = {ResourceLoader.dirt,ResourceLoader.dirtMidDark, ResourceLoader.dirtDark};
        BufferedImage[] metalTextures = {ResourceLoader.gold, ResourceLoader.copper, ResourceLoader.silver};

        // Initialisiere Map
        for(int row = 0; row < numberOfRows; row++) {
            tiles.add(new ArrayList<Tile>());

            for (int column = 0; column < numberOfColumns; column++) {

                tiles.get(row).add(new Tile(null, column*Tile.WIDTH, row*Tile.HEIGHT, row, column, false, false, false));
            }
        }

        // Erd-Layer
        int col = flatness;
        while(col < numberOfColumns - flatness) {
            try {
                for(int i = 0; i < flatness; i++) {
                    if(tiles.get(rowGenerateStartPoint).get(col + i).getTexture() == null) {
                        tiles.get(rowGenerateStartPoint).get(col + i).setTexture(earthTextures[new Random().nextInt(3)]);
                        tiles.get(rowGenerateStartPoint).get(col + i).setIsCollidable(true);
                        tiles.get(rowGenerateStartPoint).get(col + i).setHasGravity(true);
                        tiles.get(rowGenerateStartPoint).get(col + i).setIsDestructible(true);

                        for (int j = 1; j < numberOfRows - rowGenerateStartPoint; j++) {
                            try {
                                if(tiles.get(rowGenerateStartPoint + j).get(col + i).getTexture() == null) {
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setTexture(earthTextures[new Random().nextInt(3)]);
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setIsCollidable(true);
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setHasGravity(true);
                                    tiles.get(rowGenerateStartPoint + j).get(col + i).setIsDestructible(true);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (new Random().nextInt(10) < 5) {
                if (rowGenerateStartPoint++ >= numberOfRows - numberOfRows/5)
                    rowGenerateStartPoint -= numberOfRows/10;
                else
                    rowGenerateStartPoint++;
            } else {
                if (rowGenerateStartPoint-- <= numberOfRows/8)
                    rowGenerateStartPoint += numberOfRows/10;
                else
                    rowGenerateStartPoint--;
            }

            col++;
        }

        // Gras-Layer
        for(int row = 0; row < numberOfRows; row++) {
            for(int column = 0; column < numberOfColumns; column++) {
                try {
                    // Positioniere Grastile auf Erdtile-Oberste-Schicht
                    if((tiles.get(row + 1).get(column).getTexture() == ResourceLoader.dirt
                            || tiles.get(row + 1).get(column).getTexture() == ResourceLoader.dirtMidDark
                            || tiles.get(row + 1).get(column).getTexture() == ResourceLoader.dirtDark)
                            && tiles.get(row - 1).get(column).getTexture() == null) {

                        tiles.get(row).get(column).setTexture(ResourceLoader.grasTile);
                        tiles.get(row).get(column).setIsCollidable(true);
                        tiles.get(row).get(column).setHasGravity(true);
                        tiles.get(row).get(column).setIsDestructible(true);

                    }
                }
                catch(Exception ex) {}
            }
        }


        // Lava-Layer
        int lavaStartRowLeft = 0;
        int lavaStartRowRight = 0;
        outerloop:
        for(int row = 0; row < numberOfRows; row++) {
            for(int columnLeft = 0; columnLeft <= flatness; columnLeft++) {
                if(tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.dirt
                        || tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.dirtMidDark
                        || tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.dirtDark
                        || tiles.get(row).get(columnLeft + 1).getTexture() == ResourceLoader.grasTile) {

                    lavaStartRowLeft = row;
                    break;

                }
            }

            for(int columnRight = numberOfColumns - flatness; columnRight < numberOfColumns; columnRight++) {
                if(tiles.get(row + 1).get(columnRight).getTexture() == ResourceLoader.dirt
                        || tiles.get(row + 1).get(columnRight).getTexture() == ResourceLoader.dirtMidDark
                        || tiles.get(row + 1).get(columnRight).getTexture() == ResourceLoader.dirtDark
                        || tiles.get(row).get(columnRight).getTexture() == ResourceLoader.grasTile) {

                    lavaStartRowRight = row;
                    break outerloop;

                }
            }
        }

        for(int row = 0; row < numberOfRows; row++) {
            for(int column = 0; column < numberOfColumns; column++) {
                if(column < flatness || column > numberOfColumns - flatness) {
                    if(row > lavaStartRowLeft && column < flatness && tiles.get(row).get(column).getTexture() == null) {
                        if(row == lavaStartRowLeft + 1) {
                            try {
                                // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                tiles.get(row).get(column).setTexture(ResourceLoader.lavaTileTop);
                                tiles.get(row).get(column).setIsCollidable(false);
                                tiles.get(row).get(column).setHasGravity(false);
                                tiles.get(row).get(column).setIsDestructible(false);
                            }
                            catch(Exception ex) {}
                        }
                        else if(tiles.get(row).get(column).getTexture() == null) {
                            try {
                                // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                tiles.get(row).get(column).setIsCollidable(false);
                                tiles.get(row).get(column).setHasGravity(false);
                                tiles.get(row).get(column).setIsDestructible(false);
                            }
                            catch(Exception ex) {}

                        }
                    }
                    else if(row > lavaStartRowRight && column > numberOfColumns - flatness) {
                        if(row == lavaStartRowRight + 1 && tiles.get(row).get(column).getTexture() == null) {
                            try {
                                // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                tiles.get(row).get(column).setTexture(ResourceLoader.lavaTileTop);
                                tiles.get(row).get(column).setIsCollidable(false);
                                tiles.get(row).get(column).setHasGravity(false);
                                tiles.get(row).get(column).setIsDestructible(false);
                            }
                            catch(Exception ex) {}
                        }
                        else if(tiles.get(row).get(column).getTexture() == null) {
                            try {
                                // Positioniere Grastile auf Erdtile-Oberste-Schicht
                                tiles.get(row).get(column).setTexture(ResourceLoader.lavaTile);
                                tiles.get(row).get(column).setIsCollidable(false);
                                tiles.get(row).get(column).setHasGravity(false);
                                tiles.get(row).get(column).setIsDestructible(false);
                            }
                            catch(Exception ex) {}

                        }
                    }
                }
            }
        }


        // Baum
        boolean setTree = false;
        int treeStartPoint;
        for(int row = 0; row < numberOfRows; row++) {
            for(int column = 0; column < numberOfColumns; column++) {
                for(int i = 0; i < 9; i++) {
                    if(tiles.get(row).get(column + i).getTexture() == ResourceLoader.grasTile) {
                       setTree = true;
                    }
                    else {
                        setTree = false;
                        break;
                    }
                }

                if(setTree) {
                    // Baumstamm
                    try {
                        treeStartPoint = new Random().nextInt(5);
                        tiles.get(row - 1).get(column + treeStartPoint).setTexture(ResourceLoader.treeTrunkRootLeft);
                        tiles.get(row - 1).get(column + treeStartPoint + 1).setTexture(ResourceLoader.treeTrunkBottomLeft);
                        tiles.get(row - 1).get(column + treeStartPoint + 2).setTexture(ResourceLoader.treeTrunkBottomRight);
                        tiles.get(row - 1).get(column + treeStartPoint + 3).setTexture(ResourceLoader.treeTrunkRootRight);

                        tiles.get(row - 2).get(column + treeStartPoint + 1).setTexture(ResourceLoader.treeTrunkRoundedCornerTopLeft);
                        tiles.get(row - 2).get(column + treeStartPoint + 2).setTexture(ResourceLoader.treeTrunkNextToCorner);
                        tiles.get(row - 2).get(column + treeStartPoint + 3).setTexture(ResourceLoader.treeTrunkHorizontalNormal);
                        tiles.get(row - 2).get(column + treeStartPoint + 4).setTexture(ResourceLoader.treeTrunkRoundedCornerBottomRight);

                        tiles.get(row - 3).get(column + treeStartPoint + 4).setTexture(ResourceLoader.treeTrunkVerticalNormal);

                        tiles.get(row - 4).get(column + treeStartPoint + 4).setTexture(ResourceLoader.treeTrunkTopCenter);
                        tiles.get(row - 4).get(column + treeStartPoint + 3).setTexture(ResourceLoader.treeTrunkTopLeft);
                        tiles.get(row - 4).get(column + treeStartPoint + 2).setTexture(ResourceLoader.treeTrunkTopLeftEnd);
                        tiles.get(row - 4).get(column + treeStartPoint + 5).setTexture(ResourceLoader.treeTrunkTopRightEnd);

                        // Baumkrone
                        tiles.get(row - 5).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafBottomRightCorner);
                        tiles.get(row - 5).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafBottom);
                        tiles.get(row - 5).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafBottom);
                        tiles.get(row - 5).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafBottom);
                        tiles.get(row - 5).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafBottom);
                        tiles.get(row - 5).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafBottom);
                        tiles.get(row - 5).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafBottomLeftCorner);

                        tiles.get(row - 6).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafRight);
                        tiles.get(row - 6).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafNormal);
                        tiles.get(row - 6).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafNormal);
                        tiles.get(row - 6).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafNormal);
                        tiles.get(row - 6).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafNormal);
                        tiles.get(row - 6).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafNormal);
                        tiles.get(row - 6).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafLeft);

                        if(new Random().nextInt(10) < 5) {
                            tiles.get(row - 7).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafRight);
                            tiles.get(row - 7).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 7).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 7).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 7).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 7).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafNormal);
                            tiles.get(row - 7).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafLeft);

                            tiles.get(row - 8).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafTopRightCorner);
                            tiles.get(row - 8).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 8).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 8).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 8).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 8).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 8).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafTopLeftCorner);
                        }
                        else {
                            tiles.get(row - 7).get(column + treeStartPoint + 7).setTexture(ResourceLoader.leafTopRightCorner);
                            tiles.get(row - 7).get(column + treeStartPoint + 6).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 5).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 4).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 3).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 2).setTexture(ResourceLoader.leafTop);
                            tiles.get(row - 7).get(column + treeStartPoint + 1).setTexture(ResourceLoader.leafTopLeftCorner);
                        }
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }

                    break;
                }
            }
        }

        // Gold-Kupfer-Silber
        for(int row = numberOfRows/10; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                if ((tiles.get(row).get(column).getTexture() == ResourceLoader.dirt
                        || tiles.get(row).get(column).getTexture() == ResourceLoader.dirtMidDark
                        || tiles.get(row).get(column).getTexture() == ResourceLoader.dirtDark)
                        && tiles.get(row - 1).get(column).getTexture() != ResourceLoader.grasTile) {

                    if (new Random().nextInt(100) < 5) {
                        tiles.get(row).get(column).setTexture(metalTextures[new Random().nextInt(metalTextures.length)]);
                    }

                }
            }
        }

        /*
        * Speichern der erzeugten Welt
        * */
        levelSave(tiles);

    }

    public void levelSave(ArrayList<ArrayList<Tile>> tiles) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("tiles");
            doc.appendChild(rootElement);

            /* TILES */
            for(int row = 0; row < numberOfRows; row++) {
                for(int column = 0; column < numberOfColumns; column++) {
                    Element tileElement = doc.createElement("tile");
                    tileElement.setAttribute("texture", tiles.get(row).get(column).getTextureAsString());
                    tileElement.setAttribute("name", tiles.get(row).get(column).name);
                    tileElement.setAttribute("x", Integer.toString(tiles.get(row).get(column).getX()));
                    tileElement.setAttribute("y", Integer.toString(tiles.get(row).get(column).getY()));
                    tileElement.setAttribute("row", Integer.toString(tiles.get(row).get(column).getRow()));
                    tileElement.setAttribute("column", Integer.toString(tiles.get(row).get(column).getColumn()));
                    tileElement.setAttribute("collidable", Boolean.toString(tiles.get(row).get(column).isCollidable));
                    tileElement.setAttribute("gravity", Boolean.toString(tiles.get(row).get(column).hasGravity));
                    tileElement.setAttribute("destructable", Boolean.toString(tiles.get(row).get(column).isDestructible));
                    tileElement.setAttribute("tree", Boolean.toString(tiles.get(row).get(column).belongsToTree));
                    tileElement.setAttribute("resistance", Integer.toString(tiles.get(row).get(column).getResistance()));

                    rootElement.appendChild(tileElement);
                }
            }


            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/xml/playerLevelSaves/" + PlayerData.name + ".xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        }
        catch(ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch(TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public void levelLoad() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File("res/xml/playerLevelSaves/" + PlayerData.name + ".xml"));

            //Einstellungen
            NodeList tilesList = document.getElementsByTagName("*");

            for(int i = 0; i < tilesList.getLength(); i++) {
                Node tilesNode = tilesList.item(i);
                Element tilesNodeAsElement = (Element) tilesNode;


            }

        }
        catch(ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }




    }

//    public void create() {
//        BufferedImage[] earthTextures = {dirt, dirtMidDark, dirtDark};
//        int rowAdd;
//
//        for(int row = rowOffset; row < rowOffset + numberOfRowsToDraw; row++) {
//            tiles.add(new ArrayList<Tile>());
//
//            for(int column = columnOffset; column < columnOffset + numberOfColumnsToDraw; column++) {
//
//                if(row > rowGenerateStartPoint) {
//                    if(new Random().nextInt(10) < 5) {
//                        try {
//                            rowAdd = new Random().nextInt(5);
//                            tiles.get(row + rowAdd).add(new Tile(earthTextures[new Random().nextInt(earthTextures.length)], column * Tile.WIDTH, (row + rowAdd)* Tile.HEIGHT, row + rowAdd, column, true, true, true));
//                        }
//                        catch (IndexOutOfBoundsException ex) {}
//                    }
//                    else {
//                        try {
//                            rowAdd = new Random().nextInt(5);
//                            tiles.get(row - rowAdd).add(new Tile(earthTextures[new Random().nextInt(earthTextures.length)], column * Tile.WIDTH, (row - rowAdd)* Tile.HEIGHT, row - rowAdd, column, true, true, true));
//                        }
//                        catch (IndexOutOfBoundsException ex) {}
//                    }
//                }
//                else {
//                    try {
//                        tiles.get(row).add(new Tile(null, column * Tile.WIDTH, row * Tile.HEIGHT, row, column, false, false, false));
//                    }
//                    catch (IndexOutOfBoundsException ex) {}
//                }
//            }
//        }
//
//
//        // Update Grenzen
//        xmax = ScreenDimensions.WIDTH - tiles.size()*Tile.WIDTH;
//        ymax = ScreenDimensions.HEIGHT - tiles.get(0).size()*Tile.HEIGHT;
//
//    }

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

            line = scanner.nextLine();
            numbers = line.split("[,]");
            //numberOfColumns = numbers.length;

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
//                            case Tile.WATERTILE_TOP:
//                                tiles.get(row).add(new WaterTile(waterTileTop, x, y, row, column, true, false, false));
//                                row++;
//                                break;
//                            case Tile.WATERTILE:
//                                tiles.get(row).add(new WaterTile(waterTile, x, y, row, column, true, false, false) );
//                                row++;
//                                break;
//                            case Tile.LAVALTILETOP:
//                                tiles.get(row).add(new LavaTile(lavaTileTop, x, y, row, column, true, false, false));
//                                column++;
//                                break;
//                            case Tile.LAVATILE:
//                                tiles.get(row).add(new LavaTile(lavaTile, x, y, row, column, true, false, false));
//                                column++;
//                                break;
//                            case Tile.GRASTILE_RIGHT:
//                                tiles.get(row).add(new GrasTile(grasTileRight, x, y, row, column, true, true, true));
//                                column++;
//                            case Tile.GRASTILE_LEFT:
//                                tiles.get(row).add(new GrasTile(grasTileLeft, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GRASTILE_BOTTOM:
//                                tiles.get(row).add(new GrasTile(grasTileBottom, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GRASTILE_BOTTOMRIGHTCORNER:
//                                tiles.get(row).add(new GrasTile(grasTileBottomRightCorner, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GRASTILE_BOTTOMLEFTCORNER:
//                                tiles.get(row).add(new GrasTile(grasTileBottomLeftCorner, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GRASTILE_TOPRIGHTCORNER:
//                                tiles.get(row).add(new GrasTile(grasTileTopRightCorner, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GRASTILE_TOPLEFTCORNER:
//                                tiles.get(row).add(new GrasTile(grasTileTopLeftCorner, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GRASTILE:
//                                tiles.get(row).add(new GrasTile(grasTile, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.DIRTDARK:
//                                tiles.get(row).add(new DirtTile(dirtDark, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.DIRTMIDDARK:
//                                tiles.get(row).add(new DirtTile(dirtMidDark, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.DIRT:
//                                tiles.get(row).add(new DirtTile(dirt, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.STONEWHITE1:
//                                tiles.get(row).add(new StoneTile(stoneWhite1, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONEWHITE2:
//                                tiles.get(row).add(new StoneTile(stoneWhite2, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONEWHITE3:
//                                tiles.get(row).add(new StoneTile(stoneWhite3, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONEWHITELEAF:
//                                tiles.get(row).add(new StoneTile(stoneWhiteLeafs, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONERED1:
//                                tiles.get(row).add(new StoneTile(stoneRed1, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONERED2:
//                                tiles.get(row).add(new StoneTile(stoneRed2, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONERED3:
//                                tiles.get(row).add(new StoneTile(stoneRed3, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.STONEREDLEAF:
//                                tiles.get(row).add(new StoneTile(stoneRedLeafs, x, y, row, column, true, true, false));
//                                column++;
//                                break;
//                            case Tile.COPPER:
//                                tiles.get(row).add(new CopperTile(copper, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.SILVER:
//                                tiles.get(row).add(new SilverTile(silver, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.GOLD:
//                                tiles.get(row).add(new GoldTile(gold, x, y, row, column, true, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFBOTTOM_LEFTCORNER:
//                                tiles.get(row).add(new LeafTile(leafBottomLeftCorner, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFBOTTOM:
//                                tiles.get(row).add(new LeafTile(leafBottom, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFBOTTOM_RIGHTCORNER:
//                                tiles.get(row).add(new LeafTile(leafBottomRightCorner, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFRIGHT:
//                                tiles.get(row).add(new LeafTile(leafRight, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFNORMAL:
//                                tiles.get(row).add(new LeafTile(leafNormal, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFLEFT:
//                                tiles.get(row).add(new LeafTile(leafLeft, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFTOP_LEFTCORNER:
//                                tiles.get(row).add(new LeafTile(leafTopLeftCorner, x, y, row, column, true, false, true, true));
//                                column++;;
//                                break;
//                            case Tile.LEAFTOP:
//                                tiles.get(row).add(new LeafTile(leafTop, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.LEAFTOP_RIGHTCORNER:
//                                tiles.get(row).add(new LeafTile(leafTopRightCorner, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_ROOTLEFT:
//                                tiles.get(row).add(new WoodTile(treeTrunkRootLeft, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_BOTTOMLEFT:
//                                tiles.get(row).add(new WoodTile(treeTrunkBottomLeft, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_BOTTOMRIGHT:
//                                tiles.get(row).add(new WoodTile(treeTrunkBottomRight, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_ROOTRIGHT:
//                                tiles.get(row).add(new WoodTile(treeTrunkRootRight, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_ROUNDEDCORNER_TOPLEFT:
//                                tiles.get(row).add(new WoodTile(treeTrunkRoundedCornerTopLeft, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_NEXTTOCORNER:
//                                tiles.get(row).add(new WoodTile(treeTrunkNextToCorner, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_HORIZONTALNORMAL:
//                                tiles.get(row).add(new WoodTile(treeTrunkHorizontalNormal, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_ROUNDEDCORNER_BOTTOMRIGHT:
//                                tiles.get(row).add(new WoodTile(treeTrunkRoundedCornerBottomRight, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_VERTICALNORMAL:
//                                tiles.get(row).add(new WoodTile(treeTrunkVerticalNormal, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_TOPCENTER:
//                                tiles.get(row).add(new WoodTile(treeTrunkTopCenter, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_TOPLEFT:
//                                tiles.get(row).add(new WoodTile(treeTrunkTopLeft, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_TOPLEFTEND:
//                                tiles.get(row).add(new WoodTile(treeTrunkTopLeftEnd, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            case Tile.TREETRUNK_TOPRIGHTEND:
//                                tiles.get(row).add(new WoodTile(treeTrunkTopRightEnd, x, y, row, column, true, false, true, true));
//                                column++;
//                                break;
//                            default:
//                                tiles.get(row).add(new DirtTile(dirt, x, y, row, column, true, true, true));
//                                column++;
//                                break;
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

                numberOfColumns = column;
                column = 0;
                row++;
                //numberOfRows = row;
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

        if(this.y < this.ymin)
            this.y = this.ymin;

        if(this.x > this.xmax)
            this.x = this.xmax;

        if(this.y > this.ymax)
            this.y = this.ymax;

        this.columnOffset = (int)-this.x / Tile.WIDTH;
        this.rowOffset = (int)-this.y / Tile.HEIGHT;
    }

    /*
    * EventListener
    * */
    public void mouseClicked(MouseEvent e) {
        boolean wasTreeHit = false;

        // Mauszeiger Klickpunkt
        Point point = e.getPoint();

        // Durchlaufe Liste und pruefe ob auf ein Tile geklickt wurde
        outerloop:
        for(int row = rowOffset; row < rowOffset + numberOfRowsToDraw; row++) {
            for(int column = columnOffset; column < columnOffset + numberOfColumnsToDraw; column++) {
                selectedTile = tiles.get(row).get(column);

                if(selectedTile.getTexture() != null) {
                    // Erstelle Rechteck mit der Groesse des ausgewaehlten Tile
                    selectedTileBounds = new Rectangle(selectedTile.getX(), selectedTile.getY(), Tile.WIDTH, Tile.HEIGHT);

                    // Pruefe ob Klickpunkt im Bereich des Rechtecks liegt
                    if(selectedTileBounds.contains(point)) {

                        if(selectedTile.isCollidable) {
                            // Falls Tile zerstoerbar ist
                            if(selectedTile.isDestructible) {
                                // Falls Tile zerstoerbar ist loesche es
                                if(selectedTile.getResistance() == 0) {
                                    selectedTile.setIsCollidable(false);
                                    Inventory.addToInventory(selectedTile);
                                    selectedTile.delete();
                                    break;
                                }
                                else {
                                    selectedTile.wasHit();
                                }

                            }
                        }

                        if(selectedTile.belongsToTree) {
                            if(selectedTile.getResistance() == 0) {
                                Inventory.addToInventory(selectedTile);
                                selectedTile.delete();
                            }
                            else {
                                selectedTile.wasHit();
                            }
                        }

                    }

                }


            }
        }


    }

}
