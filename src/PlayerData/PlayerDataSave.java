package PlayerData;

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

import PlayerData.PlayerData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
* GameDataSave - Speichern der Spieldaten
* */
public class PlayerDataSave {

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

            Element rootElement = doc.createElement("player");
            doc.appendChild(rootElement);

            /*
            * SETTINGS NODE
            * */
            // Name
            Element nameElement = doc.createElement("name");
            rootElement.appendChild(nameElement);
            nameElement.appendChild(doc.createTextNode(PlayerData.name));

            // Character Auswahl
            Element genderElement = doc.createElement("gender");
            rootElement.appendChild(genderElement);
            genderElement.appendChild(doc.createTextNode(PlayerData.gender));

            // Aktuelles Level
            Element currentLevelElement = doc.createElement("currentLevel");
            rootElement.appendChild(currentLevelElement);
            currentLevelElement.appendChild(doc.createTextNode(PlayerData.currentLevel));

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/xml/playerSaves/" + filename + ".xml"));
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