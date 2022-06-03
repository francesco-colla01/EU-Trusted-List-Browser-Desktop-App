package project.framework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpRequestTest {
    private HttpRequest testObject;
    @BeforeAll
    void setUp() throws IOException {
        testObject = new HttpRequest("https://www.youtube.com/");
    }
    @Test
    void checkWhetherTheResponseIsBuild() throws IOException {
        assertNotNull(testObject.getResponse());
    }

    /*
    @Test
    void evaluateCorrectErrorHandling() throws IOException {
        assertThrows(java.io.IOException, HttpRequest() amogus = new HttpRequest());
    }
    */
}