package Abadash.Entities;

import Abadash.Controllers.GameController;
import Abadash.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected Image sprite;
    protected double x, y;
    protected double width = GameController.BLOCK_SIZE, height = GameController.BLOCK_SIZE;
    protected Hitbox hitbox;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public void setX(double x) {
        this.x = x;
    }
    public  void setY(double y) {
        this.y = y;
    }

    public boolean collidesWith(Entity other) {
        return this.hitbox.intersects(getX() - other.getX(), getY() - other.getY(), other.hitbox);
    }

    public void handleHitPlayer(Player player) {

    }

    public void update(double deltaTime) {

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, width, height);
    }
}
