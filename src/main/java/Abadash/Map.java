package Abadash;

import Abadash.Entities.Block;
import Abadash.Entities.Entity;
import Abadash.Entities.Player;
import javafx.scene.canvas.GraphicsContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Entity> entities = new ArrayList<>();

    public Map(String map) {
        Path filePath = Path.of("src/main/resources/Abadash/maps/" + map);
        try {
            String mapStr = Files.readString(filePath);
            JSONArray mapArr = new JSONObject(mapStr).getJSONArray("entities");
            for (int i = 0; i < mapArr.length(); i++) {
                JSONObject entityInfo = mapArr.getJSONObject(i);
                switch (entityInfo.getString("type")) {
                    case "block":
                        entities.add(new Block(entityInfo.getInt("x"), entityInfo.getInt("y"), entityInfo.getInt("width"), entityInfo.getInt("height")));
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void add(Player player) {
        entities.add(player);
    }

    public void render(GraphicsContext gc) {
        entities.forEach(e -> e.render(gc));
    }

}
