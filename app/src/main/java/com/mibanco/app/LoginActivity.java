package com.mibanco.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

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

            String user = txtUser.getText().toString();
            String pass = txtPass.getText().toString();

            login(user, pass);
        });
    }

    private void login(String user, String pass){

        new Thread(() -> {

            try {

                JSONObject resp = ApiClient.login(user, pass);
                String token = resp.getString("token");

                SessionManager session = new SessionManager(this);
                session.saveSession(user, token);

                runOnUiThread(() -> {

                    Intent i = new Intent(
                            LoginActivity.this,
                            DashboardActivity.class
                    );

                    startActivity(i);
                    finish();

                });

            } catch (Exception e){

                runOnUiThread(() ->
                        Toast.makeText(
                                LoginActivity.this,
                                "Error login",
                                Toast.LENGTH_LONG
                        ).show()
                );
            }

        }).start();
    }
}