package project.graphics.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorScene {

    public void showError(String message) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EuTrustServicesDashboard.class.getResource("error-view.fxml"));
        Stage errorStage = new Stage(fxmlLoader.load());
        errorStage.setResizable(false);

        Label errorText = new Label(message);

        AnchorPane labelPane = (AnchorPane) fxmlLoader.getNamespace().get("labelAnchorPane");

        labelPane.getChildren().add(errorText);
    }
}
