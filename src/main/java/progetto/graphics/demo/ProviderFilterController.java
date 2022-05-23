package progetto.graphics.demo;

import progetto.framework.Provider;
import progetto.framework.Service;
import javafx.scene.control.CheckBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ProviderFilterController {

    private Vector<Provider> filtered_providers = new Vector<>();
    private Vector<Provider> providers;
    private List<CheckBox> providersCheckBox = new Vector<>();
    private List<CheckBox> filteredCheckBox = new Vector<>();
    private Map<String, Provider> nameToProvider = new HashMap<>();
    //private Vector<CheckBox> selectedCheckBox = new Vector<>();

    public ProviderFilterController(Vector<Provider> providers) {
        this.providers = providers;

        for (Provider p : providers) {
            CheckBox providerCheckBox = new CheckBox(p.getName());
            providerCheckBox.getStyleClass().add("provider-check-box");
            providersCheckBox.add(providerCheckBox);

            nameToProvider.put(p.getName(), p);
        }
    }

    List<CheckBox> filterProviders(Vector<String> countries, Vector<String> types, Vector<String> statuses) {
        filteredCheckBox.clear();
        for (CheckBox providerCBox : providersCheckBox) {
            Provider provider = nameToProvider.get(providerCBox.getText());

            if (countries.contains(provider.getCountryCode())) {

                String[] providerTypes = provider.getServiceTypes();

                outerloop:
                for (int i = 0; i < providerTypes.length; i++) {
                    String providerType = providerTypes[i];

                    if (types.contains(providerType)) {

                        Service[] s = provider.getServices();
                        for (int j = 0; j< s.length; j++) {
                            Service service = s[j];

                            if (statuses.contains(service.getCurrentStatus())) {
                                filteredCheckBox.add(providerCBox);
                                break outerloop;

                            } else if (j == (s.length-1))
                                providerCBox.setSelected(false);
                        }
                    } else if(i == (providerTypes.length -1))
                        providerCBox.setSelected(false);
                }
            } else
                providerCBox.setSelected(false);
        }
        return filteredCheckBox;
    }
}
