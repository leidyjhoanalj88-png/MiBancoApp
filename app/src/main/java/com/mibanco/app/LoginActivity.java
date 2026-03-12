package com.app.bankui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;

public class LoginPinActivity extends Activity {

    EditText txtPassword;
    Button btnEntrar;
    String username = "";
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        session = new SessionManager(this);
        username = getIntent().getStringExtra("username");
        if (username == null) username = "";

        txtPassword = findViewById(R.id.txtPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {
            String pass = txtPassword.getText().toString().trim();
            if (pass.isEmpty()) {
                Toast.makeText(this, "Ingresá tu contraseña", Toast.LENGTH_SHORT).show();
                return;
            }
            doLogin(pass);
        });
    }

    private void doLogin(String password) {
        new Thread(() -> {
            try {
                JSONObject resp = ApiClient.login(username, password);
                String token = resp.getString("token");
                String user = resp.optString("username", username);
                session.saveSession(user, token);

                runOnUiThread(() -> {
                    Intent i = new Intent(LoginPinActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Contraseña incorrecta o error de red", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}