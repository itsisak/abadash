package Abadash.Entities;


import static Abadash.Constants.FLOOR_HEIGHT;
import static Abadash.Constants.BLOCK_SIZE;

import Abadash.Sprite;
import javafx.scene.image.Image;

public class Floor extends Block {
    public Floor() {
        super(0, -1, 3000, 1);
        sprite = new Sprite("Abadash/sprites/triangle.png");
    }
}
