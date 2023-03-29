package Abadash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.Exception;

public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            primaryStage.setTitle("Abadash");
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("App.fxml"))));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("---- ERROR ----");
            System.out.println(e);
        }
    }

}
