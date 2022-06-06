package project.framework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpRequestTest {
    @Test
    void checkWhetherTheResponseIsBuild() throws IOException {
        System.out.println("checkWhetherTheResponseIsBuild Test");
        HttpRequest testObject = new HttpRequest("https://www.youtube.com/");
        assertNotNull(testObject.getResponse());
    }

    @Test
    void evaluateConnectionErrorHandling1() {
        System.out.println("evaluateConnectionErrorHandling1 Test");
        //works only if nothing is uploaded in your local host
        assertThrows(java.net.ConnectException.class, () -> new HttpRequest("http://localhost"));
    }

    @Test
    void evaluateConnectionErrorHandling2() {
        System.out.println("evaluateConnectionErrorHandling2 Test");
        assertThrows(java.net.ConnectException.class, () -> new HttpRequest("http://google.com:81"));
    }
}