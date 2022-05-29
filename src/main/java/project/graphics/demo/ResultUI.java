package project.graphics.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.framework.Provider;
import project.framework.SearchEngine;
import project.framework.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ResultUI {

    public static Scene result(Stage stage, Scene backScene, Map<Provider, Vector<Service>> searchResults) throws IOException {

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("search-view.fxml"));


        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);

        Button backButton = (Button) fxmlLoader.getNamespace().get("backButton");
        backButton.setOnAction(actionEvent -> {
            stage.setScene(backScene);
        });

        //ResultUI display
        AnchorPane pane = (AnchorPane) fxmlLoader.getNamespace().get("resultAnchorPAne");

        VBox resultBox = new VBox();

        Accordion accordion = new Accordion();
        accordion.setPrefWidth(1250);
        accordion.setMaxHeight(675);

        Map<String, Accordion> countryToAccordion = new HashMap<>();
        searchResults.keySet().forEach(provider -> {
            if (!countryToAccordion.containsKey(provider.getCountryCode())) {
                TitledPane countryTPane = new TitledPane(provider.getCountryCode(), new Accordion());
                countryTPane.getStyleClass().add("country-titled-pane");
                countryToAccordion.put(provider.getCountryCode(), (Accordion) countryTPane.getContent());
                accordion.getPanes().add(countryTPane);
            }


            Accordion countryAccordion = countryToAccordion.get(provider.getCountryCode());

            Vector<Service> services = searchResults.get(provider);

            TitledPane providerTPane = new TitledPane(provider.getName(), new ListView<>());
            ListView serviceList = (ListView) providerTPane.getContent();
            serviceList.getStyleClass().add("service-list");
            services.forEach(service -> {
                serviceList.getItems().add(service.getServiceName());
            });
            countryAccordion.getPanes().add(providerTPane);
        });

        pane.getChildren().add(accordion);

        //Executing search
        Map<Provider, Vector<Service>> results = SearchEngine.getInstance().getSearchResults();
        for (Provider p : results.keySet()) {
            String tmp = p.getName() + ": ";
            Vector<Service> ser = results.get(p);
            for (Service s : ser) tmp += s.getServiceName() + ", ";
            System.out.println(tmp.substring(0, tmp.length()-2));
        }


        return scene;
    }
}
