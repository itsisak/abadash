package Abadash.Entities;


import Abadash.Hitbox;
import Abadash.ImageGallery;
import Abadash.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.List;

import static Abadash.Constants.*;

public class Player extends Entity {
    private double velocityY;
    private boolean onGround;
    private boolean dead = false;
    public Player(double x, double y) {
        this.x = x + SCENE_WIDTH / 4;
        this.y = y + FLOOR_HEIGHT;

        this.sprite = new Sprite("Abadash/sprites/webkom.png");
        this.hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, BLOCK_SIZE, BLOCK_SIZE)));
    }

    @Override
    public void update(double deltaTime) {
        velocityY += GRAVITY_CONSTANT * deltaTime;
        y += velocityY * deltaTime;
        onGround = false;

        sprite.setAngle(sprite.getAngle() + VELOCITY_X/2 * deltaTime);
    }

    public void jump() {
        jump(JUMP_FORCE);
    }

    public void jump(double velocityY) {
        if (onGround) {
            this.velocityY = -velocityY;
        }
        onGround = false;
    }

    public void kill() {
        dead = true;
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
    public boolean isDead() {
        return dead;
    }
}
