package project.framework;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.json.JSONArray;
import project.graphics.demo.FilterController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CriteriaListFactory {
    Vector<String> countryList, typeList, statusList;
    Vector<Provider> providerList;
    Map<String, String> countryNameToCode;

    public void initialize() throws IOException {
        HttpRequest fetchCountriesList = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        JSONArray jsonCountriesList = new JSONArray(fetchCountriesList.getResponse());

        countryList = new Vector<>();
        Map<String, String> countryNameToCode = new HashMap<>();

        for (int i = 0; i<jsonCountriesList.length(); i++) {
            countryList.add(jsonCountriesList.getJSONObject(i).getString("countryName"));
            countryNameToCode.put(jsonCountriesList.getJSONObject(i).getString("countryName"), jsonCountriesList.getJSONObject(i).getString("countryCode"));
        }

        HttpRequest fetchAllProviders = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
        JSONArray jsonProvidersList = new JSONArray(fetchAllProviders.getResponse());
        Provider[] all_tsp = new Provider[jsonProvidersList.length()];

        Multimap<String, Provider> countryMap = ArrayListMultimap.create();
        Multimap<String, Provider> typeMap = ArrayListMultimap.create();
        typeList = new Vector<>();
        providerList = new Vector<>();
        statusList = new Vector<>();

        // Riempimento mappe e vettori iniziali
        for (int i = 0; i<jsonProvidersList.length(); i++) {

            //Decodifica json
            all_tsp[i] = new Provider(jsonProvidersList.getJSONObject(i).toString());

            //inserimento mappa key Country value provider
            countryMap.put(all_tsp[i].getCountryCode(), all_tsp[i]);

            //inserimento vector provider e vettore copia
            providerList.add(all_tsp[i]);

            //inserimento vector statuses
            for (int j = 0; j<all_tsp[i].getServices().length; j++) {
                Service[] s = all_tsp[i].getServices();
                typeMap.put(s[j].getCurrentStatus(), all_tsp[i]);
                if (!statusList.contains(s[j].getCurrentStatus()))
                    statusList.add(s[j].getCurrentStatus());
            }

            //Inserimento mappa Service Types
            for (int j = 0; j<all_tsp[i].getServiceTypes().length; j++) {
                String[] s = all_tsp[i].getServiceTypes();
                typeMap.put(s[j], all_tsp[i]);
                if (!typeList.contains(s[j]))
                    typeList.add(s[j]);
            }

        }
    }

    public FilterController getFilterController() {
        FilterController tmp = new FilterController(countryNameToCode, typeList, statusList, providerList);
        return tmp;
    }

    public Vector<Provider> getProviderList() {
        return providerList;
    }

    public Vector<String> getCountryList() {
        return countryList;
    }

    public Vector<String> getStatusList() {
        return statusList;
    }

    public Vector<String> getTypeList() {
        return typeList;
    }
}
