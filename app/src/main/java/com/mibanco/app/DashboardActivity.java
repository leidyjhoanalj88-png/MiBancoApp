package com.mibanco.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    TextView saldo;
    Button btnActualizar;
    Button btnCerrarSesion;

    SessionManager session;

    String username;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        saldo = findViewById(R.id.saldo);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        session = new SessionManager(this);

        username = session.getUser();
        token = session.getToken();

        // cargar saldo al iniciar
        cargarSaldo();

        btnActualizar.setOnClickListener(v -> cargarSaldo());

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());
    }

    private void cargarSaldo() {

        new Thread(() -> {

            try {

                JSONObject resp = ApiClient.getSaldo(username, token);
                String monto = resp.getString("saldo");

                runOnUiThread(() ->
                        saldo.setText("Saldo: $" + monto)
                );

            } catch (Exception e) {

                runOnUiThread(() -> {
                    saldo.setText("Error saldo");
                    Toast.makeText(DashboardActivity.this,
                            "No se pudo cargar el saldo",
                            Toast.LENGTH_SHORT).show();
                });
            }

        }).start();
    }

    private void cerrarSesion() {

        session.logout();

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        finish();
    }
}