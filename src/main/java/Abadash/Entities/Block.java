package Abadash.Entities;

import Abadash.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

import java.util.List;

import Abadash.Hitbox;
import Abadash.Sprite;

import static Abadash.Constants.*;

public class Block extends Entity {
    protected int width, height;
    public Block(int x, int y, int width, int height) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - (y + 1) * BLOCK_SIZE;
        this.width = width;
        this.height = height;

        sprite = new Sprite("Abadash/sprites/block.png");
        hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, width * BLOCK_SIZE, height * BLOCK_SIZE)));
    }

    @Override
    public void handleHitPlayer(Player player, double deltaTime) {
        player.setVelocityY(0.0);
        player.setY(getY() - player.hitbox.getHeight());
        player.setOnGround(true);
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sprite.render(gc, x + j * BLOCK_SIZE, y + i * BLOCK_SIZE, camera);
            }
        }
    }
}
