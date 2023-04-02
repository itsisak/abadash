package Abadash.Entities;

import Abadash.Hitbox;
import Abadash.ImageGallery;
import Abadash.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static Abadash.Constants.*;

public class Block extends Entity {
    protected int width, height;
    public Block(double x, double y, int width, int height) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - (y + 1) * BLOCK_SIZE;
        this.width = width;
        this.height = height;

        sprite = new Sprite("Abadash/sprites/block.png");
        hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, width * BLOCK_SIZE, height * BLOCK_SIZE)));
    }

    @Override
    public void handleHitPlayer(Player player) {
        player.setVelocityY(0.0);
        player.setY(getY() - player.hitbox.getHeight());
        player.setOnGround(true);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sprite.render(gc, x + j * BLOCK_SIZE, y + i * BLOCK_SIZE);
            }
        }
    }
}
