package project.framework;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class Service {
    private ServiceInfo info;

    /**
     *  Service constructor; creates a Service object.
     *
     * @param  jsonSource is a string get from the Json file in which are
     *                    all the service information needed
     *
     *
     * @see     ServiceInfo class
     * @see     JSONObject
     */
    public Service(String jsonSource) {
        JSONObject data = new JSONObject(jsonSource);

        String cc = data.getString("countryCode");
        String sn = data.getString("serviceName");
        String t = data.getString("type");

        JSONArray jsonServiceTypes = data.getJSONArray("qServiceTypes");
        String[] st = new String[jsonServiceTypes.length()];
        for (int i = 0; i < jsonServiceTypes.length(); i++) {
            st[i] = jsonServiceTypes.getString(i);
        }

        Scanner status_reader = new Scanner(data.getString("currentStatus"));
        status_reader.useDelimiter("/");
        String cs = "";
        while (status_reader.hasNext()) {
            cs = status_reader.next();
        }

        info = new ServiceInfo(cc, cs, sn, t, st);
    }

    //Get method
    public ServiceInfo getServiceInfo() { return info; }
}
