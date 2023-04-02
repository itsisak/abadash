package Abadash.Entities;

import Abadash.Controllers.GameController;
import Abadash.Hitbox;
import Abadash.ImageGallery;
import Abadash.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static Abadash.Constants.*;

public abstract class Entity {
    protected Sprite sprite = new Sprite("Abadash/icons/default.png");
    protected double x, y;
    protected Hitbox hitbox;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public  void setY(double y) {
        this.y = y;
    }

    public boolean collidesWith(Entity other) {
        return this.hitbox.intersects(other.getX() - getX(), other.getY() - getY(), other.hitbox);
    }

    public void handleHitPlayer(Player player, double deltaTime) {

    }

    public void update(double deltaTime) {

    }

    public void render(GraphicsContext gc) {
        sprite.render(gc, x, y);
    }

    public void renderDebug(GraphicsContext gc) {
        gc.save();
        // Render hitboxes
        gc.setStroke(Color.RED);
        for (Rectangle2D rectangle : hitbox.getRectangles()) {
            gc.strokeRect(x + rectangle.getMinX(), y + rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
        }
        gc.restore();
    }
}
