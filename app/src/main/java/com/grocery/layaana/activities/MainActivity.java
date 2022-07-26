package com.grocery.layaana.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.fragment.AccountFragment;
import com.grocery.layaana.fragment.CartFragment;
import com.grocery.layaana.fragment.HomeFragment;
import com.grocery.layaana.fragment.YourOrdersFragment;
import com.grocery.layaana.model.CartItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;
import nl.joery.animatedbottombar.BadgeView;
import nl.joery.animatedbottombar.BottomBarStyle;

public class MainActivity extends AppCompatActivity {
    AnimatedBottomBar bottomBar;
    FirebaseAuth mAuth;
    ArrayList<CartItems> cartItemsList;
    LottieAnimationView noInternet;
    TextView noInternetTv;
    FrameLayout fragmentContainer;
    SwipeRefreshLayout swipeRefreshLayout;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()){
                            msg = "Failed";
                        }
                    }
                });
        mAuth = FirebaseAuth.getInstance();
        bottomBar = findViewById(R.id.bottom_bar);
        noInternet = findViewById(R.id.noInternet);
        noInternetTv = findViewById(R.id.NoInternetTv);
        fragmentContainer = findViewById(R.id.fragment_container);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isConnected()){
                    noInternet.setVisibility(View.GONE);
                    noInternetTv.setVisibility(View.GONE);
                    fragmentContainer.setVisibility(View.VISIBLE);
                }
                else{
                    fragmentContainer.setVisibility(View.GONE);
                    noInternetTv.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                }
                loadData();
                if (cartItemsList.isEmpty()){
                    bottomBar.clearBadgeAtTabIndex(1);
                }else if (!cartItemsList.isEmpty()){
                    bottomBar.setBadgeAtTabIndex(1, new AnimatedBottomBar.Badge());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if(isConnected()){
            noInternet.setVisibility(View.GONE);
            noInternetTv.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
            String cart = getIntent().getStringExtra("cart");
            if (cart == null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }else{
                if (cart.equals("cart")){
                    bottomBar.selectTabAt(1,true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
                }else if (cart.equals("order")){
                    bottomBar.selectTabAt(2,true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YourOrdersFragment()).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
            }
            loadData();
            if (cartItemsList.size()>0){
                bottomBar.setBadgeAtTabIndex(1, new AnimatedBottomBar.Badge());
            }
            bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
                public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                    Fragment currentFragment = null;
                    switch (newIndex) {
                        case 0:
                            currentFragment = new HomeFragment();
                            break;
                        case 1:
                            currentFragment = new CartFragment();
                            break;
                        case 2:
                            currentFragment = new YourOrdersFragment();
                            break;
                        case 3:
                            currentFragment = new AccountFragment();
                            break;
                    }
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
                }

                public void onTabReselected(int i, AnimatedBottomBar.Tab tab) {
                }
            });
        }
        else{
            fragmentContainer.setVisibility(View.GONE);
            noInternetTv.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }
    boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            return networkInfo.isConnected();
        }else
            return false;

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<CartItems>>() {}.getType();
        cartItemsList = gson.fromJson(json, type);
        if (cartItemsList == null) {
            cartItemsList = new ArrayList<>();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){

        }else{
            startActivity(new Intent(MainActivity.this,IntroActivity.class));
            finish();
        }

    }
}
