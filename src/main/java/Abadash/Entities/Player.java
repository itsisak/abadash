package Abadash.Entities;

import Abadash.Hitbox;
import Abadash.ImageGallery;
import Abadash.Particles.Particle;
import Abadash.Particles.ParticleFade;
import Abadash.Particles.ParticleManager;
import Abadash.Particles.ParticleShrink;
import Abadash.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.geometry.Rectangle2D;

import java.util.List;

import Abadash.Hitbox;
import Abadash.Sprite;
import static Abadash.Constants.*;

public class Player extends Entity {
    private double velocityY;
    private boolean onGround;
    private boolean dead = false;

    private ParticleManager jumpTrail;
    public Player(double x, double y) {
        this.x = x * BLOCK_SIZE + SCENE_WIDTH / 4;
        this.y = FLOOR_HEIGHT - y * BLOCK_SIZE;

        this.sprite = new Sprite("Abadash/sprites/webkom.png");
        this.hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, BLOCK_SIZE, BLOCK_SIZE)));

        Particle dust = new Particle(new Sprite("Abadash/sprites/dust.png", BLOCK_SIZE * 0.4 ,BLOCK_SIZE * 0.4));
        dust.sprite.setOpacity(0.1);
        jumpTrail = new ParticleManager(0.0025, List.of(dust), List.of(new ParticleFade(0.25), new ParticleShrink(50)));
        jumpTrail.setEnableSpawning(false);
    }

    @Override
    public void update(double deltaTime) {
        velocityY += GRAVITY_CONSTANT * deltaTime;
        y += velocityY * deltaTime;

        sprite.setAngle(sprite.getAngle() + VELOCITY_X/2 * deltaTime);

        jumpTrail.setSpawnX(x + sprite.getWidth() / 2);
        jumpTrail.setSpawnY(y + sprite.getHeight() / 2);
        jumpTrail.update(deltaTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        jumpTrail.render(gc);
        super.render(gc);
    }

    public void jump() {
        jump(JUMP_FORCE);
    }

    public void jump(double velocityY) {
        if (onGround) {
            this.velocityY = -velocityY;
            jumpTrail.setEnableSpawning(true);
        }
    }

    public void kill() {
        dead = true;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        if (onGround) jumpTrail.setEnableSpawning(false);
    }
    public boolean isOnGround() {
        return onGround;
    }
    public boolean isDead() {
        return dead;
    }
}
