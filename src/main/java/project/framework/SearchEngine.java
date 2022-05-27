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

        
    }

    public Vector<Service> getSearchResults() {
        return searchResults;
    }
}
