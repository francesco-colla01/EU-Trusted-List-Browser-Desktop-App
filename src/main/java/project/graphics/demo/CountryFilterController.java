package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.CriteriaListFactory;
import project.framework.Provider;
import project.framework.Service;

import java.util.*;

public class CountryFilterController {
    private Vector<String> selectedCountries = new Vector<>();
    private List<CheckBox> countriesCheckBox = new Vector<>();
    private Vector<String> filteredCountryCode = new Vector<>();
    private Vector<String> invalidSelectedCountryCodes = new Vector<>();
    private Map<String, String> allCountries;

    CountryFilterController() {
        allCountries = CriteriaListFactory.getCountryList();
        SortedMap<String, String> reverseAllCountries = new TreeMap<>();
        for (Map.Entry<String, String> country : allCountries.entrySet()) {
            reverseAllCountries.put(country.getValue(), country.getKey());
        }
        for (Map.Entry<String, String> country : reverseAllCountries.entrySet()) {
            CheckBox countryCheckBox = new CheckBox(country.getKey());
            countryCheckBox.getStyleClass().add("countries-check-box");
            countryCheckBox.setId(country.getValue());
            countryCheckBox.setDisable(true);
            countryCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (countryCheckBox.isSelected())
                    selectedCountries.add(country.getValue());
                else {
                    countryCheckBox.setStyle("-fx-text-fill:  black;");
                    selectedCountries.remove(country.getValue());
                }
            });
            countriesCheckBox.add(countryCheckBox);
        }
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
        Vector<String> tmp = new Vector<>();
        for (String cc : selectedCountries) {
            if (!invalidSelectedCountryCodes.contains(cc)) tmp.add(cc);
        }
        if (tmp.isEmpty()) {
            for (String cc : filteredCountryCode) tmp.add(cc);
            tmp.add(null);
        }
        else {
            int invalids = 0;
            for (String invalid : invalidSelectedCountryCodes) {
                invalids++;
                tmp.add(allCountries.get(invalid));
            }
            tmp.add(Integer.toString(invalids));
        }
        return tmp;
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
}
