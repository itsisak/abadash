package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static Abadash.Constants.*;

public class ViewController {

    @FXML private AnchorPane rootPane;
    @FXML private StackPane contentPane;
    private Pane menuPane;
    private Pane gamePane;
    private final MenuController menuController = new MenuController(this);
    private final GameController gameController = new GameController(this);

    public void initialize() {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/Abadash/Menu.fxml"));
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/Abadash/Game.fxml"));
        menuLoader.setController(menuController);
        gameLoader.setController(gameController);

        try {
            menuPane = (Pane) menuLoader.load();
            gamePane = (Pane) gameLoader.load();
            menuPane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
            gamePane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeView("Menu");
    }

    public void changeView(String contentName) {

        contentPane.getChildren().clear();

        switch (contentName) {
            case "Menu":
                contentPane.getChildren().setAll(menuPane);
                menuPane.requestFocus();
                gameController.stopGame();
                break;
            case "Game":
                contentPane.getChildren().setAll(gamePane);
                gamePane.requestFocus();
                gameController.startGame();
                break;
        }
    }

}
