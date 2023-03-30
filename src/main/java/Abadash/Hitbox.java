package Abadash;

import javafx.geometry.Rectangle2D;

import java.util.List;

public class Hitbox {
    private final List<Rectangle2D> rectangles;
    public Hitbox(List<Rectangle2D> rectangles) {
        this.rectangles = rectangles;
    }

    public List<Rectangle2D> getRectangles() {
        return rectangles;
    }

    public boolean intersects(double offsetX, double offsetY, Hitbox other) {
        for (Rectangle2D rectangle : rectangles) {
            for (Rectangle2D otherRectangle : other.getRectangles()) {
                if(rectangle.intersects(otherRectangle.getMinX() + offsetX, otherRectangle.getMinY() + offsetY, otherRectangle.getWidth(), otherRectangle.getHeight()))
                    return true;
            }
        }
        return false;
    }
}
