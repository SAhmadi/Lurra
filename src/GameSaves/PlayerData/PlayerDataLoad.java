package GameSaves.PlayerData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Spielereinstellungen laden
 *
 * @author Sirat
 * @version 1.0
 * */
public class PlayerDataLoad
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
        catch (ParserConfigurationException ex) { ex.printStackTrace(); }

        Document document = null;

        try
        {
            assert builder != null;

            //URL url = PlayerDataLoad.class.getResource("/xml/playerSaves/" + filename + ".xml");
            //document = builder.parse(new File(url.getPath()));

            document = builder.parse(new File(filename + "Save.xml"));
        }
        catch (SAXException | IOException ex) { ex.printStackTrace(); }

        //Einstellungen
        assert document != null;
        NodeList settingsList = document.getElementsByTagName("*");

        for(int i = 0; i < settingsList.getLength(); i++)
        {
            Node settingsNode = settingsList.item(i);
            Element settingsNodeAsElement = (Element)settingsNode;

            initGameData(settingsNodeAsElement);
        }
    }

    /**
     * initGameData                     Initialisieren der Einstellungsdaten
     *
     * @param settingsNodeAsElement     Knoten
     * */
    private static void initGameData(Element settingsNodeAsElement)
    {
        String nodeName = settingsNodeAsElement.getNodeName();

        if(nodeName.equals("name"))
            PlayerData.name = settingsNodeAsElement.getTextContent();
        else if(nodeName.equals("gender"))
            PlayerData.gender = settingsNodeAsElement.getTextContent();
        else if(nodeName.equals("currentLevel"))
            PlayerData.currentLevel = settingsNodeAsElement.getTextContent();
        else if(nodeName.equals("seed"))
            PlayerData.seed = settingsNodeAsElement.getTextContent();
    }

}

























