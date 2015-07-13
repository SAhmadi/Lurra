package GameSaves.TilemapData;

import Assets.World.TileMap;
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
 * Zufallswelt laden
 *
 * @author Sirat
 * @version 1.0
 * */
public class TilemapDataLoad
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
        }
        catch (ParserConfigurationException ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }

        Document document = null;

        try
        {
            assert builder != null;
            document = builder.parse(new File("res/xml/playerLevelSaves/" + filename + ".xml"));
        }
        catch (SAXException | IOException ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }

        // Tilemap Einstellungen
        assert document != null;
        NodeList settingsList = document.getElementsByTagName("*");

        for(int i = 0; i < settingsList.getLength(); i++)
        {
            Node node = settingsList.item(i);
            Element nodeAsElement = (Element)node;
            initGameData(nodeAsElement);
        }
    }

    /**
     * initGameData         Initialisieren der Einstellungsdaten
     *
     * @param node          Knoten
     * */
    private static void initGameData(Element node)
    {
        String nodeName = node.getNodeName();

        if(nodeName.equals("seed"))
            TilemapData.seed = Integer.parseInt(node.getTextContent());
        else if (nodeName.equals("noTile"))
        {
            try
            {
                TileMap.minedTiles.put(new Point(Integer.parseInt(node.getAttribute("row")), Integer.parseInt(node.getAttribute("col"))), Byte.parseByte(node.getAttribute("id")));
            } catch (NullPointerException ignored) {}
        }
    }

}


