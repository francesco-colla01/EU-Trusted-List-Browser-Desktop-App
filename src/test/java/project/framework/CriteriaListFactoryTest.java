package project.framework;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.SortedMap;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CriteriaListFactoryTest {
    private CriteriaListFactory testObject;
    @BeforeAll
    void setUp() throws IOException {
        testObject = new CriteriaListFactory();
    }
    @Test
    public void evaluateCorrectDataStructorBuilding() throws IOException {
        System.out.println("test evaluateCorrectDataStructorBuilding");
        assertTrue(!CriteriaListFactory.getCountryList().isEmpty() &&
                !CriteriaListFactory.getProviderList().isEmpty() &&
                !CriteriaListFactory.getTypeList().isEmpty() &&
                !CriteriaListFactory.getStatusList().isEmpty());
    }
}