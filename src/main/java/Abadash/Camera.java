package Abadash;

import javafx.scene.canvas.GraphicsContext;

import static Abadash.Constants.*;

public class Camera {
    private double x, y;
    private double speed;
    private double screenShakeSpeed;
    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
        speed = VELOCITY_X;
    }

    public void slowDown(double deltaTime) {
        if (speed > 0) {
            final double slowDownSpeed = 1400;
            speed -= slowDownSpeed * deltaTime;
        }else speed = 0;
    }

    public void update(double deltaTime, double goalPos) {
        this.x += speed * deltaTime;
        this.x = Math.min(this.x, goalPos - SCENE_WIDTH + BLOCK_SIZE);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void renderBegin(GraphicsContext gc) {
        gc.save();
        gc.translate(-x, -y);
    }
    public void renderEnd(GraphicsContext gc) {
        gc.restore();
    }
}
