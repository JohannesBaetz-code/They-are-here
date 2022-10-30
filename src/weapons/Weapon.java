package weapons;

import character.Player;
import javafx.scene.image.ImageView;
import character.Character;
import main.World;

public abstract class Weapon extends ImageView {
    protected Magazine magazine;
    protected int cooldown = 0;
    protected Character character;
    protected int currentAmmoCount;
    protected boolean fromPlayer = false;

    //standardkonstruktor für alle waffen
    public Weapon(int magazineSize, Character character, int currentAmmoCount) {
        magazine = new Magazine(magazineSize);
        this.character = character;
        this.currentAmmoCount = currentAmmoCount;
        if (character instanceof Player)
            fromPlayer = true;
    }

    //senkt den cooldown zwischen schüssen und beim nachladen
    public void updateCooldown(){
        if (cooldown > 0)
            cooldown--;
    }

    public abstract void shoot(World world);

    public double getFillStatus() {
        return magazine.getFillStatus();
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCurrentAmmoCount(){
        return currentAmmoCount;
    }

    public void decrementCurrentAmmoCount(){
        currentAmmoCount--;
    }

    public void increaseCurrentAmmoCount(){
        currentAmmoCount += 10;
    }
}
