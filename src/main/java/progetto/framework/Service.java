package progetto.framework;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class Service {
    private String countryCode, currentStatus, serviceName, type, tob;
    private int tspId, serviceId;

    private String[] serviceTypes;

    public Service(String jsonSource) {
        JSONObject data = new JSONObject(jsonSource);

        this.countryCode = data.getString("countryCode");
        this.serviceName = data.getString("serviceName");
        this.tob = (data.isNull("tob") ? null : data.getString("tob"));
        this.type = data.getString("type");

        this.tspId = data.getInt("tspId");
        this.serviceId = data.getInt("serviceId");

        JSONArray jsonServiceTypes = data.getJSONArray("qServiceTypes");
        serviceTypes = new String[jsonServiceTypes.length()];
        for (int i=0; i<jsonServiceTypes.length(); i++) {
            serviceTypes[i] = jsonServiceTypes.getString(i);
        }

        Scanner status_reader = new Scanner(data.getString("currentStatus"));
        status_reader.useDelimiter("/");
        while (status_reader.hasNext()) {
            this.currentStatus = status_reader.next();
        }
    }

    public int getServiceId() {
        return serviceId;
    }
    public int getTspId() {
        return tspId;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public String getCurrentStatus() {
        return currentStatus;
    }
    public String getServiceName() {
        return serviceName;
    }
    public String getTob() {
        return tob;
    }
    public String getType() {
        return type;
    }
}
