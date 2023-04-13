package Abadash.Particles;

import Abadash.Entities.Entity;
import Abadash.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class Particle {
    public Sprite sprite;
    public double x, y;
    public boolean dead = false;

    public Particle(Sprite sprite) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }
    public Particle(Sprite sprite, double x, double y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    public Particle(Particle particle) {
        this.sprite = new Sprite(particle.sprite);
        this.x = particle.x;
        this.y = particle.y;
    }

    public void render(GraphicsContext gc) {
        sprite.render(gc, x, y);
    }
}
