package pickups;

import character.Player;
import weapons.Shotgun;

public class ShotgunPickup extends Pickup {
   public ShotgunPickup(int x, int y){
       super(x, y, "sprites/pickups/shotgun-pickup.png");
   }

   //Spieler bekommt Shotgun
    @Override
    public void applyToPlayer(Player player) {
        player.setWeapon(new Shotgun(player));
    }
}
