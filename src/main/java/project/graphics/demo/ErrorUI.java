package project.graphics.demo;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Map;
import java.util.Optional;
import java.util.Vector;

public class ErrorUI {
    /**
     * Create an Alert Box for 2 types of errors:
     *      - invalidCriteria
     *      - requestFailed
     *
     * @param errorType string that can be either "invalidCriteria" or "requestFailed"
     * @see Alert
     */
    public static void showError(String errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String headerText = "";
        switch (errorType) {
            case "invalidCriteria":
                headerText = "You must select at least one valid parameter!";
                break;
            case "requestFailed":
                headerText = "The server request could not be completed. " +
                        "Check your internet connection, then press OK to retry or " +
                        "close this message to quit.";
                break;
        }
        alert.setHeaderText(headerText);
        Optional<ButtonType> result = alert.showAndWait();
        if (errorType.equals("requestFailed") && result.isEmpty()) {
            Platform.exit();
        }
    }

    /**
     * Show an Alert box whenever the user perform a search; the Alert box is unique
     * but in that box there might be showed information about: red parameters not
     * included in the search and/or warn the user about including some parameters
     * not selected
     *
     * @param selectedAll String with max 3 letter that might be {c,p,s,t} that
     *                    correspond with country, provider, status, type;
     *                    Used to see in which list the user did not select any
     *                    parameter
     * @param redCriteria Map with String keys that might be {c,p,s,t} that
     *                    correspond with country, provider, status, type; to each
     *                    key there is associated a Vector<String> value that includes
     *                    all the parameters name that are red in the UI
     *
     * @return true when the user press button OK
     *         false when the user press button ANNULLA or close the Alert box
     *
     * @see Alert
     */
    public static boolean showCriteriaAlert(String selectedAll, Map<String, Vector<String>> redCriteria) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //Not selected parameters included
        String headerText = "Information on the search:\n";
        if (!selectedAll.equals("")) {
            headerText += "You have not chosen any criteria on the following lists:\n";
            if (selectedAll.contains("c")) headerText += "countries, ";
            if (selectedAll.contains("p")) headerText += "providers, ";
            if (selectedAll.contains("t")) headerText += "types, ";
            if (selectedAll.contains("s")) headerText += "statuses, ";
            headerText = headerText.substring(0, headerText.length() - 2);
            headerText += ".\nIf you perform the search, every criteria displayed will be " +
                    "selected for those lists.\n\n";
        }

        //Red parameters info
        if (!redCriteria.isEmpty()) {
            headerText += "The following selected criteria are incompatible with the others " +
                    "(performing a search with them would return no results):\n";
            Vector<String> redCountries = redCriteria.get("c");
            if (!redCountries.isEmpty()) {
                headerText += "- Countries: ";
                for (String c : redCountries) headerText += c + ", ";
                headerText = headerText.substring(0, headerText.length() - 2);
                headerText += ";\n";
            }
            Vector<String> redProviders = redCriteria.get("p");
            if (!redProviders.isEmpty()) {
                headerText += "- Providers: ";
                for (String p : redProviders) headerText += p + ", ";
                headerText = headerText.substring(0, headerText.length() - 2);
                headerText += ";\n";
            }
            Vector<String> redTypes = redCriteria.get("t");
            if (!redTypes.isEmpty()) {
                headerText += "- Types: ";
                for (String t : redTypes) headerText += t + ", ";
                headerText = headerText.substring(0, headerText.length() - 2);
                headerText += ";\n";
            }
            Vector<String> redStatuses = redCriteria.get("s");
            if (!redStatuses.isEmpty()) {
                headerText += "- Statuses: ";
                for (String s : redStatuses) headerText += s + ", ";
                headerText = headerText.substring(0, headerText.length() - 2);
                headerText += ";\n";
            }
            headerText = headerText.substring(0, headerText.length() - 2);
            headerText += ".\nIf you perform the search, these criteria will be discarded.\n";
        }

        alert.setTitle("Criteria alert");
        alert.setHeaderText(headerText);
        alert.setContentText("Press OK to perform the search or cancel the alert " +
                "(Annulla / X) to go back and change the criteria.");
        Optional<ButtonType> result = alert.showAndWait();

        boolean outcome = false;
        if (result.isPresent() && result.get() == ButtonType.OK) outcome = true;
        return outcome;
    }
}
