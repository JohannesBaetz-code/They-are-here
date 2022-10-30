package menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static main.Main.SCREENWIDTH;

public class ControlMenu extends Pane {

    private Pane main;
    private StartMenu startMenu;
    private Label label;
    private Rectangle rectangle;
    private Button back;
    private int fontSize = 30;
    private final int PADDING = 330;

    //Konstruktor, ruft alle Methoden auf, um das ControlMenu darzustellen
    public ControlMenu(Pane main, StartMenu startMenu){
        this.main = main;
        this.startMenu = startMenu;
        placeText();
        placeTextBackGround();
        createBackButton();
        getChildren().addAll(rectangle, label, back);
    }

    //Platziert den Text des Menüs
    private void placeText(){
        label = new Label();
        label.setText("Player 1: \n" +
                "Jump \t" + "->\t" + "W\n" +
                "Left \t\t" + "->\t" + "A\n" +
                "Right \t" + "->\t" + "D\n" +
                "Shoot \t" + "->\t" + "Space\n" +
                "\n\n" +
                "Player 2: \n" +
                "Spawnen \t" + "->\t" + "P\n" +
                "Jump \t" + "->\t" + "Up-Arrow\n" +
                "Left \t\t" + "->\t" + "Left-Arrow\n" +
                "Right \t" + "->\t" + "Right-Arrow\n" +
                "Shoot \t" + "->\t" + "Num. Block 0\n");
        label.setFont(Font.font(fontSize));
        label.setTextFill(Color.WHITE);
        label.setPrefSize(400, 600);
        label.setTranslateX(SCREENWIDTH/2-label.getPrefWidth()/2);
        label.setTranslateY(PADDING-label.getPrefHeight()/2);
    }

    //Platziert Hinterhrund des textes, um ihn besser lesbar zu machen
    private void placeTextBackGround(){
        rectangle = new Rectangle();
        rectangle.setWidth(label.getPrefWidth()+10);
        rectangle.setHeight(label.getPrefHeight()+5);
        rectangle.setTranslateX(label.getTranslateX()-5);
        rectangle.setTranslateY(label.getTranslateY());
        rectangle.setFill(Color.BLACK);
    }

    //Button führt zum Startmenu zurück
    private void createBackButton(){
        back = new Button();
        back.setText("Back");
        back.setPrefSize(410, 60);
        back.setFont(Font.font(30));
        back.setTranslateX(SCREENWIDTH/2 - back.getPrefWidth()/2);
        back.setTranslateY(label.getPrefHeight()+5+label.getTranslateY());
    }

    public void handleBack(){
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                back();
                backToStartMenu();
            }
        });
    }

    private void backToStartMenu(){
        main.getChildren().add(startMenu);
    }

    public void back(){
        main.getChildren().removeAll(label, back, this);
    }
}
