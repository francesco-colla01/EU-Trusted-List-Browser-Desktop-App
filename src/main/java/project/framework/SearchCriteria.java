package project.framework;

import java.util.Vector;

public class SearchCriteria {
    private Vector<String> countries;
    private Vector<Provider> providers;
    private Vector<String> types;
    private Vector<String> statuses;
    private final boolean isCriteriaInvalid;

    /**
     * SearchCriteria constructor; if all parameters contains null at the
     * end of the vector this means the user did not select any parameter
     * in the UI
     *
     * @param c vector with all countries included in the search
     * @param p vector with all providers included in the search
     * @param t vector with all types of service included in the search
     * @param s vector with all statutes of service included in the search
     * @see Provider
     */
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

    //Get Methods
    public boolean isInvalid() { return isCriteriaInvalid; }
    public Vector<String> getCountries() { return countries; }
    public Vector<Provider> getProviders() { return providers; }
    public Vector<String> getTypes() { return types; }
    public Vector<String> getStatuses() { return statuses; }
}
