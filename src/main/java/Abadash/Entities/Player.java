package Abadash.Entities;

import javafx.scene.image.Image;

import java.net.URL;

public class Player extends Entity {
    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        this.sprite = new Image("Abadash/sprites/player.png");
    }
}
