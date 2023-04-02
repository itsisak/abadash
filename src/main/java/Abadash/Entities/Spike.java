package Abadash.Entities;

import Abadash.Hitbox;
import Abadash.ImageGallery;
import Abadash.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static Abadash.Constants.BLOCK_SIZE;
import static Abadash.Constants.FLOOR_HEIGHT;

public class Spike extends Entity {
    protected int amount;

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
    protected Direction direction;
    public Spike(int x, int y, int amount, Direction direction) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - (y + 1) * BLOCK_SIZE;

        this.amount = amount;
        this.direction = direction;

        List<Rectangle2D> hitboxRectangles = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            switch(direction) {
                case UP:
                    hitboxRectangles.add(new Rectangle2D(3 + i*BLOCK_SIZE, 18, 26, 14));
                    hitboxRectangles.add(new Rectangle2D(10 + i*BLOCK_SIZE, 6, 12, 25));
                    break;
                case RIGHT:
                    hitboxRectangles.add(new Rectangle2D(0, 3 + i*BLOCK_SIZE, 14, 26));
                    hitboxRectangles.add(new Rectangle2D(0, 10 + i*BLOCK_SIZE, 25, 12));
                    break;
                case DOWN:
                    hitboxRectangles.add(new Rectangle2D(3 + i*BLOCK_SIZE, 0, 26, 14));
                    hitboxRectangles.add(new Rectangle2D(10 + i*BLOCK_SIZE, 0, 12, 25));
                    break;
                case LEFT:
                    hitboxRectangles.add(new Rectangle2D(18, 3 + i*BLOCK_SIZE, 14, 26));
                    hitboxRectangles.add(new Rectangle2D(6, 10 + i*BLOCK_SIZE, 25, 12));
                    break;
            }
        }
        hitbox = new Hitbox(hitboxRectangles);

        sprite = new Sprite("Abadash/sprites/spike.png");
        sprite.setAngle(90 * direction.ordinal());
    }

    @Override
    public void handleHitPlayer(Player player) {
        player.kill();
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < amount; i++) {
            double x = direction.ordinal() % 2 == 0 ? this.x + i * BLOCK_SIZE : this.x;
            double y = direction.ordinal() % 2 == 0 ? this.y : this.y + i * BLOCK_SIZE;
            sprite.render(gc, x, y);
        }
    }
}
