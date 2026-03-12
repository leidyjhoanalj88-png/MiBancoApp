package com.mibanco.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {

            String user = txtUser.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            btnEntrar.setEnabled(false);
            btnEntrar.setText("Ingresando...");

            doLogin(user, pass);
        });
    }

    private void doLogin(String user, String pass) {

        new Thread(() -> {

            try {

                JSONObject resp = ApiClient.login(user, pass);

                String token = resp.getString("token");

                SessionManager session = new SessionManager(LoginActivity.this);
                session.saveSession(user, token);

                runOnUiThread(() -> {

                    Toast.makeText(
                            LoginActivity.this,
                            "Login exitoso",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();

                });

            } catch (Exception e) {

                runOnUiThread(() -> {

                    btnEntrar.setEnabled(true);
                    btnEntrar.setText("Entrar");

                    Toast.makeText(
                            LoginActivity.this,
                            "Usuario o contraseña incorrectos",
                            Toast.LENGTH_LONG
                    ).show();

                });

            }

        }).start();
    }
}