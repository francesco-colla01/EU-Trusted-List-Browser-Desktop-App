package project.graphics.demo;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResultUI {

    public static Scene result(Stage stage) throws IOException {

        //Loading file xml
        FXMLLoader fxmlLoader = new FXMLLoader(SearchUI.class.getResource("result-view.fxml"));



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

                VBox data = new VBox();
                data.setSpacing(15.0);

                Service s = (Service) serviceList.getSelectionModel().getSelectedItem();
                Vector<String> info = s.getServiceInfo();
                String providerName = info.get(0);
                String countryName = info.get(1);
                String serviceStatus = info.get(2);
                String typeIdentifier = info.get(3);
                String[] serviceTypes = info.subList(4, info.size()).toArray(new String[0]);

                Label title = (Label) fxmlLoader.getNamespace().get("sInfoLabel");
                title.setOpacity(1);

                HBox nameBox = new HBox(new Text("Provider Name:"), new Text(providerName));
                nameBox.setSpacing(20);

                String cn1 = countryName;
                Image flag = new Image("https://countryflagsapi.com/png/" + cn1.replaceAll(" ", "%20"));
                ImageView flagNode = new ImageView(flag);
                flagNode.setFitWidth(27);
                flagNode.setFitHeight(15);
                HBox countryBox = new HBox(new Text("Country:"), new HBox(flagNode, new Text(" " + cn1)));
                countryBox.setSpacing(20);

                //Status
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
                HBox statusBox = new HBox(new Text("Service status:"), voidStatusButton);
                statusBox.setSpacing(20);

                HBox buttonvec = new HBox();
                Arrays.stream(serviceTypes).toList().forEach(type -> {
                    Button butt = new Button(type);
                    butt.getStyleClass().add("default");
                    buttonvec.getChildren().add(butt);
                });

                buttonvec.setSpacing(5);
                HBox typeBox = new HBox(new Text("Service Type:"), buttonvec);
                typeBox.setSpacing(20);

                Hyperlink link = new Hyperlink(typeIdentifier);
                HBox linkBox = new HBox(new Text("Type Identifier:"), link);
                linkBox.setSpacing(20);

                link.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Desktop.getDesktop().browse(new URI(link.getText()));

                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });

                AnchorPane.setLeftAnchor(data, 10.0);

                data.getChildren().addAll(FXCollections.observableArrayList(nameBox, countryBox, statusBox, typeBox, linkBox));
                data.setSpacing(18);
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


        return scene;
    }
}
