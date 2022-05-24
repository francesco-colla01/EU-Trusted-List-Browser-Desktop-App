package project.framework;

public class ServiceInfo {
    private String countryCode, currentStatus, serviceName, typeIdentifier;
    private String[] serviceTypes;

    /**
     *  ServiceInfo constructor; creates a ServiceInfo object responsible for
     *  keeping in one place all service information.
     *
     * @param  cc country code of the service created (e.g IT)
     * @param  cs country full name of the service created (e.g italy)
     * @param  sn name of the service created
     * @param  t  typeIdentifier
     * @param  st service types related to the service created
     *
     */
    ServiceInfo(String cc, String cs, String sn, String t, String[] st) {
        countryCode = cc;
        currentStatus = cs;
        serviceName = sn;
        typeIdentifier = t;
        serviceTypes = st;
    }

    //Get methods for all the private attributes
    public String getCountryCode() { return countryCode; }
    public String getCurrentStatus() { return currentStatus; }
    public String getServiceName() { return serviceName; }
    public String getTypeIdentifier() { return typeIdentifier; }
    public String[] getServiceTypes() { return serviceTypes; }
}
