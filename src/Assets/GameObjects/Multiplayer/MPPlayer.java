package Assets.GameObjects.Multiplayer;

import Assets.GameObjects.Player;
import Assets.World.TileMap;
import Main.References;

import java.awt.*;

/**
 * Multiplayer Spielobjekt
 *
 * @author Sirat
 * */
public class MPPlayer extends Player
{

    public String playerName;
    public int playerID;

    /**
     * Player                       Konstruktor der Player Klasse
     *
     * @param width                 Breite des Bildes
     * @param height                Hoehe des Bildes
     * @param widthForCollision     Breite des Kollisionsrechteckes
     * @param heightForCollision    Hoehe des Kollisionsrechteckes
     * @param velocityX             Geschwindigkeit auf der x-Achse
     * @param velocityY             Geschwindigkeit auf der y-Achse
     * @param maxVelocityX          Maximalgeschwindigkeit auf der x-Achse
     * @param maxVelocityY          Maximalgeschwindigkeit auf der y-Achse
     * @param tileMap               Spielmap
     * @param playerName            Spielername
     * @param id                    ID
     */
    public MPPlayer(int width, int height, int widthForCollision, int heightForCollision,
                    double velocityX, double velocityY, double maxVelocityX, double maxVelocityY,
                    TileMap tileMap, String playerName, int id)
    {

        super(width, height, widthForCollision, heightForCollision, velocityX, velocityY, maxVelocityX, maxVelocityY, tileMap);
        this.playerName = playerName;
        this.playerID = id;
    }

    @Override
    public void render(Graphics2D g)
    {
        super.render(g);

        g.setColor(Color.BLACK);
        g.drawString(playerName,(int) References.SCREEN_WIDTH/2,(int) (super.getY() - y/2));
    }

}
