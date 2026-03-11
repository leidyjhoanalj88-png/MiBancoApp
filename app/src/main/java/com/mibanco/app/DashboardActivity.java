package com.mibanco.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends Activity {
    TextView saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        saldo = findViewById(R.id.saldo);
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        String u = prefs.getString("user", "");
        ApiClient.getSaldo(u, saldo);
    }
}