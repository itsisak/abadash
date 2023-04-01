package Abadash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static Abadash.Constants.SCENE_WIDTH;
import static Abadash.Constants.SCENE_HEIGHT;

public class AbadashApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("AbaDash");
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.getNamespace().put("WIDTH", SCENE_WIDTH);
        loader.getNamespace().put("HEIGHT", SCENE_HEIGHT);
        loader.setLocation(getClass().getResource("Game.fxml"));

        Scene gameScene = new Scene(loader.load());
        gameScene.getRoot().requestFocus();
        primaryStage.setScene(gameScene);

        gameScene.getStylesheets().addAll(this.getClass().getResource("App.css").toExternalForm());

        primaryStage.show();
    }
}