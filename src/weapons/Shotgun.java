package weapons;

import character.Character;
import main.World;

public class Shotgun extends Weapon {

    public Shotgun(Character character){
        super(8, character, 16);
    }

    //setzt schussrichtung, cooldown (für nachladen und schussfrequenz)
    //für shotgun eine random Range, in der Kugeln fliegen können
    @Override
    public void shoot(World world) {
        int direction = character.getDirection();
        if (magazine.isEmpty()){
            magazine.refill();
            cooldown = 150;
            return;
        }
        if (cooldown != 0){
            return;
        }
        for (int i = 0; i < 6; i++){
            double offsetY = Math.random()*600 - 300;
            double offsetX = Math.sqrt(900*900-offsetY*offsetY);
            world.addBullet(new Bullet(character.getTranslateX() + direction * 5, character.getTranslateY(), offsetX, offsetY, direction, 2), fromPlayer);
        }
        cooldown = 70;
        magazine.removeBullet();
        decrementCurrentAmmoCount();
    }
}
