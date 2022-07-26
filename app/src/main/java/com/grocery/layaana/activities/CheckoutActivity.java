package com.grocery.layaana.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.grocery.layaana.LoadingDialog;
import com.grocery.layaana.R;
import com.grocery.layaana.model.CartItems;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener {
    MaterialButton confirmOrderBtn;
    String productPrice;
    ArrayList<CartItems> cartItemsArrayList;
    int sum = 0,quantity = 0,q=0;
    TextView subtotal,discount,delivery_charge,total_calculation;
    RadioButton cash,upi,home,office,other;
    RadioGroup paymentGroup,addressGroup;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        productPrice = getIntent().getStringExtra("productPrice");
        subtotal = findViewById(R.id.subtotal);
        total_calculation = findViewById(R.id.total);
        cash = findViewById(R.id.cash_on_delivery);
        upi = findViewById(R.id.card_payment);
        home = findViewById(R.id.home);
        office = findViewById(R.id.office);
        other = findViewById(R.id.other);
        paymentGroup = findViewById(R.id.radioGroup);
        addressGroup = findViewById(R.id.address_group);
        cartItemsArrayList = new ArrayList<>();
        cartItemsArrayList = (ArrayList<CartItems>) getIntent().getSerializableExtra("QuestionListExtra");
        for (int i = 0; i < cartItemsArrayList.size(); i++) {
            sum = Integer.parseInt(cartItemsArrayList.get(i).getProductPrice());
            quantity = cartItemsArrayList.get(i).getQuantity();
            q+=sum*quantity;
        }
        String val = String.valueOf(q);
        subtotal.setText("₹ "+val);
        discount = findViewById(R.id.discount);
        int disc = 20;
        int delivery = 40;
        delivery_charge = findViewById(R.id.delivery_charge);
        int discPrice = q-disc;
        int totalPrice = discPrice+delivery;
        delivery_charge.setText("₹ "+delivery);
        discount.setText("- ₹ "+ disc);
        total_calculation.setText("₹ "+totalPrice);
        MaterialButton materialButton = findViewById(R.id.confirm_payment_btn);
        this.confirmOrderBtn = materialButton;
        materialButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkRadio(totalPrice);

            }
        });
    }

    private void checkRadio(int totalPrice) {
        if (paymentGroup.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            Toast.makeText(this, "Please select a Payment Option.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // one of the radio buttons is checked
            if (upi.isChecked()){
                if (addressGroup.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(this, "Please select an address Option.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // one of the radio buttons is checked
                    if (home.isChecked()){
                        Checkout checkout = new Checkout();
                        checkout.setKeyID("rzp_test_GLpgvh4csbleo8");
                        checkout.setImage(R.drawable.favicon);
                        int totalPriceRazor = totalPrice*100;
                        JSONObject object = new JSONObject();
                        try {
                            object.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            object.put("description","Pay to Layaana");
                            object.put("theme.color","#0093DD");
                            object.put("currency","INR");
                            object.put("amount",totalPriceRazor);
                            object.put("prefill.contact","+910000000000");
                            object.put("prefill.email","a@gmail.com");
                            checkout.open(CheckoutActivity.this,object);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }else if (office.isChecked()){
                        Checkout checkout = new Checkout();
                        checkout.setKeyID("rzp_test_GLpgvh4csbleo8");
                        checkout.setImage(R.drawable.favicon);
                        int totalPriceRazor = totalPrice*100;
                        JSONObject object = new JSONObject();
                        try {
                            object.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            object.put("description","Pay to Layaana");
                            object.put("theme.color","#0093DD");
                            object.put("currency","INR");
                            object.put("amount",totalPriceRazor);
                            object.put("prefill.contact",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            object.put("prefill.email","a@gmail.com");
                            checkout.open(CheckoutActivity.this,object);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else if (other.isChecked()){
                        Checkout checkout = new Checkout();
                        checkout.setKeyID("rzp_test_GLpgvh4csbleo8");
                        checkout.setImage(R.drawable.favicon);
                        int totalPriceRazor = totalPrice*100;
                        JSONObject object = new JSONObject();
                        try {
                            object.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            object.put("description","Pay to Layaana");
                            object.put("theme.color","#0093DD");
                            object.put("currency","INR");
                            object.put("amount",totalPriceRazor);
                            object.put("prefill.contact",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            object.put("prefill.email","a@gmail.com");
                            checkout.open(CheckoutActivity.this,object);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }


            }else if (cash.isChecked()){
                if (addressGroup.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(this, "Please select an address Option.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // one of the radio buttons is checked
                    if (home.isChecked()){
                        startActivity(new Intent(CheckoutActivity.this, OrderPlacedActivity.class));
                        finish();

                    }else if (office.isChecked()){
                        startActivity(new Intent(CheckoutActivity.this, OrderPlacedActivity.class));
                        finish();

                    }else if (other.isChecked()){
                        startActivity(new Intent(CheckoutActivity.this, OrderPlacedActivity.class));
                        finish();

                    }
                }

            }
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Payment Done");
        dialog.setMessage("You will be redirected to app Soon.");
        dialog.setCancelable(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(CheckoutActivity.this, OrderPlacedActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
}
