package Abadash.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.animation.Interpolator;

import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

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
                throw new FileNotFoundException();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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

        pageIndicator.setPrefWidth(SCENE_WIDTH);
        pageIndicator.setLayoutY(SCENE_HEIGHT - pageIndicator.getPrefHeight() - 50);
    }

    private void createPage(String titleStr) {
        // page
        VBox page = new VBox();
        page.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        page.setSpacing(20);
        page.setAlignment(Pos.CENTER);

        // title
        Text title = new Text(titleStr);
        title.setFont(new Font(75));
        title.getStyleClass().add("stageTitle");

        // playButton
        ImageView selectBtn = new ImageView(getClass().getResource("/Abadash/icons/play.png").toString());
        selectBtn.setFitWidth(50);
        selectBtn.setFitHeight(50);
        selectBtn.setOnMouseClicked(event -> viewController.changeView("Game"));

        // add all to page and add page to stageSelectContainer
        page.getChildren().addAll(title, createProgressBar(titleStr), selectBtn);
        stageContainer.getChildren().add(page);

        // create new pageIndicator circle
        Circle circle = new Circle(7.5);
        circle.getStyleClass().add("pageIndicatorCircle");
        pageIndicator.getChildren().add(circle);
    }

    private int getProgress(String titleString) {
        int best = 0;
        try {
            File file = new File(getClass().getResource("/Abadash/logs/" + titleString + ".txt").toURI());
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line == "" || line == null) 
                    continue;
                int data = Integer.valueOf(line);
                if (data > best)
                    best = data;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return best;
    }

    private StackPane createProgressBar(String titleStr) {
        int width = 400;
        int height = 30;
        int progress = getProgress(titleStr);

        StackPane progressContainer = new StackPane();
        progressContainer.setMaxSize(width, height);
        progressContainer.setAlignment(Pos.CENTER);
        
        StackPane progressBar = new StackPane();
        progressBar.setMaxSize(width, height);
        progressBar.setAlignment(Pos.CENTER_LEFT);
        
        Rectangle backgroundRect = new Rectangle(width, height);
        backgroundRect.setArcWidth(height);
        backgroundRect.setArcHeight(height);
        backgroundRect.getStyleClass().add("progressBackground");
        
        Rectangle progressRect = new Rectangle(progress * width / 100, height);
        progressRect.setArcWidth(height);
        progressRect.setArcHeight(height);
        progressRect.getStyleClass().add("progress");

        Text progressText = new Text(progress + "%");
        progressText.setFont(new Font(height * 0.75));
        progressText.getStyleClass().add("progressText"); 
        
        // add progressBar to progressBarContainer
        progressBar.getChildren().addAll(backgroundRect, progressRect);
        progressContainer.getChildren().addAll(progressBar, progressText);
        return progressContainer;
    }

    protected void updateProgress() {
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
