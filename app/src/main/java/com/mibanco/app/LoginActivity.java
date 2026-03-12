package com.mibanco.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    EditText txtUser, txtPass;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser  = findViewById(R.id.txtUser);
        txtPass  = findViewById(R.id.txtPass);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {
            String user = txtUser.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completá todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            doLogin(user, pass);
        });
    }

    private void doLogin(String user, String pass) {
        new Thread(() -> {
            try {
                org.json.JSONObject resp = ApiClient.login(user, pass);
                String token = resp.getString("token");
                new SessionManager(this).saveSession(user, token);
                runOnUiThread(() -> {
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() ->
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}