package character;

import main.World;
import weapons.Bullet;
import weapons.HandGun;
import java.util.List;

public class Marksmann extends NPC {

    //Konstruktor
    public Marksmann(int x, int y, World world){
        super(x, y, "sprites/aliens/marksmann.png", world);
        this.health = 8;
        weapon = new HandGun(this);
    }

    //überschreibt geerbte Methode
    //schießt und läuft automatisch
    //kann nur durch Kugeln des Players getroffen werden (nicht durch NPC Kugeln)
    @Override
    public void update(List<Player> players) {
        setScaleX(direction);
        go();
        shoot();
        weapon.updateCooldown();
        for (Bullet bullet : world.getPlayerBullets()) {
            hit(bullet);
        }
    }
}
