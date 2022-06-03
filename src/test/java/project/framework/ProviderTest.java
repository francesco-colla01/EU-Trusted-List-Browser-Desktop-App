package project.framework;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.SortedMap;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProviderTest {
    private Provider provider;

    @BeforeAll
    void setUp() throws IOException {
        CriteriaListFactory factory = new CriteriaListFactory();
        String shortJson = "{\n" +
                "    \"tspId\": 1,\n" +
                "    \"name\": \"A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH\",\n" +
                "    \"countryCode\": \"AT\",\n" +
                "    \"trustmark\": \"VATAT-U50272100\",\n" +
                "    \"qServiceTypes\": [\n" +
                "      \"QCertESeal\",\n" +
                "      \"CertESeal\",\n" +
                "      \"QCertESig\",\n" +
                "      \"WAC\",\n" +
                "      \"QWAC\",\n" +
                "      \"CertESig\"\n" +
                "    ],\n" +
                "    \"services\": [\n" +
                "      {\n" +
                "        \"tspId\": 1,\n" +
                "        \"serviceId\": 1,\n" +
                "        \"countryCode\": \"AT\",\n" +
                "        \"serviceName\": \"TrustSign-Sig-01 (key no. 1)\",\n" +
                "        \"type\": \"http://uri.etsi.org/TrstSvc/Svctype/CA/QC\",\n" +
                "        \"currentStatus\": \"http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn\",\n" +
                "        \"tob\": null,\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"QCertESig\"\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }";

        provider = new Provider(shortJson);
    }

    @Test
    void CountryCodeCorrectBuildingTest() {
        System.out.println("test correct building of Provider country code (Test Criteria List factory indirectly)");
        assertEquals("AT", provider.getCountryCode());
    }

    @Test
    void CountryNameCorrectBuildingTest() {
        System.out.println("test correct building of Provider country name (Test Criteria List factory indirectly)");
        assertEquals("Austria", provider.getCountryName());
    }

    @Test
    void NameCorrectBuildingTest() {
        System.out.println("test correct building of Provider country name (Test Criteria List factory indirectly)");
        assertEquals("A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH", provider.getName());
    }

    @Test
    void TrustmarkCorrectBuildingTest() {
        System.out.println("test correct building of Provider trustmark");
        assertEquals("VATAT-U50272100", provider.getTrustmark());
    }

    @Test
    void ServiceTypesCorrectBuildingTest() {
        System.out.println("test correct building of Provider's type of services");
        String[] typeExpected = {"QCertESeal", "CertESeal", "QCertESig", "WAC", "QWAC", "CertESig"};
        assertArrayEquals(typeExpected, provider.getServiceTypes());
    }
}