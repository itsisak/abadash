package Abadash.Particles;

import Abadash.Entities.Entity;

import java.util.List;

public interface ParticleEffect {
    public void update(double deltaTime, List<Particle> particles);
}
