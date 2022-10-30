package pickups;

import character.Player;

public class AmmoPickup extends Pickup {

    public AmmoPickup(int x, int y) {
        super(x, y, "sprites/pickups/ammo-pickup.png");
    }

    //Spieler bekommt mehr Munition
    @Override
    public void applyToPlayer(Player player) {
        player.increaseCurrentAmmo();
    }
}
