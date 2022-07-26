package com.grocery.layaana.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.model.CartItems;
import com.grocery.layaana.model.OrderItems;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderPlacedActivity extends AppCompatActivity {
    MaterialButton backToHomeBtn;
    LottieAnimationView animationHurray;
    TextView title,subtext;
    ArrayList<CartItems> cartItemsList;
    ArrayList<OrderItems> ordersArrayList;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        MaterialButton materialButton = findViewById(R.id.back_to_home_btn);
        animationHurray = findViewById(R.id.animation_hurray);
        title = findViewById(R.id.title);
        subtext = findViewById(R.id.subtext);
        this.backToHomeBtn = materialButton;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                loadData();
                loadDataOrder();
                AddItemsToOrdersList();
                cartItemsList.removeAll(cartItemsList);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("MyUserCartItems").apply();
                SharedPreferences sharedPreferences = getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(cartItemsList);
                editor.putString("items", json);
                editor.apply();
                animationHurray.setVisibility(View.VISIBLE);
                title.setText("Order Placed");
                subtext.setVisibility(View.VISIBLE);
            }
        }, 2000);
        materialButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OrderPlacedActivity.this.startActivity(new Intent(OrderPlacedActivity.this, MainActivity.class));
                OrderPlacedActivity.this.finish();
            }
        });
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<CartItems>>() {}.getType();
        cartItemsList = gson.fromJson(json, type);
    }
    private void loadDataOrder() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserOrderedItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items1", null);
        Type type = new TypeToken<ArrayList<OrderItems>>() {}.getType();
        ordersArrayList = gson.fromJson(json, type);
        if (ordersArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            ordersArrayList = new ArrayList<>();
        }
    }

    private void AddItemsToOrdersList() {
        for (int i1 = 0; i1 < cartItemsList.size(); i1++) {
            int i2 = 0;
            for (int i = 0;i<ordersArrayList.size();i++){
                i2 = i;
            }
            saveData(i1,i2);
        }
    }
    private void saveData(int i,int position) {
        String productName = cartItemsList.get(i).getProductName();
        String productPrice = cartItemsList.get(i).getProductPrice();
        int productImage = cartItemsList.get(i).getProductImage();
        int productQuantity = cartItemsList.get(i).getQuantity();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
        String strDate = sdf.format(c.getTime());
        ordersArrayList.add(new OrderItems(productName, productPrice, "Delivered", productImage, productQuantity,strDate));
        /*int finalQuan ;
        Toast.makeText(this, productName, Toast.LENGTH_SHORT).show();
        if (ordersArrayList.isEmpty()){
            ordersArrayList.add(new OrderItems(productName,productPrice,"Delivered",productImage,productQuantity));
        }else {
            if (ordersArrayList.get(position).getProductName().contains(productName)) {
                Toast.makeText(this, "Is There", Toast.LENGTH_SHORT).show();
                finalQuan = ordersArrayList.get(position).getQuantity() + productQuantity;
                ordersArrayList.set(ordersArrayList.indexOf(ordersArrayList.get(position)), new OrderItems(productName, productPrice, "Delivered", productImage, finalQuan));
             } else {
                ordersArrayList.add(new OrderItems(productName, productPrice, "Delivered", productImage, productQuantity));
            }
        }*/
        SharedPreferences sharedPreferences =getSharedPreferences("MyUserOrderedItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(ordersArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("items1", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }
}
