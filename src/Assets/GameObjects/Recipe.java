package Assets.GameObjects;

import Assets.Tile;

import java.util.Arrays;

/**
 * Recipe -     Crafting Rezept Klasse
 * */
public class Recipe {
    private Class[] needed;
    private Class resultObject;

    /**
     * Baurezepte fuer neue Objekte
     * @param resultObject    - herzustellendes Objekts
     * @param ingredient      - einzelne Zutaten
     */
    public Recipe(Class resultObject, Class... ingredient) {
        needed = ingredient;
        this.resultObject = resultObject;
    }

    public Class getResultObject() {
        return resultObject;
    }

    public boolean check(Tile... tiles) {
        if(tiles.length != needed.length)
            return false;
        boolean[] check = new boolean[needed.length];
        Arrays.fill(check, false);
        for(Tile t : tiles) {
            for(int i = 0; i < needed.length; i++) {
                if(t.getClass() == needed[i] && !check[i])
                    check[i] = true;
            }
        }
        for(boolean obj : check) {
            if (!obj)
                return false;
        }
        return true;
    }
}