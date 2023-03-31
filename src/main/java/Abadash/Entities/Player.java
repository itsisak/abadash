package Abadash.Entities;


import Abadash.Hitbox;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.List;

import static Abadash.Constants.*;

public class Player extends Entity {
    private double velocityY;
    private boolean onGround = false;
    public Player(double x, double y) {
        this.x = x + SCENE_WIDTH / 2;
        this.y = y + FLOOR_HEIGHT;
        width = BLOCK_SIZE;
        height = BLOCK_SIZE;

        this.sprite = new Image("Abadash/sprites/abakule.png");
        this.hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, width, height)));
    }

    @Override
    public void update(double deltaTime) {
        velocityY += GRAVITY_CONSTANT * deltaTime;
        y += velocityY * deltaTime;
    }

    public void jump() {
        System.out.println(onGround);
        if (onGround) {
            velocityY = -250;
        }
        onGround = false;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    public boolean isOnGround() {
        return onGround;
    }
}
