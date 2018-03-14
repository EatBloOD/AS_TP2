package pt.uc.dei.as;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class RestsUtils
{
    public static final String restfulWS = "http://localhost:8080/RESTfulWS/rest/1.0/";

    public static <T> T doPost(String urlSegment, Object data, Class<T> type, int httpCode) throws Exception
    {
        URL url = new URL(restfulWS + urlSegment);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

        String json = gson.toJson(data);

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes());
        os.flush();

        if (conn.getResponseCode() != httpCode)
            throw new RuntimeException("Failed with status code " + conn.getResponseCode());

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        Gson gson1 = new GsonBuilder().create();
        
        T returnedObject = gson1.fromJson(br, type);

        conn.disconnect();

        return returnedObject;
    }

    public static <T> T doGet(String urlSegment, TypeToken<T> type, int httpCode) throws Exception
    {
        URL url = new URL(restfulWS + urlSegment);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != httpCode)
            throw new RuntimeException("Failed with status code " + conn.getResponseCode());

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

        T returnedObject = gson.fromJson(br, type.getType());

        conn.disconnect();

        return returnedObject;
    }
}
