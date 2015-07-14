package GameSaves.InventoryData;

import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
import Main.References;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Inventar Einstellungen laden
 *
 * @author Sirat
 * @version 1.0
 * */
public class InventoryDataLoad
{

    /**
     * XMLRead          Lesen
     *
     * @param filename  Dateiname
     * */
    public static void XMLRead(String filename)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try
        {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) { ex.printStackTrace(); }

        Document document = null;

        try
        {
            assert builder != null;

            //URL url = InventoryDataLoad.class.getResource("/xml/playerSaves/" + filename + "Inventory.xml");
            //document = builder.parse(new File(url.getPath()));

            document = builder.parse(new File(filename + "Inventory.xml"));

        } catch (SAXException | IOException ex) { ex.printStackTrace(); }

        //Einstellungen
        assert document != null;
        NodeList rootList = document.getElementsByTagName("*");

        // Inventar Leiste Initialisieren
        InventoryData.invBar = new Cell[Inventory.inventoryLength];
        for (int i = 0; i < InventoryData.invBar.length; i++)
        {
            InventoryData.invBar[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH / 2) - ((Inventory.inventoryLength * (Inventory.inventoryCellSize + Inventory.inventoryCellSpacing)) / 2) + (i * (Inventory.inventoryCellSize + Inventory.inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (Inventory.inventoryCellSize + Inventory.inventoryBorderSpacing),
                    Inventory.inventoryCellSize,
                    Inventory.inventoryCellSize
            ));
        }

        // Inventar Drawer Initialisieren
        InventoryData.invDrawer = new Cell[Inventory.inventoryLength * Inventory.inventoryHeight];
        int x = 0, y = 0;
        for (int i = 0; i < InventoryData.invDrawer.length; i++)
        {
            InventoryData.invDrawer[i] = new Cell(new Rectangle(
                    (References.SCREEN_WIDTH / 2) - ((Inventory.inventoryLength * (Inventory.inventoryCellSize + Inventory.inventoryCellSpacing)) / 2) + (x * (Inventory.inventoryCellSize + Inventory.inventoryCellSpacing)),
                    References.SCREEN_HEIGHT - (Inventory.inventoryCellSize + Inventory.inventoryBorderSpacing) - (Inventory.inventoryHeight * (Inventory.inventoryCellSize + Inventory.inventoryCellSpacing)) + (y * (Inventory.inventoryCellSize + Inventory.inventoryCellSpacing)),
                    Inventory.inventoryCellSize,
                    Inventory.inventoryCellSize
            ));

            x++;
            if (x == Inventory.inventoryLength)
            {
                x = 0;
                y++;
            }
        }

        for(int i = 0; i < rootList.getLength()-1; i++)
        {
            Node cellNode = rootList.item(i);
            Element cellNodeAsElement = (Element)cellNode;

            initGameData(cellNodeAsElement);
        }

    }

    /**
     * initGameData                     Initialisieren der Einstellungsdaten
     *
     * @param node     Knoten
     * */
    private static void initGameData(Element node)
    {
        String nodeName = node.getNodeName();

        try
        {
            if(nodeName.equals("cell") && node.getParentNode().getNodeName().equals("bar"))
            {
                InventoryData.invBar[Integer.parseInt(node.getAttribute("index"))].setId(Byte.parseByte(node.getAttribute("id")));
                InventoryData.invBar[Integer.parseInt(node.getAttribute("index"))].setTileImage(References.getTextureById(InventoryData.invBar[Integer.parseInt(node.getAttribute("index"))].getId()));
                InventoryData.invBar[Integer.parseInt(node.getAttribute("index"))].setCount(Integer.parseInt(node.getAttribute("count")));
            }
            else if(nodeName.equals("cell") && node.getParentNode().getNodeName().equals("drawer"))
            {
                InventoryData.invDrawer[Integer.parseInt(node.getAttribute("index"))].setId(Byte.parseByte(node.getAttribute("id")));
                InventoryData.invDrawer[Integer.parseInt(node.getAttribute("index"))].setTileImage(References.getTextureById(InventoryData.invDrawer[Integer.parseInt(node.getAttribute("index"))].getId()));
                InventoryData.invDrawer[Integer.parseInt(node.getAttribute("index"))].setCount(Integer.parseInt(node.getAttribute("count")));
            }
        }
        catch (NullPointerException ex) { ex.printStackTrace(); }


    }

}

























