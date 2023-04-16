package Abadash.Entities;

import Abadash.Camera;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

import Abadash.Hitbox;
import Abadash.Sprite;
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
                    hitboxRectangles.add(new Rectangle2D((3/32.0 + i)*BLOCK_SIZE, 18/32.0*BLOCK_SIZE, 26/32.0*BLOCK_SIZE, 14/32.0*BLOCK_SIZE));
                    hitboxRectangles.add(new Rectangle2D((10/32.0 + i)*BLOCK_SIZE, 6/32.0*BLOCK_SIZE, 12/32.0*BLOCK_SIZE, 25/32.0*BLOCK_SIZE));
                    break;
                case RIGHT:
                    hitboxRectangles.add(new Rectangle2D(0, (3/32.0 + i)*BLOCK_SIZE, 14/32.0*BLOCK_SIZE, 26/32.0*BLOCK_SIZE));
                    hitboxRectangles.add(new Rectangle2D(0, (10/32.0 + i)*BLOCK_SIZE, 25/32.0*BLOCK_SIZE, 12/32.0*BLOCK_SIZE));
                    break;
                case DOWN:
                    hitboxRectangles.add(new Rectangle2D((3/32.0 + i)*BLOCK_SIZE, 0/32.0*BLOCK_SIZE, 26/32.0*BLOCK_SIZE, 14/32.0*BLOCK_SIZE));
                    hitboxRectangles.add(new Rectangle2D((10/32.0 + i)*BLOCK_SIZE, 0/32.0*BLOCK_SIZE, 12/32.0*BLOCK_SIZE, 25/32.0*BLOCK_SIZE));
                    break;
                case LEFT:
                    hitboxRectangles.add(new Rectangle2D(18/32.0*BLOCK_SIZE, (3/32.0 + i)*BLOCK_SIZE, 14/32.0*BLOCK_SIZE, 26/32.0*BLOCK_SIZE));
                    hitboxRectangles.add(new Rectangle2D(6/32.0*BLOCK_SIZE, (10/32.0 + i)*BLOCK_SIZE, 25/32.0*BLOCK_SIZE, 12/32.0*BLOCK_SIZE));
                    break;
            }
        }
        hitbox = new Hitbox(hitboxRectangles);

        sprite = new Sprite("Abadash/sprites/spike.png");
        sprite.setAngle(90 * direction.ordinal());
    }

    @Override
    public void handleHitPlayer(Player player, double deltaTime) {
        player.kill();
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        for (int i = 0; i < amount; i++) {
            double x = direction.ordinal() % 2 == 0 ? this.x + i * BLOCK_SIZE : this.x;
            double y = direction.ordinal() % 2 == 0 ? this.y : this.y + i * BLOCK_SIZE;
            sprite.render(gc, x, y, camera);
        }
    }
}
