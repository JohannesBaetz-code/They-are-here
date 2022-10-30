package weapons;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Bullet extends Circle {
    private TranslateTransition animation;
    private int damage;

    //beide Konstruktoren setzen Kugeln und deren Animation fest

    //Konstrukor für Shotgun
    public Bullet(double x, double y, double dx, double dy, int direction, int damage){
        super();
        setFill(Color.BLACK);
        setRadius(3);
        setTranslateX(x + 30 + 30 * direction);
        setTranslateY(y + 30);
        animation = new TranslateTransition();
        animation.setDuration(Duration.seconds(0.7));
        animation.setToX(x + dx * direction);
        animation.setToY(y + dy);
        animation.setNode(this);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();
        this.damage = damage;
    }

    //Konstruktor für Handgun und Rifle
    public Bullet(double x, double y, int direction, int damage){
        super();
        setFill(Color.BLACK);
        setRadius(3);
        setTranslateX(x + 30 + 30 * direction);
        setTranslateY(y + 30);
        animation = new TranslateTransition();
        animation.setDuration(Duration.seconds(0.7));
        animation.setToX(x + 900 * direction);
        animation.setNode(this);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();
        this.damage = damage;
    }

    public boolean isDone(){
        return animation.getStatus() == Animation.Status.STOPPED;
    }

    public int getDamage() {
        return damage;
    }
}
