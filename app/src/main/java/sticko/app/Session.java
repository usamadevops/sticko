package sticko.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setToken(String token) {
        prefs.edit()
                .putString("token", token)
                .apply();
    }
    public String getToken() {
        String primarykey = prefs.getString("token", "");
        return primarykey;
    }

    public void setRememberMe(boolean checked){
        prefs.edit()
                .putBoolean("remember_me", checked)
                .apply();
    }

    public boolean getRememberMe() {
        return prefs.getBoolean("remember_me", false);
    }

    public void setUsername(String username){
        prefs.edit()
                .putString("username", username)
                .apply();
    }
    public String getUserName() {
        return prefs.getString("username", "");
    }

    public void remove(){
        prefs.edit().remove("token").remove("remember_me").remove("username").apply();
    }
}
