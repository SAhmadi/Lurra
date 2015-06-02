package GameSaves.InventoryData;

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
//        try {
//            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            Document doc = docBuilder.newDocument();
//
//            Element rootElement = doc.createElement("inventory");
//            doc.appendChild(rootElement);
//
//            for(int i = 0; i < Inventory.inventoryCells.length; i++) {
//                if(Inventory.inventoryCells[i].name.equals("") || Inventory.inventoryCells[i].count == 0 || Inventory.inventoryCells[i].inUse == false)
//                    break;
//
//                Element cell = doc.createElement("cell");
//                cell.setAttribute("name", Inventory.inventoryCells[i].name);
//                cell.setAttribute("count", Integer.toString(Inventory.inventoryCells[i].count));
//                cell.setAttribute("inUse", Boolean.toString(Inventory.inventoryCells[i].inUse));
//
//                rootElement.appendChild(cell);
//            }
//
//            // Als XML schreiben
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("res/xml/playerSaves/" + filename + "Inventory.xml"));
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//            transformer.transform(source, result);
//        }
//        catch(ParserConfigurationException ex) {
//            ex.printStackTrace();
//        }
//        catch(TransformerException ex) {
//            ex.printStackTrace();
//        }
    }

}