package project.framework;

import org.json.JSONArray;
import project.graphics.demo.FilterController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CriteriaListFactory {
    private static Vector<String> countryList, typeList, statusList;
    private static Vector<Provider> providerList;
    private static Map<String, String> countryNameToCode;

    /**
     *  Is responsible for creating all the data structure needed to run the program.
     *
     * @see     HttpRequest
     * @see     JSONArray
     * @see     Provider
     *
     */
    public void initialize() throws IOException {
        //first API request to get the list of all countries
        HttpRequest fetchCountriesList = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        JSONArray jsonCountriesList = new JSONArray(fetchCountriesList.getResponse());

        countryList = new Vector<>();
        countryNameToCode = new HashMap<>(); //this map associate the country name to his code (e.g Italy ----> IT)

        for (int i = 0; i<jsonCountriesList.length(); i++) {
            countryList.add(jsonCountriesList.getJSONObject(i).getString("countryName"));
            countryNameToCode.put(jsonCountriesList.getJSONObject(i).getString("countryName"), jsonCountriesList.getJSONObject(i).getString("countryCode"));
        }

        //Second API request to get all the info needed
        HttpRequest fetchAllProviders = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
        JSONArray jsonProvidersList = new JSONArray(fetchAllProviders.getResponse()); //the API organise all services based on their provider
        Provider[] all_tsp = new Provider[jsonProvidersList.length()];  //list of all providers

        typeList = new Vector<>();
        providerList = new Vector<>();
        statusList = new Vector<>();

        //fill all maps and vectors
        for (int i = 0; i<jsonProvidersList.length(); i++) {
            all_tsp[i] = new Provider(jsonProvidersList.getJSONObject(i).toString());
            providerList.add(all_tsp[i]);

            for (int j = 0; j<all_tsp[i].getServices().length; j++) {
                Service[] s = all_tsp[i].getServices();
                if (!statusList.contains(s[j].getCurrentStatus()))
                    statusList.add(s[j].getCurrentStatus());
            }

            for (int j = 0; j < all_tsp[i].getServiceTypes().length; j++) {
                String[] s = all_tsp[i].getServiceTypes();
                if (!typeList.contains(s[j]))
                    typeList.add(s[j]);
            }
        }
    }

    //Get methods
    public static Vector<Provider> getProviderList() { return providerList; }
    public static Vector<String> getCountryList() { return countryList; }
    public static Vector<String> getCountryCodeList() {
        Vector<String> tmp = new Vector<>();
        for (String c : countryList) tmp.add(countryNameToCode.get(c));
        return tmp;
    }
    public static Vector<String> getStatusList() { return statusList; }
    public static Vector<String> getTypeList() {return typeList; }
    public static Map<String, String> getCountryNameToCode() { return countryNameToCode; }
}
