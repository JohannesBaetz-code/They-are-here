package main;

import character.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import pickups.AmmoPickup;
import pickups.Pickup;
import pickups.RiflePickup;
import pickups.ShotgunPickup;
import weapons.Bullet;

import static main.Main.SCREENHEIGHT;
import static main.Main.SCREENWIDTH;

public class World extends Pane {

    private int levelWidth;
    private int offsetScroll;

    private final String backGround = "sprites/tiles/background.gif";
    private final String wall = "sprites/tiles/wall-new.png";
    private final String groundTile = "sprites/tiles/ground.png";
    private final String deepGroundTile = "sprites/tiles/ground-deep.png";
    private final String changerTile = "sprites/tiles/changer.png";

    //Listen für Entitys
    private List<Node> ground = new ArrayList<Node>();
    private List<Node> walls = new ArrayList<Node>();
    private List<Pickup> pickups = new ArrayList<Pickup>();
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<NPC> enemies = new ArrayList<NPC>();
    private List<Player> players = new ArrayList<Player>();
    private List<Node> changers = new ArrayList<Node>();
    private List<Bullet> playerBullets = new ArrayList<Bullet>();
    private ImageView backgroundImage = null;

    //Konstruktor, ließt Level ein und ruft Methode zur Entity ersellung auf, setzt außerdem den/die Spieler auf die Pane
    public World(String path, Player[] players) throws IOException{
        super();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        levelWidth = 0;
        for(int y = 0; reader.ready(); y++) {
            String line = reader.readLine();
            levelWidth = Math.max(levelWidth, line.length() * 60);
            for (int x = 0; x < line.length(); x++) {
                loadEntity(x * 60, y * 60, line.charAt(x));
            }
        }
        reader.close();
        for(Player p : players) {
            this.players.add(p);
            getChildren().add(p);
            p.setWorld(this);
            p.toFront();
        }
    }

    //ruft player.update auf, entfernt Kugeln nach Distanz oder wenn sie eine Wand treffen
    //updatet jeden NPC
    public void update(){
        for(Player p : players) {
            p.update();
        }
        for (int i = 0; i < bullets.size(); ){
            if (bullets.get(i).isDone() || isBulletHittingWall(bullets.get(i))) {
                getChildren().remove(bullets.get(i));
                bullets.remove(i);
            }else {
                i++;
            }
        }
        for (int i = 0; i < playerBullets.size(); ) {
            if (playerBullets.get(i).isDone() || isBulletHittingWall(playerBullets.get(i))) {
                getChildren().remove(playerBullets.get(i));
                playerBullets.remove(i);
            } else {
                i++;
            }
        }
        for (int i = 0; i < enemies.size(); ) {
            enemies.get(i).update(players);
            if (enemies.get(i).isDead()) {
                getChildren().remove(enemies.get(i));
                enemies.remove(i);
            }else{
                i++;
            }
        }
    }

    //erstellt das Hintergrund-gif
    public Node loadBackGroundImage(){
        if (backgroundImage == null) {
            backgroundImage = new ImageView(new Image(backGround));
            backgroundImage.setFitHeight(SCREENHEIGHT);
            backgroundImage.setFitWidth(SCREENWIDTH);
        }
        return backgroundImage;
    }

    //erstellt Entitys je nach eingelesenem Buchstaben und fügt sie der Pane hinzu
    private void loadEntity(int x, int y, char type) throws InvalidParameterException{
        Node entity;
        switch (type) {
            case ' ':
                return;
            case '_':
                entity = new Entity(x, y, 60, 60, groundTile);
                ground.add(entity);
                break;
            case 'd':
                entity = new Entity(x, y, 60, 60, deepGroundTile);
                break;
            case '|':
                entity = new Entity(x, y, 60, 60, wall);
                walls.add(entity);
                break;
            case 'C':
                entity = new Entity(x, y, 60, 60, changerTile);
                changers.add(entity);
                ground.add(entity);
                break;
            case 'r':
                entity = new RiflePickup(x, y);
                pickups.add((Pickup)entity);
                break;
            case 's':
                entity = new ShotgunPickup(x, y);
                pickups.add((Pickup)entity);
                break;
            case 'a':
                entity = new AmmoPickup(x, y);
                pickups.add((Pickup) entity);
                break;
            case 'R':
                entity = new Runner(x, y, this);
                enemies.add((NPC)entity);
                break;
            case 'M':
                entity = new Marksmann(x, y, this);
                enemies.add((NPC)entity); break;
            default:
                throw new InvalidParameterException();
        }
        getChildren().add(entity);
    }

    //bewegt die World Pane nach links oder rechts mit dem Spieler
    public void scroll(int dx) {
        for (Node child : getChildren()) {
            if (child instanceof Entity){
                Entity entity = (Entity) child;
                if(!entity.disableScrolling) {
                    entity.scroll(dx, 0);
                }
            }
        }
        offsetScroll += dx;
    }

    //prüft ob eine Kugel eine Wand trifft
    private boolean isBulletHittingWall(Bullet bullet){
        for (Node wall : walls) {
            if (bullet.getBoundsInParent().intersects(wall.getBoundsInParent()))
                return true;
        }
        return false;
    }

    //setzt die gescrollte Bewegung zurück (für Respawn gebraucht)
    public void reset(){
        scroll(-offsetScroll);
    }

    //entfernt Pickup aus der Pane und der Liste (nachdem es vom Player aufgehoben wurde)
    public void removePickup(Pickup pickup){
        pickups.remove(pickup);
        getChildren().remove(pickup);
    }

    //fügt eine Kugel der Pane oder einer der beiden Listen hinzu
    //Enemies bekommen nur von Playerkugeln schaden, während der Player von allen Kugeln schaden bekommt (also auch vom zweiten Spieler)
    public void addBullet(Bullet bullet, boolean fromPlayer){
        if (fromPlayer){
            bullets.add(bullet);
            playerBullets.add(bullet);
            getChildren().add(bullet);
        }else{
            bullets.add(bullet);
            getChildren().add(bullet);
        }
    }

    //entfernt den zweiten Speiler aus der Pane und dem Array
    public void removeSecondPlayer(){
        getChildren().remove(players.get(1));
        players.remove(1);
    }

    //fügt den zweiten player der Pane und dem Array hinzu
    public void addPlayer(Player secondPlayer){
        players.add(secondPlayer);
        getChildren().add(secondPlayer);
        secondPlayer.setWorld(this);
    }

    //------------------------------------------------------------
    //Getter und Setter
    public List<Node> getGround(){
        return ground;
    }

    public List<Node> getWalls(){
        return walls;
    }

    public List<Pickup> getPickups(){
        return pickups;
    }

    public List<Node> getChangers() {
        return changers;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }
}
