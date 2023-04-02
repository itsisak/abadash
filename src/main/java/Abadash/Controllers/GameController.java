package Abadash.Controllers;

import Abadash.Constants;
import Abadash.Entities.*;
import Abadash.Map;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static Abadash.Constants.*;

public class GameController {
    private InputManager inputManager;
    private Player player;
    private List<Entity> entities;
    private int attempts = 0;

    @FXML
    private Canvas canvas;

    // @FXML
    // private Text attemptTXT;

    @FXML
    private BorderPane menuContainer;

    private Pane menuPane;

    // @FXML
    public void initialize() {
        // attemptTXT.toFront();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Abadash/Menu.fxml"));
        try {
            menuPane = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
            menuPane = null;
        }
        menuContainer.setCenter(menuPane);
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

    private void loadEntities() {
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

    public void restart() {
        attempts++;
        loadEntities();

        System.out.println("Attempt: " + attempts);
    }


    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(e -> e.render(gc));
        if (DEBUG_MODE) entities.forEach(e -> e.renderDebug(gc));
    }
}
