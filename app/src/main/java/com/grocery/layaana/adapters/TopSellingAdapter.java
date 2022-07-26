package com.grocery.layaana.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.MainActivity;
import com.grocery.layaana.activities.ProductActivity;
import com.grocery.layaana.model.CartItems;
import com.grocery.layaana.model.TopSellingProducts;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingAdapter.TopSellingViewHolder> {
    Context context;
    ArrayList<TopSellingProducts> topSellingProductsArrayList;
    ArrayList<CartItems> cartItemsArrayList;
    int quantityInt;


    public TopSellingAdapter(Context context2, ArrayList<TopSellingProducts> topSellingProductsArrayList2) {
        this.context = context2;
        this.topSellingProductsArrayList = topSellingProductsArrayList2;
    }

    public TopSellingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopSellingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

    }

    public void onBindViewHolder(TopSellingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TopSellingProducts topSellingProducts = topSellingProductsArrayList.get(position);
        holder.productName.setText(topSellingProducts.getProductName());
        holder.productPrice.setText("Rs. "+topSellingProducts.getProductPrice());
        holder.productImage.setImageResource(topSellingProducts.getProductImg().intValue());
        loadData();
        int finalQuan = 0;
        for (int i = 0; i < cartItemsArrayList.size(); i++) {
            if (cartItemsArrayList.get(i).getProductName().equals(topSellingProductsArrayList.get(position).getProductName())){
                finalQuan = cartItemsArrayList.get(i).getQuantity();
            }
            if (cartItemsArrayList.get(i).getProductName().equals(topSellingProductsArrayList.get(position).getProductName())){
                holder.addToCart.setVisibility(View.GONE);
                holder.remove.setVisibility(View.VISIBLE);
                holder.buyBtn.setVisibility(View.VISIBLE);
                holder.quantity.setVisibility(View.VISIBLE);
                holder.quantity.setText(String.valueOf(finalQuan));
            }
        }
        final int[] position1 = {0};
        final int[] quan = {finalQuan};
        for (int i = 0; i < cartItemsArrayList.size(); i++) {
            if (cartItemsArrayList.get(i).getProductName().equals(topSellingProductsArrayList.get(i).getProductName())){
                quan[0]++;
                position1[0] = i;
            }
        }
        int finalPosition = position1[0];
        final int[] finalQuan1 = {finalQuan};
        int finalQuan2 = finalQuan;
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                holder.remove.setVisibility(View.VISIBLE);
                holder.quantity.setVisibility(View.VISIBLE);
                holder.buyBtn.setVisibility(View.VISIBLE);
                holder.addToCart.setVisibility(View.GONE);
                int q1 = 1,i1 = 0;
                for (int i = 0; i < cartItemsArrayList.size(); i++) {
                    int q =1;
                    if (cartItemsArrayList.get(i).getProductName().equals(topSellingProductsArrayList.get(position).getProductName())){
                        q = cartItemsArrayList.get(i).getQuantity();
                        q++;
                        position1[0] = i;
                    }
                    q1 = q;
                    i1 = i;
                }
                holder.quantity.setText(String.valueOf(q1));
                AddItemToCart(view, q1,position,i1);
            }
        });
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int q = 1,i1 = 1;
                for (int i = 0; i < cartItemsArrayList.size(); i++) {
                    if (cartItemsArrayList.get(i).getProductName().equals(topSellingProductsArrayList.get(position).getProductName())){
                        q= cartItemsArrayList.get(i).getQuantity();
                        q++;
                        position1[0] = i;
                    }
                    i1 = i;

                }
                holder.quantity.setText(String.valueOf(q));
                AddItemToCart(v,q,position,i1);
            }
        });
        holder.remove.setOnClickListener(view->{
            int q = 1,i1 = 1;
            for (int i = 0; i < cartItemsArrayList.size(); i++) {
                if (cartItemsArrayList.get(i).getProductName().equals(topSellingProductsArrayList.get(position).getProductName())){
                    q= cartItemsArrayList.get(i).getQuantity();
                    q--;
                    position1[0] = i;
                }
                i1 = i;
            }
            if (q == 0){
                cartItemsArrayList.remove(cartItemsArrayList.get(i1));
                PreferenceManager.getDefaultSharedPreferences(context).edit().remove("MyUserCartItems").apply();
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(cartItemsArrayList);
                editor.putString("items", json);
                editor.apply();
                if (cartItemsArrayList.isEmpty()){
                    holder.remove.setVisibility(View.GONE);
                    holder.quantity.setVisibility(View.GONE);
                    holder.buyBtn.setVisibility(View.GONE);
                    holder.addToCart.setVisibility(View.VISIBLE);
                }
            }else {
                holder.quantity.setText(String.valueOf(q));
                AddItemToCart(view,q,position,i1);
            }
        });
        holder.productCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProductActivity.class);
                intent.putExtra("Title",topSellingProductsArrayList.get(position).getProductName());
                intent.putExtra("productPrice",topSellingProductsArrayList.get(position).getProductPrice());
                intent.putExtra("productImage",topSellingProductsArrayList.get(position).getProductImg());
                intent.putExtra("productDescription",topSellingProductsArrayList.get(position).getProductDescription());
                intent.putExtra("productNutritionValue",topSellingProductsArrayList.get(position).getProductNutritionValue());
                v.getContext().startActivity(intent);
            }
        });
    }
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<CartItems>>() {}.getType();
        cartItemsArrayList = gson.fromJson(json, type);
        if (cartItemsArrayList == null) {
            cartItemsArrayList = new ArrayList<>();
        }
        for (int i = 0; i < cartItemsArrayList.size(); i++) {
            Log.d("list",cartItemsArrayList.get(i).getProductName());
        }
    }
    private void RemoveItemFromCart(View v,int position){
        cartItemsArrayList.remove(cartItemsArrayList.get(position));
        PreferenceManager.getDefaultSharedPreferences(v.getContext()).edit().remove("MyUserCartItems").apply();
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItemsArrayList);
        editor.putString("items", json);
        editor.apply();
        Toast.makeText(context, "Item Removed!", Toast.LENGTH_SHORT).show();
    }

    private void AddItemToCart(View v,int quan,int position,int position1) {
        if (cartItemsArrayList.isEmpty()){
            cartItemsArrayList.add(new CartItems(topSellingProductsArrayList.get(position).getProductName(),
                    topSellingProductsArrayList.get(position).getProductPrice(),
                    topSellingProductsArrayList.get(position).getProductImg(),
                    topSellingProductsArrayList.get(position).getProductDescription(),
                    topSellingProductsArrayList.get(position).getProductNutritionValue(),quan));
        }else {
            if (cartItemsArrayList.get(position1).getProductName().equals(topSellingProductsArrayList.get(position).getProductName())){
                cartItemsArrayList.set( cartItemsArrayList.indexOf(cartItemsArrayList.get(position1)), new CartItems(topSellingProductsArrayList.get(position).getProductName(),
                        topSellingProductsArrayList.get(position).getProductPrice(),
                        topSellingProductsArrayList.get(position).getProductImg(),
                        topSellingProductsArrayList.get(position).getProductDescription(),
                        topSellingProductsArrayList.get(position).getProductNutritionValue(),quan));
            }else{
                cartItemsArrayList.add(new CartItems(topSellingProductsArrayList.get(position).getProductName(),
                        topSellingProductsArrayList.get(position).getProductPrice(),
                        topSellingProductsArrayList.get(position).getProductImg(),
                        topSellingProductsArrayList.get(position).getProductDescription(),
                        topSellingProductsArrayList.get(position).getProductNutritionValue(),quan));
            }
        }
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyUserCartItems", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(cartItemsArrayList);
            editor.putString("items", json);
            editor.apply();
    }

    public int getItemCount() {
        return this.topSellingProductsArrayList.size();
    }

    public class TopSellingViewHolder extends RecyclerView.ViewHolder {
        CircleImageView buyBtn,remove;
        ConstraintLayout productCard;
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView quantity;
        Button addToCart;

        public TopSellingViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_img);
            buyBtn =  itemView.findViewById(R.id.buy_btn);
            productCard = itemView.findViewById(R.id.product_card);
            quantity = itemView.findViewById(R.id.textView8);
            remove = itemView.findViewById(R.id.remove);
            addToCart = itemView.findViewById(R.id.addToCart);
        }
    }
}
