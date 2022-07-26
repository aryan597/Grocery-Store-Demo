package com.grocery.layaana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.grocery.layaana.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView backBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        setContentView( R.layout.activity_forgot_password);
        ImageView imageView = findViewById(R.id.back_btn);
        this.backBtn = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ForgotPasswordActivity.this.startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });
    }
}
