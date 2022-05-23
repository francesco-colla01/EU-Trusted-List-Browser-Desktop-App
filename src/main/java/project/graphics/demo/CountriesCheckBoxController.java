package project.graphics.demo;

import java.util.Map;
import java.util.Vector;

public class CountriesCheckBoxController {

    private Vector<String> selectedCountries = new Vector<>();
    private Vector<String> allCountries;
    private Map<String, String> countryNameToCode;

    public CountriesCheckBoxController(Vector<String> allCountries, Map<String, String> countryNameToCode) {
        this.allCountries = allCountries;
        this.countryNameToCode = countryNameToCode;
    }

    public void onCheckBoxMark(String countryName) {
        selectedCountries.add(countryName);
    }

    public void onCheckBoxUnmark(String countryName) {
        selectedCountries.remove(countryName);
    }

    public Vector<String> getSelectedCountries() {
        if (selectedCountries.isEmpty())
            return allCountries;
        return selectedCountries;
    }

    public Vector<String> getSelectedCountriesCode() {
        if (selectedCountries.isEmpty())
            return new Vector<>(countryNameToCode.values());

        Vector<String> retVector = new Vector<>();
        for (String countryName : selectedCountries) {
            retVector.add(countryNameToCode.get(countryName));
        }
        return retVector;
    }
}
