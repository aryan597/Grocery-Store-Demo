package com.grocery.layaana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.grocery.layaana.R;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    ImageView backBtn;
    TextInputEditText phone,otp;
    TextView msgTextView,timer,resendOtp,number;
    MaterialButton sendOtp,emailLogin,verifyOtp;
    private FirebaseAuth mAuth;
    private String verificationId;
    ConstraintLayout phoneLayout,otpLayout;
    public int counter;
    ProgressDialog dialog;
    boolean isOtp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        mAuth = FirebaseAuth.getInstance();
        phoneLayout = findViewById(R.id.otp_replace_layout);
        otpLayout = findViewById(R.id.otp_replace_layout_1);
        phoneLayout.setVisibility(View.VISIBLE);
        otpLayout.setVisibility(View.GONE);
        otp = findViewById(R.id.user_otp);
        msgTextView = findViewById(R.id.tex);
        verifyOtp = findViewById(R.id.verifyOtp_btn);
        timer = findViewById(R.id.timer);
        resendOtp = findViewById(R.id.resend_otp);
        number = findViewById(R.id.number);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isOtp == true){
                    otpLayout.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.VISIBLE);
                    isOtp = false;
                }else {
                    OtpActivity.super.onBackPressed();
                }
            }
        });
        phone = findViewById(R.id.user_phone);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        sendOtp = findViewById(R.id.sendOtp_btn);
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(OtpActivity.this);
                dialog.show();
                isOtp = true;
                phoneLayout.setVisibility(View.GONE);
                otpLayout.setVisibility(View.VISIBLE);
                String phoneNumber = "+91" + phone.getText().toString();
                sendVerificationCode(phoneNumber);
                msgTextView.setText("Please Enter the One Time Password sent on : "+phoneNumber);
                new CountDownTimer(60000, 1000){
                    public void onTick(long millisUntilFinished){
                        number.setText(millisUntilFinished / 1000 +" Seconds");
                        timer.setText("Resend OTP in ");
                    }
                    public  void onFinish(){
                        timer.setVisibility(View.GONE);
                        number.setVisibility(View.GONE);
                        resendOtp.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                verifyCode(otp.getText().toString());
            }
        });
        emailLogin = findViewById(R.id.login);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void sendVerificationCode(String toString) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(toString)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        dialog.dismiss();
    }
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Intent i = new Intent(OtpActivity.this, MainActivity.class);
                            dialog.dismiss();
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                otp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onBackPressed() {
        if (isOtp == true){
            otpLayout.setVisibility(View.GONE);
            phoneLayout.setVisibility(View.VISIBLE);
            isOtp = false;
        }else {
            super.onBackPressed();
        }
    }
}