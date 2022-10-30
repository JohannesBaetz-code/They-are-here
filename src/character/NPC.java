package character;

import javafx.scene.Node;
import main.World;
import java.util.List;

public abstract class NPC extends Character {

    //Konstrukor
    public NPC(int x, int y, String path, World world){
        super(x, y, 60, 60, path, world);
        direction = -1;
    }

    public abstract void update(List<Player> players);

    //Methode zum automatischen Laufen mit Kollisionsabfrage.
    //ändert Laufrichtung nach Kollision
    public void go(){
        setTranslateY(getTranslateY() - 1);
        boolean touchWall = false;
        setTranslateX(getTranslateX() + direction);
        absX += direction;
        for (Node tile : world.getWalls()) {
            if (tile.getBoundsInParent().intersects(getBoundsInParent())) {
                touchWall = true;
                break;
            }
        }
        if (touchWall) {
            direction *= -1;
        }
        setTranslateY(getTranslateY() + 1);
    }
}
