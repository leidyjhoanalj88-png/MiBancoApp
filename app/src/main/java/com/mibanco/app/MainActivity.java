package com.mibanco.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        session = new SessionManager(this);

        if (session.isLogged()) {

            Intent i = new Intent(
                    MainActivity.this,
                    DashboardActivity.class
            );

            startActivity(i);

        } else {

            Intent i = new Intent(
                    MainActivity.this,
                    LoginActivity.class
            );

            startActivity(i);

        }

        finish();
    }
}