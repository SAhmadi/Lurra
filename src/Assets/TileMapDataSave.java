package Assets;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
* GameDataSave - Speichern der Spieldaten
* */
public class TileMapDataSave {

    /*
    * XMLSave - Speichern der Spieldaten
    *
    * @param argv   - Argumente
    * */
    public static void XMLSave(String filename, int maxColumnSize, int maxRowSize, int tileSize) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("map");
            doc.appendChild(rootElement);


            for(int row = -maxColumnSize/2; row < maxColumnSize/2; row++) {

                for(int column = -maxRowSize/2; column < maxRowSize/2; column++) {
                    Element tileElement = doc.createElement("tile");

                    // x Koordinate
                    tileElement.setAttribute("x", Integer.toString(row*tileSize));
                    // y Koordinate
                    tileElement.setAttribute("y", Integer.toString(column*tileSize));
                    // Zeile
                    tileElement.setAttribute("row", Integer.toString(row));
                    // Spalte
                    tileElement.setAttribute("column", Integer.toString(column));
                    // ID
                    tileElement.setAttribute("id", Integer.toString(column*tileSize));

                    rootElement.appendChild(tileElement);
                }


            }



            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/xml/" + filename));
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

}