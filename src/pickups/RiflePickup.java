package pickups;

import character.Player;
import weapons.Rifle;

public class RiflePickup extends Pickup{
    public RiflePickup(int x, int y){
        super(x, y, "sprites/pickups/rifle-pickup.png");
    }

    //Spieler bekommt Rifle
    @Override
    public void applyToPlayer(Player player) {
        player.setWeapon(new Rifle(player));
    }
}
