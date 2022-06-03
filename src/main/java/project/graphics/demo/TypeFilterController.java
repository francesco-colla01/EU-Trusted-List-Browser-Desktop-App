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
    private Vector<String> invalidSelectedTypes = new Vector<>();

    public TypeFilterController() {
        Vector<String> allTypes = CriteriaListFactory.getTypeList();
        for (String type : allTypes) {
            CheckBox typeCheckBox = new CheckBox(type);
            typeCheckBox.getStyleClass().add("countries-check-box");
            typeCheckBox.setDisable(true);
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

    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<Provider> p, Vector<String> s) {
        filteredTypes.clear();
        invalidSelectedTypes.clear();

        typesCheckBox.forEach(CBox -> {
            CBox.setDisable(true);
        });

        for (Provider prov : p) {
            if (c.contains(prov.getCountryCode())) {
                Service[] services = prov.getServices();

                for (Service ser : services) {

                    if (s.contains(ser.getCurrentStatus())) {

                        Vector<String> types = new Vector<>(Arrays.asList(prov.getServiceTypes()));

                        for (CheckBox CBox : typesCheckBox) {
                            if (types.contains(CBox.getText()) && !filteredTypes.contains(CBox.getText())) {
                                CBox.setDisable(false);
                                filteredTypes.add(CBox.getText());
                                CBox.setStyle("-fx-text-fill:  black;");
                            }
                        }
                    }
                }
            }
        }

        typesCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredTypes.contains(CBox.getText())) {
                invalidSelectedTypes.add(CBox.getText());
                CBox.setDisable(false);
                CBox.setStyle("-fx-text-fill: #b50202;");
            }});

        return typesCheckBox;
    }

    public Vector<String> getCriteria() {
        Vector<String> tmp = new Vector<>();
        for (String t : selectedTypes) {
            if (!invalidSelectedTypes.contains(t)) tmp.add(t);
        }
        if (tmp.isEmpty()) {
            for (String t : filteredTypes) tmp.add(t);
            tmp.add(null);
        }
        return tmp;
    }

    public Vector<String> getFilterCriteria() {
        if (selectedTypes.isEmpty())
            return CriteriaListFactory.getTypeList();
        return selectedTypes;
    }
}
