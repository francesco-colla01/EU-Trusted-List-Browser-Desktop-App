package project.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SearchCriteria {
    private Vector<String> countries;
    private Vector<Provider> providers;
    private Vector<String> types;
    private Vector<String> statuses;
    private Map<String, Vector<String>> redCriteria;
    private final boolean isCriteriaInvalid;
    private String selectedAll;

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
        selectedAll = "";

        //if some criteria lists include every criterion available (null at the end of the vector),
        //add that list to selectedAll
        if (countries.contains(null)) {
            countries.remove(null);
            selectedAll += "c";
        }
        if (providers.contains(null)) {
            providers.remove(null);
            selectedAll += "p";
        }
        if (types.contains(null)) {
            types.remove(null);
            selectedAll += "t";
        }
        if (statuses.contains(null)) {
            statuses.remove(null);
            selectedAll += "s";
        }

        //if no list was touched, the criteria are not valid
        if (selectedAll.equals("cpts")) {
            isCriteriaInvalid = true;
            return;
        }

        isCriteriaInvalid = false;
        boolean nothingRed = true;
        redCriteria = new HashMap<>();

        //build the map for red (incompatible) criteria
        //if something was selected on a list, the last element of the corresponding vector contains
        //the number x of invalid criteria, with the x elements behind it being the invalid criteria
        //for every kind of criteria, the list of incompatible criteria is build and added to the map
        //(if no red criteria are present, an empty list is added)
        int redsCountry;
        int redsProvider;
        int redsType;
        int redsStatus;

        try {
            redsCountry = Integer.parseInt(countries.get(countries.size() - 1));
            countries.remove(countries.size() - 1);
        } catch (NumberFormatException e) {
            redsCountry = 0;
        }
        if (redsCountry != 0) {
            nothingRed = false;
            Vector<String> redCountries = new Vector<>();
            for (String redCountry : countries.subList(countries.size() - redsCountry, countries.size())) {
                redCountries.add(redCountry);
                countries.remove(redCountry);
            }
            redCriteria.put("c", redCountries);
        }
        else {
            redCriteria.put("c", new Vector<>());
        }

        //in this case, if something was selected from the providers, the last element is a
        //Provider instance with the number of incompatible providers as the name
        try {
            Provider last = providers.get(providers.size() - 1);
            redsProvider = Integer.parseInt(last.getName());
            providers.remove(providers.size() - 1);
        } catch (NumberFormatException e) {
            redsProvider = 0;
        }
        if (redsProvider != 0) {
            Vector<String> redProviders = new Vector<>();
            for (Provider redProvider : providers.subList(providers.size()-redsProvider, providers.size())) {
                redProviders.add(redProvider.getName());
                providers.remove(redProvider);
            }
            redCriteria.put("p", redProviders);
            nothingRed = false;
        }
        else {
            redCriteria.put("p", new Vector<>());
        }

        try {
            redsType = Integer.parseInt(types.get(types.size() - 1));
            types.remove(types.size() - 1);
        } catch (NumberFormatException e) {
            redsType = 0;
        }
        if (redsType != 0) {
            Vector<String> redTypes = new Vector<>();
            for (String redType : types.subList(types.size() - redsType, types.size())) {
                redTypes.add(redType);
                types.remove(redType);
            }
            redCriteria.put("t", redTypes);
            nothingRed = false;
        }
        else {
            redCriteria.put("t", new Vector<>());
        }

        try {
            redsStatus = Integer.parseInt(statuses.get(statuses.size() - 1));
            statuses.remove(statuses.size() - 1);
        } catch (NumberFormatException e) {
            redsStatus = 0;
        }
        if (redsStatus != 0) {
            Vector<String> redStatuses = new Vector<>();
            for (String redStatus : statuses.subList(statuses.size() - redsStatus, statuses.size())) {
                redStatuses.add(redStatus);
                statuses.remove(redStatus);
            }
            redCriteria.put("s", redStatuses);
            nothingRed = false;
        }
        else {
            redCriteria.put("s", new Vector<>());
        }

        //if all lists are empty, clear the map
        if (nothingRed) redCriteria.clear();
    }

    //Get Methods
    public boolean isInvalid() { return isCriteriaInvalid; }
    public Vector<String> getCountries() { return countries; }
    public Vector<Provider> getProviders() { return providers; }
    public Vector<String> getTypes() { return types; }
    public Vector<String> getStatuses() { return statuses; }
    public String getSelectedAll() { return selectedAll; }
    public Map<String, Vector<String>> getRedCriteria() { return redCriteria; }
}
