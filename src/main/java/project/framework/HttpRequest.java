package project.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    private String resultBody;
    private HttpURLConnection con;
    private int requestStatus;
    private boolean errorStatus;

    /**
     * HttpRequest constructor; this class is used to prepare a connection with
     * the EU Trust Services Dashboard API. If it takes more than
     * 10 seconds or no internet connection is available, it will fail.
     * At the end calls method executeConnection().
     *
     * @param urlConnection link expressed in a String
     * @throws IOException
     */
    public HttpRequest(String urlConnection) throws IOException {
        URL url = new URL(urlConnection);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setReadTimeout(10000);

        executeConnection();
    }

    /**
     * Establish connection with the server and check whether the connection
     * fails with value of responseStatus variable
     *
     * @throws IOException
     */
    private void executeConnection() throws IOException {
        requestStatus = con.getResponseCode();

        errorStatus = requestStatus > 299;

        buildResultString();
    }

    /**
     * Build the resultBody variable through JSon response
     *
     * @throws IOException
     */
    private void buildResultString() throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        resultBody = content.toString();
    }

    //Get methods
    public int getRequestStatus() { return requestStatus; }
    public String getResponse() throws IOException { return resultBody; }
}