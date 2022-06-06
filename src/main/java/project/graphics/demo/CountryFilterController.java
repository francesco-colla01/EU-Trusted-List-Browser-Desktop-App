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

    /**
     * CountryFilterController constructor: creates all the checkboxes
     *
     * @see CriteriaListFactory
     */
    public CountryFilterController() {
        allCountries = CriteriaListFactory.getCountryList();

        for (Map.Entry<String, String> country : allCountries.entrySet()) {
            //The value passed is what is shown in the UI, the checkbox text
            CheckBox countryCheckBox = new CheckBox(country.getValue());
            countryCheckBox.getStyleClass().add("countries-check-box");
            //The country code (e.g. IT) is the checkbox ID
            countryCheckBox.setId(country.getKey());
            countryCheckBox.setDisable(true);

            //If the checkbox is selected add it to the selected countries, otherwise remove it
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

    /**
     * get the checkboxes to show on screen
     *
     * @param p the vector returned from ProviderFilterController.getCriteriaForCheckboxes()
     * @param t the vector returned from TypeFilterController.getCriteriaForCheckboxes()
     * @param s the vector returned from StatusFilterController.getCriteriaForCheckboxes()
     * @return a list with the checkboxes to show on screen
     */
    public List<CheckBox> getCheckBoxes(Vector<Provider> p, Vector<String> t, Vector<String> s) {
        //clears the vectors containing filtered and invalidly selected countries
        filteredCountryCode.clear();
        invalidSelectedCountryCodes.clear();

        //by default, disables every checkbox (the checkboxes to be shown will be activated later)
        countriesCheckBox.forEach(CBox -> {
            CBox.setDisable(true);
        });

        //for every provider passed as parameter
        //(see ProviderFilterController.getCriteriaForCheckboxes())
        for (Provider provider : p) {

            String cc = provider.getCountryCode();
            //if country is already present, skip this iteration
            if (filteredCountryCode.contains(cc))
                continue;

            //start iterating over the service types
            String[] providerServiceTypes = provider.getServiceTypes();

            outerloop:
            for (String serviceType : providerServiceTypes) {

                //if the service type is included in those passed as parameters
                //(see TypesFilterController.getCriteriaForCheckboxes())
                if (t.contains(serviceType)) {

                    //start iterating over the provider's services
                    Service[] services = provider.getServices();

                    for (Service service : services) {

                        //if the service status is included in those passed as parameters
                        //(see StatusFilterController.getCriteriaForCheckboxes())
                        if (s.contains(service.getCurrentStatus())) {

                            //check every country checkbox
                            //when a checkbox with the country of the provider is found,
                            //the checkbox is activated, is marked in black (it can be selected),
                            //the country code is added to the filtered countries
                            //and outerloop stops (on to the next provider)
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

        //for every checkbox, if the country is not valid (it is selected but is not included
        //in the filtered checkboxes, those that can be displayed), the checkbox is added
        //to the invalid ones, is marked in red and is activated (so that the user may deselect it)
        countriesCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredCountryCode.contains(CBox.getId())) {
                invalidSelectedCountryCodes.add(CBox.getId());
                CBox.setStyle("-fx-text-fill: #b50202;");
                CBox.setDisable(false);
            }
        });

        return countriesCheckBox;
    }

    /**
     * @return a Vector with the criteria to use for the search
     */
    public Vector<String> getCriteria() {
        Vector<String> tmp = new Vector<>();

        //add every selected country that is not invalid
        for (String cc : selectedCountries) {
            if (!invalidSelectedCountryCodes.contains(cc)) tmp.add(cc);
        }

        //if no country was added (no valid criteria selected), every displayed country
        //will be returned, and a null will be added to represent such case
        if (tmp.isEmpty()) {
            for (String cc : filteredCountryCode) tmp.add(cc);
            tmp.add(null);
        }

        //if any countries were added (valid criteria selected), every invalid
        //selected criteria is added to the criteria list, followed by a number representing
        //how many invalid criteria have been selected
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

    /**
     * @return a vector with the criteria needed by the getCheckBoxes methods: returns the selected country codes
     * if any are selected, returns every country code otherwise
     */
    public Vector<String> getCriteriaForCheckboxes() {
        Vector<String> tmp = new Vector<>();
        if (selectedCountries.isEmpty()) {
            for (CheckBox cb : countriesCheckBox) tmp.add(cb.getId());
        }
        else {
            tmp.addAll(selectedCountries);
        }
        return tmp;
    }
}
