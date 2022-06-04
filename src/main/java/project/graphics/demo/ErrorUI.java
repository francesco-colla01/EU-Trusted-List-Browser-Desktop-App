package project.graphics.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

interface ErrorButtonAction {
    void action();
}

public class ErrorUI {
        public static void showError(String headerText, String contentText, ErrorButtonAction action) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            action.action();
        }
    }

    public static void showError(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public static void showError(String errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String headerText = "";
        switch (errorType) {
            case "invalidCriteria":
                headerText = "You must select at least one valid parameter!";
                break;
            case "requestFailed":
                headerText = "The server request could not be completed. Check your internet connection and press OK or exit to retry.";
                break;
        }
        alert.setHeaderText(headerText);
        alert.showAndWait();
        /*if (errorType == "requestFailed") {
            CompleteUI.swapScene("sl", null);
        }*/
    }
}
