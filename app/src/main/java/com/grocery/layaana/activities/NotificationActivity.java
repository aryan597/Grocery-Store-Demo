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
import com.grocery.layaana.adapters.NotificationAdapter;
import com.grocery.layaana.adapters.WishListAdapter;
import com.grocery.layaana.model.NotificationItems;
import com.grocery.layaana.model.WishListItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerViewNotification;
    ArrayList<NotificationItems> notificationItemsArrayList;
    ImageView backBtn,NoItem,clearNotification;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        clearNotification = findViewById(R.id.clear_notification);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationActivity.super.onBackPressed();
            }
        });
        NoItem = findViewById(R.id.NoItem);
        loadDataNotification();
        recyclerViewNotification = findViewById(R.id.notification_recycler);
        if (notificationItemsArrayList.isEmpty()){
            recyclerViewNotification.setVisibility(View.GONE);
            NoItem.setVisibility(View.VISIBLE);
        }
        setNotificationRecyclerView(notificationItemsArrayList,NotificationActivity.this);
        clearNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationItemsArrayList.removeAll(notificationItemsArrayList);
                addNotificationToSp();
                notificationAdapter.notifyDataSetChanged();
            }
        });
    }
    public void setNotificationRecyclerView(ArrayList<NotificationItems> arrayList, Context context) {
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        notificationAdapter = new NotificationAdapter(context, arrayList);
        recyclerViewNotification.setAdapter(notificationAdapter);
    }
    private void addNotificationToSp() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserNotificationItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationItemsArrayList);
        editor.putString("items3", json);
        editor.apply();
    }
    private void loadDataNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserNotificationItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items3", null);
        Type type = new TypeToken<ArrayList<NotificationItems>>() {}.getType();
        notificationItemsArrayList = gson.fromJson(json, type);
        if (notificationItemsArrayList== null) {
            // if the array list is empty
            // creating a new array list.
            notificationItemsArrayList = new ArrayList<>();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        notificationAdapter.notifyDataSetChanged();
    }
}