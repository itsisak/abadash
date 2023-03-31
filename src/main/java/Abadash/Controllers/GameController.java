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
    private Map map;

    @FXML
    private Canvas canvas;
    @FXML
    public void initialize() {
        map = new Map("A3.json");
        entities = map.getEntities();
        player = new Player(0, 10);
        entities.add(player);
        entities.add(new Floor());

        String map = "MAP NOT FOUND";
        Path filePath = Path.of("src/main/resources/Abadash/maps/A3.json");
        try {
          map = Files.readString(filePath);
        } catch (Exception e) {
            System.out.println(e);
        }
        JSONArray mapArr = new JSONObject(map).getJSONArray("entities");
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
        if (inputManager.isPressed(KeyCode.SPACE)) {
            player.jump();
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

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.render(gc);
    }
}
