package project.graphics.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AlertScene {

    public static boolean alertScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("alert-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        AnchorPane pane = (AnchorPane) fxmlLoader.getNamespace().get("messageAnchorPane");

        Label l = new Label("kek");

        pane.getChildren().add(l);

        stage.show();
        return true;
    }
}
