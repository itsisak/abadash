package Abadash.Entities;


import static Abadash.Constants.FLOOR_HEIGHT;
import static Abadash.Constants.BLOCK_SIZE;

import javafx.scene.image.Image;

public class Floor extends Block {
    public Floor() {
        super(0, -1, 3000, 1);
        sprite = new Image("Abadash/sprites/triangle.png");

    }
}
