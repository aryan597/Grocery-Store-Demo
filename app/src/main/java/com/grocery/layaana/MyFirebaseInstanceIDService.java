package com.grocery.layaana;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.model.NotificationItems;
import com.grocery.layaana.model.WishListItems;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    ArrayList<NotificationItems> notificationItemsArrayList;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        loadData();
        getFirebaseMessage(message.getNotification().getTitle(),message.getNotification().getBody());
        addNotificationToSp(message.getNotification().getTitle(),message.getNotification().getBody(),message.getNotification().getImageUrl().toString());
    }


    private void loadData() {
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

    private void addNotificationToSp(String title, String body,String uri) {
        notificationItemsArrayList.add(new NotificationItems(title,body,uri));
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserNotificationItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationItemsArrayList);
        editor.putString("items3", json);
        editor.apply();
    }

    public void getFirebaseMessage(String title,String msg){
        long[] pattern = {0, 1000, 1000};
        NotificationCompat.Builder builder
                 = new NotificationCompat.Builder(this,"myFirebaseChannel")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(msg)
                .setVibrate(pattern)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(101,builder.build());

    }
}
