package com.example.akshay_agrawal.btp_2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                //Intent intent=new Intent(activity_splash.this,carousel.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}

