package Abadash.Controllers;

import Abadash.Camera;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Abadash.Entities.*;
import Abadash.Map;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import static Abadash.Constants.*;

public class GameController {
    @FXML private Pane gamePane;
    @FXML private Canvas canvas;
    @FXML private ImageView menuBtn;
    @FXML private HBox attemptTextBox;
    @FXML private Text attemptText;
    @FXML private VBox debugBox;
    @FXML private Text debugFramerate;
    @FXML private Text debugVelocityY;
    @FXML private Text debugProgress;

    private ViewController viewController;
    private InputManager inputManager;
    private AnimationTimer animationTimer;
    private Player player;
    private List<Entity> entities;
    private String whichMap;
    private Camera camera;
    private int attempts;
    private long startTime;
    private double goalPos;
    private static boolean hasWon;
    private double colorValue = 0.01;

    protected GameController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void initialize() {
        inputManager = new InputManager();

        camera = new Camera(-SCENE_WIDTH/4, 0);

        whichMap = "A3";
        gamePane.getStyleClass().add(whichMap);
        debugBox.setVisible(DEBUG_MODE);

        // Setting variables defined in Constants
        canvas.setWidth(SCENE_WIDTH);
        canvas.setHeight(SCENE_HEIGHT);
        menuBtn.setLayoutX(SCENE_WIDTH - menuBtn.getFitWidth() - 20);
        attemptTextBox.setPrefWidth(SCENE_WIDTH);

        // Event handling
        menuBtn.setOnMouseClicked(event -> viewController.changeView("Menu"));
        gamePane.setOnKeyPressed(event -> inputManager.handleKeyPress(event));
        gamePane.setOnKeyReleased(event -> inputManager.handleKeyRelease(event));

        animationTimer = new AnimationTimer() {
            long lastFrameTime = System.nanoTime();

            public void handle(long currentTime) {
                double deltaTime = (currentTime - lastFrameTime) / 1000000000.0;
                lastFrameTime = currentTime;

                debugFramerate.setText("FPS: " + Math.round(1d / deltaTime));
                debugVelocityY.setText("VelocityY: " + (Math.round(player.getVelocityY())));
                debugProgress.setText("Progress: " + getProgressPercent() + "%");
                update(deltaTime);
                render(canvas.getGraphicsContext2D());
            }
        };


        loadEntities();
    }
    private double deathTimer = 0;
    private boolean hasStarted = false;
    private void update(double deltaTime) {
        if (hasWon) {
            viewController.changeView("Menu");
            hasWon = false;
        }
        if(!hasStarted) {
            hasStarted = true;
            return;
        }
        if (inputManager.isPressed(KeyCode.ESCAPE)) {
            viewController.changeView("Menu");
        }
        if (inputManager.isPressed(KeyCode.SPACE) || inputManager.isPressed(KeyCode.UP)) {
            player.jump();
        }

        for (Entity entity : entities) {
            if (entity != player) {
                //entity.setX(entity.getX() - VELOCITY_X * deltaTime);
                if (entity.collidesWith(player)) {
                    entity.handleHitPlayer(player, deltaTime);
                }
            }
            entity.update(deltaTime);
        }

        if (inputManager.isClicked(KeyCode.R)) {
            restart();
        }

        if (player.isDead()) {
            camera.slowDown(deltaTime);
            deathTimer+=deltaTime;
            if (deathTimer > 1) restart();
        }
        camera.update(deltaTime, player, goalPos);
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Background
        if (colorValue >= 360)
            colorValue = 0;
        Stop[] stops = new Stop[] { new Stop(0, Color.hsb(colorValue, 1, .6, 1)), new Stop(1, Color.hsb(colorValue, 1, 1, 1))};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 0.75, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(lg1);
        gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
        switch (whichMap) {
            case "A3" -> colorValue += 0.02;
            case "Megalovania" -> colorValue += 1;
            default -> colorValue += 0.2;
        }

        camera.renderBegin(gc);
        entities.forEach(e -> e.render(gc, camera));
        if (DEBUG_MODE)
            entities.forEach(e -> e.renderDebug(gc));
        camera.renderEnd(gc);
    }

    private void loadEntities() {
        entities = new ArrayList<>();
        player = new Player(0, 1);
        Map map = new Map(whichMap);
        goalPos = map.getGoalPos() * BLOCK_SIZE;

        entities.add(player);
        for (Entity e : map.getEntities()) {
            entities.add(e);
        }
    }

    protected void startGame() {
        AudioManager.getInstance().playAudio(whichMap);
        animationTimer.start();
        startTime = System.nanoTime();
        attempts = 0;
        camera = new Camera(-SCENE_WIDTH/4, 0);

        gamePane.getStyleClass().clear();
        gamePane.getStyleClass().add(whichMap);
        deathTimer = 0;
        loadEntities();
    }

    protected void stopGame() {
        animationTimer.stop();
        AudioManager.getInstance().stopAudio(whichMap);
    }

    protected void restart() {
        deathTimer = 0;
        AudioManager.getInstance().stopAudio("death");
        // save progress
        int percent = getProgressPercent();
        if (percent >= 100) 
            percent = 99;
        if (hasWon)
            percent = 100;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Path.of("src/main/resources/Abadash/logs/" + whichMap +".txt").toString(), true));
            bufferedWriter.write(percent + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // restart
        camera = new Camera(-SCENE_WIDTH/4, 0);
        startTime = System.nanoTime();
        hasWon = false;
        attempts++;
        attemptText.setText("ATTEMPT " + attempts);
        loadEntities();
        AudioManager.getInstance().restartAudio(whichMap);
    }

    private int getProgressPercent() {
        double elapsedTime = (startTime - System.nanoTime()) / 1000000000.0;
        double progress = -elapsedTime * VELOCITY_X + SCENE_WIDTH / 4;
        int percent = (int) (progress / goalPos * 100);
        return percent;
    }

    protected void setWhichMap(String whichMap) {
        this.whichMap = whichMap;
    }

    public static void setHasWon(boolean b) {
        hasWon = b;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void test() {
        System.out.println("TEST");
    }
}
