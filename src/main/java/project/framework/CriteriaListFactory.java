package project.framework;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.json.JSONArray;
import project.graphics.demo.FilterControllerInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CriteriaListFactory {
    private static Vector<String> countryList, typeList, statusList;
    private static Vector<Provider> providerList;
    private static Map<String, String> countryNameToCode;

    public void initialize() throws IOException {
        HttpRequest fetchCountriesList = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        JSONArray jsonCountriesList = new JSONArray(fetchCountriesList.getResponse());

        countryList = new Vector<>();
        countryNameToCode = new HashMap<>();

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
                typeMap.put(s[j].getServiceInfo().getCurrentStatus(), all_tsp[i]);
                if (!statusList.contains(s[j].getServiceInfo().getCurrentStatus()))
                    statusList.add(s[j].getServiceInfo().getCurrentStatus());
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

    public FilterControllerInterface getFilterController() {
        FilterControllerInterface tmp = new FilterControllerInterface();
        return tmp;
    }

    public static Vector<Provider> getProviderList() {
        return providerList;
    }

    public static Vector<String> getCountryList() {
        return countryList;
    }

    public static Vector<String> getStatusList() {
        return statusList;
    }

    public static Vector<String> getTypeList() {
        return typeList;
    }

    public static Map<String, String> getCountryNameToCode() {
        return countryNameToCode;
    }
}
