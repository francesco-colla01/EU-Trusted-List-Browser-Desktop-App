package project.graphics.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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

        swapScene("l", null);
        primaryStage.show();
        swapScene("s", null);
    }

    /*public static void swapScene(Scene newScene) {
        Scene tmpScene = stage.getScene();
        stage.setScene(newScene);
        backScene = tmpScene;
    }*/

    public static void swapScene(String sceneType, AtomicBoolean darkMode) throws IOException {
        backScene = stage.getScene();
        if (Objects.equals(sceneType, "r")) {
            stage.setScene(ResultUI.result(stage, darkMode));
            return;
        }
        if (sceneType.contains("l")) {
            stage.getIcons().add(new Image("https://i.imgur.com/xm62NkC.png"));
            stage.setScene(LoadingUI.getScene());
        }
        if (sceneType.contains("s")) {
            Service<Scene> process = new Service<>() {
                @Override
                protected Task<Scene> createTask() {
                    return new Task<Scene>() {
                        @Override
                        protected Scene call() throws Exception {
                            return SearchUI.getScene();
                        }
                    };
                }
            };

            process.setOnSucceeded( e -> {
                stage.setScene(process.getValue());
            });

            process.setOnFailed( e -> {
                ErrorUI.showError("requestFailed");
                try {
                    swapScene("sl", null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            process.start();
        }
    }

    public static void backScene() {
        Scene tmpScene = stage.getScene();
        stage.setScene(backScene);
        backScene = tmpScene;
    }
}
