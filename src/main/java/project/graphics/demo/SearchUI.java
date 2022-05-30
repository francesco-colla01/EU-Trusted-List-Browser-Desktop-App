package project.graphics.demo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.framework.SearchCriteria;
import project.framework.SearchEngine;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

//TODO METTERE A POSTO LE CLASSI CHE GESTISCONO L'UPDATE DELLE TABELLE
//TODO CAMBIARE BOTTONE PER LA RICERCA
//TODO FARE LA RICERCA NELLA CLASSE SPECIFICA
//TODO FARE LA GUI PER I SERVIZI CHE ESCONO DOPO AVER FATTO LA RICERCA

public class SearchUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("hello-view.fxml"));


        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);
        stage.setTitle("EU Trust Service Dashboard");
        stage.setScene(scene);
        stage.setResizable(false);

        FilterController filter = new FilterController();

        // Countries
        AnchorPane countriesPane = (AnchorPane) fxmlLoader.getNamespace().get("countriesAnchorPane");

        VBox countriesCheckBoxesLeft = new VBox();
        VBox countriesCheckBoxesRight = new VBox();

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

        // Types
        //TypeCheckBoxController typeCheckBoxController = new TypeCheckBoxController(typesOfService);

        AnchorPane tosAnchorPane = (AnchorPane) fxmlLoader.getNamespace().get("tosAnchorPane");

        VBox tosCheckBoxesLeft = new VBox();
        VBox tosCheckBoxesRight = new VBox();

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

        AnchorPane.setTopAnchor(tosCheckBoxes, 75.0);
        AnchorPane.setLeftAnchor(tosCheckBoxes, 22.0);


        tosCheckBoxes.setSpacing(55);
        tosCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        // Statuses
        AnchorPane ssAnchorPane = (AnchorPane) fxmlLoader.getNamespace().get("ssAnchorPane");
        //StatusCheckBoxController statusCheckBoxController = new StatusCheckBoxController(statuses);

        VBox ssCheckBoxesLeft = new VBox();
        VBox ssCheckBoxesRight = new VBox();

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

        AnchorPane.setTopAnchor(ssCheckBoxes, 75.0);
        AnchorPane.setLeftAnchor(ssCheckBoxes, 22.0);


        ssCheckBoxes.setSpacing(25);
        ssCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        // Load Select All CBox
        CheckBox selectAllCBox = (CheckBox) fxmlLoader.getNamespace().get("selectAllProviders");
        AtomicBoolean edit = new AtomicBoolean(true);
        selectAllCBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            List<CheckBox> filtered_providers = filter.getCheckBoxes("p");
            if (edit.get())
                filtered_providers.forEach(CBox ->CBox.setSelected(selectAllCBox.isSelected()));
        });


        // Provider
        AnchorPane scrollPane = (AnchorPane) fxmlLoader.getNamespace().get("providersScrollPAne");

        VBox providersCheckBoxes = new VBox();

        List<CheckBox> old_filteredCountriesCheckBox = new LinkedList<>();

        Timeline pane = new Timeline(new KeyFrame(Duration.seconds(0.25), ev -> {
            List<CheckBox> filtered_providers = filter.getCheckBoxes("p");


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

                if (filtered_providers.size() != filter.getSelectedProvidersSize() && selectAllCBox.isSelected()) {
                    edit.set(false);
                    selectAllCBox.setSelected(false);
                    edit.set(true);
                }

                if (!selectAllCBox.isSelected() && filtered_providers.size() == filter.getSelectedProvidersSize()) {
                    edit.set(false);
                    selectAllCBox.setSelected(true);
                    edit.set(true);
                }
                selectAllCBox.setDisable(false);
            } else {
                selectAllCBox.setDisable(true);
            }
            /*else if (!selectAllCBox.isSelected() && filtered_providers.size() != filter.getSelectedCriteria().getProviders().size()) {
                edit.set(false);
                selectAllCBox.setSelected(true);
                edit.set(true);
            } */

            old_filteredCountriesCheckBox.clear();
            old_filteredCountriesCheckBox.addAll((filtered_providers == null ? new LinkedList<>() : filtered_providers));
        }));

        pane.setCycleCount(Animation.INDEFINITE);
        pane.play();

        AnchorPane.setTopAnchor(providersCheckBoxes, 10.0);
        AnchorPane.setLeftAnchor(providersCheckBoxes, 22.0);

        providersCheckBoxes.setSpacing(10);
        providersCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        countriesPane.getChildren().add(countriesCheckBoxes);
        tosAnchorPane.getChildren().add(tosCheckBoxes);
        ssAnchorPane.getChildren().add(ssCheckBoxes);
        scrollPane.getChildren().add(providersCheckBoxes);


        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/stylesheet.css")).toExternalForm());

        Button searchButton = (Button) fxmlLoader.getNamespace().get("searchButton");
        searchButton.getStyleClass().add("searchButton");
        searchButton.setOnAction(actionEvent -> {
            SearchCriteria criteria = filter.getCriteria();
            if (criteria.isInvalid()) {
                ErrorUI.showError("You must select at least one parameter!");
                return;
            }
            try {
                SearchEngine.getInstance().performSearch(criteria);
                Scene resultScene = ResultUI.result(stage, scene);
                resultScene.getStylesheets().add(Objects.requireNonNull(ResultUI.class.getResource("css/stylesheet.css")).toExternalForm());
                stage.setScene(resultScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ToggleButton darkMode = (ToggleButton) fxmlLoader.getNamespace().get("darkMode");
        darkMode.setSelected(false);
        darkMode.setOnAction(actionEvent -> {
            if(darkMode.isSelected()){
                System.out.println("darkmode is enabled");
                countriesPane.getStyleClass().add("dark-mode");
                countriesCheckBoxes.getStyleClass().add("dark-mode");
                providersCheckBoxes.getStyleClass().add("dark-mode");
                scrollPane.getStyleClass().add("dark-mode");
                tosAnchorPane.getStyleClass().add("dark-mode");
                tosCheckBoxes.getStyleClass().add("dark-mode");
                ssAnchorPane.getStyleClass().add("dark-mode");


            } else if (!darkMode.isSelected()) {
                System.out.println("darkmode is disabled");
                countriesPane.getStyleClass().remove("dark-mode");
                countriesCheckBoxes.getStyleClass().remove("dark-mode");
                providersCheckBoxes.getStyleClass().remove("dark-mode");
                scrollPane.getStyleClass().remove("dark-mode");
                tosAnchorPane.getStyleClass().remove("dark-mode");
                tosCheckBoxes.getStyleClass().remove("dark-mode");
                ssAnchorPane.getStyleClass().remove("dark-mode");
            }
        });


        stage.getIcons().add(new Image("https://i.imgur.com/xm62NkC.png"));


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}