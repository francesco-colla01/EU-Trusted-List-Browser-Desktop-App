package project.graphics.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import project.framework.Provider;

import java.io.IOException;
import java.util.Vector;

public class Result {

    public static Scene result(Stage stage, Scene backScene, Vector<Provider> result) throws IOException {

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(EuTrustServicesDashboard.class.getResource("search-view.fxml"));


        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);

        Button backButton = (Button) fxmlLoader.getNamespace().get("backButton");
        backButton.setOnAction(actionEvent -> {
            stage.setScene(backScene);
        });

        //Executing search
        System.out.println(result);


        return scene;
    }
}
