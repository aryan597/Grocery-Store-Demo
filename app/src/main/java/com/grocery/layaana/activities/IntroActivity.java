package com.grocery.layaana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.grocery.layaana.R;

public class IntroActivity extends AppCompatActivity {
    MaterialButton loginBtn;
    TextView signup;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        this.loginBtn = findViewById(R.id.login_btn);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntroActivity.this.startActivity(new Intent(IntroActivity.this, OtpActivity.class));
            }
        });
        signup = findViewById(R.id.textView6);
        signup.setOnClickListener(v->{
            startActivity(new Intent(this,CreateAccountActivity.class));
        });
    }
}
