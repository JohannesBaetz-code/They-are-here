package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity extends ImageView{

    //absolute und relative Koordinaten des Spielers
    protected int absX;
    protected int absY;
    protected int offsetX = 0;
    protected int offsetY = 0;

    public boolean disableScrolling;

    //Konstruktor setzt Bild und absolute Koordinaten
    public Entity(int x, int y, int w, int h, String path){
        super();
        setImage(new Image(path));
        setTranslateX(x);
        setTranslateY(y);
        setTranslateZ(0);
        setFitWidth(w);
        setFitHeight(h);
        absX = x;
        absY = y;
        disableScrolling = false;
    }

    //tile verschieben, ohne absolute koordinaten zu verändern
    public void scroll(int dx, int dy){
        offsetX += dx;
        offsetY += dy;
        setTranslateX(absX + offsetX);
        setTranslateY(absY + offsetY);
    }
}
