package Abadash.Controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class InputManager {
    // private Set<KeyCode> pressedKeys;
    private Map<KeyCode, Integer> clickedState;

    public InputManager() {
        // pressedKeys = new HashSet<>();
        clickedState = new HashMap<>();
    }

    public boolean isPressed(KeyCode keyCode) {
        return clickedState.containsKey(keyCode) && clickedState.get(keyCode) != 0;
        // return pressedKeys.contains(keyCode);
    }

    public boolean isClicked(KeyCode keyCode) {
        if (clickedState.containsKey(keyCode) && clickedState.get(keyCode) == 1) {
            clickedState.put(keyCode, 2);   
            return true;
        }
        return false;
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        // pressedKeys.add(keyCode);

        if (!clickedState.containsKey(keyCode) || clickedState.get(keyCode) == 0) {
            clickedState.put(keyCode, 1);
        } 
    }

    public void handleKeyRelease(KeyEvent keyEvent) {
        // pressedKeys.remove(keyEvent.getCode());
        clickedState.put(keyEvent.getCode(), 0);
    }
}
