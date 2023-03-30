package Abadash.Entities;

import Abadash.Controllers.GameController;
import Abadash.Hitbox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.net.URL;
import static Abadash.Constants.SCENE_HEIGHT;
import static Abadash.Constants.BLOCK_SIZE;
import static Abadash.Constants.GRAVITY_CONSTANT;
import static Abadash.Constants.VELOCITY_X;

public class Player extends Entity {
    private double velocityY;
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        width = GameController.BLOCK_SIZE;
        height = GameController.BLOCK_SIZE;

        this.sprite = new Image("Abadash/sprites/player.png");
        this.hitbox = new Hitbox(List.of(new Rectangle2D(0.0, 0.0, width, height)));
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

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
