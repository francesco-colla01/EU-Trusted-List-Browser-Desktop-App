package project.framework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineTest {
    @Test
    public void evaluateSearchEngineUniqueness() throws IOException {
        System.out.println("test evaluateSearchEngineUniqueness (Singleton GoF pattern)");
        SearchEngine se1 = SearchEngine.getInstance();
        SearchEngine se2 = SearchEngine.getInstance();
        assertTrue(se1 == se2);
    }

}