package project.graphics.demo;

import com.google.common.base.Supplier;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.concurrent.Callable;

interface ErrorButtonAction {
    void action();
}

public class ErrorScene {
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

    public static void showError(String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
