package GameData;

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
public class GameDataSave {

    /*
    * XMLSave - Speichern der Spieldaten
    *
    * @param argv   - Argumente
    * */
    public static void XMLSave() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("save");
            doc.appendChild(rootElement);

            /*
            * SETTINGS NODE
            * */
            // Settings
            Element settings = doc.createElement("settings");
            rootElement.appendChild(settings);

            // sound
            Element sound = doc.createElement("sound");
            sound.appendChild(doc.createTextNode(GameData.isSoundOn));
            settings.appendChild(sound);

            // Gender
            Element gender = doc.createElement("gender");
            gender.appendChild(doc.createTextNode(GameData.gender));
            settings.appendChild(gender);

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/xml/gameSaves.xml"));
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