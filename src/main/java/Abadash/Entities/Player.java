package Abadash.Entities;

import Abadash.Controllers.GameController;
import Abadash.Hitbox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.List;

public class Player extends Entity {
    private static final double GRAVITY_CONSTANT = 1000;
    private static final double VELOCITY_X = 150;
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
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
