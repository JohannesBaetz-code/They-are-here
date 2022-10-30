package ui;

import character.Player;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import main.Main;

import java.util.ArrayList;
import java.util.List;


public class UI extends Pane {

    private Rectangle healthBar = new Rectangle();
    private Rectangle ammoClip = new Rectangle();
    private Label playerNumberLabel = new Label();
    private Label currentAmmo = new Label();
    private Player player;
    private static final int PADDING = 20;
    private static final int FONTSIZE = 18;
    private int difference = 40;
    private final String live = "sprites/ui/heart.png";
    Image heartImage = new Image(live);
    private ImageView heartView = new ImageView();
    private List<ImageView> liveList = new ArrayList<ImageView>();
    private int playerNumber;

    public UI(Player player, int playerNumber){
        super();
        this.player = player;
        this.playerNumber = playerNumber;
        if (player != null)
            init();
    }

    //erzeugt alle UI elemente
    private void init(){
        //Lebensanzeige
        healthBar.setX(PADDING);
        healthBar.setY(PADDING + difference* playerNumber);
        healthBar.setFill(Color.RED);
        //Magazin Füllstand
        ammoClip.setY(PADDING + difference* playerNumber);
        ammoClip.setFill(Color.GOLD);
        //ANzeige welcher Player
        this.playerNumberLabel.setText("Player " + (playerNumber + 1));
        this.playerNumberLabel.setTranslateX(640- this.playerNumberLabel.getTranslateX()/2);
        this.playerNumberLabel.setTranslateY(PADDING + difference* playerNumber);
        this.playerNumberLabel.setFont(Font.font(FONTSIZE));
        //Anzeige wie viel MaximalMunition vorhanden ist
        currentAmmo.setText(String.valueOf(player.getWeapon().getCurrentAmmoCount()));
        currentAmmo.setTranslateX(Main.SCREENWIDTH - PADDING - 200);
        currentAmmo.setTranslateY(PADDING + difference* playerNumber);
        currentAmmo.setFont(Font.font(FONTSIZE));
        currentAmmo.setVisible(true);
        //Anzeige wie viele Leben der Spieler hat
        setLiveUICount();

        getChildren().addAll(healthBar, ammoClip, this.playerNumberLabel, currentAmmo);
    }

    //healthBar, ammoClip und currentAmmo werden von der Länge der Gesundheit und Munition angepasst
    public void update() {
        if (player != null) {
            toFront();
            healthBar.setHeight(PADDING);
            healthBar.setWidth(player.getHealth() * 10);
            ammoClip.setX(Main.SCREENWIDTH - PADDING - player.getWeaponFillStatus() * 100);
            ammoClip.setHeight(PADDING);
            ammoClip.setWidth((player.getWeaponFillStatus() * 100));
            currentAmmo.setText(String.valueOf(player.getWeapon().getCurrentAmmoCount()));
            //entfernt UI bei totem Spieler
            if (player.isWasDead()) {
                getChildren().remove(liveList.get(player.getLiveCount()));
                liveList.remove(player.getLiveCount());
                player.setWasDead(false);
            }
        }
    }

    //erzeugt UI für ereugten Spieler (zweiter Player spawned)
    public void setPlayer(Player player) {
        this.player = player;
        if (player != null)
            init();
        else
            getChildren().clear();
    }

    public void setLiveUICount() {
        for (int i = 0; i < player.getLiveCount(); i++){
            heartView = new ImageView(heartImage);
            heartView.setFitWidth(30);
            heartView.setFitHeight(30);
            heartView.setX(player.getHealth() * 12 + PADDING*1.5*i);
            heartView.setY(PADDING + difference* playerNumber);
            liveList.add(heartView);
            getChildren().add(heartView);
        }
    }
}
