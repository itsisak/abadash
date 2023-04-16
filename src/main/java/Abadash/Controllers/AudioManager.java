package Abadash.Controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;



public class AudioManager {
    private static AudioManager instance;
    private HashMap<String, MediaPlayer> mediaPlayers = new HashMap<>();
    private double volume;

    private AudioManager() {
        volume = .75;
        try {
            File[] audioDir = new File(getClass().getResource("/Abadash/audio").toURI()).listFiles(); 
            for (File audio : audioDir) {
                mediaPlayers.put(audio.getName().replaceAll(".mp3|.m4a|.wav", ""), new MediaPlayer(new Media(audio.toURI().toString())));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
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

    public void toggleMute() {

    }

    public void changeVolume(double volume) {
        volume = Math.min(Math.max(volume, 0), 1);
        this.volume = volume;
        for (MediaPlayer player : mediaPlayers.values()) {
            player.setVolume(volume);
        }
    }

    public double getVolume() {
        return volume;
    }

}
