package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CountryFilterController {
    private Vector<String> selectedCountries = new Vector<>();
    private List<CheckBox> countriesCheckBox = new Vector<>();
    private Vector<String> filteredCountryCode = new Vector<>();
    private Vector<String> invalidSelectedCountryCodes = new Vector<>();

    CountryFilterController() {
        generateCountriesCheckBoxes();
    }

    public List<CheckBox> getCheckBoxes(Vector<Provider> p, Vector<String> t, Vector<String> s) {
        filteredCountryCode.clear();
        invalidSelectedCountryCodes.clear();
        countriesCheckBox.forEach(CBox -> {
            CBox.setDisable(true);
        });

        for (Provider provider : p) {
            String cc = provider.getCountryCode();
            if (filteredCountryCode.contains(cc))
                continue;

            String[] providerTypes = provider.getServiceTypes();

            outerloop:
            for (int i = 0; i < providerTypes.length; i++) {
                String providerType = providerTypes[i];

                if (t.contains(providerType)) {
                    Service[] ser = provider.getServices();
                    for (int j = 0; j < ser.length; j++) {
                        Service service = ser[j];

                        if (s.contains(service.getCurrentStatus())) {

                            for (CheckBox CCBox : countriesCheckBox) {
                                if (CCBox.getText().equals(provider.getCountryName())) {
                                    CCBox.setDisable(false);
                                    filteredCountryCode.add(cc);
                                    CCBox.setStyle("-fx-text-fill:  black;");
                                    break outerloop;
                                }
                            }
                        }
                    }
                }
            }
        }

        countriesCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredCountryCode.contains(CBox.getId())) {
                invalidSelectedCountryCodes.add(CBox.getId());
                CBox.setStyle("-fx-text-fill: #b50202;");
                CBox.setDisable(false);
            }
        });

        return countriesCheckBox;
    }

    public Vector<String> getCriteria() {
        if (selectedCountries.isEmpty()) {
            filteredCountryCode.add(null);
            return filteredCountryCode;
        }
        else {
            for (String code : selectedCountries) {
                if (invalidSelectedCountryCodes.contains(code))
                    selectedCountries.remove(code);
            }
        }
        return selectedCountries;
    }

    public Vector<String> getFilterCriteria() {
        Vector<String> tmp = new Vector<>();
        if (selectedCountries.isEmpty()) {
            for (CheckBox cb : countriesCheckBox) tmp.add(cb.getId());
        }
        else {
            for (String countryCode : selectedCountries) tmp.add(countryCode);
        }
        return tmp;
    }

    private void generateCountriesCheckBoxes() {
        Map<String, String> allCountries = CriteriaListFactory.getCountryList();
        for (Map.Entry<String, String> country : allCountries.entrySet()) {
            System.out.println(country.getKey() + ": " + country.getValue());
            CheckBox countryCheckBox = new CheckBox(country.getValue());
            countryCheckBox.getStyleClass().add("countries-check-box");
            countryCheckBox.setId(country.getKey());
            countryCheckBox.setDisable(true);
            countryCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (countryCheckBox.isSelected())
                    selectedCountries.add(country.getKey());
                else {
                    countryCheckBox.setStyle("-fx-text-fill:  black;");
                    selectedCountries.remove(country.getKey());
                }
            });
            countriesCheckBox.add(countryCheckBox);
        }
    }
}
