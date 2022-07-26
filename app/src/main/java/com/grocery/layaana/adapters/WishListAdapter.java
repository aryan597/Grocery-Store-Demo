package com.grocery.layaana.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.layaana.R;
import com.grocery.layaana.activities.ProductActivity;
import com.grocery.layaana.model.WishListItems;

import java.util.ArrayList;

public class WishListAdapter  extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {
    Context context;
    ArrayList<WishListItems> wishListItemsArrayList;

    public WishListAdapter(Context context, ArrayList<WishListItems> wishListItemsArrayList) {
        this.context = context;
        this.wishListItemsArrayList = wishListItemsArrayList;
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishListAdapter.WishListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(wishListItemsArrayList.get(position).getProductName());
        holder.productPrice.setText("Rs. "+wishListItemsArrayList.get(position).getProductPrice());
        holder.productImage.setImageResource(wishListItemsArrayList.get(position).getProductImage().intValue());
        holder.cartItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductActivity.class);
                intent.putExtra("Title",wishListItemsArrayList.get(position).getProductName());
                intent.putExtra("productPrice",wishListItemsArrayList.get(position).getProductPrice());
                intent.putExtra("productImage",wishListItemsArrayList.get(position).getProductImage());
                intent.putExtra("productDescription",wishListItemsArrayList.get(position).getProductDescription());
                intent.putExtra("productNutritionValue",wishListItemsArrayList.get(position).getProductNutritionValue());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishListItemsArrayList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cartItemCard;
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        Button addToCart;
        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.product_img);
            productPrice = itemView.findViewById(R.id.order_price);
            cartItemCard = itemView.findViewById(R.id.order_card);
            addToCart = itemView.findViewById(R.id.addToCart);
        }
    }
}
