package project.graphics.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CompleteUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("EU Trust Service Dashboard");
        primaryStage.setResizable(false);

        primaryStage.getIcons().add(new Image("https://i.imgur.com/xm62NkC.png"));

        Scene search = SearchUI.search(primaryStage);
        primaryStage.setScene(search);

        primaryStage.show();
    }
}
