package Abadash.Entities;

import Abadash.Hitbox;
import Abadash.ImageGallery;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static Abadash.Constants.BLOCK_SIZE;
import static Abadash.Constants.FLOOR_HEIGHT;

public class Spike extends Entity {
    protected int amount;
    public Spike(int x, int y, int amount) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - (y + 1) * BLOCK_SIZE;

        this.amount = amount;
        width = amount * BLOCK_SIZE;
        height = BLOCK_SIZE;

        List<Rectangle2D> hitboxRectangles = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            hitboxRectangles.add(new Rectangle2D(3 + i*BLOCK_SIZE, 18, 26, 14));
            hitboxRectangles.add(new Rectangle2D(10 + i*BLOCK_SIZE, 6, 12, 25));
        }
        hitbox = new Hitbox(hitboxRectangles);

        sprite = ImageGallery.getInstance().load("Abadash/sprites/spike.png");
    }

    @Override
    public void handleHitPlayer(Player player) {
        player.kill();
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < amount; i++) {
            gc.drawImage(sprite, x + i * BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
        }
    }
}
