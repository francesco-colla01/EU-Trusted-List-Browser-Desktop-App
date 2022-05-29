package project.framework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.SortedMap;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class CriteriaListFactoryTest {
    @Test
    public void evaluateCorrectDataStructorBuilding() throws IOException {
        System.out.println("test evaluateCorrectDataStructorBuilding");
        CriteriaListFactory f = new CriteriaListFactory();
        assertTrue(!f.getCountryList().isEmpty() && !f.getProviderList().isEmpty()
                    && !f.getTypeList().isEmpty() && !f.getStatusList().isEmpty());
    }
}