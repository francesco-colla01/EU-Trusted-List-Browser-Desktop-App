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
    private Vector<String> invalidSelectedTypes = new Vector<>();

    /**
     * TypeFilterController constructor: creates all the checkboxes
     *
     * @see CriteriaListFactory
     */
    public TypeFilterController() {
        Vector<String> allTypes = CriteriaListFactory.getTypeList();

        for (String type : allTypes) {
            //The value passed is what is shown in the UI, the checkbox text
            CheckBox typeCheckBox = new CheckBox(type);
            typeCheckBox.getStyleClass().add("countries-check-box");
            typeCheckBox.setDisable(true);

            //If the checkbox is selected add it to the selected types, otherwise remove it
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

    /**
     * get the checkboxes to show on screen
     *
     * @param c the vector returned from CountryFilterController.getCriteriaForCheckboxes()
     * @param s the vector returned from StatusFilterController.getCriteriaForCheckboxes()
     * @param p the vector returned from ProviderFilterController.getCriteriaForCheckboxes()
     * @return a list with the checkboxes to show on screen
     */
    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<Provider> p, Vector<String> s) {
        //clears the vectors containing filtered and invalid selected types
        filteredTypes.clear();
        invalidSelectedTypes.clear();

        //by default, disables every checkbox (the checkboxes to be shown will be activated later)
        typesCheckBox.forEach(CBox -> {
            CBox.setDisable(true);
        });

        //for every provider passed as parameter
        //(see ProviderFilterController.getCriteriaForCheckboxes())
        for (Provider prov : p) {

            //if the country code is included in those passed as parameters
            //(see CountryFilterController.getCriteriaForCheckboxes())
            if (c.contains(prov.getCountryCode())) {

                //start iterating over the provider's services
                Service[] services = prov.getServices();

                for (Service ser : services) {

                    //if the service status is included in those passed as parameters
                    //(see StatusFilterController.getCriteriaForCheckboxes())
                    if (s.contains(ser.getCurrentStatus())) {

                        //check every type checkbox
                        //if a checkbox's type is included in the types associated with the provider
                        //and is not already included in the filtered types, the checkbox is activated
                        //and the type is included in the filtered types
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

        //for every checkbox, if the type is not valid (it is selected but is not included
        //in the filtered checkboxes, those that can be displayed), the checkbox is added
        //to the invalid ones, is marked in red and is activated (so that the user may deselect it)
        typesCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredTypes.contains(CBox.getText())) {
                invalidSelectedTypes.add(CBox.getText());
                CBox.setDisable(false);
                CBox.setStyle("-fx-text-fill: #b50202;");
            }});

        return typesCheckBox;
    }

    /**
     * @return a Vector with the criteria to use for the search
     */
    public Vector<String> getCriteria() {
        Vector<String> tmp = new Vector<>();

        //add every selected type that is not invalid
        for (String t : selectedTypes) {
            if (!invalidSelectedTypes.contains(t)) tmp.add(t);
        }

        //if no type was added (no valid criteria selected), every displayed type
        //will be returned, and a null will be added to represent such case
        if (tmp.isEmpty()) {
            for (String t : filteredTypes) tmp.add(t);
            tmp.add(null);
        }

        //if any types were added (valid criteria selected), every invalid
        //selected criteria is added to the criteria list, followed by a number representing
        //how many invalid criteria have been selected
        else {
            int invalids = 0;
            for (String invalid : invalidSelectedTypes) {
                tmp.add(invalid);
                invalids++;
            }
            tmp.add(Integer.toString(invalids));
        }

        return tmp;
    }

    /**
     * @return a vector with the criteria needed by the getCheckBoxes methods: returns the selected types
     * if any are selected, returns every type otherwise
     */
    public Vector<String> getCriteriaForCheckboxes() {
        if (selectedTypes.isEmpty())
            return CriteriaListFactory.getTypeList();
        return selectedTypes;
    }
}
