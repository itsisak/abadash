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
                if(otherRectangle.getMaxX() > rectangle.getMinX() + offsetX && otherRectangle.getMaxY() > rectangle.getMinY() + offsetY && otherRectangle.getMinX() < rectangle.getMaxX() + offsetX && otherRectangle.getMinY() < rectangle.getMaxY() + offsetY)
                    return true;
            }
        }
        return false;
    }
}
