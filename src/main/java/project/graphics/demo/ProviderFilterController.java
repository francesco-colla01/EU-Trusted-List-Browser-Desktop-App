package project.graphics.demo;

import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;
import javafx.scene.control.CheckBox;

import java.util.*;

public class ProviderFilterController {
    private Vector<Provider> selectedProviders = new Vector<>();
    private List<CheckBox> providersCheckBox = new Vector<>();
    private List<CheckBox> filteredProviderCheckBox = new Vector<>();
    private Map<String, Provider> nameToProvider = new HashMap<>();
    private Vector<Provider> invalidSelectedProviders = new Vector<>();

    public ProviderFilterController() {
        Vector<Provider> p = CriteriaListFactory.getProviderList();
        for (Provider provider : p) {
            String name = provider.getName();
            String cc = provider.getCountryCode();
            nameToProvider.put(name + cc, provider);
            CheckBox providerCheckBox = new CheckBox(provider.getName());
            providerCheckBox.getStyleClass().add("provider-check-box");
            providerCheckBox.setId(name + cc);
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

    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<String> t, Vector<String> s) {
        filteredProviderCheckBox.clear();
        invalidSelectedProviders.clear();
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
                invalidSelectedProviders.add(nameToProvider.get(CBox.getId()));
                CBox.setStyle("-fx-text-fill: #b50202;");
            }});

        if (filteredProviderCheckBox.isEmpty() && selectedProviders.isEmpty())
                return null;

        return filteredProviderCheckBox;
    }

    public Vector<Provider> getCriteria() {
        Vector<Provider> tmp = new Vector<>();
        for (Provider p : selectedProviders) {
            if (!invalidSelectedProviders.contains(p)) tmp.add(p);
        }
        if (tmp.isEmpty()) {
            for (CheckBox cb : filteredProviderCheckBox) tmp.add(nameToProvider.get(cb.getId()));
            tmp.add(null);
        }
        return tmp;
    }

    public int getSelectedProviderSize() {
        return selectedProviders.size();
    }

    public Vector<Provider> getFilterCriteria() {
        if (selectedProviders.isEmpty())
            return CriteriaListFactory.getProviderList();
        return selectedProviders;
    }
}
