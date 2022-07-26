package com.grocery.layaana.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.grocery.layaana.R;

public class SplashScreen extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (restorePrefData()) {
                    Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class );
                    startActivity(mainActivity);
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(),SplashActivity.class));
                }
            }
        }, 2000);
    }
    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }
}
