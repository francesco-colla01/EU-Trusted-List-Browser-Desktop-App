package project.graphics.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.framework.Provider;
import project.framework.SearchEngine;
import project.framework.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class ResultUI {

    public static Scene result(Stage stage, Scene backScene) throws IOException {

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("search-view.fxml"));


        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);

        Button backButton = (Button) fxmlLoader.getNamespace().get("backButton");
        backButton.setOnAction(actionEvent -> {
            stage.setScene(backScene);
        });

        Button anotherSearchButton = (Button) fxmlLoader.getNamespace().get("anotherSearchButton");
        anotherSearchButton.setOnAction(action -> {
            try {
                Scene searchScene = SearchUI.search(stage);
                searchScene.getStylesheets().add(Objects.requireNonNull(SearchUI.class.getResource("css/stylesheet.css")).toExternalForm());
                stage.setScene(searchScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //ResultUI display
        AnchorPane pane = (AnchorPane) fxmlLoader.getNamespace().get("resultAnchorPAne");

        VBox resultBox = new VBox();

        Accordion accordion = new Accordion();
        accordion.setPrefWidth(833);

        Map<Provider, Vector<Service>> searchResults = SearchEngine.getInstance().getSearchResults();
        Map<String, Accordion> countryToAccordion = new HashMap<>();
        searchResults.keySet().forEach(provider -> {
            String cc = provider.getCountryCode();
            String cn = provider.getCountryName();
            if (!countryToAccordion.containsKey(cc)) {
                TitledPane countryTPane = new TitledPane(cn, new Accordion());
                countryToAccordion.put(cc, (Accordion) countryTPane.getContent());
                accordion.getPanes().add(countryTPane);
            }


            Accordion countryAccordion = countryToAccordion.get(cc);

            Vector<Service> services = searchResults.get(provider);

            countryAccordion.setPadding(new Insets(0,0 ,0,18));

            ObservableList<Service> sList = FXCollections.observableArrayList();
            sList.addAll(services);

            TitledPane providerTPane = new TitledPane(provider.getName(), new ListView<>(sList));

            ListView serviceList = (ListView) providerTPane.getContent();

            serviceList.setCellFactory(svc -> new ListCell<Service>() {

                @Override
                protected void updateItem(Service service, boolean isEmpty) {
                    super.updateItem(service, isEmpty);

                    if (isEmpty || service == null ) {
                        setText(null);
                    } else
                        setText(service.getServiceName());
                }
            });

            serviceList.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    AnchorPane serviceInfoPane = (AnchorPane) fxmlLoader.getNamespace().get("serviceInfo");

                    VBox data = new VBox();

                    Label title = (Label) fxmlLoader.getNamespace().get("sInfoLabel");
                    title.setOpacity(1);


                    serviceInfoPane.getChildren().add(data);
                }
            });

            countryAccordion.getPanes().add(providerTPane);
        });

        pane.getChildren().add(accordion);

        //Executing search
        for (Provider p : searchResults.keySet()) {
            String tmp = p.getName() + ": ";
            Vector<Service> ser = searchResults.get(p);
            for (Service s : ser) tmp += s.getServiceName() + ", ";
            System.out.println(tmp.substring(0, tmp.length()-2));
        }

        scene.getStylesheets().add(Objects.requireNonNull(ResultUI.class.getResource("css/stylesheet.css")).toExternalForm());

        return scene;
    }
}
