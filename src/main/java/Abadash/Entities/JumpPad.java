package Abadash.Entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import Abadash.Hitbox;
import Abadash.Sprite;
import static Abadash.Constants.BLOCK_SIZE;
import static Abadash.Constants.FLOOR_HEIGHT;

public class JumpPad extends Entity {
    protected int amount;

    public JumpPad(int x, int y, int amount) {
        this.x = x * BLOCK_SIZE;
        this.y = FLOOR_HEIGHT - (y + 1) * BLOCK_SIZE;
        this.amount = amount;

        for (int i = 0; i < amount; i++) {
            hitbox = new Hitbox(List.of(new Rectangle2D(BLOCK_SIZE * 0.25, 55, BLOCK_SIZE * 0.5 + (amount - 1) * BLOCK_SIZE, BLOCK_SIZE * 0.15)));
        }
        sprite = new Sprite("Abadash/sprites/jumpPad.png");
    }

    @Override
    public void handleHitPlayer(Player player, double deltaTime) {
        player.setOnGround(true);
        player.jump(1500);
        player.update(deltaTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < amount; i++) {
            sprite.render(gc, x + i * BLOCK_SIZE, y);
        }
    }
    


}
