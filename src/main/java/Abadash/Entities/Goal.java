package Abadash.Entities;

import Abadash.Sprite;
import Abadash.Hitbox;
import static Abadash.Constants.FLOOR_HEIGHT;
import static Abadash.Constants.BLOCK_SIZE;

import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Goal extends Entity {
    public Goal(int x) {
        this.x = x * BLOCK_SIZE;
        y = 0;
        sprite = new Sprite("Abadash/sprites/goal.png");
        hitbox = new Hitbox(List.of(new Rectangle2D(BLOCK_SIZE * 0.8, 0, BLOCK_SIZE * 0.2, FLOOR_HEIGHT)));
    }

    @Override
    public void handleHitPlayer(Player player, double deltaTime) {
        player.kill();
        System.out.println("YOU WON");
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < FLOOR_HEIGHT / BLOCK_SIZE; i++) {
                sprite.render(gc, x, y + i * BLOCK_SIZE);
        }
    }
}
