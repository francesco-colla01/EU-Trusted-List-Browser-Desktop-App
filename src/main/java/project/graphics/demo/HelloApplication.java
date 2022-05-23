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

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carico file xml
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        //Creazione scena
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);

        CriteriaListFactory criteriaLists = new CriteriaListFactory();
        criteriaLists.initialize();

        /*HttpRequest fetchContriesList = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        JSONArray jsonCountriesList = new JSONArray(fetchContriesList.getResponse());

        Vector<String> countries = new Vector<>();
        Map<String, String> countryNameToCode = new HashMap<>();

        for (int i = 0; i<jsonCountriesList.length(); i++) {
            countries.add(jsonCountriesList.getJSONObject(i).getString("countryName"));
            countryNameToCode.put(jsonCountriesList.getJSONObject(i).getString("countryName"), jsonCountriesList.getJSONObject(i).getString("countryCode"));
        }

        HttpRequest fetchAllProviders = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
        JSONArray jsonProvidersList = new JSONArray(fetchAllProviders.getResponse());
        Provider[] all_tsp = new Provider[jsonProvidersList.length()];

        Multimap<String, Provider> countryMap = ArrayListMultimap.create();
        Multimap<String, Provider> typeMap = ArrayListMultimap.create();
        Vector<String> typesOfService = new Vector<>();
        Vector<Provider> providers = new Vector<>();
        Vector<String> statuses = new Vector<>();

        // Riempimento mappe e vettori iniziali
        for (int i = 0; i<jsonProvidersList.length(); i++) {

            //Decodifica json
            all_tsp[i] = new Provider(jsonProvidersList.getJSONObject(i).toString());

            //inserimento mappa key Country value provider
            countryMap.put(all_tsp[i].getCountryCode(), all_tsp[i]);

            //inserimento vector provider e vettore copia
            providers.add(all_tsp[i]);

            //inserimento vector statuses
            for (int j = 0; j<all_tsp[i].getServices().length; j++) {
                Service[] s = all_tsp[i].getServices();
                typeMap.put(s[j].getCurrentStatus(), all_tsp[i]);
                if (!statuses.contains(s[j].getCurrentStatus()))
                    statuses.add(s[j].getCurrentStatus());
            }

            //Inserimento mappa Service Types
            for (int j = 0; j<all_tsp[i].getServiceTypes().length; j++) {
                String[] s = all_tsp[i].getServiceTypes();
                typeMap.put(s[j], all_tsp[i]);
                if (!typesOfService.contains(s[j]))
                    typesOfService.add(s[j]);
            }
        }

        FilterController filter = new FilterController(countryNameToCode, typesOfService, statuses, providers);
        */

        FilterController filter = criteriaLists.getFilterController();
        // Countries
        AnchorPane countriesPane = (AnchorPane) fxmlLoader.getNamespace().get("countriesAnchorPane");

        VBox countriesCheckBoxesLeft = new VBox();
        VBox countriesCheckBoxesRight = new VBox();

        Timeline countriesTPane = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {

            List<CheckBox> filteredCountries = filter.filterCountries();
            System.out.println(filteredCountries);
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


        // Provider
        AnchorPane scrollPane = (AnchorPane) fxmlLoader.getNamespace().get("providersScrollPAne");

        VBox providersCheckBoxes = new VBox();

        Timeline pane = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            List<CheckBox> filtered_providers = filter.filterProviders();

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
        scrollPane.getChildren().add(providersCheckBoxes);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/stylesheet.css")).toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}