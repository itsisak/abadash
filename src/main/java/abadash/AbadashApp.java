package abadash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AbadashApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("AbaDash");
        Scene gameScene = new Scene(FXMLLoader.load(getClass().getResource("Game.fxml")));
        primaryStage.setScene(gameScene);
        gameScene.getRoot().requestFocus();
        primaryStage.show();
    }
}
