package project.framework;

import org.json.JSONArray;
import org.json.JSONObject;

public class Provider {

    private String countryCode, name, trustmark, jsonFormat, currentStatus;
    private int tspId;
    private String[] serviceTypes;
    private Service[] services;

    public Provider(String jsonSource) {
        JSONObject data = new JSONObject(jsonSource);

        this.countryCode = data.getString("countryCode");
        this.name = data.getString("name");
        this.trustmark = data.getString("trustmark");

        this.tspId = data.getInt("tspId");

        JSONArray jsonServiceTypes = data.getJSONArray("qServiceTypes");
        serviceTypes = new String[jsonServiceTypes.length()];
        for (int i=0; i<jsonServiceTypes.length(); i++) {
            serviceTypes[i] = jsonServiceTypes.getString(i);
        }

        JSONArray jsonServices = data.getJSONArray("services");
        services = new Service[jsonServices.length()];
        for (int i=0; i<jsonServices.length(); i++) {
            services[i] = new Service(jsonServices.getJSONObject(i).toString());
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getTspId() {
        return tspId;
    }

    public Service[] getServices() {
        return services;
    }

    public String getJsonFormat() {
        return jsonFormat;
    }

    public String getName() {
        return name;
    }

    public String getTrustmark() {
        return trustmark;
    }

    public String[] getServiceTypes() {
        return serviceTypes;
    }
}
