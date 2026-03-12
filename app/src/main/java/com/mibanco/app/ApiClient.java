package com.mibanco.app;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    // URL correcta de tu backend
    private static final String BASE_URL = "https://web-production-079c6.up.railway.app";

    private static String readResponse(HttpURLConnection conn) throws Exception {

        BufferedReader reader;

        if (conn.getResponseCode() >= 400) {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

    public static JSONObject login(String username, String password) throws Exception {

        URL url = new URL(BASE_URL + "/login");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setDoOutput(true);

        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);

        OutputStream os = conn.getOutputStream();
        os.write(body.toString().getBytes("UTF-8"));
        os.close();

        int code = conn.getResponseCode();

        String response = readResponse(conn);

        if (code != 200) {
            throw new Exception("Login fallido: " + response);
        }

        return new JSONObject(response);
    }

    public static JSONObject register(String username, String password) throws Exception {

        URL url = new URL(BASE_URL + "/register");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setDoOutput(true);

        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);

        OutputStream os = conn.getOutputStream();
        os.write(body.toString().getBytes("UTF-8"));
        os.close();

        int code = conn.getResponseCode();

        String response = readResponse(conn);

        if (code != 200 && code != 201) {
            throw new Exception("Error al registrar: " + response);
        }

        return new JSONObject(response);
    }

    public static JSONObject getSaldo(String username, String token) throws Exception {

        URL url = new URL(BASE_URL + "/saldo/" + username);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        int code = conn.getResponseCode();

        String response = readResponse(conn);

        if (code == 401) {
            throw new Exception("Token inválido");
        }

        if (code != 200) {
            throw new Exception("Error servidor: " + response);
        }

        return new JSONObject(response);
    }
}