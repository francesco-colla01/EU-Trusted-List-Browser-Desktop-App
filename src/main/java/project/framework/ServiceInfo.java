package project.framework;

public class ServiceInfo {
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
