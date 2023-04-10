package Abadash;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import static Abadash.Constants.*;

public class Sprite {
    private Image img;
    private double width = BLOCK_SIZE;
    private double height = BLOCK_SIZE;
    private double angle = 0;
    private boolean dynamicOpacity = true;

    public Sprite(String imagePath) {
        img = ImageGallery.getInstance().load(imagePath);
    }

    public Sprite(String imagePath, double width, double height) {
        this(imagePath);
        setWidth(width);
        setHeight(height);
    }

    public void render(GraphicsContext gc, double x, double y) {
        gc.save();
        Rotate r = new Rotate(angle, x + width / 2, y + height / 2);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

       if (dynamicOpacity) gc.setGlobalAlpha(calculateOpacity(x));
        gc.drawImage(img, x, y, width, height);
        gc.restore();
    }

    private double calculateOpacity(double x) {
        double centeredX = x + getWidth() / 2;

        if (centeredX > SCENE_WIDTH - FADE_DISTANCE) {
            return (SCENE_WIDTH - centeredX)/FADE_DISTANCE;
        }
        if (centeredX < FADE_DISTANCE) {
            return centeredX/FADE_DISTANCE;
        }

        return 1;
    }

    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setDynamicOpacity(boolean dynamicOpacity) { 
        this.dynamicOpacity = dynamicOpacity; 
    }
}
