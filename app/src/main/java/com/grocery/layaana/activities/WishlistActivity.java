package com.grocery.layaana.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.adapters.OrdersAdapter;
import com.grocery.layaana.adapters.WishListAdapter;
import com.grocery.layaana.model.OrderItems;
import com.grocery.layaana.model.WishListItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {
    RecyclerView recyclerViewWishList;
    ArrayList<WishListItems> wishListItemsArrayList;
    ImageView backBtn,NoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WishlistActivity.super.onBackPressed();
            }
        });
        NoItem = findViewById(R.id.NoItem);
        loadDataWishList();
        recyclerViewWishList = findViewById(R.id.recyclerViewWishList);
        if (wishListItemsArrayList.isEmpty()){
            recyclerViewWishList.setVisibility(View.GONE);
            NoItem.setVisibility(View.VISIBLE);
        }
        setOrdersRecyclerview(wishListItemsArrayList,getApplicationContext());

    }
    public void setOrdersRecyclerview(ArrayList<WishListItems> arrayList, Context context) {
        recyclerViewWishList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        WishListAdapter wishListAdapter = new WishListAdapter(context, arrayList);
        recyclerViewWishList.setAdapter(wishListAdapter);
    }
    private void loadDataWishList() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserWishListItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items2", null);
        Type type = new TypeToken<ArrayList<WishListItems>>() {}.getType();
        wishListItemsArrayList = gson.fromJson(json, type);
        if (wishListItemsArrayList== null) {
            // if the array list is empty
            // creating a new array list.
            wishListItemsArrayList = new ArrayList<>();
        }
    }
}