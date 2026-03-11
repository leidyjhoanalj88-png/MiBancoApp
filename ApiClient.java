package com.mibanco.app;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import java.io.IOException;
import okhttp3.*;

public class ApiClient {

    static String API = "https://tuservidor.com/api";

    static OkHttpClient client = new OkHttpClient();

    public static void login(String user, String pass, Context ctx) {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(API + "/login").post(body).build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    ((Activity) ctx).runOnUiThread(() ->
                        ctx.startActivity(new Intent(ctx, DashboardActivity.class))
                    );
                }
            }
        });
    }

    public static void register(String user, String pass) {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(API + "/register").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            public void onResponse(Call call, Response response) {}
        });
    }

    public static void getSaldo(String user, TextView view) {
        Request request = new Request.Builder().url(API + "/saldo/" + user).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    view.post(() -> view.setText(data));
                }
            }
        });
    }
              }
