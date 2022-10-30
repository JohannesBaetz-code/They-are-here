package menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Main;

public class StartMenu extends Pane{

    private final Stage pri;
    private Pane main;
    private Pane controlMenu;
    private final Button startGame;
    private final Button closeGame;
    private final Button controlMenuButton;
    private Label titel = new Label();
    private Rectangle background = new Rectangle();
    private final int fontSize = 30;
    private final int sizeX = 300;
    private final int sizeY = 60;
    private final int PADDING = 60;

    public StartMenu(Pane main, Stage stage, String[] ids, String[] texts){
        this.main = main;
        this.pri = stage;
        placeTitel();
        startGame = createStartMenuButton(Main.SCREENWIDTH/2, Main.SCREENHEIGHT/2, ids[0], texts[0]);
        closeGame = createStartMenuButton(Main.SCREENWIDTH/2, Main.SCREENHEIGHT/2+sizeY+PADDING , ids[1], texts[1]);
        controlMenuButton = createStartMenuButton(Main.SCREENWIDTH/2, Main.SCREENHEIGHT/2+2*sizeY+2*PADDING, ids[2], texts[2]);
        getChildren().addAll(startGame, closeGame, controlMenuButton, background, titel);
    }

    //Buttongenerierung
    public Button createStartMenuButton(double setX, double setY, String id, String text){
        Button button = new Button();
        button.setId(id);
        button.setText(text);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(Font.font(fontSize));
        button.setPrefSize(sizeX, sizeY);
        button.setTranslateX(setX-sizeX/2);
        button.setTranslateY(setY-sizeY/2);
        return button;
    }

    //sets Title and TitleBackground of the Game
    private void placeTitel(){
        titel.setText("They are here!");
        titel.setPrefSize(sizeX*2, sizeY*4);
        titel.setTranslateX(Main.SCREENWIDTH/2 - titel.getPrefWidth()/2);
        titel.setTranslateY(Main.SCREENHEIGHT/8 - titel.getPrefHeight()/4);
        titel.setFont(Font.font(fontSize*3));
        titel.setTextFill(Color.WHITE);

        background.setX(titel.getTranslateX()-5);
        background.setY(titel.getTranslateY()-5);
        background.setHeight(titel.getPrefHeight()+10);
        background.setWidth(titel.getPrefWidth()+10);
        background.setFill(Color.BLACK);

    }

    //eventhandling
    public void handleCloseAndControlButton(){
        closeGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pri.close();
            }
        });
        controlMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearStartMenu();
                main.getChildren().add(controlMenu);
            }
        });
    }

    public void clearStartMenu(){
        main.getChildren().removeAll(startGame, closeGame, controlMenuButton, this);
    }

    public void setControlMenu(Pane controlMenu) {
        this.controlMenu = controlMenu;
    }

    public Button getStartGame() {
        return startGame;
    }
}
