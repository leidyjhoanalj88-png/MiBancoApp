package com.mibanco.app;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        saldo = findViewById(R.id.saldo);

        SessionManager session = new SessionManager(this);
        String username = session.getUsername();
        String token = session.getToken();

        new Thread(() -> {
            try {
                org.json.JSONObject resp = ApiClient.getSaldo(username, token);
                String monto = resp.getString("saldo");
                runOnUiThread(() -> saldo.setText("Saldo: $" + monto));
            } catch (Exception e) {
                runOnUiThread(() -> saldo.setText("Error al obtener saldo"));
            }
        }).start();
    }
}