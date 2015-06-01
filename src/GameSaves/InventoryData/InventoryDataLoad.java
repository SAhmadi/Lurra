package GameSaves.InventoryData;

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
public class InventoryDataLoad {

    /*
    * XMLRead - Einlesen der Spieldaten
    *
    * @param args   - Argumente
    * */
    public static void XMLRead(String filename) {

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
            if (builder != null) {
                document = builder.parse(new File("res/xml/playerSaves/" + filename + "Inventory.xml"));
            }
        }
        catch (SAXException | IOException ex) {
            ex.printStackTrace();
        }

        //Einstellungen
        assert document != null;
        NodeList rootList = document.getElementsByTagName("*");

        InventoryData.counts = new String[rootList.getLength()-1];
        InventoryData.names = new String[rootList.getLength()-1];
        InventoryData.inUse = new String[rootList.getLength()-1];

        for(int i = 0; i < rootList.getLength()-1; i++){
            Node cellNode = rootList.item(i);
            System.out.println(cellNode.getNodeName());
            Element cellNodeAsElement = (Element)cellNode;

            InventoryData.counts[i] = cellNodeAsElement.getAttribute("count");
            InventoryData.names[i] = cellNodeAsElement.getAttribute("name");
            InventoryData.inUse[i] = cellNodeAsElement.getAttribute("inUse");

        }
    }

}

























