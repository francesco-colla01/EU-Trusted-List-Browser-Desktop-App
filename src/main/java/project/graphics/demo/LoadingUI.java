package project.graphics.demo;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;


import java.io.IOException;


public class LoadingUI {

    /**
     * Loads and creates a scene whenever the application needs to connect to the APIs
     *
     * @return scene used into the primaryStage
     * @throws IOException
     * @see ProgressBar
     */
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
