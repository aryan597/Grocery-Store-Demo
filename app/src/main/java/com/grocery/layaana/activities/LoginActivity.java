package com.grocery.layaana.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView createAccount;
    TextView forgotPassword;
    MaterialButton loginBtn;
    private FirebaseAuth mAuth;
    TextInputEditText email,password;
    private SharedPreferences sp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        loginBtn = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        createAccount = findViewById(R.id.create_account_text);
        forgotPassword = findViewById(R.id.forgot_password);
        backBtn = findViewById(R.id.back_btn);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        sp = getSharedPreferences("MyUserData", Context.MODE_PRIVATE);
        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this,R.layout.custom_dialog_load_logging);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).build();

                                    user.updateProfile(profileUpdates);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("userName",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                    editor.putString("userPhone",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                                    editor.apply();
                                    loadingDialog.dismissDialog();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else if (!task.isSuccessful()){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.dismissDialog();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
                finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, IntroActivity.class));
                LoginActivity.this.finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, IntroActivity.class));
        finish();
    }
}
