package com.grocery.layaana.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.grocery.layaana.LoadingDialog;
import com.grocery.layaana.R;

public class CreateAccountActivity extends AppCompatActivity {
    ImageView backBtn;
    MaterialButton createAccountBtn;
    TextView login;
    private SharedPreferences sp;
    TextInputEditText name,email,phone,password;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        createAccountBtn = findViewById(R.id.create_account_btn);
        sp = getSharedPreferences("MyUserData", Context.MODE_PRIVATE);
        login = findViewById(R.id.login_text);
        backBtn = findViewById(R.id.back_btn);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.user_phone);
        password = findViewById(R.id.user_password);
        final LoadingDialog loadingDialog = new LoadingDialog(CreateAccountActivity.this,R.layout.custom_dialog_load_create_account);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.getText().toString()).build();

                                    user.updateProfile(profileUpdates);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("userName",name.getText().toString());
                                    editor.putString("userPhone",phone.getText().toString());
                                    editor.apply();
                                    loadingDialog.dismissDialog();
                                    startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.dismissDialog();
                                Toast.makeText(CreateAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, IntroActivity.class));
                finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, IntroActivity.class));
        finish();
    }
}
