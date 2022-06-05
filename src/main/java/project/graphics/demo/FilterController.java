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

    public FilterController() throws IOException {
        criteriaLists = new CriteriaListFactory();
        countries = new CountryFilterController();
        providers = new ProviderFilterController();
        types = new TypeFilterController();
        statuses = new StatusFilterController();
    }

    public List<CheckBox> getCheckBoxes(String criterion) {
        List<CheckBox> tmp = new ArrayList<>();
        Vector<String> c = countries.getFilterCriteria();
        Vector<Provider> p = providers.getFilterCriteria();
        Vector<String> t = types.getFilterCriteria();
        Vector<String> s = statuses.getFilterCriteria();
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

    public SearchCriteria getCriteria() {
        Vector<String> c = countries.getCriteria(); //returns country codes
        Vector<Provider> p = providers.getCriteria();
        Vector<String> t = types.getCriteria();
        Vector<String> s = statuses.getCriteria();
        return new SearchCriteria(c, p, t, s);
    }

    public int getProviderSelectedSize() {
        return providers.getSelectedSize();
    }
}
