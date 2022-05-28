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
    private Map<String, String> codeToCountryName = new HashMap<>();
    private List<CheckBox> countriesCheckBox = new Vector<>();
    private Vector<String> filteredCountryCode = new Vector<>();
    private Vector<String> invalidSelectedCountryCodes = new Vector<>();

    CountryFilterController() {
        Map<String, String> countryNameToCode = CriteriaListFactory.getCountryNameToCode();
        for(Map.Entry<String, String> entry : countryNameToCode.entrySet()){
            codeToCountryName.put(entry.getValue(), entry.getKey());
        }
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
                                if (CCBox.getText().equals(codeToCountryName.get(cc))) {
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

        Map<String, String> nameToCode = CriteriaListFactory.getCountryNameToCode();
        countriesCheckBox.forEach(CBox -> {
            String code = nameToCode.get(CBox.getText());
            if (CBox.isSelected() && !filteredCountryCode.contains(code)) {
                invalidSelectedCountryCodes.add(code);
                CBox.setStyle("-fx-text-fill: #b50202;");
                CBox.setDisable(false);
            }
        });

        return countriesCheckBox;
    }

    public Vector<String> getCriteria() {
        if (selectedCountries.isEmpty()) return filteredCountryCode;
        for (String code : selectedCountries) {
            if (invalidSelectedCountryCodes.contains(code))
                selectedCountries.remove(code);
        }
        return selectedCountries;
    }

    public Vector<String> getFilterCriteria() {
        Map<String, String> countryNameToCode = CriteriaListFactory.getCountryNameToCode();
        if (selectedCountries.isEmpty())
            return new Vector<>(countryNameToCode.values());

        Vector<String> retVec = new Vector<>();
        for (String countryCode : selectedCountries) {
            retVec.add(countryCode);
        }
        return retVec;
    }

    private void generateCountriesCheckBoxes() {
        Map<String, String> countryNameToCode = CriteriaListFactory.getCountryNameToCode();
        Vector<String> allCountries = CriteriaListFactory.getCountryList();
        for (String country : allCountries) {
            CheckBox countryCheckBox = new CheckBox(country);
            countryCheckBox.getStyleClass().add("countries-check-box");
            countryCheckBox.setDisable(true);
            countryCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (countryCheckBox.isSelected())
                    selectedCountries.add(countryNameToCode.get(countryCheckBox.getText()));
                else {
                    countryCheckBox.setStyle("-fx-text-fill:  black;");
                    selectedCountries.remove(countryNameToCode.get(countryCheckBox.getText()));
                }
            });
            countriesCheckBox.add(countryCheckBox);
        }
    }
}
