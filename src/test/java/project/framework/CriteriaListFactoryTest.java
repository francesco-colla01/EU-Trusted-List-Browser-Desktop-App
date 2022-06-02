package project.framework;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CriteriaListFactoryTest {
    @BeforeAll
    void setUp() throws IOException {
        CriteriaListFactory testObject = new CriteriaListFactory();
    }
    @Test
    public void evaluateCorrectDataStructureBuilding() {
        System.out.println("test evaluateCorrectDataStructureBuilding");
        Map<String, String> countries = CriteriaListFactory.getCountryList();
        Vector<Provider> providers = CriteriaListFactory.getProviderList();
        Vector<String> types = CriteriaListFactory.getTypeList();
        Vector<String> statuses = CriteriaListFactory.getStatusList();
        assertTrue(!countries.isEmpty() && !providers.isEmpty() &&
                !types.isEmpty() && !statuses.isEmpty() && !countries.containsKey(null) &&
                !countries.containsValue(null) && !providers.contains(null) &&
                !types.contains(null) && !statuses.contains(null));
    }
    @Test
    public void evaluateCorrectCountryList() {
        Map<String, String> countries = CriteriaListFactory.getCountryList();
        for (String code : countries.keySet()) {
            assertEquals(2, code.length());
        }
    }
    @Test
    public void evaluateCorrectProviderList() {
        Vector<Provider> providers = CriteriaListFactory.getProviderList();
        for (Provider p : providers) {
            assertEquals(p.getClass(), Provider.class);
        }
    }
}