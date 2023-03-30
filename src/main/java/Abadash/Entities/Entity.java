package Abadash.Entities;

import Abadash.Controllers.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected Image sprite = new Image("Abadash/icons/default.png");
    protected double x, y;
    protected double width = GameController.BLOCK_SIZE, height = GameController.BLOCK_SIZE;

    public void update(double deltaTime) {

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, width, height);
    }
}
