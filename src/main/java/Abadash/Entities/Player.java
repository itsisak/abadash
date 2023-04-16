package Abadash.Entities;

import Abadash.*;
import Abadash.Hitbox;
import Abadash.ImageGallery;
import Abadash.Particles.Particle;
import Abadash.Particles.ParticleFade;
import Abadash.Particles.ParticleManager;
import Abadash.Particles.ParticleShrink;
import Abadash.Sprite;
import Abadash.Controllers.AudioManager;
import Abadash.Particles.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import Abadash.Hitbox;
import Abadash.Sprite;
import static Abadash.Constants.*;

public class Player extends Entity {
    private double velocityY;
    private boolean onGround;
    private boolean dead = false;
    private ParticleManager jumpTrail;
    private ParticleManager rollSkid;
    private ParticleManager deathExplosion;
    public Player(double x, double y) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - y * BLOCK_SIZE;

        this.sprite = new Sprite("Abadash/sprites/webkom.png");
        //this.hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, BLOCK_SIZE, BLOCK_SIZE)));
        this.hitbox = new Hitbox(List.of(new Rectangle2D(BLOCK_SIZE * 0.2, 0, BLOCK_SIZE * 0.6, BLOCK_SIZE)));

        Particle dust = new Particle(new Sprite("Abadash/sprites/dust.png", BLOCK_SIZE * 0.4 ,BLOCK_SIZE * 0.4));
        dust.sprite.setOpacity(0.1);
        jumpTrail = new ParticleManager(0.0025, List.of(dust), List.of(new ParticleFade(0.25), new ParticleShrink(50)));
        jumpTrail.setEnableSpawning(false);

        Particle p = new Particle(new Sprite("Abadash/sprites/dust.png", BLOCK_SIZE * 0.5, BLOCK_SIZE * 0.5), 0, 0);
        p.sprite.setOpacity(0.2);
        rollSkid = new ParticleManager(0.1, List.of(p), List.of(new ParticleFade(0.75)));
        rollSkid.setEnableSpawning(true);

        Particle cloud1 = new Particle(new Sprite("Abadash/sprites/dust.png", BLOCK_SIZE, BLOCK_SIZE), 0,-50);
        Particle cloud2 = new Particle(new Sprite("Abadash/sprites/dust.png", BLOCK_SIZE*0.5, BLOCK_SIZE*0.5), 100,0);
        List<Particle> clouds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String sprite = i < 8 ? "2" : "3";
            double size = Math.random() * BLOCK_SIZE/ 2 + BLOCK_SIZE / 2;
            double maxSpeed = 500;
            clouds.add(new Particle(new Sprite("Abadash/sprites/dust" + sprite + ".png", size, size), Math.random() * maxSpeed * 2 - maxSpeed, Math.random() * maxSpeed * 2 - maxSpeed));
        }
        deathExplosion = new ParticleManager(0, clouds, List.of(new ParticleShrink(50), new ParticleFade(1), new ParticleRetard(500)));
        deathExplosion.setEnableSpawning(false);
    }

    @Override
    public void update(double deltaTime) {
        if (!isDead()) {
            velocityY += GRAVITY_CONSTANT * deltaTime;
            y += velocityY * deltaTime;
            x += VELOCITY_X * deltaTime;
        }

        sprite.setAngle(sprite.getAngle() + VELOCITY_X/2 * deltaTime);

        jumpTrail.setSpawnX(x + sprite.getWidth() / 2);
        jumpTrail.setSpawnY(y + sprite.getHeight() / 2);
        jumpTrail.setEnableSpawning(!onGround && !isDead());
        jumpTrail.update(deltaTime);

        rollSkid.setSpawnX(x + sprite.getWidth() / 2);
        rollSkid.setSpawnY(y + sprite.getHeight() / 2);
        rollSkid.setEnableSpawning(onGround && !isDead());
        rollSkid.update(deltaTime);

        deathExplosion.setSpawnX(x + sprite.getWidth() / 2);
        deathExplosion.setSpawnY(y + sprite.getHeight() / 2);
        deathExplosion.update(deltaTime);

        onGround = false;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        jumpTrail.render(gc, camera);
        rollSkid.render(gc, camera);
        if (!isDead()) super.render(gc, camera);
        deathExplosion.render(gc, camera);
    }

    public void jump() {
        jump(JUMP_FORCE);
    }

    public void jump(double velocityY) {
        if (onGround) {
            this.velocityY = -velocityY;
        }
    }

    public void kill() {
        if (!dead) {
            deathExplosion.spawn();
            AudioManager.getInstance().playAudio("death");
        }
        dead = true;
    }

    public void startKill() {
        dead = true;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    public double getVelocityY() {
        return velocityY;
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
