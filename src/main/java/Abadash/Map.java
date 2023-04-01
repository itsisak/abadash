package Abadash;

import Abadash.Entities.Block;
import Abadash.Entities.Entity;
import Abadash.Entities.Floor;
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
        String mapStr = new String();
        Path filePath = Path.of("src/main/resources/Abadash/maps/" + map);
        try {
            mapStr = Files.readString(filePath);
        } catch (Exception e) {
            System.out.println(e);
        }
        JSONArray mapArr = new JSONObject(mapStr).getJSONArray("entities");
        for (int i = 0; i < mapArr.length(); i++) {
            JSONObject entityInfo = mapArr.getJSONObject(i);
            switch (entityInfo.getString("type")) {
                case "block":
                    entities.add(new Block(entityInfo.getInt("x"), entityInfo.getInt("y"), entityInfo.getInt("width"), entityInfo.getInt("height")));
                    break;
            }
        }
        entities.add(new Floor());
    }

    public List<Entity> getEntities() {
        return entities;
    }

}
