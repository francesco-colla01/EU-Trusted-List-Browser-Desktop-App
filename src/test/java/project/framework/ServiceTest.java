package project.framework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceTest {
    Service serviceTest;

    @BeforeAll
    void setUp() {
        String TestJson = "{\n" +
                "        \"tspId\": 1,\n" +
                "        \"serviceId\": 1,\n" +
                "        \"countryCode\": \"IT\",\n" +
                "        \"serviceName\": \"RandomName\",\n" +
                "        \"type\": \"RandomLink\",\n" +
                "        \"currentStatus\": \"ExpressionBeforeStatus/ExampleStatus\",\n" +
                "        \"tob\": null,\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"Type1\",\n" +
                "\t  \"Type2\"\n" +
                "        ]\n" +
                "      }";
        serviceTest = new Service(TestJson, "Italy");
    }

    @Test
    public void evaluateServiceNameCorrectBuilding() {
        System.out.println("test evaluateServiceNameCorrectBuilding");
        assertEquals("RandomName", serviceTest.getServiceName());
    }

    @Test
    public void evaluateCountryCodeCorrectBuilding() {
        System.out.println("test evaluateCountryCodeCorrectBuilding");
        assertEquals("IT", serviceTest.getCountryCode());
    }

    @Test
    public void evaluateStatusCorrectBuilding() {
        System.out.println("test evaluateStatusCorrectBuilding");
        assertEquals("ExampleStatus", serviceTest.getCurrentStatus());
    }

    @Test
    public void evaluateTypeCorrectBuilding() {
        System.out.println("test evaluateTypeCorrectBuilding");
        assertEquals("RandomLink", serviceTest.getTypeIdentifier());
    }

    @Test
    public void evaluateServiceTypesCorrectBuilding() {
        System.out.println("test evaluateServiceTypesCorrectBuilding");
        String[] Expected = {"Type1", "Type2"};
        assertArrayEquals(Expected, serviceTest.getServiceTypes());
    }
}