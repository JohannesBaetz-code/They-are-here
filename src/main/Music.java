package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.nio.file.Paths;

public class Music{

    Media music;
    MediaPlayer playMusic;
    MediaView mediaView = null;
    String path;

    //Konstruktor instantiiert Media, Mediaplayer und MediaView, durch welche Musik abgespielt werden kann
    public Music(String path){
        this.path = path;
        music = new Media(Paths.get(path).toUri().toString());
        playMusic = new MediaPlayer(music);
        playMusic.setVolume(0.2);
        playMusic.setCycleCount(MediaPlayer.INDEFINITE);
        playMusic.play();
    }

    public MediaView getMediaView() {
        if (mediaView == null)
            mediaView = new MediaView(playMusic);
        return mediaView;
    }

    public void stoppMusic(){
        playMusic.stop();
    }
}
