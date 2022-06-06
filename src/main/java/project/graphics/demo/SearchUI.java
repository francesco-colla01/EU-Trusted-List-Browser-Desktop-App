package project.graphics.demo;

import javafx.scene.control.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import project.framework.SearchCriteria;
import project.framework.SearchEngine;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchUI {

    /**
     * This method create, shows and dynamically update the searchUI; where the user can click
     * to different checkboxes to selected different searching criteria.
     *
     * @throws IOException
     *
     * @see FilterController
     */
    public static Scene search() throws IOException {
        //AtomicBoolean darkmode = new AtomicBoolean(false);
        FilterController filter = new FilterController();

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("search-view.fxml"));

        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);

        //COUNTRIES SEARCHUI ZONE
        AnchorPane countriesPane = (AnchorPane) fxmlLoader.getNamespace().get("countriesAnchorPane");

        VBox countriesCheckBoxesLeft = new VBox();
        VBox countriesCheckBoxesRight = new VBox();

        //This lambda expression update every 0.25 second the countries panel
        Timeline countriesTPane = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            List<CheckBox> filteredCountries = filter.getCheckBoxes("c");
            int separator = 0;

            for (CheckBox CBox : filteredCountries) {
                if (separator < (filteredCountries.size()/2)) {
                    if (!countriesCheckBoxesLeft.getChildren().contains(CBox)) {
                        countriesCheckBoxesLeft.getChildren().add(CBox);
                    }
                }
                else {
                    if (!countriesCheckBoxesRight.getChildren().contains(CBox)) {
                        countriesCheckBoxesRight.getChildren().add(CBox);
                    }
                }

                separator++;
            }
        }
        ));

        countriesTPane.setCycleCount(Animation.INDEFINITE);
        countriesTPane.play();

        HBox countriesCheckBoxes = new HBox(countriesCheckBoxesLeft, countriesCheckBoxesRight);
        countriesCheckBoxes.setSpacing(15);

        AnchorPane.setTopAnchor(countriesCheckBoxes, 10.0);
        AnchorPane.setLeftAnchor(countriesCheckBoxes, 22.0);

        countriesCheckBoxes.setSpacing(55);
        countriesCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        //TYPES SEARCHUI ZONE
        AnchorPane tosAnchorPane = (AnchorPane) fxmlLoader.getNamespace().get("tosAnchorPane");

        VBox tosCheckBoxesLeft = new VBox();
        VBox tosCheckBoxesRight = new VBox();

        //This lambda expression update every 0.25 second the types of service panel
        Timeline tosPane = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            List<CheckBox> tosCBoxes = filter.getCheckBoxes("t");

            int separator = 0;
            for (CheckBox CBox : tosCBoxes) {
                if (separator < tosCBoxes.size()/2) {
                    if (!tosCheckBoxesLeft.getChildren().contains(CBox)) {
                        tosCheckBoxesLeft.getChildren().add(CBox);
                    }
                } else {
                    if (!tosCheckBoxesRight.getChildren().contains(CBox)) {
                        tosCheckBoxesRight.getChildren().add(CBox);
                    }
                }
                separator++;
            }

        }));
        tosPane.setCycleCount(Animation.INDEFINITE);
        tosPane.play();

        HBox tosCheckBoxes = new HBox(tosCheckBoxesLeft, tosCheckBoxesRight);
        tosCheckBoxes.setSpacing(15);

        AnchorPane.setTopAnchor(tosCheckBoxes, 100.0);
        AnchorPane.setLeftAnchor(tosCheckBoxes, 22.0);

        tosCheckBoxes.setSpacing(55);
        tosCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        //STATUS SEARCHUI ZONE
        AnchorPane ssAnchorPane = (AnchorPane) fxmlLoader.getNamespace().get("ssAnchorPane");

        VBox ssCheckBoxesLeft = new VBox();
        VBox ssCheckBoxesRight = new VBox();

        //This lambda expression update every 0.25 second the service statuses panel
        Timeline ssPane = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            List<CheckBox> ssCBoxes = filter.getCheckBoxes("s");

            int separator = 0;
            for (CheckBox CBox : ssCBoxes) {
                if (separator < ssCBoxes.size()/2) {
                    if (!ssCheckBoxesLeft.getChildren().contains(CBox)) {
                        ssCheckBoxesLeft.getChildren().add(CBox);
                    }
                } else {
                    if (!ssCheckBoxesRight.getChildren().contains(CBox)) {
                        ssCheckBoxesRight.getChildren().add(CBox);
                    }
                }
                separator++;
            }

        }));
        ssPane.setCycleCount(Animation.INDEFINITE);
        ssPane.play();

        HBox ssCheckBoxes = new HBox(ssCheckBoxesLeft, ssCheckBoxesRight);

        AnchorPane.setTopAnchor(ssCheckBoxes, 85.0);
        AnchorPane.setLeftAnchor(ssCheckBoxes, 22.0);

        ssCheckBoxes.setSpacing(25);
        ssCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        //PROVIDERS SEARCHUI ZONE
        //Load Select All CBox
        CheckBox selectAllCBox = (CheckBox) fxmlLoader.getNamespace().get("selectAllProviders");
        AtomicBoolean edit = new AtomicBoolean(true);
        selectAllCBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            List<CheckBox> filtered_providers = filter.getCheckBoxes("p");
            if (edit.get())
                filtered_providers.forEach(CBox ->CBox.setSelected(selectAllCBox.isSelected()));
        });

        AnchorPane scrollPane = (AnchorPane) fxmlLoader.getNamespace().get("providersScrollPAne");

        VBox providersCheckBoxes = new VBox();

        List<CheckBox> old_filteredCountriesCheckBox = new LinkedList<>();

        //This lambda expression update every 0.25 second the providers panel
        Timeline pane = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            List<CheckBox> filtered_providers = filter.getCheckBoxes("p");
            int selectedProviderSize = filter.getProviderSelectedSize();

            if (filtered_providers == null) {
                providersCheckBoxes.getChildren().clear();
                providersCheckBoxes.getChildren().add(new Label("No providers found with matching criteria"));
            } else {
                if (old_filteredCountriesCheckBox.size() != filtered_providers.size()) {
                    providersCheckBoxes.getChildren().clear();
                    providersCheckBoxes.getChildren().addAll(filtered_providers);
                }
            }

            if (filtered_providers != null) {
                if (old_filteredCountriesCheckBox.size() < filtered_providers.size()) {
                    edit.set(false);
                    selectAllCBox.setSelected(false);
                    edit.set(true);
                }

                if (filtered_providers.size() != selectedProviderSize && selectAllCBox.isSelected()) {
                    edit.set(false);
                    selectAllCBox.setSelected(false);
                    edit.set(true);
                }

                if (!selectAllCBox.isSelected() && filtered_providers.size() == selectedProviderSize) {
                    edit.set(false);
                    selectAllCBox.setSelected(true);
                    edit.set(true);
                }
                selectAllCBox.setDisable(false);
            } else {
                selectAllCBox.setDisable(true);
            }

            old_filteredCountriesCheckBox.clear();
            old_filteredCountriesCheckBox.addAll((filtered_providers == null ? new LinkedList<>() : filtered_providers));
        }));

        pane.setCycleCount(Animation.INDEFINITE);
        pane.play();

        AnchorPane.setTopAnchor(providersCheckBoxes, 10.0);
        AnchorPane.setLeftAnchor(providersCheckBoxes, 22.0);

        providersCheckBoxes.setSpacing(10);
        providersCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        //Start building the SearchUI
        countriesPane.getChildren().add(countriesCheckBoxes);
        tosAnchorPane.getChildren().add(tosCheckBoxes);
        ssAnchorPane.getChildren().add(ssCheckBoxes);
        scrollPane.getChildren().add(providersCheckBoxes);

        scene.getStylesheets().add(Objects.requireNonNull(SearchUI.class.getResource("css/stylesheet.css")).toExternalForm());

        Button searchButton = (Button) fxmlLoader.getNamespace().get("searchButton");
        searchButton.getStyleClass().add("searchButton");
        searchButton.setOnAction(actionEvent -> {
            SearchCriteria criteria = filter.getCriteria();
            if (criteria.isInvalid()) {
                ErrorUI.showError("invalidCriteria");
                return;
            }
            try {
                //get parameters for criteria alert
                String selectedAll = criteria.getSelectedAll();
                Map<String, Vector<String>> redCriteria = criteria.getRedCriteria();

                //something selected across all lists with no incompatible criteria
                if (selectedAll.equals("") && redCriteria.isEmpty()) {
                    SearchEngine.getInstance().performSearch(criteria);
                    CompleteUI.swapScene("r");
                }
                else {
                    boolean result = ErrorUI.showCriteriaAlert(selectedAll, redCriteria);
                    //ok button pressed
                    if (result) {
                        SearchEngine.getInstance().performSearch(criteria);
                        CompleteUI.swapScene("r");
                    }
                    //cancelled alert / Annulla pressed: back to the SearchUI
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return scene;

        //DARK MODE W(R)IP
        /*FlowPane backgroundFlowPane = (FlowPane)fxmlLoader.getNamespace().get("backgroundFlowPane");

        ToggleButton darkMode = (ToggleButton) fxmlLoader.getNamespace().get("darkMode");

        darkMode.setOnAction(actionEvent -> {
            if(darkMode.isSelected()){
                darkmode.set(false);
                System.out.println("darkmode is enabled");
                backgroundFlowPane.getStyleClass().add("dark-mode");
                countriesCheckBoxes.getStyleClass().add("dark-mode");
                providersCheckBoxes.getStyleClass().add("dark-mode");
                scrollPane.getStyleClass().add("dark-mode");
                tosCheckBoxes.getStyleClass().add("dark-mode");
                selectAllCBox.getStyleClass().add("dark-mode");


            } else if (!darkMode.isSelected()) {
                darkmode.set(false);
                System.out.println("darkmode is disabled");
                backgroundFlowPane.getStyleClass().remove("dark-mode");
                countriesCheckBoxes.getStyleClass().remove("dark-mode");
                providersCheckBoxes.getStyleClass().remove("dark-mode");
                scrollPane.getStyleClass().remove("dark-mode");
                tosCheckBoxes.getStyleClass().remove("dark-mode");
                selectAllCBox.getStyleClass().remove("dark-mode");
            }
        });
         */
    }
}