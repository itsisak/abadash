package Abadash.Controllers;

import Abadash.Entities.Block;
import Abadash.Entities.Entity;
import Abadash.Entities.Player;
import Abadash.Entities.Floor;
import Abadash.Map;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Path;

import static Abadash.Constants.*;

public class GameController {
    private InputManager inputManager;
    private Player player;
    private List<Entity> entities;
    private int attempts = 0;

    @FXML
    private Canvas canvas;

    @FXML
    public void initialize() {
        inputManager = new InputManager();
        loadEntities();
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

    public void loadEntities() {
        entities = new ArrayList<>();
        
        player = new Player(0, 1);
        entities.add(player);
        
        Map map = new Map("A3.json");
        for (Entity e : map.getEntities())
            entities.add(e);
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
        if (inputManager.isPressed(KeyCode.SPACE)) {
            player.jump();
        }
        if (inputManager.isClicked(KeyCode.R)) {
            restart();
        }
        for (Entity entity : entities) {
            if (entity != player) {
                entity.setX(entity.getX() - VELOCITY_X * deltaTime);
                if (entity.collidesWith(player)) {
                    entity.handleHitPlayer(player);
                }
            }
            entity.update(deltaTime);
        }
    }

    public void restart() {
        attempts++;
        loadEntities();

        System.out.println("Attempt: " + attempts);
    }


    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(e -> e.render(gc));
    }
}
