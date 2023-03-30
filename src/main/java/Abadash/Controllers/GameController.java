package Abadash.Controllers;

import Abadash.Entities.Block;
import Abadash.Entities.Entity;
import Abadash.Entities.Player;
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

public class GameController {
    private InputManager inputManager;
    private Player player;
    private List<Entity> entities;

    @FXML
    private Canvas canvas;
    @FXML
    public void initialize() {
        entities = new ArrayList<>();
        player = new Player(0, 0);
        entities.add(player);
        entities.add(new Block(0, 3, 5, 1));
        entities.add(new Block(6, 4, 1, 1));
        entities.add(new Block(8, 5, 1, 1));
        entities.add(new Block(10, 6, 1, 1));
        entities.add(new Block(12, 7, 5, 1));

        String map = "MAP NOT FOUND";
        Path filePath = Path.of("src/main/resources/Abadash/maps/A3.json");
        try {
          map = Files.readString(filePath);
        } catch (Exception e) {
            System.out.println(e);
        }
        JSONArray mapArr = new JSONObject(map).getJSONArray("entities");
        System.out.println(mapArr);
        for (int i = 0; i < mapArr.length(); i++) {
           JSONObject entityInfo =  mapArr.getJSONObject(i);
           switch (entityInfo.getString("type")) {
               case "block":
                    entities.add(new Block(entityInfo.getInt("x"), entityInfo.getInt("y"), entityInfo.getInt("width"), entityInfo.getInt("height")));
                    break;
            }
        }



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
        for (Entity entity : entities) {
            if (entity != player && entity.collidesWith(player)) {
                entity.handleHitPlayer(player);
            }
            entity.update(deltaTime);
        }
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity entity : entities) {
            entity.render(gc);
        }
    }
}
