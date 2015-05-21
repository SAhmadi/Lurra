package Main;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
* LevelSave - Speichern der Spieldaten
* */
public class LevelSave {

    /*
    * XMLSave - Speichern der Spieldaten
    *
    * @param argv   - Argumente
    * */
    public static void XMLSave(String argv[]) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Save");
            doc.appendChild(rootElement);

            // Settings
            Element settings = doc.createElement("Settings");
            rootElement.appendChild(settings);

            // sound
            Element sound = doc.createElement("Sound");
            sound.appendChild(doc.createTextNode("On/Off"));
            settings.appendChild(sound);

            // Gender
            Element gender = doc.createElement("Gender");
            gender.appendChild(doc.createTextNode("Male/Female"));
            settings.appendChild(gender);

            // Gesammelte Gegenstände
            Element storage = doc.createElement("Storage");
            rootElement.appendChild(storage);

            // Schwert
            Element sword = doc.createElement("Sword");
            sword.appendChild(doc.createTextNode("5"));
            storage.appendChild(sword);

            // Fackeln
            Element torch = doc.createElement("Torch");
            torch.appendChild(doc.createTextNode("9"));
            storage.appendChild(torch);

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/TestSave.xml"));
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