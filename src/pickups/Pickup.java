package pickups;

import main.Entity;
import character.Player;

public abstract class Pickup extends Entity {

    //Konstruktor für Bild und Position
    public Pickup(int x, int y, String path) {
        super(x, y, 60, 60, path);
    }

    //methode, um Pickup dem Spieler hinzuzufügen
    public abstract void applyToPlayer(Player player);
}
