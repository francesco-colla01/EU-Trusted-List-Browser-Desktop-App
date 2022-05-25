package project.graphics.demo;

import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ProviderFilterController {
    //private Vector<Provider> allProviders; da CLF
    private Vector<Provider> selectedProviders = new Vector<>();
    private List<CheckBox> providersCheckBox = new Vector<>();
    private List<CheckBox> filteredProviderCheckBox = new Vector<>();
    private Map<String, Provider> nameToProvider = new HashMap<>();

    public ProviderFilterController() {
        generateProviderCheckboxes();
    }

    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<String> t, Vector<String> s) {
        filteredProviderCheckBox.clear();
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

                            if (s.contains(service.getServiceInfo().getCurrentStatus())) {
                                filteredProviderCheckBox.add(providerCBox);
                                break outerloop;

                            }
                        }
                    }
                }
            }
        }
        return filteredProviderCheckBox;
    }

    public Vector<Provider> getSelectedCriteria() {
        return selectedProviders;
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
            CheckBox providerCheckBox = new CheckBox(provider.getName());
            providerCheckBox.getStyleClass().add("provider-check-box");
            providerCheckBox.setId(provider.getName() + provider.getCountryCode());
            providerCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (providerCheckBox.isSelected())
                    selectedProviders.add(nameToProvider.get(providerCheckBox.getText()));
                else
                    selectedProviders.remove(nameToProvider.get(providerCheckBox.getText()));
            });
            providersCheckBox.add(providerCheckBox);
        }
    }
}
