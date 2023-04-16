package Abadash.Particles;

import java.util.List;

public class ParticleRetard implements ParticleEffect {
    private double retardation;
    public ParticleRetard(double retardation) {
        this.retardation = retardation;
    }

    @Override
    public void update(double deltaTime, List<Particle> particles) {
        particles.forEach((particle) -> {
            particle.vx += -Math.signum(particle.vx) * retardation * deltaTime;
            particle.vy += -Math.signum(particle.vy) * retardation * deltaTime;
        });
    }
}
