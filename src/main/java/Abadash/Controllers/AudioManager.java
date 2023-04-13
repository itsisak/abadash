package Abadash.Controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;



public class AudioManager {

    private HashMap<String, MediaPlayer> mediaPlayers = new HashMap<>();

    public AudioManager() {
        try {
            File[] audioDir = new File(getClass().getResource("/Abadash/audio").toURI()).listFiles(); 
            for (File audio : audioDir) {
                mediaPlayers.put(audio.getName().replaceAll(".mp3|.m4a", ""), new MediaPlayer(new Media(audio.toURI().toString())));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } 
    }


    public void playAudio(String audioName) {
        mediaPlayers.get(audioName).play();
    }


    public void stopAudio(String audioName) {
        mediaPlayers.get(audioName).stop();
    }

    public void restartAudio(String audioName) {
        stopAudio(audioName);
        playAudio(audioName);
    }

    public MediaPlayer getAudio(String audioName) {
        return mediaPlayers.get(audioName);
    }


}
