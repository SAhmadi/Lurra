package GameSaves.InventoryData;

import Assets.Inventory.Cell;
import Assets.Inventory.Inventory;
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
import java.io.File;

/**
 * Inventar Einstellungen speichern
 *
 * @author Sirat
 * @version 1.0
 * */
public class InventoryDataSave
{

    /**
     * XMLSave          Speichern
     *
     * @param filename  Dateiname
     * */
    public static void XMLSave(String filename)
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("inventory");
            doc.appendChild(rootElement);

            // InvBar
            Element invBarElement = doc.createElement("bar");
            rootElement.appendChild(invBarElement);

            int index = 0;
            for (Cell cell : Inventory.invBar)
            {
                Element cellElement = doc.createElement("cell");
                cellElement.setAttribute("index", Integer.toString(index));
                cellElement.setAttribute("id", Byte.toString(cell.getId()));
                cellElement.setAttribute("count", Integer.toString(cell.getCount()));

                invBarElement.appendChild(cellElement);
                index++;
            }

            // InvDrawer
            Element invDrawerElement = doc.createElement("drawer");
            rootElement.appendChild(invDrawerElement);

            index = 0;
            for (Cell cell : Inventory.invDrawer)
            {
                Element cellElement = doc.createElement("cell");
                cellElement.setAttribute("index", Integer.toString(index));
                cellElement.setAttribute("id", Byte.toString(cell.getId()));
                cellElement.setAttribute("count", Integer.toString(cell.getCount()));

                invDrawerElement.appendChild(cellElement);
                index++;
            }

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename + "Inventory.xml"));

            //StreamResult result = new StreamResult(new File("/res/xml/playerSaves/" + filename + "Inventory.xml"));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);

        }
        catch(ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

}