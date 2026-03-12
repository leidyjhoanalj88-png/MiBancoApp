package com.mibanco.app;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    TextView saldo;
    Button btnActualizar;

    SessionManager session;
    String username;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        saldo = findViewById(R.id.saldo);
        btnActualizar = findViewById(R.id.btnActualizar);

        session = new SessionManager(this);

        username = session.getUser();
        token = session.getToken();

        cargarSaldo();

        btnActualizar.setOnClickListener(v -> cargarSaldo());
    }

    private void cargarSaldo(){

        new Thread(() -> {

            try {

                JSONObject resp = ApiClient.getSaldo(username, token);
                String monto = resp.getString("saldo");

                runOnUiThread(() ->
                        saldo.setText("Saldo: $" + monto)
                );

            } catch (Exception e){

                runOnUiThread(() ->
                        saldo.setText("Error saldo")
                );
            }

        }).start();
    }
}