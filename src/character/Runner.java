package character;

import main.World;
import weapons.Bullet;
import java.util.List;

public class Runner extends NPC{

    //Konstruktor
    public Runner(int x, int y, World world){
        super(x, y, "sprites/aliens/runner.png", world);
        this.health = 10;
        this.direction = -1;
    }

    //automatisches Laufen, checkt Kollision mit Player und fügt Nahkampf schaden zu, kann nur von Player-Kugeln getroffen werden
    @Override
    public void update(List<Player> players) {
        setScaleX(direction);
        go();
        for (Player p : players) {
            if (p.getBoundsInParent().intersects(getBoundsInParent())) {
                p.hit(1);
            }
        }
        for (Bullet bullet : world.getPlayerBullets()) {
            hit(bullet);
        }
    }
}
