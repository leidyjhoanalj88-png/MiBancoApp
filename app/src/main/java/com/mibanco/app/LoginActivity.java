package com.app.bankui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    EditText txtUser;
    Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.txtUser);
        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(v -> {
            String user = txtUser.getText().toString().trim();
            if (user.isEmpty()) {
                Toast.makeText(this, "Ingresá tu usuario", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(LoginActivity.this, LoginPinActivity.class);
            i.putExtra("username", user);
            startActivity(i);
        });
    }
}