package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

import static Abadash.Constants.SCENE_WIDTH;
import static Abadash.Constants.SCENE_HEIGHT;

public class MenuController {

    @FXML private Pane menuPane;
    @FXML private GridPane titleContainer;
    @FXML private ImageView playBtn;
    @FXML private ImageView githubBtn;
    private ViewController viewController;
    

    protected MenuController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void initialize() {
        
        menuPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> viewController.changeView("StageSelect");
                case SPACE -> viewController.changeView("StageSelect");
                default -> {}
            }
        });
        
        titleContainer.setPrefWidth(SCENE_WIDTH);
        playBtn.setLayoutX((SCENE_WIDTH - playBtn.getFitWidth()) / 2);
        playBtn.setLayoutY((SCENE_HEIGHT - playBtn.getFitHeight()) / 2 + 50);
        githubBtn.setLayoutY(SCENE_HEIGHT - githubBtn.getFitHeight() - 20);
        
        playBtn.setOnMouseClicked(event -> viewController.changeView("StageSelect"));
        githubBtn.setOnMouseClicked(event -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://github.com/itsisak/abadash"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    
}
