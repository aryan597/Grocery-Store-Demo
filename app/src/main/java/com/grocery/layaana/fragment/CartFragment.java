package com.grocery.layaana.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.CheckoutActivity;
import com.grocery.layaana.activities.MainActivity;
import com.grocery.layaana.adapters.CartItemsAdapter;
import com.grocery.layaana.model.CartItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView cartItemRecyclerview;
    CartItemsAdapter cartItemsAdapter;
    ArrayList<CartItems> cartItemsList;
    MaterialButton proceedToCheckoutBtn;
    TextView  emptyCartTv;
    ImageView emptyCartImageView,clearCart;
    Button emptyCartButton;
    int noOfItems = 0;
    private CartItemsAdapter.RecyclerViewClickListener listener;
    View.OnClickListener onClickListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartItemRecyclerview = view.findViewById(R.id.cart_recyclerview);
        proceedToCheckoutBtn = view.findViewById(R.id.checkout_btn);
        clearCart = view.findViewById(R.id.clear_cart);
        cartItemsList = new ArrayList<CartItems>();
        emptyCartButton = view.findViewById(R.id.shop_now_button);
        emptyCartTv = view.findViewById(R.id.textView7);
        emptyCartImageView = view.findViewById(R.id.image_empty_cart);
        loadData();
        if (cartItemsList.isEmpty()){
            proceedToCheckoutBtn.setVisibility(View.GONE);
            cartItemRecyclerview.setVisibility(View.GONE);
            emptyCartImageView.setVisibility(View.VISIBLE);
            emptyCartTv.setVisibility(View.VISIBLE);
            emptyCartButton.setVisibility(View.VISIBLE);
            emptyCartButton.setOnClickListener(v->{
                startActivity(new Intent(getContext(), MainActivity.class));
            });
            clearCart.setVisibility(View.GONE);
        }
        noOfItems = cartItemsList.size();
        /*if (noOfItems == 1){
            Toast.makeText(getContext(), "You have "+noOfItems+" item in your Cart..!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "You have "+noOfItems+" items in your Cart..!", Toast.LENGTH_SHORT).show();
        }*/
        clearCart.setOnClickListener(v->{
            cartItemsList.removeAll(cartItemsList);
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("MyUserCartItems").apply();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(cartItemsList);
            editor.putString("items", json);
            editor.apply();
            cartItemsAdapter.notifyDataSetChanged();
            proceedToCheckoutBtn.setVisibility(View.GONE);
            cartItemRecyclerview.setVisibility(View.GONE);
            emptyCartImageView.setVisibility(View.VISIBLE);
            emptyCartTv.setVisibility(View.VISIBLE);
            emptyCartButton.setVisibility(View.VISIBLE);
            emptyCartButton.setOnClickListener(v1-> {
                startActivity(new Intent(getContext(), MainActivity.class));
            });
            Toast.makeText(getContext(), "Removed all Items from Cart..!", Toast.LENGTH_SHORT).show();
            clearCart.setVisibility(View.GONE);
        });
        return view;
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<CartItems>>() {}.getType();
        cartItemsList = gson.fromJson(json, type);
        setTopSellingRecyclerview(cartItemsList);
        if (cartItemsList == null) {
            cartItemsList = new ArrayList<>();
        }
        for (int i = 0; i < cartItemsList.size(); i++) {
            Log.d("list",cartItemsList.get(i).getProductName());
        }
    }

    public void setTopSellingRecyclerview(ArrayList<CartItems> arrayList) {
        SetOnClickListner();
        cartItemRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        CartItemsAdapter cartItemsAdapter2 = new CartItemsAdapter(getContext(), arrayList,proceedToCheckoutBtn,emptyCartButton,emptyCartImageView,emptyCartTv,cartItemRecyclerview);
        cartItemsAdapter = cartItemsAdapter2;
        cartItemRecyclerview.setAdapter(cartItemsAdapter2);
    }

    private void SetOnClickListner() {
        listener = new CartItemsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                cartItemsList.remove(position);
                cartItemsAdapter.notifyDataSetChanged();
            }
        };
    }
}
