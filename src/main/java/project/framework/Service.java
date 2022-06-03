package project.framework;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Service {
    private String countryCode, countryName, currentStatus, serviceName, typeIdentifier, providerName;
    private String[] serviceTypes;

    /**
     *  Service constructor; creates a Service object.
     *
     * @param  jsonSource is a string get from the Json file in which are
     *                    all the service information needed
     *
     * @see     JSONObject
     */
    public Service(String jsonSource, String cn, String providerName) {
        JSONObject data = new JSONObject(jsonSource);

        countryCode = data.getString("countryCode");
        countryName = cn;
        serviceName = data.getString("serviceName");
        typeIdentifier = data.getString("type");
        this.providerName = providerName;

        JSONArray jsonServiceTypes = data.getJSONArray("qServiceTypes");
        serviceTypes = new String[jsonServiceTypes.length()];
        for (int i = 0; i < jsonServiceTypes.length(); i++) {
            serviceTypes[i] = jsonServiceTypes.getString(i);
        }

        Scanner status_reader = new Scanner(data.getString("currentStatus"));
        status_reader.useDelimiter("/");
        currentStatus = "";
        while (status_reader.hasNext()) {
            currentStatus = status_reader.next();
        }
    }

    //Get method: one vector including every private attribute
    //first four elements: countryName, currentStatus, serviceName, typeIdentifier
    //other elements: serviceTypes
    public Vector<String> getServiceInfo() {
        Vector<String> info = new Vector<>();
        info.add(countryCode);
        info.add(countryName);
        info.add(currentStatus);
        info.add(serviceName);
        info.add(typeIdentifier);
        Collections.addAll(info, serviceTypes);
        return info;
    }

    //Individual get methods for all the private attributes
    public String getCountryCode() { return countryCode; }
    public String getCountryName() {
        return countryName;
    }
    public String getCurrentStatus() { return currentStatus; }
    public String getServiceName() { return serviceName; }
    public String getTypeIdentifier() { return typeIdentifier; }
    public String[] getServiceTypes() { return serviceTypes; }
    public String getProviderName() {
        return providerName;
    }
}
