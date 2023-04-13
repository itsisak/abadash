package Abadash.Particles;

import java.util.List;

public class ParticleShrink implements ParticleEffect {
    private final double speed;

    public ParticleShrink(double speed) {
        this.speed = speed;
    }


    public void update(double deltaTime, List<Particle> particles) {
        particles.forEach((particle) -> {
            particle.sprite.setWidth(particle.sprite.getWidth() - speed * deltaTime);
            particle.sprite.setHeight(particle.sprite.getHeight() - speed * deltaTime);

            if (particle.sprite.getWidth() <= 0 || particle.sprite.getHeight() <= 0) {
                particle.dead = true;
            }
        });
    }
}
