package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Interpolator;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import static Abadash.Constants.SCENE_WIDTH;
import static Abadash.Constants.SCENE_HEIGHT;

public class StageSelectController {
   
    private ViewController viewController;

    @FXML private Pane stageSelectPane;
    @FXML private HBox stageContainer;
    @FXML private ImageView menuBtn;
    @FXML private ImageView previousStageBtn;
    @FXML private ImageView nextStageBtn;
    @FXML private HBox pageIndicator;
    private List<String> titleStrArr = new ArrayList<>();
    private File[] mapsDir = null;
    private int pageCount = 0;
    private int currentPageIndex = 0;
    private int lastPageIndex = 0;
    

    protected StageSelectController(ViewController viewController) {
        this.viewController = viewController;
    }


    public void initialize() {

        // Set keycontrols
        stageSelectPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT -> switchStage(true);
                case LEFT -> switchStage(false);
                case ENTER -> viewController.changeView("Game");
                case SPACE -> viewController.changeView("Game");
                case ESCAPE -> viewController.changeView("Menu");
                default -> {}
            }
        });

        // get maps directory
        try {
            mapsDir = new File(getClass().getResource("/Abadash/maps").toURI()).listFiles();
            if (mapsDir == null) 
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pageCount = mapsDir.length;
        
        // create pages
        for (File file : mapsDir) {
            String titleStr = file.getName().replace(".json", "");
            titleStrArr.add(titleStr);
            
            createPage(titleStr);
        } 
        

        // set fxml variables
        menuBtn.setLayoutX(SCENE_WIDTH - menuBtn.getFitWidth() - 20);
        menuBtn.setOnMouseClicked(event -> viewController.changeView("Menu"));

        stageContainer.setPrefSize(SCENE_WIDTH * pageCount, SCENE_HEIGHT);

        previousStageBtn.setY((SCENE_HEIGHT - previousStageBtn.getFitHeight())/ 2);
        previousStageBtn.setX(20);
        previousStageBtn.setRotate(180);
        previousStageBtn.setOnMouseClicked(event -> switchStage(false));

        nextStageBtn.setY((SCENE_HEIGHT - nextStageBtn.getFitHeight()) / 2);
        nextStageBtn.setX(SCENE_WIDTH - nextStageBtn.getFitWidth() - 20);
        nextStageBtn.setOnMouseClicked(event -> switchStage(true));

        // pageIndicator.setLayoutX((SCENE_WIDTH - pageIndicator.getPrefWidth()) / 2);
        pageIndicator.setPrefWidth(SCENE_WIDTH);
        pageIndicator.setLayoutY(SCENE_HEIGHT - pageIndicator.getPrefHeight() - 50);
    }

    private void createPage(String titleStr) {
        VBox page = new VBox();
        page.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        page.setSpacing(20);
        page.setAlignment(Pos.CENTER);

        Text title = new Text(titleStr);
        title.setFont(new Font(75));
        title.getStyleClass().add("stageTitle");

        ImageView selectBtn = new ImageView(getClass().getResource("/Abadash/icons/play.png").toString());
        selectBtn.setFitWidth(50);
        selectBtn.setFitHeight(50);
        selectBtn.setOnMouseClicked(event -> viewController.changeView("Game"));

        page.getChildren().addAll(title, selectBtn);
        stageContainer.getChildren().add(page);


        Circle circle = new Circle(7.5);
        circle.getStyleClass().add("pageIndicatorCircle");
        pageIndicator.getChildren().add(circle);
    }

    private void switchStage(boolean next) {
        lastPageIndex = currentPageIndex;
        currentPageIndex += (next) ? 1 : -1;

        if (currentPageIndex < 0) 
            currentPageIndex = pageCount - 1;
        if (currentPageIndex > pageCount - 1)
            currentPageIndex = 0;

        setWhichMap(titleStrArr.get(currentPageIndex));
        scrollContent(currentPageIndex * SCENE_WIDTH * ((currentPageIndex > lastPageIndex) ? -1 : 1));
    }

    private void scrollContent(double newX) {
        scrollContent(newX, false);
    }
 
    protected void scrollContent(double newX, boolean instant) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(stageContainer.layoutXProperty(), newX, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(instant ? 1 : 300), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        pageIndicator.getChildren().get(lastPageIndex).getStyleClass().remove("currentPageCircle");
        pageIndicator.getChildren().get(currentPageIndex).getStyleClass().add("currentPageCircle");
    }


    protected void setWhichMap(String whichMap) {
        viewController.setWhichMap(whichMap);
    }
}
