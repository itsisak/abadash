package Abadash.Entities;

import javafx.scene.image.Image;

import java.net.URL;
import static Abadash.Constants.SCENE_HEIGHT;
import static Abadash.Constants.BLOCK_SIZE;

public class Player extends Entity {
    private final double GRAVITY_CONSTANT = 500;
    private final double VELOCITY_X = 50;
    private double velocityY;
    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        this.sprite = new Image("Abadash/sprites/player.png");
    }

    @Override
    public void update(double deltaTime) {
        velocityY += GRAVITY_CONSTANT * deltaTime;
        y += velocityY * deltaTime;
        x += VELOCITY_X * deltaTime;
        if (y > SCENE_HEIGHT - BLOCK_SIZE)
            y = SCENE_HEIGHT - BLOCK_SIZE; 
        System.out.println("deltaTime: " + deltaTime + "| x: " + x + "| y: " + y + "| velocity: " + velocityY + "| GRAVITY: " + GRAVITY_CONSTANT);
    }
}
