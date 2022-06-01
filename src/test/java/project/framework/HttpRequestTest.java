package project.framework;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    @Test
    void checkWhetherTheResponseIsBuild() throws IOException {
        HttpRequest queryTest = new HttpRequest("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        assertNotNull(queryTest.getResponse());
    }
    /*
    @Test
    void evaluateCorrectErrorHandling() throws IOException {
        assertThrows(java.io.IOException, HttpRequest() amogus = new HttpRequest());
    }
    */
}