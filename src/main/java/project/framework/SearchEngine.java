package project.framework;

import java.util.Vector;

public class SearchEngine {
    private Vector<Service> searchResults;
    private static SearchEngine instance;

    SearchEngine() {
        clearSearchResults();
    }

    private void clearSearchResults() {searchResults = new Vector<>();}

    public static SearchEngine getInstance() {
        if (instance == null) instance = new SearchEngine();
        return instance;
    }

    public void performSearch(Vector<String> countries, Vector<Provider> providers, Vector<String> types, Vector<String> statuses) {
        clearSearchResults();
        for (Provider p : providers) {
            for (Service s : p.getServices()) {
                String[] t = s.getServiceInfo().getServiceTypes();
                for (String type : t) {
                        if (countries.contains(s.getServiceInfo().getCountryCode()) && types.contains(type)
                                && statuses.contains(s.getServiceInfo().getCurrentStatus())) {
                            searchResults.add(s);
                            break;
                        }
                }
            }
        }
    }

    public Vector<Service> getSearchResults() {
        return searchResults;
    }

    public ServiceInfo getServiceInfo(int serviceIndex) {
        return searchResults.get(serviceIndex).getServiceInfo();
    }
}
