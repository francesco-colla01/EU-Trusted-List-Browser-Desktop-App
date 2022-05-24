package project.framework;

import java.util.Vector;

public class SearchEngine {
    Vector<Service> searchResults;
    static SearchEngine instance;

    SearchEngine() {
        clearSearchResults();
    }

    private void clearSearchResults() {searchResults = new Vector<>();}

    public static SearchEngine getInstance() {
        if (instance == null) instance = new SearchEngine();
        return instance;
    }

    public Vector<Service> performSearch(Vector<String> countries, Vector<Provider> providers, Vector<String> types, Vector<String> statuses) {
        clearSearchResults();
        for (Provider p : providers) {
            for (Service s : p.getServices()) {
                if (countries.contains(s.getServiceInfo().getCountryCode()) && types.contains(s.getServiceInfo().getType()) && statuses.contains(s.getServiceInfo().getCurrentStatus())) {
                    searchResults.add(s);
                }
            }
        }
        return searchResults;
    }

    public Service.ServiceInfo getServiceInfo(int serviceIndex) {
        return searchResults.get(serviceIndex).getServiceInfo();
    }
}
