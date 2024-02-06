package sticko.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.LoginScreen.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo_sticko;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo_sticko = findViewById(R.id.logo_sticko);
        session = new Session(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getToken().equals("") || !session.getRememberMe()) {
                    Intent intent1 = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent1 = new Intent(SplashScreen.this, HomeScreenActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
