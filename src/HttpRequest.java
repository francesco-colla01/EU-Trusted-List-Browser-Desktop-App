package it.markovii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRequest {

    String parameters, resultBody;

    HttpURLConnection con;

    int requestStatus;
    boolean errorStatus;


    public HttpRequest(String urlConnection, HashMap<String, String> values) throws IOException {
        try {
            buildParameters(values);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URL url = new URL(urlConnection + parameters);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        executeConnection();
    }

    public HttpRequest(String urlConnection) throws IOException {
        URL url = new URL(urlConnection);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        executeConnection();
    }

    private void buildParameters(HashMap<String, String> values) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (HashMap.Entry<String, String> entry : values.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        parameters = result.toString();
    }

    private void executeConnection() throws IOException {
        requestStatus = con.getResponseCode();

        errorStatus = requestStatus > 299;

        buildResultString();
    }

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

    public int getRequestStatus() { return requestStatus; }

    public String getResponse() throws IOException {
        return resultBody;
    }
}