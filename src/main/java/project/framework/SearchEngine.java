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

    public void performSearch(SearchCriteria criteria) {
        Map<String, String> nameToCode = CriteriaListFactory.getCountryNameToCode();
        Vector<String> codeToName = new Vector<>(); //vector with country codes instead of country names
        for (String c : criteria.getCountries()) codeToName.add(nameToCode.get(c));
        for (Provider p : criteria.getProviders()) {
            Service[] ser = p.getServices();
            for (Service s : ser) {
                if (codeToName.contains(s.getCountryCode()) &&
                        criteria.getStatuses().contains(s.getCurrentStatus())) {
                    for (String type : s.getServiceTypes()) {
                        if (criteria.getTypes().contains(type)) {
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
