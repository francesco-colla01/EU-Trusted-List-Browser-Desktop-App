package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class StatusFilterController {
    private Vector<String> selectedStatuses = new Vector<>();
    private Vector<String> filteredStatuses = new Vector<>();
    private List<CheckBox> statusesCheckBox = new Vector<>();
    private List<CheckBox> filteredStatusCheckBox = new Vector<>();

    public StatusFilterController() {
        generateStatusCheckboxes();
    }

    /**
     *creates a list of the selected checkboxes to filter
     *
     *
     * @param c, p, t vectors of the selected checkboxes
     *
     * @throws IOException
     *
     * @return filteredStatusCheckBox list of the checkboxes????
     *
     *
     * @see
     */
    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<Provider> p, Vector<String> t) {
        filteredStatuses.clear();
        filteredStatusCheckBox.clear();

        for (Provider prov : p) {

            if (c.contains(prov.getCountryCode())) {
                Vector<String> types = new Vector<>(Arrays.asList(prov.getServiceTypes()));

                outerloop:
                for (String type : types) {
                    if (t.contains(type)) {
                        Service[] services = prov.getServices();
                        Vector<String> ss = new Vector<>();

                        for (Service service : services) {
                            if (!ss.contains(service.getServiceInfo().getCurrentStatus()))
                                ss.add(service.getServiceInfo().getCurrentStatus());
                        }

                        for (CheckBox CBox : statusesCheckBox) {
                            if (ss.contains(CBox.getText()) && !filteredStatuses.contains(CBox.getText())) {
                                filteredStatusCheckBox.add(CBox);
                                filteredStatuses.add(CBox.getText());
                                break outerloop;
                            }
                        }
                    }
                }
            }
        }

        return filteredStatusCheckBox;
    }

    public Vector<String> getSelectedCriteria() {
        return selectedStatuses;
    }

    public Vector<String> getFilterCriteria() {
        if (selectedStatuses.isEmpty())
            return CriteriaListFactory.getStatusList();
        return selectedStatuses;
    }

    private void generateStatusCheckboxes() {
        Vector<String> allStatuses = CriteriaListFactory.getStatusList();
        for(String status : allStatuses) {
            CheckBox statusCheckBox = new CheckBox(status);
            statusCheckBox.getStyleClass().add("countries-check-box");
            statusCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (statusCheckBox.isSelected())
                    selectedStatuses.add(statusCheckBox.getText());
                else
                    selectedStatuses.remove(statusCheckBox.getText());
            });
            statusesCheckBox.add(statusCheckBox);
        }
    }
}
