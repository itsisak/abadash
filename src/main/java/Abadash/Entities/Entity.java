package Abadash.Entities;

public class Entity {
    // Position of entity
    protected double xPos, yPos;
    // Default dimensions
    protected double width, height = 20;

    public void hit() {
        System.out.println("Hit Entity");
    }
}
