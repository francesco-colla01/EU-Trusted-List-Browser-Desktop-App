package project.graphics.demo;

import project.framework.Provider;
import project.framework.Service;
import javafx.scene.control.CheckBox;

import java.util.*;

public class FilterController {

    // Countries
    private Vector<String> selectedCountries = new Vector<>();
    private Vector<String> allCountries = new Vector<>();
    private Map<String, String> countryNameToCode;
    private Map<String, String> codeToCountryName = new HashMap<>();
    private List<CheckBox> countriesCheckBox = new Vector<>();
    private List<CheckBox> filteredCountriesCheckBox = new Vector<>();
    private Vector<String> filteredCountryCode = new Vector<>();

    // Types
    private Vector<String> selectedTypes = new Vector<>();
    private Vector<String> allTypes;
    private List<CheckBox> typesCheckBox = new Vector<>();
    private List<CheckBox> filteredTypesCheckBox = new Vector<>();

    // Status
    private Vector<String> selectedStatuses = new Vector<>();
    private Vector<String> allStatuses;
    private List<CheckBox> statusesCheckBox = new Vector<>();
    private List<CheckBox> filteredStatusCheckBox = new Vector<>();

    // Provider
    private Vector<Provider> filtered_providers = new Vector<>();
    private Vector<Provider> allProviders;
    private Vector<Provider> selectedProviders = new Vector<>();
    private List<CheckBox> providersCheckBox = new Vector<>();
    private List<CheckBox> filteredProviderCheckBox = new Vector<>();
    private Map<String, Provider> nameToProvider = new HashMap<>();

    public FilterController(Map<String, String> countryNameToCode, Vector<String> allTypes, Vector<String> allStatuses, Vector<Provider> allProviders) {
        assert false;
        this.allCountries.addAll(countryNameToCode.keySet());
        this.allTypes = allTypes;
        this.allStatuses = allStatuses;
        this.allProviders = allProviders;
        this.countryNameToCode = countryNameToCode;

        for(Map.Entry<String, String> entry : countryNameToCode.entrySet()){
            codeToCountryName.put(entry.getValue(), entry.getKey());
        }

        generateCountriesCheckBoxes();
        generateTypesCheckBoxes();
        generateStatusCheckboxes();
        generateProviderCheckboxes();

        System.out.println(providersCheckBox);
        System.out.println(countriesCheckBox);
        System.out.println(statusesCheckBox);
        System.out.println(typesCheckBox);
    }

    private void generateCountriesCheckBoxes() {
        for (String country : allCountries) {
            CheckBox countryCheckBox = new CheckBox(country);
            countryCheckBox.getStyleClass().add("countries-check-box");
            countryCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (countryCheckBox.isSelected())
                    selectedCountries.add(countryNameToCode.get(countryCheckBox.getText()));
                else
                    selectedCountries.remove(countryNameToCode.get(countryCheckBox.getText()));
            });
            countriesCheckBox.add(countryCheckBox);
        }
    }

    private void generateTypesCheckBoxes() {
        for (String type : allTypes) {
            CheckBox typeCheckBox = new CheckBox(type);
            typeCheckBox.getStyleClass().add("countries-check-box");
            typeCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (typeCheckBox.isSelected())
                    selectedTypes.add(typeCheckBox.getText());
                else
                    selectedTypes.remove(typeCheckBox.getText());
            });
            typesCheckBox.add(typeCheckBox);
        }
    }

    private void generateStatusCheckboxes() {
        for (String status : allStatuses) {
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

    private void generateProviderCheckboxes() {
        for (Provider provider : allProviders) {
            nameToProvider.put(provider.getName(), provider);
            CheckBox providerCheckBox = new CheckBox(provider.getName());
            providerCheckBox.getStyleClass().add("provider-check-box");
            providerCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (providerCheckBox.isSelected())
                    selectedProviders.add(nameToProvider.get(providerCheckBox.getText()));
                else
                    selectedProviders.remove(nameToProvider.get(providerCheckBox.getText()));
            });
            providersCheckBox.add(providerCheckBox);
        }
    }

    public Vector<String> getSelectdTypes() {
        return selectedTypes;
    }

    public Vector<String> getFilterTypes() {
        if (selectedTypes.isEmpty())
            return allTypes;
        return selectedTypes;
    }

    public Vector<String> getSelectedStatuses() {
        return selectedStatuses;
    }

    public Vector<String> getFilterStatuses() {
        if (selectedStatuses.isEmpty())
            return allStatuses;
        return selectedStatuses;
    }

    public Vector<String> getSelectedCountries() {
        return selectedCountries;
    }

    public Vector<String> getFilterCountries() {
        if (selectedCountries.isEmpty())
            return new Vector<>(countryNameToCode.values());

        Vector<String> retVec = new Vector<>();
        for (String countryCode : selectedCountries) {
            retVec.add(countryCode);
        }
        return retVec;
    }

    public Vector<Provider> getSelectedProviders() {
        return selectedProviders;
    }

    public Vector<Provider> getFilterProviders() {
        if (selectedProviders.isEmpty())
            return allProviders;
        return selectedProviders;
    }

    public List<CheckBox> filterProviders() {
        filteredProviderCheckBox.clear();
        System.out.println(selectedCountries);
        for (CheckBox providerCBox : providersCheckBox) {
            Provider provider = nameToProvider.get(providerCBox.getText());

            if (getFilterCountries().contains(provider.getCountryCode())) {

                String[] providerTypes = provider.getServiceTypes();

                outerloop:
                for (int i = 0; i < providerTypes.length; i++) {
                    String providerType = providerTypes[i];

                    if (getFilterTypes().contains(providerType)) {

                        Service[] s = provider.getServices();
                        for (int j = 0; j< s.length; j++) {
                            Service service = s[j];

                            if (getFilterStatuses().contains(service.getCurrentStatus())) {
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

    public List<CheckBox> filterCountries() {
        filteredCountriesCheckBox.clear();
        filteredCountryCode.clear();

        for (Provider provider : getFilterProviders()) {
            if (filteredCountryCode.contains(provider.getCountryCode()))
                continue;

            String[] providerTypes = provider.getServiceTypes();

            outerloop:
            for (int i = 0; i < providerTypes.length; i++) {
                String providerType = providerTypes[i];

                if (getFilterTypes().contains(providerType)) {
                    Service[] s = provider.getServices();
                    for (int j = 0; j < s.length; j++) {
                        Service service = s[j];

                        if (getFilterStatuses().contains(service.getCurrentStatus())) {

                            for (CheckBox CCBox : countriesCheckBox) {
                                if (CCBox.getText().equals(codeToCountryName.get(provider.getCountryCode()))) {
                                    filteredCountriesCheckBox.add(CCBox);
                                    filteredCountryCode.add(provider.getCountryCode());
                                    break outerloop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return filteredCountriesCheckBox;
    }



}
