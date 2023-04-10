package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import static Abadash.Constants.SCENE_WIDTH;

public class MenuController {

    @FXML
    private Button startBtn;
    @FXML
    private GridPane titleContainer;
    private ViewController viewController;

    public MenuController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void initialize() {
        titleContainer.setPrefWidth(SCENE_WIDTH);
        startBtn.setOnAction(event -> viewController.changeView("Game"));
    }

}
