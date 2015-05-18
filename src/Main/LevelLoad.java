package Main;

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
* LevelLoad - Laden der Spieldaten
* */
public class LevelLoad {

    /*
    * XMLRead - Einlesen der Spieldaten
    *
    * @param args   - Argumente
    * */
    public static void XMLRead(String [] args) throws SAXException, IOException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse( new File("teama2\\src\\State\\TestSave.xml") );

        //Einstellungen
        NodeList setList = document.getElementsByTagName("settings");

        for(int setPos = 0; setPos < setList.getLength(); setPos++){
            Node settings = setList.item(setPos);
            Element set = (Element) settings;

            //Gesammelte Gegenstände
            NodeList storList = document.getElementsByTagName("storage");

            for(int storPos = 0; storPos < storList.getLength(); storPos++){
                Node storage = storList.item(storPos);
                Element stor = (Element) storage;

            }
        }
    }

}