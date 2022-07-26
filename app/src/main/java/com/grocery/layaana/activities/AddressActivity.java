package com.grocery.layaana.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.AddAddressActivity;
import com.grocery.layaana.R;
import com.grocery.layaana.adapters.AddressAdapter;
import com.grocery.layaana.model.AddressItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {
    ArrayList<AddressItems> addressItemsArrayList;
    RecyclerView addressRecyclerView;
    AddressAdapter addressAdapter;
    Button addAddress;
    SwipeRefreshLayout refreshLayout;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        loadDataAddress();
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        addressRecyclerView = findViewById(R.id.recyclerViewAddress);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        addressAdapter = new AddressAdapter(addressItemsArrayList,AddressActivity.this);
        addressRecyclerView.setAdapter(addressAdapter);
        refreshLayout = findViewById(R.id.swipr);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addressAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        addAddress = findViewById(R.id.add_address);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }
    private void loadDataAddress() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserAddressItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items4", null);
        Type type = new TypeToken<ArrayList<AddressItems>>() {}.getType();
        addressItemsArrayList = gson.fromJson(json, type);
        if (addressItemsArrayList== null) {
            // if the array list is empty
            // creating a new array list.
            addressItemsArrayList = new ArrayList<>();
        }
    }
}