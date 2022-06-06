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

    /**
     * ProviderFilterController constructor: creates all the checkboxes
     *
     * @see CriteriaListFactory
     */
    public ProviderFilterController() {
        Vector<Provider> p = CriteriaListFactory.getProviderList();

        for (Provider provider : p) {
            String name = provider.getName();
            String cc = provider.getCountryCode();
            //this map associates the string name+countryCode to its corresponding provider
            //this is because there are providers with the same name across different countries
            nameToProvider.put(name + cc, provider);
            //The value passed is what is shown in the UI, the checkbox text
            CheckBox providerCheckBox = new CheckBox(name);
            providerCheckBox.getStyleClass().add("provider-check-box");
            //the string name+countryCode will be the checkbox ID
            providerCheckBox.setId(name + cc);

            //If the checkbox is selected add it to the selected providers, otherwise remove it
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

    /**
     * get the checkboxes to show on screen
     *
     * @param c the vector returned from CountryFilterController.getCriteriaForCheckboxes()
     * @param t the vector returned from TypeFilterController.getCriteriaForCheckboxes()
     * @param s the vector returned from StatusFilterController.getCriteriaForCheckboxes()
     * @return a list with the checkboxes to show on screen
     */
    public List<CheckBox> getCheckBoxes(Vector<String> c, Vector<String> t, Vector<String> s) {
        //clears the vectors containing filtered and invalid selected statuses
        filteredProviderCheckBox.clear();
        invalidSelectedProviders.clear();

        //for every provider checkbox
        for (CheckBox providerCBox : providersCheckBox) {

            //get the provider corresponding to the checkbox
            Provider provider = nameToProvider.get(providerCBox.getId());

            //if the country code is included in those passed as parameters
            //(see CountryFilterController.getCriteriaForCheckboxes())
            if (c.contains(provider.getCountryCode())) {

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

                            //start iterating over the service's types and check if any of them
                            //are included in those passed as parameters
                            //(see TypesFilterController.getCriteriaForCheckboxes())
                            boolean contains = false;
                            String[] types = service.getServiceTypes();
                            for (String type : types) {
                                if (t.contains(type))
                                    contains = true;
                            }

                            //if the service status and any of the types are included in those passed as parameters
                            //(see StatusFilterController.getCriteriaForCheckboxes())
                            //add the checkbox to the filtered checkboxes and exit outerloop
                            //(on to the next provider)
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

        //for every checkbox, if the provider is not valid (it is selected but is not included
        //in the filtered checkboxes, those that can be displayed), the checkbox is added
        //to the invalid ones, is marked in red and is activated (so that the user may deselect it)
        providersCheckBox.forEach(CBox -> {
            if (CBox.isSelected() && !filteredProviderCheckBox.contains(CBox)) {
                filteredProviderCheckBox.add(CBox);
                invalidSelectedProviders.add(nameToProvider.get(CBox.getId()));
                CBox.setStyle("-fx-text-fill: #b50202;");
            }});

        //if no checkbox were selected and no checkbox can be displayed, return null
        //(this is used by SearchUI to decide whether to display "No providers found with matching criteria"
        if (filteredProviderCheckBox.isEmpty() && selectedProviders.isEmpty())
                return null;

        return filteredProviderCheckBox;
    }

    /**
     * @return a Vector with the criteria to use for the search
     */
    public Vector<Provider> getCriteria() {
        Vector<Provider> tmp = new Vector<>();

        //add every selected provider that is not invalid
        for (Provider p : selectedProviders) {
            if (!invalidSelectedProviders.contains(p)) tmp.add(p);
        }

        //if no provider was added (no valid criteria selected), every displayed provider
        //will be returned, and a null will be added to represent such case
        if (tmp.isEmpty()) {
            for (CheckBox cb : filteredProviderCheckBox) tmp.add(nameToProvider.get(cb.getId()));
            tmp.add(null);
        }

        //if any provideers were added (valid criteria selected), every invalid
        //selected criteria is added to the criteria list, followed by a number representing
        //how many invalid criteria have been selected
        else  {
            int invalids = 0;
            for (Provider invalid : invalidSelectedProviders) {
                invalids++;
                tmp.add(invalid);
            }
            tmp.add(new Provider("num" + invalids));
        }

        return tmp;
    }

    /**
     * @return the number of selected providers (this is used by SearchUI for the "select all" button)
     */
    public int getSelectedSize() {
        return selectedProviders.size();
    }

    /**
     * @return a vector with the criteria needed by the getCheckBoxes methods: returns the selected providers
     * if any are selected, returns every provider otherwise
     */
    public Vector<Provider> getCriteriaForCheckboxes() {
        if (selectedProviders.isEmpty())
            return CriteriaListFactory.getProviderList();
        return selectedProviders;
    }
}
