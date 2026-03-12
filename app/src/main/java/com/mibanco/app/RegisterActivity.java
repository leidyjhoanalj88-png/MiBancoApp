package com.mibanco.app;

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
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    ApiClient.register(u, p);
                    runOnUiThread(() ->
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    );
                } catch (Exception e) {
                    runOnUiThread(() ->
                        Toast.makeText(this, "Error al registrar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();
        });
    }
}