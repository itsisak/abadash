package Abadash.Entities;

import Abadash.Camera;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import Abadash.Hitbox;
import Abadash.Sprite;
import static Abadash.Constants.*;

public class Floor extends Entity {
    public Floor() {
        this.x = 0;
        this.y = FLOOR_HEIGHT;

        sprite = new Sprite("Abadash/sprites/floor.png", SCENE_WIDTH, 6);
        sprite.setDynamicOpacity(false);

        double INF = 999999;
        hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, INF * BLOCK_SIZE, INF * BLOCK_SIZE)));
    }

    @Override
    public void handleHitPlayer(Player player, double deltaTime) {
        player.y = FLOOR_HEIGHT - player.hitbox.getHeight();
        player.setOnGround(true);
        player.setVelocityY(0);
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        sprite.render(gc, camera.getX(), FLOOR_HEIGHT, camera);
    }
}
