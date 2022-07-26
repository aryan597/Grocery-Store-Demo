package com.grocery.layaana.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.model.CartItems;
import com.grocery.layaana.model.WishListItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class ProductActivity extends AppCompatActivity {
    ImageView addToWishlistBtn;
    ImageView backBtn;
    ImageView decreaseBtn,productImageIv,cart_btn;
    String title,productPrice,productDescription,productNutritionValue;
    int productImage;
    ImageView increaseBtn;
    private SharedPreferences sp;
    ConstraintLayout c;
    LinearLayout add_remove_layout;
    int productQuantity = 1;
    TextView quantity,productName,productPriceTv,productDescriptionTv,productNutritionValueTv,back;
    MaterialButton addToCart;
    ArrayList<CartItems> CartItemsArrayList;
    ArrayList<WishListItems> wishListItemsArrayList;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        c= findViewById(R.id.constraintLayout);
        back = findViewById(R.id.textView3);
        cart_btn = findViewById(R.id.cart_btn);
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this,MainActivity.class);
                intent.putExtra("cart","cart");
                startActivity(intent);
            }
        });
        sp = getSharedPreferences("MyUserCartItems", Context.MODE_PRIVATE);
        c.setAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_top));
        title = getIntent().getStringExtra("Title");
        productPrice = getIntent().getStringExtra("productPrice");
        productImage = getIntent().getIntExtra("productImage",0);
        productDescription = getIntent().getStringExtra("productDescription");
        productNutritionValue = getIntent().getStringExtra("productNutritionValue");
        productName = findViewById(R.id.product_name);
        productName.setText(title);
        productPriceTv = findViewById(R.id.textView4);
        productPriceTv.setText("Rs. "+productPrice);
        productImageIv = findViewById(R.id.imageView3);
        productImageIv.setImageResource(productImage);
        productImageIv.setAnimation(AnimationUtils.loadAnimation(this,R.anim.abc_slide_in_top));
        productDescriptionTv = findViewById(R.id.product_description);
        productDescriptionTv.setText(productDescription);
        productNutritionValueTv = findViewById(R.id.nutrition_value);
        add_remove_layout = findViewById(R.id.add_remove_layout);
        productNutritionValueTv.setText(productNutritionValue);
        increaseBtn = findViewById(R.id.increase_qty);
        decreaseBtn = findViewById(R.id.decrease_qty);
        quantity = findViewById(R.id.quantity);
        backBtn = findViewById(R.id.back_btn);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        loadData();
        loadDataWishList();
        addToCart = findViewById(R.id.add_to_cart_btn);
        int finalQuan = 1;
        for (int i = 0; i < CartItemsArrayList.size(); i++) {
            if (CartItemsArrayList.get(i).getProductName().equals(title)){
                add_remove_layout.setVisibility(View.VISIBLE);
                addToCart.setText("Remove from Cart");
                quantity.setText(String.valueOf(CartItemsArrayList.get(i).getQuantity()));
                finalQuan = CartItemsArrayList.get(i).getQuantity();
            }
        }
        for (int i = 0;i<wishListItemsArrayList.size();i++){
            if (wishListItemsArrayList.get(i).getProductName().equals(title)){
                ProductActivity.this.addToWishlistBtn.setImageResource(R.drawable.ic_whishlist_fill);
            }
        }
        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pos = 0;
                for (int i = 0;i<wishListItemsArrayList.size();i++){
                    pos = i;
                }
                AddProductToWishList(pos);
                ProductActivity.this.addToWishlistBtn.setImageResource(R.drawable.ic_whishlist_fill);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ProductActivity.this,MainActivity.class));
                finish();
            }
        });
        back.setOnClickListener(v->{
            startActivity(new Intent(ProductActivity.this,MainActivity.class));
            finish();
        });
        final int[] position = {0};
        int quan = finalQuan;
        for (int i = 0; i < CartItemsArrayList.size(); i++) {
            if (CartItemsArrayList.get(i).getProductName().equals(title)){
                quan++;
                position[0] = i;
            }
        }
        int finalPosition = position[0];
        final int[] finalQuan1 = {finalQuan};
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < CartItemsArrayList.size(); i++) {
                    if (CartItemsArrayList.get(i).getProductName().equals(title)){
                        finalQuan1[0]++;
                        position[0] = i;
                    }
                }
                quantity.setText(String.valueOf(finalQuan1[0]));
                saveData(finalQuan1[0],position[0]);
            }
        });
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < CartItemsArrayList.size(); i++) {
                    if (CartItemsArrayList.get(i).getProductName().equals(title)){
                        finalQuan1[0]--;
                    }
                    position[0] = i;
                }
                if (finalQuan1[0] == 0){
                    add_remove_layout.setVisibility(View.GONE);
                    addToCart.setText("Add To Cart");
                    CartItemsArrayList.remove(CartItemsArrayList.get(position[0]));
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("MyUserCartItems").apply();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(CartItemsArrayList);
                    editor.putString("items", json);
                    editor.apply();
                }else {
                    quantity.setText(String.valueOf(finalQuan1[0]));
                    saveData(finalQuan1[0],position[0]);
                }
            }
        });
        if (addToCart.getText().equals("Add To Cart")){
            addToCart.setOnClickListener(v->{
                add_remove_layout.setVisibility(View.VISIBLE);
                addToCart.setText("Proceed to Cart");
                quantity.setText(String.valueOf(finalQuan1[0]));
                saveData(finalQuan1[0], finalPosition);
            });
        }else if (addToCart.getText().equals("Remove from Cart")){
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserCartItems", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("items", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<CartItems>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        CartItemsArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (CartItemsArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            CartItemsArrayList = new ArrayList<>();
        }
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
    private void saveData(int quan,int position) {
        if (CartItemsArrayList.isEmpty()){
            CartItemsArrayList.add(new CartItems(title,productPrice,productImage,productDescription,productNutritionValue,quan));
        }else {
            if (CartItemsArrayList.get(position).getProductName().equals(title)){
                CartItemsArrayList.set( CartItemsArrayList.indexOf(CartItemsArrayList.get(position)), new CartItems(title,productPrice,productImage,productDescription,productNutritionValue,quan));
            }else{
                CartItemsArrayList.add(new CartItems(title,productPrice,productImage,productDescription,productNutritionValue,quan));
            }
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(CartItemsArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("items", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }


    private void AddItemsToCart() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyUserCartItems",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(CartItemsArrayList);
        editor.putString("items",json);
        editor.apply();
        Toast.makeText(this, "Added to Cart..!", Toast.LENGTH_SHORT).show();

    }

    private void AddProductToWishList(int position) {
        if (wishListItemsArrayList.isEmpty()){
            wishListItemsArrayList.add(new WishListItems(title,productPrice,productImage,productDescription,productNutritionValue));
        }else {
            if (wishListItemsArrayList.get(position).getProductName().equals(title)){
                wishListItemsArrayList.set( wishListItemsArrayList.indexOf(wishListItemsArrayList.get(position)), new WishListItems(title,productPrice,productImage,productDescription,productNutritionValue));
            }else{
                wishListItemsArrayList.add(new WishListItems(title,productPrice,productImage,productDescription,productNutritionValue));
            }
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserWishListItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(wishListItemsArrayList);
        editor.putString("items2", json);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProductActivity.this,MainActivity.class));
        finish();
    }
}
