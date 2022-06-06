package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.CriteriaListFactory;
import project.framework.SearchCriteria;
import project.framework.Provider;

import java.io.IOException;
import java.util.*;

public class FilterController {

    private CountryFilterController countries;
    private ProviderFilterController providers;
    private TypeFilterController types;
    private StatusFilterController statuses;
    private CriteriaListFactory criteriaLists;

    /**
     * constructs the FilterController instance: constructs the CriteriaListFactory instance
     * and the subclasses instances (e.g. CountryFilterController)
     * @throws IOException
     */
    public FilterController() throws IOException {
        criteriaLists = new CriteriaListFactory();
        countries = new CountryFilterController();
        providers = new ProviderFilterController();
        types = new TypeFilterController();
        statuses = new StatusFilterController();
    }

    /**
     * get the checkboxes to show on screen for a certain criteria type
     *
     * @param criterion a string that represents the criteria type for which to get the checkboxes
     * @return the list of checkbox to show on screen
     */
    public List<CheckBox> getCheckBoxes(String criterion) {
        List<CheckBox> tmp = new ArrayList<>();

        //get the criteria needed to get the checkboxes from every subclass
        Vector<String> c = countries.getCriteriaForCheckboxes();
        Vector<Provider> p = providers.getCriteriaForCheckboxes();
        Vector<String> t = types.getCriteriaForCheckboxes();
        Vector<String> s = statuses.getCriteriaForCheckboxes();

        //get checkboxes for one criteria type depending on the parameter
        //the getCheckBoxes() for the certain criteria type is called, and the parameters passed
        //are the vectors returned by the getCriteriaForCheckboxes() of the other criteria type
        //(e.g. countries.getCheckBoxes() has the vector returned by providers.getCriteriaForCheckboxes(),
        //types.getCriteriaForCheckboxes() and statuses.getCriteriaForCheckboxes() as parameters)
        switch (criterion) {
            case "c":
                tmp = countries.getCheckBoxes(p, t, s);
                break;
            case "p":
                tmp = providers.getCheckBoxes(c, t, s);
                break;
            case "t":
                tmp = types.getCheckBoxes(c, p, s);
                break;
            case "s":
                tmp = statuses.getCheckBoxes(c, p, t);
                break;
        }

        return tmp;
    }

    /**
     * prepare the criteria for the search
     *
     * @return a SearchCriteria instance with the criteria needed for the search
     */
    public SearchCriteria getCriteria() {
        Vector<String> c = countries.getCriteria();
        Vector<Provider> p = providers.getCriteria();
        Vector<String> t = types.getCriteria();
        Vector<String> s = statuses.getCriteria();
        return new SearchCriteria(c, p, t, s);
    }

    /**
     * @return the number of selected providers
     */
    public int getProviderSelectedSize() {
        return providers.getSelectedSize();
    }
}
