package Abadash.Entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static Abadash.Constants.BLOCK_SIZE;

public class Block extends Entity {
    public Block(int x, int y, int width, int height) {
        this.x = x * BLOCK_SIZE;
        this.y = y * BLOCK_SIZE;
        this.width = width * BLOCK_SIZE;
        this.height = height * BLOCK_SIZE;

        sprite = new Image("Abadash/sprites/block.png");
        Shape hitbox = new Rectangle(0, 0, width, height);
    }

    @Override
    public void handleHitPlayer(Player player) {
        player.setVelocityY(0.0);
        player.setY(getY() - player.getHeight());
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gc.drawImage(sprite, x + j * BLOCK_SIZE, y + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }
}
