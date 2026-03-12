package com.mibanco.app;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences("mibanco", Context.MODE_PRIVATE);
    }

    public void saveSession(String user, String token){
        prefs.edit().putString("user", user)
                .putString("token", token)
                .apply();
    }

}