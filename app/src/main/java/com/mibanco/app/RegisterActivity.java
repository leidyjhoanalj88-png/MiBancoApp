package com.mibanco.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
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
            ApiClient.register(u, p);
        });
    }
}