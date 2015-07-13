package GameSaves.GameData;

import Main.References;
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
 * Generelle Spieleinstellungen laden
 *
 * @author Sirat
 * @version 1.0
 * */
public class GameDataLoad
{

    /**
     * XMLRead      Lesen der Einstellungen
     * */
    public static void XMLRead()
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
            document = builder.parse(new File("res/xml/gameSaves.xml"));
        }
        catch (SAXException | IOException ex) { if (References.SHOW_EXCEPTION) System.out.println("Error: " + ex.getMessage()); }

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

        if(nodeName.equals("sound"))
            GameData.isSoundOn = settingsNodeAsElement.getTextContent();
        else if(nodeName.equals("gender"))
            GameData.gender = settingsNodeAsElement.getTextContent();
    }

}