package project.graphics.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CompleteUI extends Application {
    private static Scene backScene;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("EU Trust Service Dashboard");
        primaryStage.setResizable(false);
        stage = primaryStage;

        primaryStage.getIcons().add(new Image("https://i.imgur.com/xm62NkC.png"));

        Scene search = SearchUI.search(primaryStage);
        primaryStage.setScene(search);
        backScene = search;

        primaryStage.show();
    }

    public static void swapScene(Scene newScene) {
        Scene tmpScene = stage.getScene();
        stage.setScene(newScene);
        backScene = tmpScene;
    }

    public static void backScene() {
        Scene tmpScene = stage.getScene();
        stage.setScene(backScene);
        backScene = tmpScene;
    }
}
