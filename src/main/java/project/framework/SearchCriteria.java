package project.framework;

import project.graphics.demo.FilterController;

import java.util.Vector;

public class SearchCriteria {
    private Vector<String> countries;
    private Vector<Provider> providers;
    private Vector<String> types;
    private Vector<String> statuses;
    private final boolean isCriteriaInvalid;

    public SearchCriteria(Vector<String> c, Vector<Provider> p, Vector<String> t, Vector<String> s) {
        countries = c;
        providers = p;
        types = t;
        statuses = s;
        if (countries.contains(null) && providers.contains(null)
                && types.contains(null) && statuses.contains(null)) isCriteriaInvalid = true;
        else {
            isCriteriaInvalid = false;
            if (countries.contains(null)) countries.remove(null);
            if (providers.contains(null)) providers.remove(null);
            if (types.contains(null)) types.remove(null);
            if (statuses.contains(null)) statuses.remove(null);
        }
    }

    public boolean isInvalid() {
        return isCriteriaInvalid;
    }

    public Vector<String> getCountries() {
        return countries;
    }

    public Vector<Provider> getProviders() {
        return providers;
    }

    public Vector<String> getTypes() {
        return types;
    }

    public Vector<String> getStatuses() {
        return statuses;
    }
}
