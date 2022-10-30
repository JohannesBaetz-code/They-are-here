package character;

import weapons.HandGun;


public class SecondPlayer extends Player{

    //Konstruktor
    public SecondPlayer(int x, int y, String path){
        super(x, y, path);
        disableScrolling = false;
    }

    //nutzt Gravitation des Spielers
    public void gravity() {
        super.gravity();
        absY = (int) getTranslateY();
    }

    //setzte Spieler 2 an den Anfang des Levels zurück und setzt seine Waffe zurück
    @Override
    public void respawn(){
        health = 10;
        weapon = new HandGun(this);
        absX = (int) respawnPoint.getX();
        absY = (int) respawnPoint.getY();
        setTranslateX(absX);
        setTranslateY(absY);
    }

    public void teleportToSpawn(){
        absX = (int) respawnPoint.getX();
        absY = (int) respawnPoint.getY();
        setTranslateX(absX);
        setTranslateY(absY);
        offsetX = 0;
        offsetY = 0;
    }

    //---------------------------------------------
    //Bewegungen

    public void goLeft() {
        direction = -1;
        if(!checkCollision()) {
            setTranslateX(getTranslateX() - walkingSpeed);
            absX -= walkingSpeed;
        }
    }

    public void goRight(){
        direction = 1;
        if(!checkCollision()) {
            setTranslateX(getTranslateX() + walkingSpeed);
            absX += walkingSpeed;
        }
    }
}
