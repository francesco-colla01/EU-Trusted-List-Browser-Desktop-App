package project.framework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchCriteriaTest {
    @Test
    void evaluateInvalidParameters() {
        System.out.println("evaluateInvalidParameters test for SearchCriteria class");
        Vector<String> v1 = new Vector<>( );
        Vector<Provider> v2 = new Vector<>();
        Vector<String> v3 = new Vector<>();
        Vector<String> v4 = new Vector<>();
        v1.add(null);
        v2.add(null);
        v3.add(null);
        v4.add(null);
        SearchCriteria sc = new SearchCriteria(v1, v2, v3, v4);
        assertTrue(sc.isInvalid());
    }
}