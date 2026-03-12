package com.mibanco.app;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences("mibanco", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveSession(String user, String token){
        editor.putString("user", user);
        editor.putString("token", token);
        editor.apply();
    }

    public String getUser(){
        return prefs.getString("user", null);
    }

    public String getToken(){
        return prefs.getString("token", null);
    }

    public void logout(){
        editor.clear();
        editor.apply();
    }
}