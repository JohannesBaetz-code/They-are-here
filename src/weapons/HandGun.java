package weapons;

import character.Character;
import main.World;

public class HandGun extends Weapon {

    public HandGun(Character character){
        super(10, character, 20);
    }

    //setzt schussrichtung, cooldown (für nachladen und schussfrequenz)
    @Override
    public void shoot(World world) {
        int direction = character.getDirection();
        if (magazine.isEmpty()){
            magazine.refill();
            cooldown = 110;
            return;
        }
        if (cooldown != 0){
            return;
        }
        world.addBullet(new Bullet(character.getTranslateX() + direction*5, character.getTranslateY(), direction, 3), fromPlayer);
        cooldown = 40;
        magazine.removeBullet();
        decrementCurrentAmmoCount();
    }
}
