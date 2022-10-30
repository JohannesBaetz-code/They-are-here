package weapons;

import character.Character;
import main.World;

public class Rifle extends Weapon {
    public Rifle(Character character){
        super(30, character, 60);
    }

    //setzt schussrichtung, cooldown (für nachladen und schussfrequenz)
    @Override
    public void shoot(World world) {
        int direction = character.getDirection();
        if (magazine.isEmpty()){
            magazine.refill();
            cooldown = 480;
            return;
        }
        if (cooldown != 0){
            return;
        }
        world.addBullet(new Bullet(character.getTranslateX() + direction * 5, character.getTranslateY(), direction, 5), fromPlayer);
        cooldown = 10;
        magazine.removeBullet();
        decrementCurrentAmmoCount();
    }
}
