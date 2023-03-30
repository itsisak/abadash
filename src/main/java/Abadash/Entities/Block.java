package Abadash.Entities;

public class Block extends Entity {

    // Constructors
    public Block() {

    }

    public Block(double width, double height) {
        this.width = width;
        this.height = height;
    }

    
    @Override
    public void hit() {
        System.out.println("Hit Block");
    }
}
