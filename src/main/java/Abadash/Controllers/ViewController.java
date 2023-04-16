package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
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
    private Pane stageSelectPane;
    private final MenuController menuController = new MenuController(this);
    private final GameController gameController = new GameController(this);
    private final StageSelectController stageSelectController = new StageSelectController(this);

    public void initialize() {
        System.out.println("viewcontroller init");
        // load all pages
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/Abadash/Menu.fxml"));
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/Abadash/Game.fxml"));
        FXMLLoader stageSelectLoader = new FXMLLoader(getClass().getResource("/Abadash/StageSelect.fxml"));
        menuLoader.setController(menuController);
        gameLoader.setController(gameController);
        stageSelectLoader.setController(stageSelectController);

        try {
            menuPane = (Pane) menuLoader.load();
            gamePane = (Pane) gameLoader.load();
            stageSelectPane = (Pane) stageSelectLoader.load();
            menuPane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
            gamePane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
            stageSelectPane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if set to "Game" the game is not in focus (for some reason...)
        changeView("Menu");
    }

    protected void changeView(String contentName) {
        if (!(contentName == "Menu" || contentName =="Game" || contentName == "StageSelect")) return;

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
            case "StageSelect":
                contentPane.getChildren().setAll(stageSelectPane);
                stageSelectPane.requestFocus();
                stageSelectController.scrollContent(0, true);
                // stageSelectController.updateProgress();
                // gameController.stopGame();
                break;
        }
    }

    protected void setWhichMap(String whichMap) {
        gameController.setWhichMap(whichMap);
    }

    public GameController getGameControllerInstance() {
        return gameController;
    }

    public MenuController getMenuControllerInstance() {
        return menuController;
    }
}
