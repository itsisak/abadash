package Abadash;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Abadash.Entities.*;

public class Map {
    private List<Entity> entities = new ArrayList<>();
    private double goalPos;

    public Map(String map) {
        String mapStr = new String();
        // System.out.println(map);
        Path filePath = Path.of("src/main/resources/Abadash/maps/" + map + ".json");
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
                case "spike":
                    entities.add(new Spike(entityInfo.getInt("x"), entityInfo.getInt("y"), entityInfo.getInt("amount"), Spike.Direction.valueOf(entityInfo.getString("direction"))));;
                    break;
                case "jumpPad":
                    entities.add(new JumpPad(entityInfo.getInt("x"), entityInfo.getInt("y"), entityInfo.getInt("amount")));
                    break;
                case "goal":
                    entities.add(new Goal(entityInfo.getInt("x")));
                    goalPos = entityInfo.getInt("x");
                    break;
            }
        }
        entities.add(new Floor());
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public double getGoalPos() {
        return goalPos;
    }

}
