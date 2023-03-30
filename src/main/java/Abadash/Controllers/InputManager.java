package Abadash.Controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class InputManager {
    private Set<KeyCode> pressedKeys;

    public InputManager() {
        pressedKeys = new HashSet<>();
    }

    public boolean isPressed(KeyCode keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        pressedKeys.add(keyEvent.getCode());
    }

    public void handleKeyRelease(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());
    }
}
