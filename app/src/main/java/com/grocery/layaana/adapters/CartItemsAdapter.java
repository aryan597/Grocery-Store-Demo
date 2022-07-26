package com.grocery.layaana.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.CheckoutActivity;
import com.grocery.layaana.activities.MainActivity;
import com.grocery.layaana.activities.ProductActivity;
import com.grocery.layaana.model.CartItems;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder> {
    ArrayList<CartItems> cartItemsArrayList;
    Context context;
    private RecyclerViewClickListener recyclerViewClickListener;
    MaterialButton proceedToCheckoutBtn;
    Button emptyCartButton;
    RecyclerView cartItemRecyclerview;
    ImageView emptyCartImageView;
    TextView emptyCartTv;

    public CartItemsAdapter(Context context2, ArrayList<CartItems> cartItemsArrayList2,MaterialButton proceedToCheckoutBtn,Button emptyCartButton,ImageView emptyCartImageView,TextView emptyCartTv,RecyclerView cartItemRecyclerview) {
        this.context = context2;
        this.cartItemsArrayList = cartItemsArrayList2;
        this.proceedToCheckoutBtn = proceedToCheckoutBtn;
        this.emptyCartButton = emptyCartButton;
        this.emptyCartImageView = emptyCartImageView;
        this.emptyCartTv = emptyCartTv;
        this.cartItemRecyclerview = cartItemRecyclerview;
    }

    public CartItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false));
    }

    public void onBindViewHolder(CartItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loadData();
        holder.quantity.setText(String.valueOf(cartItemsArrayList.get(position).getQuantity()));
        holder.productName.setText(this.cartItemsArrayList.get(position).getProductName());
        holder.productPrice.setText("Rs. "+cartItemsArrayList.get(position).getProductPrice());
        holder.productImage.setImageResource(this.cartItemsArrayList.get(position).getProductImage().intValue());
        holder.cartItemCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProductActivity.class);
                intent.putExtra("Title",cartItemsArrayList.get(position).getProductName());
                intent.putExtra("productPrice",cartItemsArrayList.get(position).getProductPrice());
                intent.putExtra("productImage",cartItemsArrayList.get(position).getProductImage());
                intent.putExtra("productDescription",cartItemsArrayList.get(position).getProductDescription());
                intent.putExtra("productNutritionValue",cartItemsArrayList.get(position).getProductNutritionValue());
                v.getContext().startActivity(intent);
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = 1,i1 = 1;
                for (int i = 0; i < cartItemsArrayList.size(); i++) {
                    if (cartItemsArrayList.get(i).getProductName().equals(cartItemsArrayList.get(position).getProductName())){
                        q= cartItemsArrayList.get(i).getQuantity();
                        q++;
                    }
                    i1 = i;

                }
                holder.quantity.setText(String.valueOf(q));
                AddItemToCart(view,q,position,i1);
            }
        });
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int q = 1,i1 = 1;
                for (int i = 0; i < cartItemsArrayList.size(); i++) {
                    if (cartItemsArrayList.get(i).getProductName().equals(cartItemsArrayList.get(position).getProductName())){
                        q= cartItemsArrayList.get(i).getQuantity();
                        q--;
                    }
                    i1 = i;

                }
                if (q == 0){
                    cartItemsArrayList.remove(cartItemsArrayList.get(position));
                    PreferenceManager.getDefaultSharedPreferences(context).edit().remove("MyUserCartItems").apply();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(cartItemsArrayList);
                    editor.putString("items", json);
                    editor.apply();
                    notifyDataSetChanged();
                    if (cartItemsArrayList.isEmpty()){
                        proceedToCheckoutBtn.setVisibility(View.GONE);
                        cartItemRecyclerview.setVisibility(View.GONE);
                        emptyCartImageView.setVisibility(View.VISIBLE);
                        emptyCartTv.setVisibility(View.VISIBLE);
                        emptyCartButton.setVisibility(View.VISIBLE);
                        emptyCartButton.setOnClickListener(v2->{
                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);
                        });
                    }
                }else {
                    holder.quantity.setText(String.valueOf(q));
                    AddItemToCart(view,q,position,i1);
                }
            }
        });
        proceedToCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CheckoutActivity.class);
                intent.putExtra("QuestionListExtra", cartItemsArrayList);
                intent.putExtra("productPrice",cartItemsArrayList.get(position).getProductPrice());
                intent.putExtra("productQuantity",cartItemsArrayList.get(position).getQuantity());
                view.getContext().startActivity(intent);
            }
        });
        holder.deleteItem.setOnClickListener(v->{
            cartItemsArrayList.remove(cartItemsArrayList.get(position));
            PreferenceManager.getDefaultSharedPreferences(context).edit().remove("MyUserCartItems").apply();
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(cartItemsArrayList);
            editor.putString("items", json);
            editor.apply();
            notifyDataSetChanged();
            Toast.makeText(context, "Item Removed!", Toast.LENGTH_SHORT).show();
            if (cartItemsArrayList.isEmpty()){
                proceedToCheckoutBtn.setVisibility(View.GONE);
                cartItemRecyclerview.setVisibility(View.GONE);
                emptyCartImageView.setVisibility(View.VISIBLE);
                emptyCartTv.setVisibility(View.VISIBLE);
                emptyCartButton.setVisibility(View.VISIBLE);
                emptyCartButton.setOnClickListener(v2->{
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                });
            }
        });
    }
    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserCartItems", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("items", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<CartItems>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        cartItemsArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (cartItemsArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            cartItemsArrayList = new ArrayList<>();
        }
    }
    private void AddItemToCart(View v,int quan,int position,int position1) {
        cartItemsArrayList.set( cartItemsArrayList.indexOf(cartItemsArrayList.get(position1)), new CartItems(cartItemsArrayList.get(position).getProductName(),
                cartItemsArrayList.get(position).getProductPrice(),
                cartItemsArrayList.get(position).getProductImage(),
                cartItemsArrayList.get(position).getProductDescription(),
                cartItemsArrayList.get(position).getProductNutritionValue(),quan));
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItemsArrayList);
        editor.putString("items", json);
        editor.apply();
    }

    public int getItemCount() {
        return this.cartItemsArrayList.size();
    }

    public class CartItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout cartItemCard;
        ImageView productImage,addBtn,removeBtn;
        TextView productName;
        TextView productPrice;
        ImageView deleteItem;
        TextView quantity;

        public CartItemsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.product_img);
            productPrice = itemView.findViewById(R.id.item_price);
            cartItemCard = itemView.findViewById(R.id.cart_item_card);
            deleteItem = itemView.findViewById(R.id.delete_item);
            quantity = itemView.findViewById(R.id.quantity);
            addBtn = itemView.findViewById(R.id.add_btn);
            removeBtn = itemView.findViewById(R.id.remove_btn);
        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view,getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }
}
