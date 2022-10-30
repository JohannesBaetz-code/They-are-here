package character;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import pickups.Pickup;
import main.World;
import weapons.HandGun;

public class Player extends Character {

    protected Point2D respawnPoint;
    private boolean nextLevel = false;
    protected int cooldown;
    protected int liveCount = 3;
    protected boolean wasDead = false;

    //Konstruktor, legt Objektvariablen fest
    public Player(int x, int y, String path) {
        super(x, y, 60, 60, path, null);
        weapon = new HandGun(this);
        cooldown = weapon.getCooldown();
        walkingSpeed = 4;
        this.health = 10;
        disableScrolling = true;
        respawnPoint = new Point2D(x, y);
    }

    //checkt Kollision mit Pickups
    //checkt Kollision mit LevelChange-Objekten (ein neues Level wird geladen)
    //checkt Lebensstatus, reduziert Anzahl der Leben
    public void update(){
        super.update();
        weapon.updateCooldown();
        for (Pickup pickup: world.getPickups()) {
            if (pickup.getBoundsInParent().intersects(getBoundsInParent())){
                pickup.applyToPlayer(this);
                world.removePickup(pickup);
                break;
            }
        }
        changerCollision();
        if (isDead()){
            respawn();
            liveCount--;
            wasDead = true;
        }
    }

    //checkt Kollision mit LevelChange Objekten (ein neues Level wird geladen)
    private void changerCollision(){
        for (Node changer : world.getChangers()) {
            if (changer.getBoundsInParent().intersects(getBoundsInParent())) {
                nextLevel = true;
            }
        }
    }

    //setzt den Spieler an den Levelanfang zurück, resettet Player Waffe und Welt
    public void respawn(){
        health = 10;
        weapon = new HandGun(this);
        setTranslateX(respawnPoint.getX());
        setTranslateY(respawnPoint.getY());
        world.reset();
    }

    //----------------------------------------------
    //Methoden zur Bewegung und zum Schießen

    public void goLeft() {
        direction = -1;
        if(!checkCollision()) {
            world.scroll(walkingSpeed);
        }
    }

    public void goRight(){
        direction = 1;
        if(!checkCollision()) {
            world.scroll(-walkingSpeed);
        }
    }

    public void jump(){
        if (onGround)
            speedY = -100;
    }

    @Override
    public void shoot(){
        if (weapon != null && weapon.getCurrentAmmoCount() > 0) {
            weapon.shoot(world);
        }
    }

    //-----------------------------------------------------------------------
    //Getter und Setter

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isGameOver(){
        return liveCount<=0;
    }

    public int getHealth(){
        return this.health;
    }

    public double getWeaponFillStatus() {
        return weapon.getFillStatus();
    }

    public boolean isNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(boolean nextLevel) {
        this.nextLevel = nextLevel;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }

    public int getLiveCount() {
        return liveCount;
    }

    public boolean isWasDead() {
        return wasDead;
    }

    public void setWasDead(boolean wasDead) {
        this.wasDead = wasDead;
    }
}
