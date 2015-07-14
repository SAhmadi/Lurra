package GameSaves.TilemapData;

import Assets.World.TileMap;
import GameSaves.GameData.GameData;
import Main.Sound;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import java.io.File;
import java.util.Map;

/**
 * Zufallswelt speichern
 *
 * @author Sirat
 * @version 1.0
 * */
public class TilemapDataSave
{

    /**
     * XMLSave          Speichern
     *
     * @param filename  Dateiname
     * */
    public static void XMLSave(String filename)
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("tilemap");
            doc.appendChild(rootElement);

            // Zufallswelt Seed
            Element seedElement = doc.createElement("seed");
            rootElement.appendChild(seedElement);
            seedElement.appendChild(doc.createTextNode(Integer.toString(TilemapData.seed)));

            // Alle abgebauten Tiles speichern
            if (TileMap.minedTiles.size() > 0)
            {
                for (Object o : TileMap.minedTiles.entrySet())
                {
                    Map.Entry pair = (Map.Entry) o;

                    Point point = (Point) pair.getKey();
                    byte id = (byte) pair.getValue();

                    Element noTile = doc.createElement("noTile");
                    noTile.setAttribute("col", Integer.toString((int) point.getY()));
                    noTile.setAttribute("id", Byte.toString(id));
                    noTile.setAttribute("row", Integer.toString((int) point.getX()));  // Umgekehrte Anwendung von row = X und col = Y

                    rootElement.appendChild(noTile);
                }
            }

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename + "Level.xml"));

            //StreamResult result = new StreamResult(new File("teama2/res/xml/playerLevelSaves/" + filename + ".xml"));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);

            // Spiele Sound als Bestaetigung
            if (GameData.isSoundOn.equals("On"))
                Sound.metalSound.play();


        }
        catch(ParserConfigurationException | TransformerException ex) { ex.printStackTrace(); }

    }

}