package Abadash.Controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class InputManager {
    // private Set<KeyCode> pressedKeys;
    enum state {
        RELEASED,
        CLICKED,
        HELD
    }

    private Map<KeyCode, state> clickedState;

    public InputManager() {
        // pressedKeys = new HashSet<>();
        clickedState = new HashMap<>();
    }

    public boolean isPressed(KeyCode keyCode) {
        return clickedState.containsKey(keyCode) && clickedState.get(keyCode) != state.RELEASED;
        // return pressedKeys.contains(keyCode);
    }

    public boolean isClicked(KeyCode keyCode) {
        if (clickedState.containsKey(keyCode) && clickedState.get(keyCode) == state.CLICKED) {
            clickedState.put(keyCode, state.HELD);   
            return true;
        }
        return false;
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        // pressedKeys.add(keyCode);

        if (!clickedState.containsKey(keyCode) || clickedState.get(keyCode) == state.RELEASED) {
            clickedState.put(keyCode, state.CLICKED);
        } 
    }

    public void handleKeyRelease(KeyEvent keyEvent) {
        // pressedKeys.remove(keyEvent.getCode());
        clickedState.put(keyEvent.getCode(), state.RELEASED);
    }

    public Map<KeyCode, state> getClickedState() {
        return clickedState;
    }
}
