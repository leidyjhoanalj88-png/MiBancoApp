package com.mibanco.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    Button btnEntrar;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnEntrar = findViewById(R.id.btnEntrar);

        session = new SessionManager(this);

        // Si ya hay sesión abierta, ir directo al dashboard
        if (session.isLogged()) {
            abrirDashboard();
            return;
        }

        btnEntrar.setOnClickListener(v -> {

            String user = txtUser.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(
                        LoginActivity.this,
                        "Ingrese usuario y contraseña",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            login(user, pass);
        });
    }

    private void login(String user, String pass){

        new Thread(() -> {

            try {

                JSONObject resp = ApiClient.login(user, pass);
                String token = resp.getString("token");

                session.saveSession(user, token);

                runOnUiThread(this::abrirDashboard);

            } catch (Exception e){

                runOnUiThread(() ->
                        Toast.makeText(
                                LoginActivity.this,
                                "Error login: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show()
                );
            }

        }).start();
    }

    private void abrirDashboard(){

        Intent i = new Intent(
                LoginActivity.this,
                DashboardActivity.class
        );

        startActivity(i);
        finish();
    }
}