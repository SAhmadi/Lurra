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
            // Fackel
            if (craftBench[0].name.equals("Holz")
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
            // Axt
            else if ( craftBench[0].name.equals("Holz")
                    && craftBench[1].name.equals("Holz")
                    && craftBench[2].name.equals("Holz")
                    && craftBench[3].name.equals("Holz")
                    && craftBench[4].name.equals("Holz")
                    && craftBench[5].name.equals("Eisen")
                    && craftBench[6].name.equals("Eisen")
                    && craftBench[7].name.equals("Eisen")
                    && craftBench[8].name.equals("Eisen") )
            {
                if (holdingName.equals("Axt"))
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

                if (!holdingName.equals("Axt"))
                {
                    productBench[0].name = "Axt";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Schwert
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
                if (holdingName.equals("Schwert"))
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

                if (!holdingName.equals("Schwert")) {
                    productBench[0].name = "Schwert";
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
            // IronMan Ruestung
            else if ( craftBench[0].name.equals("Rubin")
                    && craftBench[1].name.equals("Rubin")
                    && craftBench[2].name.equals("Rubin")
                    && craftBench[3].name.equals("Rubin")
                    && craftBench[4].name.equals("Rubin")
                    && craftBench[5].name.equals("Gold")
                    && craftBench[6].name.equals("Gold")
                    && craftBench[7].name.equals("Gold")
                    && craftBench[8].name.equals("Gold") )
            {
                if (holdingName.equals("Ironmanruestung"))
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

                if (!holdingName.equals("Ironmanruestung"))
                {
                    productBench[0].name = "Ironmanruestung";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Captian America Ruestung
            else if ( craftBench[0].name.equals("Saphire")
                    && craftBench[1].name.equals("Saphire")
                    && craftBench[2].name.equals("Saphire")
                    && craftBench[3].name.equals("Saphire")
                    && craftBench[4].name.equals("Saphire")
                    && craftBench[5].name.equals("Silber")
                    && craftBench[6].name.equals("Silber")
                    && craftBench[7].name.equals("Silber")
                    && craftBench[8].name.equals("Silber") )
            {
                if (holdingName.equals("Captianruestung"))
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

                if (!holdingName.equals("Captianruestung"))
                {
                    productBench[0].name = "Captianruestung";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Burger
            else if(craftBench[0].name.equals("Erde")
                    && craftBench[1].name.equals("Eisen")
                    && craftBench[2].name.equals("null")
                    && craftBench[3].name.equals("null")
                    && craftBench[4].name.equals("null")
                    && craftBench[5].name.equals("null")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if(holdingName.equals("Burger"))
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

                if(!holdingName.equals("Burger"))
                {
                    productBench[0].name = "Burger";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // ZauberTrank
            else if(craftBench[0].name.equals("Erde")
                    && craftBench[1].name.equals("Eisen")
                    && craftBench[2].name.equals("Eis")
                    && craftBench[3].name.equals("null")
                    && craftBench[4].name.equals("null")
                    && craftBench[5].name.equals("null")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if(holdingName.equals("Rottrank"))
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

                if(!holdingName.equals("Rottrank"))
                {
                    productBench[0].name = "Rottrank";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // TNT
            else if ( craftBench[0].name.equals("Erde")
                    && craftBench[1].name.equals("Erde")
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
            // Bluerock
            else if(craftBench[0].name.equals("Kupfer")
                    && craftBench[1].name.equals("Kupfer")
                    && craftBench[2].name.equals("Kupfer")
                    && craftBench[3].name.equals("Kupfer")
                    && craftBench[4].name.equals("Kupfer")
                    && craftBench[5].name.equals("null")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if(holdingName.equals("BluerockOff"))
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

                if(!holdingName.equals("BluerockOff"))
                {
                    productBench[0].name = "BluerockOff";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Batterien
            else if(craftBench[0].name.equals("Diamant")
                    && craftBench[1].name.equals("Diamant")
                    && craftBench[2].name.equals("Diamant")
                    && craftBench[3].name.equals("Diamant")
                    && craftBench[4].name.equals("Diamant")
                    && craftBench[5].name.equals("Diamant")
                    && craftBench[6].name.equals("Diamant")
                    && craftBench[7].name.equals("Diamant")
                    && craftBench[8].name.equals("Diamant") )
            {
                if(holdingName.equals("Batterie"))
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

                if(!holdingName.equals("Batterie"))
                {
                    productBench[0].name = "Batterie";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // Switch
            else if(craftBench[0].name.equals("Kupfer")
                    && craftBench[1].name.equals("Kupfer")
                    && craftBench[2].name.equals("Kupfer")
                    && craftBench[3].name.equals("Kupfer")
                    && craftBench[4].name.equals("Kupfer")
                    && craftBench[5].name.equals("Kupfer")
                    && craftBench[6].name.equals("null")
                    && craftBench[7].name.equals("null")
                    && craftBench[8].name.equals("null") )
            {
                if(holdingName.equals("Switch"))
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

                if(!holdingName.equals("Switch"))
                {
                    productBench[0].name = "Switch";
                    productBench[0].setTileImage();
                    productBench[0].count = 1;
                }
            }
            // NAND
            else if(craftBench[0].name.equals("Kupfer")
                    && craftBench[1].name.equals("Kupfer")
                    && craftBench[2].name.equals("Kupfer")
                    && craftBench[3].name.equals("Kupfer")
                    && craftBench[4].name.equals("Kupfer")
                    && craftBench[5].name.equals("Kupfer")
                    && craftBench[6].name.equals("Kupfer")
                    && craftBench[7].name.equals("Kupfer")
                    && craftBench[8].name.equals("Kupfer") )
            {
                if(holdingName.equals("NAND"))
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

                if(!holdingName.equals("NAND"))
                {
                    productBench[0].name = "NAND";
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