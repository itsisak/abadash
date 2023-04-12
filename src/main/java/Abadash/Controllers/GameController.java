package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

import Abadash.Entities.*;
import Abadash.Map;
import static Abadash.Constants.*;

public class GameController {
    @FXML private Pane gamePane;
    @FXML private Canvas canvas;
    @FXML private ImageView menuBtn;
    @FXML private HBox attemptTextBox;
    @FXML private Text attemptText;

    private ViewController viewController;
    private InputManager inputManager;
    private AnimationTimer animationTimer;
    private Player player;
    private List<Entity> entities;
    private int attempts;
    private String whichMap = "A3"; 

    protected GameController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void initialize() {
        inputManager = new InputManager();

        gamePane.getStyleClass().add(whichMap);

        // Setting variables defined in Constants
        canvas.setWidth(SCENE_WIDTH);
        canvas.setHeight(SCENE_HEIGHT);
        menuBtn.setLayoutX(SCENE_WIDTH - menuBtn.getFitWidth() - 20);
        attemptTextBox.setPrefWidth(SCENE_WIDTH);
        attemptText.setTextAlignment(TextAlignment.CENTER);
        // attemptText.setLayoutX();

        // Event handling
        menuBtn.setOnMouseClicked(event -> viewController.changeView("Menu"));
        gamePane.setOnKeyPressed(event -> inputManager.handleKeyPress(event));
        gamePane.setOnKeyReleased(event -> inputManager.handleKeyRelease(event));

        animationTimer = new AnimationTimer() {
            long lastFrameTime = System.nanoTime();

            public void handle(long currentTime) {
                double deltaTime = (currentTime - lastFrameTime) / 1000000000.0;
                lastFrameTime = currentTime;

                update(deltaTime);
                render(canvas.getGraphicsContext2D());
            }
        };

        animationTimer.start();
        loadEntities();
    }
    
    private void update(double deltaTime) {
        if (inputManager.isPressed(KeyCode.ESCAPE)) {
            viewController.changeView("Menu");
        }
        if (inputManager.isPressed(KeyCode.SPACE) || inputManager.isPressed(KeyCode.UP)) {
            player.jump();
        }
        player.setOnGround(true);
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

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Background
        // Stop[] stops = new Stop[] { new Stop(0, new Color(0, 0.2, 0.2, 1)), new Stop(1, new Color(0, 0.8, 0.8, 1))};
        // LinearGradient lg1 = new LinearGradient(0, 0, 0, 0.75, true, CycleMethod.NO_CYCLE, stops);
        // gc.setFill(lg1);
        // gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
        
        entities.forEach(e -> e.render(gc));
        if (DEBUG_MODE)
            entities.forEach(e -> e.renderDebug(gc));
    }

    private void loadEntities() {
        entities = new ArrayList<>();
        player = new Player(0, 10);
        Map map = new Map(whichMap);

        entities.add(player);
        for (Entity e : map.getEntities()) {
            entities.add(e);
        }
    }

    protected void startGame() {
        animationTimer.start();
        attempts = 0;
        player.kill();

        gamePane.getStyleClass().clear();
        gamePane.getStyleClass().add(whichMap);
    }

    protected void stopGame() {
        animationTimer.stop();
    }

    protected void restart() {
        attempts++;
        attemptText.setText("ATTEMPT " + attempts);
        loadEntities();
    }

    protected void setWhichMap(String whichMap) {
        this.whichMap = whichMap;
    }
}
