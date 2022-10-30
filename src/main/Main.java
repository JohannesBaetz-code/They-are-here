package main;

import character.Player;
import character.SecondPlayer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import menus.ControlMenu;
import menus.DeathMenu;
import menus.StartMenu;
import ui.UI;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    //Main Pane und Scene
    private Pane main = new Pane();
    private Scene scene;
    private AnimationTimer timer;
    private Stage primaryStage;

    //Buttons in Main Klasse
    private Button startGame;
    private Button retryButton;

    //Deklarationen eigener Klassen
    private Player player;
    private SecondPlayer secondPlayer;
    private World world;
    private UI ui;
    private UI uiSec;
    private StartMenu startMenu;
    private ControlMenu controlMenu;
    private DeathMenu deathMenu;
    private Music music;

    //eigene Variablen
    public static final int SCREENWIDTH = 1280;
    public static final int SCREENHEIGHT = 720;
    private boolean secondPlayerDied = false;

    //Pfade zu allen Leveln
    private List<String> levelPaths = Arrays.asList(
            "src/level/Level_1.txt",
            "src/level/Level_2.txt",
            "src/level/Level_3.txt",
            "src/level/Level_4.txt");

    private List<String> musicPaths = Arrays.asList(
            "src/music/startmenu.mp3",
            "src/music/levelmusic.mp3");

    //Hashmap für Keyinputs
    private static HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private int levelID = 0;
    private int musicID = 0;

    //ID und Namen für Buttons in anderen klassen
    private String[] startMenuText = {"Start new Game", "Close Game", "Controls"};
    private String[] startMenuIDS = {"start", "close", "controls"};
    private String[] gameMenuIDS = new String[]{"resume", "close", "controls"};
    private String[] gameMenuText = new String[]{"Resume Game", "Close Game", "Controls"};

    //update Methode, wird jeden Frame ausgeführt
    //fragt Keyinputs ab, ruft Gravitation auf, ruft alle anderen update Methoden auf
    //checkt ob Player tot ist oder Gameover und ruft Methode zum Levelwechsel auf
    //checkt, ob zweiter Spieler dem Spiel hinzugefügt werden kann
    private void update() throws Exception{
        if (isPressed(KeyCode.ESCAPE)){
            handleGameMenu();
        }
        player.gravity();
        if (secondPlayer != null)
            secondPlayer.gravity();
        updateBothPlayerInput();
        if (player.isNextLevel()){
            main.getChildren().removeAll(world.loadBackGroundImage(), world, ui, uiSec);
            levelID++;
            loadLevel(levelPaths.get(levelID%levelPaths.size()));
            player.setNextLevel(false);
        }
        world.update();
        ui.update();
        uiSec.update();
        if (player.isGameOver()){
            timer.stop();
            handleDeathMenu();
        }
        if (secondPlayer != null && secondPlayer.isGameOver()){
            world.removeSecondPlayer();
            secondPlayer = null;
            uiSec.setPlayer(null);
            secondPlayerDied = true;
        }
    }

    //ruft Instantiierungen und Methoden der Menüs auf
    //setzt die Scene und Keyinputlistener
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        instantiateMainPane();
        handleStartAndControlMenu();
        scene = new Scene(main);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        this.primaryStage.setTitle("Platformer: They are here");
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        //respawnpoint bearbeiten
    }

    public static void main(String[] args) {
        launch(args);
    }

    //instantiiert eigene Klassen und startet Musik
    private void instantiateMainPane(){
        music  = new Music(musicPaths.get(musicID));
        player = new Player(610, 300, "sprites/player/player_1.png");
        secondPlayer = null;
        ui = new UI(player,  0);
        uiSec = new UI(secondPlayer,  1);
        main.getChildren().addAll(music.getMediaView());
        musicID++;
    }

    //lädt ein Level und ermöglicht dem zweiten Spieler nach Gameover einen Respawn
    private void loadLevel(String path) throws IOException{
        if (secondPlayer != null) {
            secondPlayer.teleportToSpawn();
            uiSec.setLiveUICount();
            world = new World(path, new Player[]{player, secondPlayer});
        }else {
            world = new World(path, new Player[]{player});
        }
        secondPlayerDied = false;
        main.getChildren().addAll(world.loadBackGroundImage(), world, ui, uiSec);
    }

    //instantiiert die Menüs und implementiert das Eventhandling für den startgame-Button
    private void handleStartAndControlMenu(){
        ImageView imageView = new ImageView(new Image("sprites/tiles/background.gif"));
        imageView.setFitHeight(SCREENHEIGHT);
        imageView.setFitWidth(SCREENWIDTH);
        startMenu = new StartMenu(main, primaryStage, startMenuIDS, startMenuText);
        controlMenu = new ControlMenu(main, startMenu);
        main.getChildren().addAll(imageView, startMenu);
        startMenu.setControlMenu(controlMenu);
        startMenu.handleCloseAndControlButton();
        controlMenu.handleBack();
        startGame = startMenu.getStartGame();
        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                music.stoppMusic();
                music = new Music(musicPaths.get(musicID));
                main.getChildren().add(music.getMediaView());
                startGame();
            }
        });
    }

    //ruft loadLevel auf und startet die update Mathode
    private void startGame(){
        main.getChildren().remove(startMenu);
        try {
            loadLevel(levelPaths.get(levelID));
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    //pausiert die Update Methode und blendet das Startmenü ein
    private void handleGameMenu(){
        timer.stop();
        startMenu = new StartMenu(main, primaryStage, gameMenuIDS, gameMenuText);
        main.getChildren().add(startMenu);
        startMenu.handleCloseAndControlButton();
        startGame = startMenu.getStartGame();
        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                timer.start();
                main.getChildren().remove(startMenu);
            }
        });
    }

    //wenn der erste Spieler GameOver ist, wird dieses Menü eingeblendet und er kann von Level 1 erneut beginnen
    private void handleDeathMenu(){
        deathMenu = new DeathMenu();
        retryButton = deathMenu.getRetryButton();
        main.getChildren().add(deathMenu);
        retryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                main.getChildren().removeAll(world.loadBackGroundImage(), world, ui, uiSec, deathMenu);
                try {
                    loadLevel(levelPaths.get(0));
                    levelID = 0;
                    secondPlayerDied = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.setLiveCount(3);
                ui.setLiveUICount();
                timer.start();
            }
        });
    }

    //Keyinputs und welche Methoden dadurch aufgerufen werden
    private void updateBothPlayerInput(){
        if (isPressed(KeyCode.A)){
            player.goLeft();
        }
        if (isPressed(KeyCode.D)){
            player.goRight();
        }
        if (isPressed(KeyCode.W)){
            player.jump();
        }
        if (isPressed(KeyCode.SPACE)){
            player.shoot();
        }
        if (isPressed(KeyCode.P)){
            if (secondPlayer == null && !secondPlayerDied) {
                secondPlayer = new SecondPlayer(500, 300, "sprites/player/secondplayer.png");
                if (world != null)
                    world.addPlayer(secondPlayer);
                uiSec.setPlayer(secondPlayer);
            }
        }
        if (secondPlayer != null) {
            if (isPressed(KeyCode.LEFT)) {
                secondPlayer.goLeft();
            }
            if (isPressed(KeyCode.RIGHT)) {
                secondPlayer.goRight();
            }
            if (isPressed(KeyCode.UP)) {
                secondPlayer.jump();
            }
            if (isPressed(KeyCode.NUMPAD0)) {
                secondPlayer.shoot();
            }
        }
    }

    //gibt für einen Keyinput true oder keinen Keyinput false zurück
    private static boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key,false);
    }
}
