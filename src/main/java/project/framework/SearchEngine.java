package project.framework;

import java.util.Map;
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
        Map<String, String> nameToCode = CriteriaListFactory.getCountryNameToCode();
        Vector<String> codeToName = new Vector<>(); //vector with country codes instead of country names
        for (String c : countries) codeToName.add(nameToCode.get(c));
        for (Provider p : providers) {
            Service[] ser = p.getServices();
            for (Service s : ser) {
                if (codeToName.contains(s.getCountryCode()) &&
                        statuses.contains(s.getCurrentStatus())) {
                    for (String type : s.getServiceTypes()) {
                        if (types.contains(type)) {
                            searchResults.add(s);
                            break;
                        }
                    }
                }
            }
        }
    }

    public Vector<Service> getSearchResults() {
        return searchResults;
    }
}
