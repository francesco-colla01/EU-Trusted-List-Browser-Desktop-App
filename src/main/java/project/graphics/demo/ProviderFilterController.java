package project.graphics.demo;

import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;
import javafx.scene.control.CheckBox;

import java.util.*;

public class ProviderFilterController {
    //private Vector<Provider> allProviders; da CLF
    private Vector<Provider> selectedProviders = new Vector<>();
    private List<CheckBox> providersCheckBox = new Vector<>();
    private List<CheckBox> filteredProviderCheckBox = new Vector<>();
    private Map<String, Provider> nameToProvider = new HashMap<>();
    private Map<String, Provider> nameToProvider2 = new HashMap<>();
    private List<CheckBox> invalidSelectedCheckBoxes = new Vector<>();

    public ProviderFilterController() { generateProviderCheckboxes(); }

    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<String> t, Vector<String> s) {
        filteredProviderCheckBox.clear();
        invalidSelectedCheckBoxes.clear();
        for (CheckBox providerCBox : providersCheckBox) {

            Provider provider = nameToProvider.get(providerCBox.getId());

            if (c.contains(provider.getCountryCode())) {

                String[] providerTypes = provider.getServiceTypes();

                outerloop:
                for (int i = 0; i < providerTypes.length; i++) {
                    String providerType = providerTypes[i];

                    if (t.contains(providerType)) {

                        Service[] ser = provider.getServices();
                        for (int j = 0; j< ser.length; j++) {
                            Service service = ser[j];

                            boolean contains = false;
                            for (String typo : service.getServiceTypes()) {
                                if (t.contains(typo))
                                    contains = true;
                                break;
                            }

                            if (s.contains(service.getCurrentStatus()) && contains) {
                                filteredProviderCheckBox.add(providerCBox);
                                providerCBox.setStyle("-fx-text-fill:  black;");
                                break outerloop;
                            }
                        }
                    }
                }
            }
        }

        providersCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredProviderCheckBox.contains(CBox)) {
                filteredProviderCheckBox.add(CBox);
                invalidSelectedCheckBoxes.add(CBox);
                CBox.setStyle("-fx-text-fill: #b50202;");
            }});

        if (filteredProviderCheckBox.isEmpty() && selectedProviders.isEmpty())
                return null;

        return filteredProviderCheckBox;
    }

    public Vector<Provider> getCriteria() {
        if (selectedProviders.isEmpty()) {
            Vector<Provider> tmp = new Vector<>();
            for (CheckBox cb : filteredProviderCheckBox) tmp.add(nameToProvider2.get(cb.getText()));
            return tmp;
        }
        for (CheckBox cb : invalidSelectedCheckBoxes) {
            Provider p = nameToProvider2.get(cb.getText());
            if (selectedProviders.contains(p)) selectedProviders.remove(p);
        }
        return selectedProviders;
    }

    public int getSelectedProviderSize() {
        return selectedProviders.size();
    }

    public Vector<Provider> getFilterCriteria() {
        if (selectedProviders.isEmpty())
            return CriteriaListFactory.getProviderList();
        return selectedProviders;
    }

    private void generateProviderCheckboxes() {
        Vector<Provider> p = CriteriaListFactory.getProviderList();
        for (Provider provider : p) {
            nameToProvider.put(provider.getName() + provider.getCountryCode(), provider);
            nameToProvider2.put(provider.getName(), provider);
            CheckBox providerCheckBox = new CheckBox(provider.getName());
            providerCheckBox.getStyleClass().add("provider-check-box");
            providerCheckBox.setId(provider.getName() + provider.getCountryCode());
            providerCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (providerCheckBox.isSelected())
                    selectedProviders.add(nameToProvider.get(providerCheckBox.getId()));
                else {
                    selectedProviders.remove(nameToProvider.get(providerCheckBox.getId()));
                    providerCheckBox.setStyle("-fx-text-fill:  black;");
                }
            });
            providersCheckBox.add(providerCheckBox);
        }
    }
}
