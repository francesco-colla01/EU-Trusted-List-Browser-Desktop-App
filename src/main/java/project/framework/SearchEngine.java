package project.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SearchEngine {
    private Map<Provider, Vector<Service>> searchResults;
    private static SearchEngine instance;

    /**
     * SearchEngine private constructor; the only way to create
     * or duplicate an SearchEngine instance is to call the method
     * getInstance()
     */
    private SearchEngine() { clearSearchResults(); }

    /**
     * Clear any data in the private parameter searchResults
     */
    private void clearSearchResults() { searchResults = new HashMap<>(); }

    /**
     * This method create a new object SearchEngine if none was ever
     * created, otherwise it returns a copy of the first SearchEngine
     * created
     *
     * @return SearchEngine object
     */
    public static SearchEngine getInstance() {
        if (instance == null) instance = new SearchEngine();
        return instance;
    }

    /**
     * Insert in the map searchResults the provider (key) and the service (value)
     *
     * @param p Provider instance
     * @param s Service instance
     */
    private void addService(Provider p, Service s) {
        if (searchResults.get(p) == null) {
            Vector<Service> tmp = new Vector<>();
            tmp.add(s);
            searchResults.put(p, tmp);
        }
        else searchResults.get(p).add(s);
    }



    /**
     * Gather all selected and valid parameters and build the
     * search results
     *
     * @param criteria SearchCriteria object
     * @see Provider
     * @see Service
     * @see SearchCriteria
     */
    public void performSearch(SearchCriteria criteria) {
        clearSearchResults();
        for (Provider p : criteria.getProviders()) {
            Service[] ser = p.getServices();
            for (Service s : ser) {
                if (criteria.getCountries().contains(s.getCountryCode()) &&
                        criteria.getStatuses().contains(s.getCurrentStatus())) {
                    Vector<String> types = criteria.getTypes();
                    for (String type : s.getServiceTypes()) {
                        if (types.contains(type)) {
                            addService(p, s);
                            break;
                        }
                    }
                }
            }
        }
    }

    //Get Methods
    public Map<Provider, Vector<Service>> getSearchResults() { return searchResults; }
}
