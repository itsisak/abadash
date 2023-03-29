package abadash;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.security.Key;

public class GameController {
    @FXML
    private Canvas canvas;

    private InputManager inputManager;

    @FXML
    public void initialize() {
        inputManager = new InputManager();
        run();
    }

    @FXML
    public void handleKeyPress(KeyEvent keyEvent) {
        inputManager.handleKeyPress(keyEvent);
    }

    @FXML
    public void handleKeyRelease(KeyEvent keyEvent) {
        inputManager.handleKeyRelease(keyEvent);
    }

    public void run() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        final long startTime = System.nanoTime();
        new AnimationTimer() {
            long lastFrameTime = startTime;
            public void handle(long currentTime) {
                double deltaTime = (currentTime - lastFrameTime) / 1000000000.0;
                lastFrameTime = currentTime;

                update(deltaTime);
                render(gc);
            }
        }.start();
    }

    public void update(double deltaTime) {
        // do something
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // render something
    }
}
