package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import static Abadash.Constants.SCENE_WIDTH;
import static Abadash.Constants.SCENE_HEIGHT;

public class MenuController {

    @FXML private GridPane titleContainer;
    @FXML private Button startGameBtn;
    private ViewController viewController;

    public MenuController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void initialize() {
        titleContainer.setPrefWidth(SCENE_WIDTH);
        startGameBtn.setOnAction(event -> viewController.changeView("Game"));
        startGameBtn.setLayoutX((SCENE_WIDTH - startGameBtn.getPrefWidth()) / 2);
        startGameBtn.setLayoutY((SCENE_HEIGHT - startGameBtn.getPrefHeight()) / 2);
    }

}
