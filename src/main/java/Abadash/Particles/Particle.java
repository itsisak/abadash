package Abadash.Particles;

import Abadash.Camera;
import Abadash.Entities.Entity;
import Abadash.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class Particle {
    public Sprite sprite;
    public double x, y;
    public double vx, vy;
    public boolean dead = false;

    public Particle(Sprite sprite) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }
    public Particle(Sprite sprite, double vx, double vy) {
        this.sprite = sprite;
        this.vx = vx;
        this.vy = vy;
    }

    public Particle(Particle particle) {
        this.sprite = new Sprite(particle.sprite);
        this.x = particle.x;
        this.y = particle.y;
        this.vx = particle.vx;
        this.vy = particle.vy;
    }

    public void render(GraphicsContext gc, Camera camera) {
        sprite.render(gc, x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, camera);
    }
}
