package Assets.Crafting;

import Assets.Inventory.Cell;

/**
 * Rezepte die gemixt werden koennnen
 *
 * @author Sirat, Carola
 * */
public class Recipe
{

    public static void checkRecipes(Cell[] craftBench, Cell[] productBench, String holdingName)
    {
        if (productBench[0].name.equals("null"))
        {
            // Sand
            if ( craftBench[0].name.equals("Erde")
                    && craftBench[1].name.equals("Wasser")
                    && craftBench[2].name.equals("null")
                    && craftBench[3].name.equals("null")
                    && craftBench[4].name.equals("null")
                    && craftBench[5].name.equals("null")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if (holdingName.equals("Sand"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Sand"))
                {
                    productBench[0].name = "Sand";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Pfeil
            else if ( craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Holz")
                    && craftBench[4].name.equals("Stein")
                    && craftBench[5].name.equals("Stein")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if (holdingName.equals("Pfeil"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Pfeil"))
                {
                    productBench[0].name = "Pfeil";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Bogen
            else if ( craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Holz")
                    && craftBench[4].name.equals("Holz")
                    && craftBench[5].name.equals("Holz")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if (holdingName.equals("Bogen"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Bogen")) {
                    productBench[0].name = "Bogen";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Werkbank
            else if ( craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Holz")
                    && craftBench[4].name.equals("Holz")
                    && craftBench[5].name.equals("Holz")
                    && craftBench[6].name.equals("Holz")
                    && craftBench[7].name.equals("Holz")
                    && craftBench[8].name.equals("Holz") )
            {
                if (holdingName.equals("Werkbank"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Werkbank"))
                {
                    productBench[0].name = "Werkbank";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Fackel
            else if (craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Stein")
                    && craftBench[4].name.equals("Gras")
                    && craftBench[5].name.equals("Gras")
                    && craftBench[6].name.equals("Gras")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if (holdingName.equals("Fackel"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Fackel"))
                {
                    productBench[0].name = "Fackel";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Steinhacke
            else if ( craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Holz")
                    && craftBench[4].name.equals("Holz")
                    && craftBench[5].name.equals("Stein")
                    && craftBench[6].name.equals("Stein")
                    && craftBench[7].name.equals("Stein")
                    && craftBench[8].name.equals("Stein") )
            {
                if (holdingName.equals("Steinhacke"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Steinhacke"))
                {
                    productBench[0].name = "Steinhacke";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Holzschwert
            else if ( craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Holz")
                    && craftBench[4].name.equals("Holz")
                    && craftBench[5].name.equals("Holz")
                    && craftBench[6].name.equals("Stein")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if (holdingName.equals("Holzschwert"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Holzschwert"))
                {
                    productBench[0].name = "Holzschwert";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Eisenschwert
            else if ( craftBench[0].name.equals("Eisen")
                    && craftBench[1].name.equals("Eisen")
                    && craftBench[2].name.equals("Eisen")
                    && craftBench[3].name.equals("Eisen")
                    && craftBench[4].name.equals("Eisen")
                    && craftBench[5].name.equals("Stein")
                    && craftBench[6].name.equals("Stein")
                    && craftBench[7].name.equals("Stein")
                    && craftBench[8].name.equals("Stein") )
            {
                if (holdingName.equals("Eisenschwert"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Eisenschwert")) {
                    productBench[0].name = "Eisenschwert";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Steinofen
            else if ( craftBench[0].name.equals("Stein")
                    && craftBench[1].name.equals("Stein")
                    && craftBench[2].name.equals("Stein")
                    && craftBench[3].name.equals("Stein")
                    && craftBench[4].name.equals("Stein")
                    && craftBench[5].name.equals("Stein")
                    && craftBench[6].name.equals("Stein")
                    && craftBench[7].name.equals("Stein")
                    && craftBench[8].name.equals("Fackel") )
            {
                if (holdingName.equals("Steinofen"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Steinofen")) {
                    productBench[0].name = "Steinofen";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Steinbank
            else if ( craftBench[0].name.equals("Stein")
                    && craftBench[1].name.equals("Stein")
                    && craftBench[2].name.equals("Stein")
                    && craftBench[3].name.equals("Stein")
                    && craftBench[4].name.equals("Stein")
                    && craftBench[5].name.equals("Stein")
                    && craftBench[6].name.equals("Stein")
                    && craftBench[7].name.equals("Stein")
                    && craftBench[8].name.equals("Stein") )
            {
                if (holdingName.equals("Steinbank"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Steinbank"))
                {
                    productBench[0].name = "Steinbank";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Eisenruestung
            else if ( craftBench[0].name.equals("Eisen")
                    && craftBench[1].name.equals("Eisen")
                    && craftBench[2].name.equals("Eisen")
                    && craftBench[3].name.equals("Eisen")
                    && craftBench[4].name.equals("Eisen")
                    && craftBench[5].name.equals("Kupfer")
                    && craftBench[6].name.equals("Kupfer")
                    && craftBench[7].name.equals("Kupfer")
                    && craftBench[8].name.equals("Kupfer") )
            {
                if (holdingName.equals("Eisenruestung"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Eisenruestung"))
                {
                    productBench[0].name = "Eisenruestung";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Edelruestung
            else if ( craftBench[0].name.equals("Eisen")
                    && craftBench[1].name.equals("Kupfer")
                    && craftBench[2].name.equals("Silber")
                    && craftBench[3].name.equals("Gold")
                    && craftBench[4].name.equals("Rubin")
                    && craftBench[5].name.equals("Saphire")
                    && craftBench[6].name.equals("Smaragd")
                    && craftBench[7].name.equals("Diamand")
                    && craftBench[8].name.equals("Diamand") )
            {
                if (holdingName.equals("Edelruestung"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Edelruestung"))
                {
                    productBench[0].name = "Edelruestung";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Diamantruestung
            else if ( craftBench[0].name.equals("Diamant")
                    && craftBench[1].name.equals("Diamant")
                    && craftBench[2].name.equals("Diamant")
                    && craftBench[3].name.equals("Diamant")
                    && craftBench[4].name.equals("Diamant")
                    && craftBench[5].name.equals("Diamant")
                    && craftBench[6].name.equals("Diamant")
                    && craftBench[7].name.equals("Diamant")
                    && craftBench[8].name.equals("Diamant") )
            {
                if (holdingName.equals("Diamantruestung"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Diamantruestung"))
                {
                    productBench[0].name = "Diamantruestung";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Blautrank
            else if ( craftBench[0].name.equals("Saphir")
                    && craftBench[1].name.equals("Saphir")
                    && craftBench[2].name.equals("Saphir")
                    && craftBench[3].name.equals("Eis")
                    && craftBench[4].name.equals("Eis")
                    && craftBench[5].name.equals("Eis")
                    && craftBench[6].name.equals("Eis")
                    && craftBench[7].name.equals("Eis")
                    && craftBench[8].name.equals("Eis") )
            {
                if (holdingName.equals("Blautrank"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Blautrank"))
                {
                    productBench[0].name = "Blautrank";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Rottrank
            else if ( craftBench[0].name.equals("Rubin")
                    && craftBench[1].name.equals("Rubin")
                    && craftBench[2].name.equals("Rubin")
                    && craftBench[3].name.equals("Eis")
                    && craftBench[4].name.equals("Eis")
                    && craftBench[5].name.equals("Eis")
                    && craftBench[6].name.equals("Eis")
                    && craftBench[7].name.equals("Eis")
                    && craftBench[8].name.equals("Eis") )
            {
                if (holdingName.equals("Rottrank"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Rottrank"))
                {
                    productBench[0].name = "Rottrank";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Schwarzpulver
            else if ( craftBench[0].name.equals("Eisen")
                    && craftBench[1].name.equals("Eisen")
                    && craftBench[2].name.equals("Eisen")
                    && craftBench[3].name.equals("Sand")
                    && craftBench[4].name.equals("Sand")
                    && craftBench[5].name.equals("Sand")
                    && craftBench[6].name.equals("Eis")
                    && craftBench[7].name.equals("Eis")
                    && craftBench[8].name.equals("Eis") )
            {
                if (holdingName.equals("Schwarzpulver"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Schwarzpulver"))
                {
                    productBench[0].name = "Schwarzpulver";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // TNT
            else if ( craftBench[0].name.equals("Schwarzpulver")
                    && craftBench[1].name.equals("Schwarzpulver")
                    && craftBench[2].name.equals("Eisen")
                    && craftBench[3].name.equals("Eisen")
                    && craftBench[4].name.equals("Eisen")
                    && craftBench[5].name.equals("Eisen")
                    && craftBench[6].name.equals("Eisen")
                    && craftBench[7].name.equals("Eisen")
                    && craftBench[8].name.equals("Eisen") )
            {

                if (holdingName.equals("TNT"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("TNT"))
                {
                    productBench[0].name = "TNT";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Granate
            else if ( craftBench[0].name.equals("Schwarzpulver")
                    && craftBench[1].name.equals("Schwarzpulver")
                    && craftBench[2].name.equals("Eisen")
                    && craftBench[3].name.equals("Eisen")
                    && craftBench[4].name.equals("Eisen")
                    && craftBench[5].name.equals("Kupfer")
                    && craftBench[6].name.equals("Kupfer")
                    && craftBench[7].name.equals("Kupfer")
                    && craftBench[8].name.equals("null") )
            {
                if (holdingName.equals("Granate"))
                {
                    for (Cell cell : craftBench)
                    {
                        cell.name = "null";
                        cell.setTileImage();
                        cell.count = 0;
                    }
                    productBench[0].name = "null";
                    productBench[0].setTileImage();
                    productBench[0].count = 0;
                }

                if (!holdingName.equals("Granate"))
                {
                    productBench[0].name = "Granate";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            else
            {
                productBench[0].name = "null";
                productBench[0].setTileImage();
                productBench[0].count = 0;
            }
        }
    }

}