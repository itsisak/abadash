package Abadash.Entities;

import Abadash.Controllers.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Block extends Entity {
    public Block(int xInBlockSpace, int yInBlockSpace, int widthInBlockSpace, int heightInBlockSpace) {
        this.x = xInBlockSpace;
        this.y = yInBlockSpace;
        this.width = widthInBlockSpace;
        this.height = heightInBlockSpace;

        sprite = new Image("Abadash/sprites/block.png");
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
