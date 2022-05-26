package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TypeFilterController {
    private Vector<String> selectedTypes = new Vector<>();
    private Vector<String> filteredTypes = new Vector<>();
    private List<CheckBox> typesCheckBox = new Vector<>();
    private List<CheckBox> filteredTypesCheckBox = new Vector<>();

    public TypeFilterController() {
        generateTypesCheckBoxes();
    }

    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<Provider> p, Vector<String> s) {
        filteredTypesCheckBox.clear();
        filteredTypes.clear();

        for (Provider prov : p) {
            if (c.contains(prov.getCountryCode())) {
                Service[] services = prov.getServices();

                for (Service ser : services) {

                    if (s.contains(ser.getCurrentStatus())) {

                        Vector<String> types = new Vector<>(Arrays.asList(prov.getServiceTypes()));

                        for (CheckBox CBox : typesCheckBox) {
                            if (types.contains(CBox.getText()) && !filteredTypes.contains(CBox.getText())) {
                                filteredTypesCheckBox.add(CBox);
                                filteredTypes.add(CBox.getText());
                                CBox.setStyle("-fx-text-fill:  black;");
                            }
                        }
                    }
                }
            }
        }

        typesCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredTypesCheckBox.contains(CBox)) {
                filteredTypesCheckBox.add(CBox);
                CBox.setStyle("-fx-text-fill: #b50202;");
            }});

        return filteredTypesCheckBox;
    }

    public Vector<String> getSelectedCriteria() {
        return selectedTypes;
    }

    public Vector<String> getFilterCriteria() {
        if (selectedTypes.isEmpty())
            return CriteriaListFactory.getTypeList();
        return selectedTypes;
    }

    private void generateTypesCheckBoxes() {
        Vector<String> allTypes = CriteriaListFactory.getTypeList();
        for (String type : allTypes) {
            CheckBox typeCheckBox = new CheckBox(type);
            typeCheckBox.getStyleClass().add("countries-check-box");
            typeCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (typeCheckBox.isSelected())
                    selectedTypes.add(typeCheckBox.getText());
                else {
                    selectedTypes.remove(typeCheckBox.getText());
                    typeCheckBox.setStyle("-fx-text-fill:  black;");
                }
            });
            typesCheckBox.add(typeCheckBox);
        }
    }
}
