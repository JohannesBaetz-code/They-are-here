package menus;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class DeathMenu extends Pane {

    private Button retryButton = new Button();
    private Label label = new Label();
    private Rectangle rectangle = new Rectangle();
    private int sizeX = 300;
    private int sizeY = 120;

    //konstruktor
    public DeathMenu(){
        init();
        getChildren().addAll(rectangle, label, retryButton);
    }

    //erzeugt alle elemente der Pane
    private void init(){
        label.setText("YOU DIED!");
        label.setFont(Font.font(60));
        label.setPrefSize(sizeX, sizeY);
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setTranslateX(Main.SCREENWIDTH/2 - label.getPrefWidth()/2);
        label.setTranslateY(Main.SCREENHEIGHT/2 - sizeY/2);

        rectangle.setX(label.getTranslateX() - 5);
        rectangle.setY(label.getTranslateY() - 5);
        rectangle.setFill(Color.BLACK);
        rectangle.setWidth(label.getPrefWidth() + 10);
        rectangle.setHeight(label.getPrefHeight() + 10);

        retryButton.setText("Retry!");
        retryButton.setPrefSize(sizeX, sizeY);
        retryButton.setTranslateX(Main.SCREENWIDTH/2 - sizeX/2);
        retryButton.setTranslateY(Main.SCREENHEIGHT - Main.SCREENHEIGHT/4 - sizeY/2);
        retryButton.setFont(Font.font(30));
        retryButton.setTextAlignment(TextAlignment.CENTER);
    }

    public Button getRetryButton() {
        return retryButton;
    }
}
