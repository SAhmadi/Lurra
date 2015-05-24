package Assets;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
* GameDataLoad - Laden der Spieldaten
* */
public class TileMapDataLoad {

    /*
    * XMLRead - Einlesen der Spieldaten
    *
    * @param args   - Argumente
    * */
    public static void XMLRead(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }

        Document document = null;

        try {
            document = builder.parse(new File("res/xml/gameSaves.xml"));
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        //Einstellungen
        NodeList settingsList = document.getElementsByTagName("*");
        for(int i = 0; i < settingsList.getLength(); i++){
            Node settingsNode = settingsList.item(i);
            Element settingsNodeAsElement = (Element)settingsNode;

            initGameData(settingsNodeAsElement);

        }
    }

    /*
    * initGameData
    * */
    private static void initGameData(Element settingsNodeAsElement) {
        String nodeName = settingsNodeAsElement.getNodeName();

//        if(nodeName == "sound") {
//            GameData.isSoundOn = settingsNodeAsElement.getTextContent();
//        }
//        else if(nodeName == "gender") {
//            GameData.gender = settingsNodeAsElement.getTextContent();
//        }
    }

}