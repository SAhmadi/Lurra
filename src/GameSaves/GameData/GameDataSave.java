package GameSaves.GameData;

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
 * Generelle Spieleinstellungen speichern
 *
 * @author Sirat
 * @version 1.0
 * */
public class GameDataSave
{

    /**
     * XMLSave      Speichern
     * */
    public static void XMLSave()
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("save");
            doc.appendChild(rootElement);

            // Settings
            Element settings = doc.createElement("settings");
            rootElement.appendChild(settings);

            // sound
            Element sound = doc.createElement("sound");
            if (GameData.isSoundOn == null) sound.appendChild(doc.createTextNode("Off"));
            else sound.appendChild(doc.createTextNode(GameData.isSoundOn));
            settings.appendChild(sound);

            // Gender
            Element gender = doc.createElement("gender");
            if (GameData.gender == null) gender.appendChild(doc.createTextNode("Male"));
            else gender.appendChild(doc.createTextNode(GameData.gender));
            settings.appendChild(gender);

            // Als XML schreiben
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            //URL url = GameDataSave.class.getResource("/xml/gameSaves.xml");

            //File gameSaves = new File("gameSaves.xml");
            //if (!gameSaves.exists()) gameSaves.createNewFile();

            StreamResult result = new StreamResult(new File("gameSaves.xml"));

            //StreamResult result = new StreamResult(new File(GameDataSave.class.getResource("/src/GameSaves/xml/gameSaves.xml").toURI()));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        }
        catch(ParserConfigurationException | TransformerException | NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }

}