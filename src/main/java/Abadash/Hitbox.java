package Abadash;

import javafx.geometry.Rectangle2D;

import java.util.List;

public class Hitbox {
    private final List<Rectangle2D> rectangles;
    private final double minx, miny, maxx, maxy;
    
    public Hitbox(List<Rectangle2D> rectangles) {
        this.rectangles = rectangles;

        double minx = 0, miny = 0, maxx = 0, maxy = 0;
        if (rectangles.size() > 0) {
            minx = rectangles.get(0).getMinX();
            miny = rectangles.get(0).getMinY();
            maxx = rectangles.get(0).getMaxX();
            maxy = rectangles.get(0).getMaxY();

            for (int i = 1; i < rectangles.size(); i++) {
                minx = Math.min(minx, rectangles.get(i).getMinX());
                miny = Math.min(miny, rectangles.get(i).getMinY());
                maxx = Math.max(maxx, rectangles.get(i).getMaxX());
                maxy = Math.max(maxy, rectangles.get(i).getMaxY());
            }
        }
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
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

    public double getMinX() {
        return minx;
    }

    public double getMinY() {
        return miny;
    }

    public double getMaxX() {
        return maxx;
    }

    public double getMaxY() {
        return maxy;
    }

    public double getWidth() {
        return maxx - minx;
    }

    public double getHeight() {
        return maxy - miny;
    }

    public List<Rectangle2D> getRectangles() {
        return rectangles;
    }
}
