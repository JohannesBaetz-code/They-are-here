package character;

import javafx.scene.Node;
import main.Entity;
import main.World;
import weapons.Bullet;
import weapons.Weapon;

public class Character extends Entity {

    //Deklarationen
    protected World world;
    protected Weapon weapon;
    protected int health;
    protected int direction = 1;
    protected int walkingSpeed = 2;
    protected double speedY;
    protected boolean onGround;

    //Konstruktor
    public Character(int x, int y, int w, int h, String path, World world) {
        super(x, y, w, h, path);
        this.world = world;
    }

    //spiegelt den Sprite nach Laufrichtung, checkt Kollision mit Kugeln
    public void update() {
        //spiegelung character an y Achse
        setScaleX(direction);
        for (Bullet bullet : world.getBullets()) {
            hit(bullet);
        }
    }

    //zieht Gesundheit nach Treffer mit Kugel ab
    public void hit(Bullet bullet){
        if (isHit(bullet))
            health -= bullet.getDamage();
    }

    //zieht Gesundheit nach Nahkampftreffer ab
    public void hit(int damage){
        health -= damage;
    }

    //feuert die vorhandene Waffe ab
    public void shoot(){
        if (weapon != null)
            weapon.shoot(world);
    }

    //Gravitation mit Beschleunigungskurve beim Springen und Fallen.
    //Trifft der Spieler mit Füßen auf Boden oder Wand, stoppt die Gravitation
    //Bei einem Sprung gegen die Decke wird collision ausgelöst und Beschleunigung entfernt
    public void gravity(){
        onGround = false;
        double yPos = getTranslateY();
        setTranslateY(getTranslateY() + speedY * 0.1);
        for (Node tile : world.getGround()){
            if (tile.getBoundsInParent().intersects(getBoundsInParent())){
                if (getTranslateY() > tile.getTranslateY() + 30){
                    setTranslateY(tile.getTranslateY()+60);
                    onGround = false;
                    speedY = 0;
                    break;
                }else {
                    onGround = true;
                    setTranslateY(tile.getTranslateY()-60);
                    break;
                }
            }
        }
        for (Node tile : world.getWalls()){
            if (tile.getBoundsInParent().intersects(getBoundsInParent())){
                if (getTranslateY() > tile.getTranslateY() + 30){
                    setTranslateY(tile.getTranslateY()+60);
                    onGround = false;
                    speedY = 0;
                    break;
                }else {
                    onGround = true;
                    setTranslateY(tile.getTranslateY()-60);
                    break;
                }
            }
        }
        if (!onGround)
            speedY += 2;
        else
            speedY = 0;
    }

    //prüft Kollision der Wände (links, rechts)
    //anheben des Entitys um 1 während er Bewegung (sonst bleibt er an Boden Tiles hängen)
    //setzt Entity um seine Geschwindigleit und Richtung nach vorn/zurück, um zu prüfen, ob Kollision möglich ist, danach um gleichen Wert nach hinten/vorn gesetzt
    public boolean checkCollision() {
        setTranslateY(getTranslateY() - 1);
        boolean collision = false;
        setTranslateX(getTranslateX() + walkingSpeed*direction);
        for (Node tile : world.getWalls()) {
            if (tile.getBoundsInParent().intersects(getBoundsInParent())) {
                collision = true;
                break;
            }
        }
        setTranslateX(getTranslateX() - walkingSpeed*direction);
        setTranslateY(getTranslateY() + 1);
        return collision;
    }

    //erhöht Munitionsmenge
    public void increaseCurrentAmmo(){
        weapon.increaseCurrentAmmoCount();
    }

    //-------------------------------------------------------------------------------
    //Getter und Setter
    public boolean isDead(){
        return health <= 0;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getDirection(){
        return direction;
    }

    public boolean isHit(Bullet bullet){
        return getBoundsInParent().intersects(bullet.getBoundsInParent());
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
