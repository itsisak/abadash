package Abadash.Particles;

import java.util.List;

public class ParticleFade implements ParticleEffect {
    private final double speed;

    public ParticleFade(double speed) {
        this.speed = speed;
    }


    public void update(double deltaTime, List<Particle> particles) {
        particles.forEach((particle) -> {
            particle.sprite.setOpacity(particle.sprite.getOpacity() - speed * deltaTime);

            if (particle.sprite.getOpacity() <= 0) {
                particle.dead = true;
            }
        });
    }
}
