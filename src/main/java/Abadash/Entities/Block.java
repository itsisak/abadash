package Abadash.Entities;

import Abadash.Hitbox;
import Abadash.ImageGallery;
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
    public Block(double x, double y, int width, int height) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - (y + 1) * BLOCK_SIZE;
        this.width = width * BLOCK_SIZE;
        this.height = height * BLOCK_SIZE;

        sprite = ImageGallery.getInstance().load("Abadash/sprites/block.png");
        hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, this.width, this.height)));
    }

    @Override
    public void handleHitPlayer(Player player) {
        player.setVelocityY(0.0);
        player.setY(getY() - player.getHeight());
        player.setOnGround(true);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < height / BLOCK_SIZE; i++) {
            for (int j = 0; j < width / BLOCK_SIZE; j++) {
                gc.drawImage(sprite, x + j * BLOCK_SIZE, y + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }
}
