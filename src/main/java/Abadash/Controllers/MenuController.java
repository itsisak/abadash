package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import Abadash.ImageGallery;

import java.io.IOException;

import static Abadash.Constants.SCENE_WIDTH;
import static Abadash.Constants.SCENE_HEIGHT;

public class MenuController {

    @FXML private Pane menuPane;
    @FXML private VBox titleContainer;
    @FXML private HBox playBtn;
    @FXML private HBox footer;
    @FXML private Pane githubBtn;
    @FXML private Pane volumeBtn;
    @FXML private ImageView volumeImg;
    @FXML private Pane volumeBox;
    @FXML private StackPane volumeContainer;
    @FXML private Rectangle volumeSlider;
    private boolean mute = false;
    private double lastVolume;
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

        menuPane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        titleContainer.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);

        playBtn.setOnMouseClicked(event -> viewController.changeView("StageSelect"));

        
        footer.setPrefWidth(SCENE_WIDTH);
        footer.setLayoutY(SCENE_HEIGHT - footer.getMaxHeight() - 20);
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

        AudioManager.getInstance().changeVolume(.5);
        volumeBtn.setOnMouseClicked(event -> toggleMute());

        Rectangle clipRect = new Rectangle(300, 30);
        clipRect.setArcHeight(30);
        clipRect.setArcWidth(30);
        volumeContainer.setClip(clipRect);
        volumeContainer.setOnMousePressed(event -> handleVolumeInteraction(event));
        volumeContainer.setOnMouseDragged(event -> handleVolumeInteraction(event));

    }

    private void handleVolumeInteraction(MouseEvent event) {
        if (mute) {
            toggleMute();
            mute = false;
        }
        double volume = (event.getSceneX() - volumeBox.getLayoutX()) / volumeContainer.getWidth();

        volumeImg.setImage(ImageGallery.getInstance().load("Abadash/icons/" + (volume > 0 ? (volume >= .6 ? "high_volume" : "low_volume2") : "mute") + ".png"));

        AudioManager.getInstance().changeVolume(volume);
        volumeSlider.setWidth(event.getSceneX() - volumeBox.getLayoutX());
    }

    private void toggleMute() {
        double currentVolume = AudioManager.getInstance().getVolume();

        AudioManager.getInstance().changeVolume(mute ? lastVolume : 0);
        volumeImg.setImage(ImageGallery.getInstance().load("Abadash/icons/" + (mute ? (lastVolume >= .6 ? "high_volume" : "low_volume2") : "mute") + ".png"));
        volumeSlider.setWidth(mute ? lastVolume * 300 : 0); 

        lastVolume = currentVolume;
        mute = !mute;
    }

    
}
