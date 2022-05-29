package project.framework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineTest {

    SearchEngine se1 = new SearchEngine();
    SearchEngine se2 = new SearchEngine();

    @BeforeEach
    void setUp() {
    }

    @Test
    public void evaluateSearchEngineUniqueness() throws IOException {
        System.out.println("test evaluateSearchEngineUniqueness");
        CriteriaListFactory f = new CriteriaListFactory();
        assertTrue(se1 == se2);
    }

}