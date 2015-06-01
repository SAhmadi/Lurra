package GameSaves.InventoryData;

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

import Assets.Inventory.Inventory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
* GameDataSave - Speichern der Spieldaten
* */
public class InventoryDataSave {

    /*
    * XMLSave - Speichern der Spieldaten
    *
    * @param argv   - Argumente
    * */
    public static void XMLSave(String filename) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("inventory");
            doc.appendChild(rootElement);

            for(int i = 0; i < Inventory.inventoryCells.length; i++) {
                if(Inventory.inventoryCells[i].name.equals("") || Inventory.inventoryCells[i].count == 0 || Inventory.inventoryCells[i].inUse == false)
                    break;

                Element cell = doc.createElement("cell");
                cell.setAttribute("name", Inventory.inventoryCells[i].name);
                cell.setAttribute("count", Integer.toString(Inventory.inventoryCells[i].count));
                cell.setAttribute("inUse", Boolean.toString(Inventory.inventoryCells[i].inUse));

                rootElement.appendChild(cell);
            }

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/xml/playerSaves/" + filename + "Inventory.xml"));
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