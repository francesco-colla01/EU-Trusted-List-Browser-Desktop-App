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
    private static int selectedProviderSize;
    private CriteriaListFactory criteriaLists;

    public FilterController() throws IOException {
        criteriaLists = new CriteriaListFactory();
        countries = new CountryFilterController();
        providers = new ProviderFilterController();
        types = new TypeFilterController();
        statuses = new StatusFilterController();
        selectedProviderSize = 0;
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
                tmp = statuses.getCheckBoxes(c, p, t);
                break;
        }
        selectedProviderSize = providers.getSelectedProviderSize();
        return tmp;
    }

    public SearchCriteria getCriteria() {
        Vector<String> c = countries.getCriteria();
        Vector<Provider> p = providers.getCriteria();
        Vector<String> t = types.getCriteria();
        Vector<String> s = statuses.getCriteria();
        return new SearchCriteria(c, p, t, s);
    }

    public static int getSelectedProvidersSize() {
        return selectedProviderSize;
    }
}
