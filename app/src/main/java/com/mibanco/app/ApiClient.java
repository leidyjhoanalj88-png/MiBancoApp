package com.mibanco.app;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    static String API = "https://tuservidor.com/api";

    static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    public static void login(String user, String pass, Context ctx) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", user);
            json.put("password", pass);

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),
                    json.toString()
            );

            Request request = new Request.Builder()
                    .url(API + "/login")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        ctx.startActivity(new Intent(ctx, DashboardActivity.class));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(String user, String pass) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", user);
            json.put("password", pass);

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),
                    json.toString()
            );

            Request request = new Request.Builder()
                    .url(API + "/register")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSaldo(String user, TextView view) {
        Request request = new Request.Builder()
                .url(API + "/saldo/" + user)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    view.post(() -> view.setText(data));
                }
            }
        });
    }
}