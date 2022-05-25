package project.graphics.demo;

import project.framework.CriteriaListFactory;
import project.framework.HttpRequest;
import project.framework.Provider;
import project.framework.Service;
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
import org.json.JSONArray;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.*;

//TODO METTERE A POSTO LE CLASSI CHE GESTISCONO L'UPDATE DELLE TABELLE
//TODO CAMBIARE BOTTONE PER LA RICERCA
//TODO FARE LA RICERCA NELLA CLASSE SPECIFICA
//TODO FARE LA GUI PER I SERVIZI CHE ESCONO DOPO AVER FATTO LA RICERCA

public class EuTrustServicesDashboard extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(EuTrustServicesDashboard.class.getResource("hello-view.fxml"));

        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);
        stage.setTitle("EU Trust Service Dashboard");
        stage.setScene(scene);
        stage.setResizable(false);

        CriteriaListFactory criteriaListFactory = new CriteriaListFactory();
        criteriaListFactory.initialize(); //fill all the data structure in CriteriaListFactory

        FilterController filter = criteriaListFactory.getFilterController();

        // Countries
        AnchorPane countriesPane = (AnchorPane) fxmlLoader.getNamespace().get("countriesAnchorPane");

        VBox countriesCheckBoxesLeft = new VBox();
        VBox countriesCheckBoxesRight = new VBox();

        Timeline countriesTPane = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {

            List<CheckBox> filteredCountries = filter.getCheckBoxes("c");
            countriesCheckBoxesLeft.getChildren().clear();
            countriesCheckBoxesRight.getChildren().clear();
            int separator = 0;
            for (CheckBox CBox : filteredCountries) {
                if (separator < (filteredCountries.size()/2))
                    countriesCheckBoxesLeft.getChildren().add(CBox);
                else
                    countriesCheckBoxesRight.getChildren().add(CBox);
                separator++;
            }
        }));

        countriesTPane.setCycleCount(Animation.INDEFINITE);
        countriesTPane.play();

        HBox countriesCheckBoxes = new HBox(countriesCheckBoxesLeft, countriesCheckBoxesRight);
        countriesCheckBoxes.setSpacing(15);

        AnchorPane.setTopAnchor(countriesCheckBoxes, 75.0);
        AnchorPane.setLeftAnchor(countriesCheckBoxes, 22.0);

        countriesCheckBoxes.setSpacing(55);
        countriesCheckBoxes.setAlignment(Pos.CENTER_LEFT);

        // Types
        //TypeCheckBoxController typeCheckBoxController = new TypeCheckBoxController(typesOfService);

        AnchorPane tosAnchorPane = (AnchorPane) fxmlLoader.getNamespace().get("tosAnchorPane");

        VBox tosCheckBoxesLeft = new VBox();
        VBox tosCheckBoxesRight = new VBox();

        Timeline tosPane = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            List<CheckBox> tosCBoxes = filter.getCheckBoxes("t");

            tosCheckBoxesLeft.getChildren().clear();
            tosCheckBoxesRight.getChildren().clear();

            int separator = 0;
            for (CheckBox CBox : tosCBoxes) {
                if (separator < tosCBoxes.size()/2)
                    tosCheckBoxesLeft.getChildren().add(CBox);
                else
                    tosCheckBoxesRight.getChildren().add(CBox);
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

        Timeline ssPane = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            List<CheckBox> ssCBoxes = filter.getCheckBoxes("s");

            ssCheckBoxesLeft.getChildren().clear();
            ssCheckBoxesRight.getChildren().clear();

            System.out.println(ssCBoxes);

            int separator = 0;
            for (CheckBox CBox : ssCBoxes) {
                if (separator < ssCBoxes.size()/2)
                    ssCheckBoxesLeft.getChildren().add(CBox);
                else
                    ssCheckBoxesRight.getChildren().add(CBox);
                separator++;
            }

        }));
        ssPane.setCycleCount(Animation.INDEFINITE);
        ssPane.play();

        HBox ssCheckBoxes = new HBox(ssCheckBoxesLeft, ssCheckBoxesRight);
        ssCheckBoxes.setSpacing(15);

        AnchorPane.setTopAnchor(ssCheckBoxes, 75.0);
        AnchorPane.setLeftAnchor(ssCheckBoxes, 22.0);


        ssCheckBoxes.setSpacing(55);
        ssCheckBoxes.setAlignment(Pos.CENTER_LEFT);



        // Provider
        AnchorPane scrollPane = (AnchorPane) fxmlLoader.getNamespace().get("providersScrollPAne");

        VBox providersCheckBoxes = new VBox();

        Timeline pane = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            List<CheckBox> filtered_providers = filter.getCheckBoxes("p");

            providersCheckBoxes.getChildren().clear();
            providersCheckBoxes.getChildren().addAll(filtered_providers);
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
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}