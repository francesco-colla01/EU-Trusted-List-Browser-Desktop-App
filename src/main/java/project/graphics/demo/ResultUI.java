package project.graphics.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import project.framework.Provider;
import project.framework.SearchEngine;
import project.framework.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResultUI {

    public static Scene result(Stage stage, AtomicBoolean darkMode) throws IOException {

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("search-view.fxml"));



        //Scene creation
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);
        scene.getStylesheets().add(Objects.requireNonNull(ResultUI.class.getResource("css/stylesheet.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(ResultUI.class.getResource("css/statuses.css")).toExternalForm());


        Button backButton = (Button) fxmlLoader.getNamespace().get("backButton");
        backButton.setOnAction(actionEvent -> {
            CompleteUI.backScene();
        });

        Button anotherSearchButton = (Button) fxmlLoader.getNamespace().get("anotherSearchButton");
        anotherSearchButton.setOnAction(action -> {
            try {
                CompleteUI.swapScene("sl", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //ResultUI display
        AnchorPane pane = (AnchorPane) fxmlLoader.getNamespace().get("resultAnchorPAne");


        Accordion accordion = new Accordion();
        accordion.setPrefWidth(833);

        Map<Provider, Vector<Service>> searchResults = SearchEngine.getInstance().getSearchResults();
        Map<String, Accordion> countryToAccordion = new HashMap<>();
        searchResults.keySet().forEach(provider -> {
            String cc = provider.getCountryCode();
            String cn = provider.getCountryName();
            if (!countryToAccordion.containsKey(cc)) {
                TitledPane countryTPane = new TitledPane(cn, new Accordion());
                countryTPane.getStyleClass().add("country-titled-pane");
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

            serviceList.setOnMouseClicked(mouseEvent -> {
                if (serviceList.getSelectionModel().getSelectedItem() == null)
                    return;

                AnchorPane ePane = (AnchorPane) fxmlLoader.getNamespace().get("infoErasablePane");

                ePane.getChildren().clear();
                ePane.getStyleClass().add("info-pane");

                VBox dataText = new VBox();
                VBox dataValue = new VBox();
                HBox data = new HBox(dataText, dataValue);
                data.setSpacing(15.0);

                Service s = (Service) serviceList.getSelectionModel().getSelectedItem();
                Vector<String> info = s.getServiceInfo();
                String serviceName = info.get(0);
                String providerName = info.get(1);
                String countryName = info.get(2);
                String serviceStatus = info.get(3);
                String typeIdentifier = info.get(4);
                String[] serviceTypes = info.subList(5, info.size()).toArray(new String[0]);

                Label title = (Label) fxmlLoader.getNamespace().get("sInfoLabel");
                title.setOpacity(1);

                //dataText.getChildren().add(new Text("Service Name:"));
                dataValue.getChildren().add(new Text(serviceName));//

                dataText.getChildren().add(new Text("Provider Name:"));
                dataValue.getChildren().add(new Text(providerName));

                dataText.getChildren().add(new Text("Country:"));
                String cn1 = countryName;
                Image flag = new Image("https://countryflagsapi.com/png/" + cn1.replaceAll(" ", "%20"));
                ImageView flagNode = new ImageView(flag);
                flagNode.setFitWidth(27);
                flagNode.setFitHeight(15);
                dataValue.getChildren().add(new TextFlow(flagNode, new Text(" " + cn1)));

                //Status
                dataText.getChildren().add(new Text("Service status:"));
                String cs = serviceStatus;
                Button voidStatusButton = new Button(cs);
                switch (cs) {
                    case "granted":
                        voidStatusButton.getStyleClass().add("granted");
                        break;

                    case "withdrawn":
                        voidStatusButton.getStyleClass().add("withdrawn");
                        break;

                    case "deprecatedatnationallevel":
                        voidStatusButton.getStyleClass().add("deprecatedatnationallevel");
                        break;

                    case "recognisedatnationallevel":
                        voidStatusButton.getStyleClass().add("recognisedatnationallevel");
                        break;

                    default:
                        voidStatusButton.getStyleClass().add("default");
                        break;
                }
                dataValue.getChildren().add(voidStatusButton);

                dataText.getChildren().add(new Text("Service Type:"));
                HBox buttonvec = new HBox();
                Arrays.stream(serviceTypes).toList().forEach(type -> {
                    Button butt = new Button(type);
                    butt.getStyleClass().add("default");
                    buttonvec.getChildren().add(butt);
                });

                buttonvec.setSpacing(5);
                dataValue.getChildren().add(buttonvec);

                dataText.getChildren().add(new Text("Type Identifier:"));
                dataValue.getChildren().add((new Hyperlink(typeIdentifier)));

                AnchorPane.setLeftAnchor(data, 10.0);

                data.setSpacing(18);
                dataText.setSpacing(23);
                dataValue.setSpacing(20);
                ePane.getChildren().add(data);
            });

            countryAccordion.getPanes().add(providerTPane);
        });

        pane.getChildren().add(accordion);

        //Executing search
        /*for (Provider p : searchResults.keySet()) {
            String tmp = p.getName() + ": ";
            Vector<Service> ser = searchResults.get(p);
            for (Service s : ser) tmp += s.getServiceName() + ", ";
            System.out.println(tmp.substring(0, tmp.length()-2));
        }*/

        scene.getStylesheets().add(Objects.requireNonNull(ResultUI.class.getResource("css/stylesheet.css")).toExternalForm());

        if(darkMode.get()){
            
        }

        return scene;
    }
}
