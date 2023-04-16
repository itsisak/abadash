package Abadash.Particles;

import Abadash.Camera;
import Abadash.Sprite;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

import static Abadash.Constants.BLOCK_SIZE;
import static Abadash.Constants.VELOCITY_X;

// TODO: Add Camera class
// TODO: Add Renderable class
// TODO: Add ParticleManager

public class ParticleManager {
    private List<Particle> templateParticles;
    private List<Particle> particles;
    private List<ParticleEffect> particleEffects;

    private boolean enableSpawning = true;
    private double spawnInterval, timer;
    private double spawnX, spawnY;

    public ParticleManager(double spawnInterval, List<Particle> templateParticles, List<ParticleEffect> particleEffects) {
        this.spawnInterval = spawnInterval;

        this.particles = new ArrayList<>();
        this.templateParticles = templateParticles;
        this.particleEffects = particleEffects;
    }

    public void update(double deltaTime) {
        if (enableSpawning) timer += deltaTime;
        if (spawnInterval > 0 && timer >= spawnInterval) {
            spawn();
            timer = 0;
        }

        particleEffects.forEach(particleEffect -> particleEffect.update(deltaTime, particles));
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).x += particles.get(i).vx * deltaTime;
            particles.get(i).y += particles.get(i).vy * deltaTime;
            if (particles.get(i).dead) {
                particles.remove(i);
                i--;
            }
        }
    }

    public void spawn() {
        for (Particle particle : templateParticles) {
            particles.add(new Particle(particle));
            particles.get(particles.size()-1).x = spawnX + particle.x;
            particles.get(particles.size()-1).y = spawnY + particle.y;
        }
    }

    public void render(GraphicsContext gc, Camera camera) {
        particles.forEach(particle -> particle.render(gc, camera));
    }

    public double getSpawnX() {
        return spawnX;
    }

    public double getSpawnY() {
        return spawnY;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public void setSpawnY(double spawnY) {
        this.spawnY = spawnY;
    }
    public void setEnableSpawning(boolean enableSpawning) {
        this.enableSpawning = enableSpawning;
    }
    public boolean isSpawning() {
        return enableSpawning;
    }
}
