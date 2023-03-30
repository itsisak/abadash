package Abadash.Entities;

import Abadash.Controllers.GameController;
import Abadash.Hitbox;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Block extends Entity {
    public Block(int xInBlockSpace, int yInBlockSpace, int widthInBlockSpace, int heightInBlockSpace) {
        this.x = xInBlockSpace;
        this.y = yInBlockSpace;
        this.width = widthInBlockSpace;
        this.height = heightInBlockSpace;

        sprite = new Image("Abadash/sprites/block.png");
        hitbox = new Hitbox(List.of(new Rectangle2D(0, 0, widthInBlockSpace * GameController.BLOCK_SIZE, heightInBlockSpace * GameController.BLOCK_SIZE)));
    }

    @Override
    public double getX() {
        return x * GameController.BLOCK_SIZE;
    }

    @Override
    public double getY() {
        return y * GameController.BLOCK_SIZE;
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
                gc.drawImage(sprite,(x + j) * GameController.BLOCK_SIZE, (y + i) * GameController.BLOCK_SIZE, GameController.BLOCK_SIZE, GameController.BLOCK_SIZE);
            }
        }
    }
}
