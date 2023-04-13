package Abadash;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static Abadash.Constants.SCENE_WIDTH;
import static Abadash.Constants.SCENE_HEIGHT;

public class AbadashApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Abadash/App.fxml"));
        Scene scene = new Scene(loader.load(), SCENE_WIDTH, SCENE_HEIGHT);

        // for transparent app
        // primaryStage.initStyle(StageStyle.TRANSPARENT);
        // scene.setFill(Color.TRANSPARENT);
        
        primaryStage.setTitle("AbaDash");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Abadash/sprites/abakule.png")));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}