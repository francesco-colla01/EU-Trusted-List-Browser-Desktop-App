package project.graphics.demo;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoadingUI {

    public static Scene getScene() throws IOException {

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("loading-view.fxml"));

        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);

        ProgressBar progressBar = (ProgressBar) fxmlLoader.getNamespace().get("progressBar");
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        return scene;
    }
}
