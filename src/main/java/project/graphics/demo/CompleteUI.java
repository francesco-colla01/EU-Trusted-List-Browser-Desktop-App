package project.graphics.demo;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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

        swapScene("l");
        primaryStage.show();
        swapScene("s");
    }

    /*public static void swapScene(Scene newScene) {
        Scene tmpScene = stage.getScene();
        stage.setScene(newScene);
        backScene = tmpScene;
    }*/

    public static void swapScene(String sceneType) throws IOException {
        backScene = stage.getScene();
        //show results UI
        if (Objects.equals(sceneType, "r")) {
            stage.setScene(ResultUI.result(stage));
            return;
        }
        //show loading UI
        if (sceneType.contains("l")) {
            stage.getIcons().add(new Image("https://i.imgur.com/xm62NkC.png"));
            stage.setScene(LoadingUI.getScene());
        }
        //show search UI; while it is built, the loading UI will remain on screen
        if (sceneType.contains("s")) {
            Service<Scene> process = new Service<>() {
                @Override
                protected Task<Scene> createTask() {
                    return new Task<Scene>() {
                        @Override
                        protected Scene call() throws Exception {
                            return SearchUI.search();
                        }
                    };
                }
            };

            //build successful = server requests successful
            process.setOnSucceeded( e -> {
                stage.setScene(process.getValue());
            });

            //build failed = server requests failed
            //an error message is shown, then try to build the scene again if the program
            //has not been closed
            process.setOnFailed( e -> {
                ErrorUI.showError("requestFailed");
                try {
                    swapScene("sl");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            process.start();
        }
    }

    //switch the program back to the previous scene
    public static void backScene() {
        Scene tmpScene = stage.getScene();
        stage.setScene(backScene);
        backScene = tmpScene;
    }
}
