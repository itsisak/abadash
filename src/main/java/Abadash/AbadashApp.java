package Abadash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import Abadash.Controllers.*;
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

        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/Abadash/App.fxml"));
        // ViewController viewController = new ViewController();
        // appLoader.setController(viewController);
        
        Scene scene = new Scene(appLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
        scene.getRoot().requestFocus();
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}