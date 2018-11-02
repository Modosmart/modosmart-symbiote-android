package com.modosmart.symbiote.modosmartsymbioteandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.modosmart.symbiote.modosmartsymbioteandroid.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startTimer();
    }

    /**
     * Starts the next activity with a little delay
     */
    private void startTimer() {
        Handler splashTimer = new Handler();
        splashTimer.postDelayed(new Runnable() {
            public void run() {
                Intent configIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(configIntent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
