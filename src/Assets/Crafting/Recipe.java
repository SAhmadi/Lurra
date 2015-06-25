package Assets.Crafting;


import Assets.Inventory.Cell;

public class Recipe {

    public static void checkRecipes(Cell[] craftBench, Cell[] productBench, String holdingName) {
        if(productBench[0].name.equals("null")) {

            // Sand herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Wasser") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Sand")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Sand")) {   // Die klappen noch nicht ganz, kleiner Fehler
                    productBench[0].name = "Sand";
                    productBench[0].setTileImage();
                    System.out.println("Sand hergestellt");
                }
            }

            // Eisen herstellen
            if(craftBench[0].name.equals("Stein") && craftBench[1].name.equals("Lava") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisen")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisen")) {
                    productBench[0].name = "Eisen";
                    productBench[0].setTileImage();
                    System.out.println("Eisen hergestellt");
                }
            }

            /*
            // Diamant herstellen
            if(craftBench[0].name.equals("") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Diamant")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Diamant")) {
                    productBench[0].name = "Diamant";
                    productBench[0].setTileImage();
                    System.out.println("Diamant hergestellt");
                }
            }

            // Smaragd herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Smaragd")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Smaragd")) {
                    productBench[0].name = "Smaragd";
                    productBench[0].setTileImage();
                    System.out.println("Smaragd hergestellt");
                }
            }

            // Rubin herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Rubin")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Rubin")) {
                    productBench[0].name = "Rubin";
                    productBench[0].setTileImage();
                    System.out.println("Rubin hergestellt");
                }
            }

            // Saphir herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Saphir")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Saphir")) {
                    productBench[0].name = "Saphir";
                    productBench[0].setTileImage();
                    System.out.println("Saphir hergestellt");
                }
            }
            */

            // Gold herstellen
            if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Gras") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Gold")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Gold")) {
                    productBench[0].name = "Gold";
                    productBench[0].setTileImage();
                    System.out.println("Gold hergestellt");
                }
            }

            //Burger herstellen
            else if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Silber") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Burger")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Burger")) {
                    productBench[0].name = "Burger";
                    productBench[0].setTileImage();
                    System.out.println("Burger hergestellt");
                }
            }

            //ZauberTrank
            else if(craftBench[0].name.equals("Erde") && craftBench[1].name.equals("Silber") && craftBench[2].name.equals("Rubin") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Zaubertrank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Zaubertrank")) {
                    productBench[0].name = "Zaubertrank";
                    productBench[0].setTileImage();
                    System.out.println("Zaubertrank hergestellt");
                }
            }

            // Silber herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Lava") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Silber")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Silber")) {
                    productBench[0].name = "Silber";
                    productBench[0].setTileImage();
                    System.out.println("Silber hergestellt");
                }
            }

            // Kupfer herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Blatt") && craftBench[2].name.equals("Lava") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Kupfer")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Kupfer")) {
                    productBench[0].name = "Kupfer";
                    productBench[0].setTileImage();
                    System.out.println("Kupfer hergestellt");
                }
            }

            // Eis herstellen
            else if(craftBench[0].name.equals("Wasser") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eis")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eis")) {
                    productBench[0].name = "Eis";
                    productBench[0].setTileImage();
                    System.out.println("Eis hergestellt");
                }
            }

            // Axt herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Eisen") && craftBench[3].name.equals("Eisen") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Axt")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Axt")) {
                    productBench[0].name = "Axt";
                    productBench[0].setTileImage();
                    System.out.println("Axt hergestellt");
                }
            }

            // Pfeil herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Pfeil")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Pfeil")) {
                    productBench[0].name = "Pfeil";
                    productBench[0].setTileImage();
                    System.out.println("Pfeil hergestellt");
                }
            }

            // Bogen herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Stein") && craftBench[3].name.equals("Stein") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Bogen")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Bogen")) {
                    productBench[0].name = "Bogen";
                    productBench[0].setTileImage();
                    System.out.println("Bogen hergestellt");
                }
            }

            // Werkbank herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Holz") && craftBench[3].name.equals("Holz") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Werkbank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Werkbank")) {
                    productBench[0].name = "Werkbank";
                    productBench[0].setTileImage();
                    System.out.println("Werkbank hergestellt");
                }
            }

            // Fackel herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Blatt") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Fackel")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Fackel")) {
                    productBench[0].name = "Fackel";
                    productBench[0].setTileImage();
                    System.out.println("Fakel hergestellt");
                }
            }

            // Steinhacke herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("Stein") && craftBench[3].name.equals("Stein") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Steinhacke")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Steinhacke")) {
                    productBench[0].name = "Steinhacke";
                    productBench[0].setTileImage();
                    System.out.println("Steinhacke hergestellt");
                }
            }

            // Holzschwert herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Holz") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Holzschwert")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Holzschwert")) {
                    productBench[0].name = "Holzschwert";
                    productBench[0].setTileImage();
                    System.out.println("Holzschwert hergestellt");
                }
            }

            // Edelschwert herstellen
            else if(craftBench[0].name.equals("Kupfer") && craftBench[1].name.equals("Silber") && craftBench[2].name.equals("Gold") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Edelschwert")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Edelschwert")) {
                    productBench[0].name = "Edelschwert";
                    productBench[0].setTileImage();
                    System.out.println("Edelschwert hergestellt");
                }
            }

            // Eisenschwert herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisenschwert")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisenschwert")) {
                    productBench[0].name = "Eisenschwert";
                    productBench[0].setTileImage();
                    System.out.println("Eisenschwert hergestellt");
                }
            }

            // Glas herstellen
            else if(craftBench[0].name.equals("Sand") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Eisen") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Glas")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Glas")) {
                    productBench[0].name = "Glas";
                    productBench[0].setTileImage();
                    System.out.println("Glas hergestellt");
                }
            }

            // Steinofen herstellen
            else if(craftBench[0].name.equals("Stein") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Holz") && craftBench[3].name.equals("Holz") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Steinofen")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Steinofen")) {
                    productBench[0].name = "Steinofen";
                    productBench[0].setTileImage();
                    System.out.println("Steinofen hergestellt");
                }
            }

            // Steinbank herstellen
            else if(craftBench[0].name.equals("Stein") && craftBench[1].name.equals("Stein") && craftBench[2].name.equals("Stein") && craftBench[3].name.equals("Stein") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Steinbank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Steinbank")) {
                    productBench[0].name = "Steinbank";
                    productBench[0].setTileImage();
                    System.out.println("Steinbank hergestellt");
                }
            }

            // Holzruestung herstellen
            else if(craftBench[0].name.equals("Holz") && craftBench[1].name.equals("Blatt") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Holzruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Holzruestung")) {
                    productBench[0].name = "Holzruestung";
                    productBench[0].setTileImage();
                    System.out.println("Holzruestung hergestellt");
                }
            }

            // Eisenruestung herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Kupfer") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisenruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisenruestung")) {
                    productBench[0].name = "Eisenruestung";
                    productBench[0].setTileImage();
                    System.out.println("Eisenruestung hergestellt");
                }
            }

            // Edelruestung herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Kupfer") && craftBench[2].name.equals("Silber") && craftBench[3].name.equals("Gold") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Edelruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Edelruestung")) {
                    productBench[0].name = "Edelruestung";
                    productBench[0].setTileImage();
                    System.out.println("Edelruestung hergestellt");
                }
            }

            // Silberruestung herstellen
            else if(craftBench[0].name.equals("Silber") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("Kupfer") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Silberruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Silberruestung")) {
                    productBench[0].name = "Silberruestung";
                    productBench[0].setTileImage();
                    System.out.println("Silberruestung hergestellt");
                }
            }

            // Goldruestung herstellen
            else if(craftBench[0].name.equals("Gold") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("Kupfer") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Goldruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Goldruestung")) {
                    productBench[0].name = "Goldruestung";
                    productBench[0].setTileImage();
                    System.out.println("Goldruestung hergestellt");
                }
            }

            // Blauen Genesungstrank herstellen
            else if(craftBench[0].name.equals("Saphir") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Blauer Genesungstrank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Blauer Genesungstrank")) {
                    productBench[0].name = "Blauer Genesungstrank";
                    productBench[0].setTileImage();
                    System.out.println("Blauen Genesungstrank hergestellt");
                }
            }

            // Roten Genesungstrank herstellen
            else if(craftBench[0].name.equals("Rubin") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Roter Genesungstrank")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Roter Genesungstrank")) {
                    productBench[0].name = "Roter Genesungstrank";
                    productBench[0].setTileImage();
                    System.out.println("Roten Genesungstrank hergestellt");
                }
            }

            // Gift herstellen
            else if(craftBench[0].name.equals("Smaragd") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("Glas") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Gift")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Gift")) {
                    productBench[0].name = "Gift";
                    productBench[0].setTileImage();
                    System.out.println("Gift hergestellt");
                }
            }

            // Schwarzpulver herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Eis") && craftBench[2].name.equals("Sand") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Schwarzpulver")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Schwarzpulver")) {
                    productBench[0].name = "Schwarzpulver";
                    productBench[0].setTileImage();
                    System.out.println("Schwarzpulver hergestellt");
                }
            }

            // TNT herstellen
            else if(craftBench[0].name.equals("Schwarzpulver") && craftBench[1].name.equals("Schwarzpulver") && craftBench[2].name.equals("Eisen") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("TNT")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("TNT")) {
                    productBench[0].name = "TNT";
                    productBench[0].setTileImage();
                    System.out.println("TNT hergestellt");
                }
            }

            // Granate herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Schwarzpulver") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Granate")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Granate")) {
                    productBench[0].name = "Granate";
                    productBench[0].setTileImage();
                    System.out.println("Granate hergestellt");
                }
            }

            // Pistole herstellen
            else if(craftBench[0].name.equals("Eisen") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("Kupfer") && craftBench[3].name.equals("Kupfer") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Pistole")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Pistole")) {
                    productBench[0].name = "Pistole";
                    productBench[0].setTileImage();
                    System.out.println("Pistole hergestellt");
                }
            }

            // Eisenkugel herstellen
            else if(craftBench[0].name.equals("Schwarzpulver") && craftBench[1].name.equals("Eisen") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Eisenkugel")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Eisenkugel")) {
                    productBench[0].name = "Eisenkugel";
                    productBench[0].setTileImage();
                    System.out.println("Eisenkugel hergestellt");
                }
            }

            // Kupferkugel herstellen
            else if(craftBench[0].name.equals("Schwarzpulver") && craftBench[1].name.equals("Kupfer") && craftBench[2].name.equals("null") && craftBench[3].name.equals("null") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Kupferkugel")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Kupferkugel")) {
                    productBench[0].name = "Kupferkugel";
                    productBench[0].setTileImage();
                    System.out.println("Kupferkugel hergestellt");
                }
            }

            // Diamantruestung herstellen
            else if(craftBench[0].name.equals("Diamant") && craftBench[1].name.equals("Rubin") && craftBench[2].name.equals("Saphir") && craftBench[3].name.equals("Smaragd") && craftBench[4].name.equals("null") && craftBench[5].name.equals("null") && craftBench[6].name.equals("null") && craftBench[7].name.equals("null") && craftBench[8].name.equals("null") ) {
                if(holdingName.equals("Diamantruestung")) {
                    craftBench[0].name = "null";
                    craftBench[0].setTileImage();
                    craftBench[1].name = "null";
                    craftBench[1].setTileImage();
                    craftBench[2].name = "null";
                    craftBench[2].setTileImage();
                    craftBench[3].name = "null";
                    craftBench[3].setTileImage();
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                }
                if(!holdingName.equals("Diamantruestung")) {
                    productBench[0].name = "Diamantruestung";
                    productBench[0].setTileImage();
                    System.out.println("Diamantruestung hergestellt");
                }
            }

            else {
                productBench[0].name = "null";
                productBench[0].setTileImage();
            }
        }
    }

}