package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

import Abadash.Entities.*;
import Abadash.Map;
import static Abadash.Constants.*;

public class GameController {
    @FXML private Pane gamePane;
    @FXML private Canvas canvas;
    @FXML private Button menuBtn;
    @FXML private Text attemptText;

    private InputManager inputManager;
    private AnimationTimer animationTimer;
    private ViewController viewController;
    private Player player;
    private List<Entity> entities;
    private int attempts;

    public GameController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void initialize() {
        inputManager = new InputManager();

        canvas.setWidth(SCENE_WIDTH);
        canvas.setHeight(SCENE_HEIGHT);
        menuBtn.setPrefWidth(150);
        menuBtn.setLayoutX(SCENE_WIDTH - menuBtn.getPrefWidth() - 20);
        menuBtn.setOnAction(event -> viewController.changeView("Menu"));
        gamePane.requestFocus();
        gamePane.setOnKeyPressed(event -> inputManager.handleKeyPress(event));
        gamePane.setOnKeyReleased(event -> inputManager.handleKeyRelease(event));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        final long startTime = System.nanoTime();
        animationTimer = new AnimationTimer() {
            long lastFrameTime = startTime;

            public void handle(long currentTime) {
                double deltaTime = (currentTime - lastFrameTime) / 1000000000.0;
                lastFrameTime = currentTime;

                update(deltaTime);
                render(gc);
            }
        };

        animationTimer.start();
        loadEntities();
    }
    
    public void update(double deltaTime) {
        if (inputManager.isPressed(KeyCode.SPACE)) {
            player.jump();
        }
        for (Entity entity : entities) {
            if (entity != player) {
                entity.setX(entity.getX() - VELOCITY_X * deltaTime);
                if (entity.collidesWith(player)) {
                    entity.handleHitPlayer(player, deltaTime);
                }
            }
            entity.update(deltaTime);
        }
        if (inputManager.isClicked(KeyCode.R) || player.isDead()) {
            restart();
        }
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(e -> e.render(gc));
        if (DEBUG_MODE)
            entities.forEach(e -> e.renderDebug(gc));
    }

    private void loadEntities() {
        entities = new ArrayList<>();
        player = new Player(0, 1);
        Map map = new Map("A3.json");

        for (Entity e : map.getEntities()) {
            entities.add(e);
        }
        entities.add(player);
    }

    public void startGame() {
        animationTimer.start();
        attempts = 0;
        player.kill();
    }

    public void stopGame() {
        animationTimer.stop();
    }

    public void restart() {
        attempts++;
        attemptText.setText("Attempt: " + attempts);
        loadEntities();
    }
}
