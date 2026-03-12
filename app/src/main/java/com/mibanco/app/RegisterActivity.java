package com.mibanco.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText user, pass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        register = findViewById(R.id.register);

        register.setOnClickListener(v -> {

            String u = user.getText().toString().trim();
            String p = pass.getText().toString().trim();

            if (u.isEmpty() || p.isEmpty()) {

                Toast.makeText(
                        RegisterActivity.this,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            register.setEnabled(false);
            register.setText("Registrando...");

            new Thread(() -> {

                try {

                    ApiClient.register(u, p);

                    runOnUiThread(() -> {

                        Toast.makeText(
                                RegisterActivity.this,
                                "Registro exitoso",
                                Toast.LENGTH_LONG
                        ).show();

                        Intent i = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class
                        );

                        startActivity(i);
                        finish();

                    });

                } catch (Exception e) {

                    runOnUiThread(() -> {

                        register.setEnabled(true);
                        register.setText("Registrarse");

                        Toast.makeText(
                                RegisterActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    });

                }

            }).start();

        });

    }
}