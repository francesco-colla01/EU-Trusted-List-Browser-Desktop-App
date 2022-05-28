package project.graphics.demo;

import javafx.scene.control.CheckBox;
import project.framework.SearchCriteria;
import project.framework.Provider;

import java.util.*;

public class FilterController {

    private CountryFilterController countries;
    private ProviderFilterController providers;
    private TypeFilterController types;
    private StatusFilterController statuses;

    public FilterController() {
        countries = new CountryFilterController();
        providers = new ProviderFilterController();
        types = new TypeFilterController();
        statuses = new StatusFilterController();
    }

    public List<CheckBox> getCheckBoxes(String criterion) {
        List<CheckBox> tmp = new ArrayList<>();
        Vector<String> c = countries.getFilterCriteria();
        Vector<String> t = types.getFilterCriteria();
        Vector<String> s = statuses.getFilterCriteria();
        Vector<Provider> p = providers.getFilterCriteria();
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
                tmp =statuses.getCheckBoxes(c, p, t);
                break;
        }
        return tmp;
    }

    public SearchCriteria getCriteria() {
        Vector<String> c = countries.getCriteria();
        Vector<String> t = types.getCriteria();
        Vector<String> s = statuses.getCriteria();
        Vector<Provider> p = providers.getCriteria();
        return new SearchCriteria(c, p, t, s);
    }

    public int getSelectedProvidersSize() {
        return providers.getSelectedProviderSize();
    }

    /*public SearchCriteria getFilterCriteria() {
        Vector<String> c = countries.getFilterCriteria();
        Vector<Provider> p = providers.getFilterCriteria();
        Vector<String> t = types.getFilterCriteria();
        Vector<String> s = statuses.getFilterCriteria();
        SearchCriteria criteria = new SearchCriteria(c, p, t, s);
        return criteria;
    }*/
}
