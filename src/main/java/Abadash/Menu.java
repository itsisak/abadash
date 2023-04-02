package Abadash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.Exception;

import static Abadash.Constants.SCENE_HEIGHT;
import static Abadash.Constants.SCENE_WIDTH;

public class Menu extends Application {

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
        loader.setLocation(getClass().getResource("Menu.fxml"));

        Scene menuScene = new Scene(loader.load());
        // gameScene.getRoot().requestFocus();
        primaryStage.setScene(menuScene);

        primaryStage.show();
    }

}
