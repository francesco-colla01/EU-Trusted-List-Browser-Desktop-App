package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;

import java.util.List;
import java.util.Vector;

public class StatusFilterController {
    private Vector<String> selectedStatuses = new Vector<>();
    private Vector<String> filteredStatuses = new Vector<>();
    private List<CheckBox> statusesCheckBox = new Vector<>();
    private List<CheckBox> filteredStatusCheckBox = new Vector<>();
    private Vector<String> invalidSelectedStatuses = new Vector<>();

    /**
     * StatusFilterController constructor: creates all the checkboxes
     *
     * @see CriteriaListFactory
     */
    public StatusFilterController() {
        Vector<String> allStatuses = CriteriaListFactory.getStatusList();

        for(String status : allStatuses) {
            //The value passed is what is shown in the UI, the checkbox text
            CheckBox statusCheckBox = new CheckBox(status);
            statusCheckBox.getStyleClass().add("countries-check-box");
            statusCheckBox.setDisable(true);

            //If the checkbox is selected add it to the selected statuses, otherwise remove it
            statusCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (statusCheckBox.isSelected())
                    selectedStatuses.add(statusCheckBox.getText());
                else {
                    selectedStatuses.remove(statusCheckBox.getText());
                    statusCheckBox.setStyle("-fx-text-fill:  black;");
                }
            });

            statusesCheckBox.add(statusCheckBox);
        }
    }

    /**
     * get the checkboxes to show on screen
     *
     * @param c the vector returned from CountryFilterController.getCriteriaForCheckboxes()
     * @param p the vector returned from ProviderFilterController.getCriteriaForCheckboxes()
     * @param t the vector returned from TypeFilterController.getCriteriaForCheckboxes()
     * @return a list with the checkboxes to show on screen
     */
    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<Provider> p, Vector<String> t) {
        //clears the vectors containing filtered and invalid selected statuses
        filteredStatuses.clear();
        invalidSelectedStatuses.clear();

        //by default, disables every checkbox (the checkboxes to be shown will be activated later)
        statusesCheckBox.forEach(CBox -> {
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

                for (Service s : services) {

                    //start iterating over the service types
                    String[] types = s.getServiceTypes();

                    for (String type : types) {

                        //if the type is included in those passed as parameters
                        //(see TypesFilterController.getCriteriaForCheckboxes())
                        //and the status is not already included in the filtered statuses,
                        //add it to the filtered statuses
                        String cs = s.getCurrentStatus();
                        if (t.contains(type) && !filteredStatuses.contains(cs))
                            filteredStatuses.add(cs);
                    }
                }

                //if a checkbox has to be shown (the filtered statuses include the status
                //corresponding to the checkbox), activate the checkbox
                for (CheckBox CBox : statusesCheckBox) {
                    if (filteredStatuses.contains(CBox.getText())) {
                        CBox.setStyle("-fx-text-fill:  black;");
                        CBox.setDisable(false);
                    }
                }
            }
        }

        //for every checkbox, if the status is not valid (it is selected but is not included
        //in the filtered checkboxes, those that can be displayed), the checkbox is added
        //to the invalid ones, is marked in red and is activated (so that the user may deselect it)
        statusesCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredStatuses.contains(CBox.getText())) {
                filteredStatusCheckBox.add(CBox);
                invalidSelectedStatuses.add(CBox.getText());
                CBox.setStyle("-fx-text-fill: #b50202;");
                CBox.setDisable(false);
            }});

        return statusesCheckBox;
    }

    /**
     * @return a Vector with the criteria to use for the search
     */
    public Vector<String> getCriteria() {
        Vector<String> tmp = new Vector<>();

        //add every selected status that is not invalid
        for (String s : selectedStatuses) {
            if (!invalidSelectedStatuses.contains(s)) tmp.add(s);
        }

        //if no status was added (no valid criteria selected), every displayed status
        //will be returned, and a null will be added to represent such case
        if (tmp.isEmpty()) {
            for (String s : filteredStatuses) tmp.add(s);
            tmp.add(null);
        }

        //if any statuses were added (valid criteria selected), every invalid
        //selected criteria is added to the criteria list, followed by a number representing
        //how many invalid criteria have been selected
        else {
            int invalids = 0;
            for (String invalid : invalidSelectedStatuses) {
                tmp.add(invalid);
                invalids++;
            }
            tmp.add(Integer.toString(invalids));
        }

        return tmp;
    }

    /**
     * @return a vector with the criteria needed by the getCheckBoxes methods: returns the selected statuses
     * if any are selected, returns every status otherwise
     */
    public Vector<String> getCriteriaForCheckboxes() {
        if (selectedStatuses.isEmpty())
            return CriteriaListFactory.getStatusList();
        return selectedStatuses;
    }
}
