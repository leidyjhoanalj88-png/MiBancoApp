package com.app.bankui;

import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiClient {

    private static final String BASE_URL = "https://web-production-0e1d83.up.railway.app";

    public static JSONObject login(String username, String password) throws Exception {
        URL url = new URL(BASE_URL + "/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);

        OutputStream os = conn.getOutputStream();
        os.write(body.toString().getBytes("UTF-8"));
        os.close();

        int code = conn.getResponseCode();
        if (code != 200) throw new Exception("Login fallido. Código: " + code);

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) sb.append(sc.nextLine());
        sc.close();
        return new JSONObject(sb.toString());
    }

    public static JSONObject getSaldo(String username, String token) throws Exception {
        URL url = new URL(BASE_URL + "/saldo/" + username);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int code = conn.getResponseCode();
        if (code == 401) throw new Exception("Token inválido o expirado");
        if (code != 200) throw new Exception("Error al obtener saldo. Código: " + code);

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) sb.append(sc.nextLine());
        sc.close();
        return new JSONObject(sb.toString());
    }

    public static JSONObject getMovimientos(String username, String token) throws Exception {
        URL url = new URL(BASE_URL + "/movimientos/" + username);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int code = conn.getResponseCode();
        if (code == 401) throw new Exception("Token inválido o expirado");
        if (code != 200) throw new Exception("Error al obtener movimientos. Código: " + code);

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) sb.append(sc.nextLine());
        sc.close();
        return new JSONObject(sb.toString());
    }
}