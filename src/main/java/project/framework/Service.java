package project.framework;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class Service {
    private String countryCode, currentStatus, serviceName, type, tob;
    private int tspId, serviceId;

    private String[] serviceTypes;

    class ServiceInfo {
        private String countryCode, currentStatus, serviceName, type, tob;
        private int tspId, serviceId;
        private String[] serviceTypes;
        ServiceInfo(String cc, String cs, String sn, String t, String to, int tsp, int sId, String[] st) {
            countryCode = cc;
            currentStatus = cs;
            serviceName = sn;
            type = t;
            tob = to;
            tspId = tsp;
            serviceId = sId;
            serviceTypes = st;
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
        public String[] getServiceTypes() {
            return serviceTypes;
        }
    }

    private ServiceInfo info;

    public Service(String jsonSource) {
        JSONObject data = new JSONObject(jsonSource);

        String cc = data.getString("countryCode");
        String sn = data.getString("serviceName");
        String to = (data.isNull("tob") ? null : data.getString("tob"));
        String t = data.getString("type");

        int tsp = data.getInt("tspId");
        int sId = data.getInt("serviceId");

        JSONArray jsonServiceTypes = data.getJSONArray("qServiceTypes");
        String[] st = new String[jsonServiceTypes.length()];
        for (int i=0; i<jsonServiceTypes.length(); i++) {
            st[i] = jsonServiceTypes.getString(i);
        }

        Scanner status_reader = new Scanner(data.getString("currentStatus"));
        status_reader.useDelimiter("/");
        String cs = "";
        while (status_reader.hasNext()) {
            cs = status_reader.next();
        }

        info = new ServiceInfo(cc, cs, sn, t, to, tsp, sId, st);
    }

    public Service.ServiceInfo getServiceInfo() {
        return info;
    }
}
