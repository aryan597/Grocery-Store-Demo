package com.grocery.layaana.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.layaana.R;
import com.grocery.layaana.activities.ProductActivity;
import com.grocery.layaana.model.AllItems;
import com.grocery.layaana.model.TopSellingProducts;

import java.util.ArrayList;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.AllItemsViewHolder>{
    ArrayList<AllItems> allItemsArrayList;
    Context context;

    public AllItemsAdapter(ArrayList<AllItems> allItemsArrayList, Context context) {
        this.allItemsArrayList = allItemsArrayList;
        this.context = context;
    }
    public void setFilteredList(ArrayList<AllItems> filteredList){
        this.allItemsArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new AllItemsAdapter.AllItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(allItemsArrayList.get(position).getProductName());
        holder.productImage.setImageResource(allItemsArrayList.get(position).getProductImg());
        holder.productPrice.setText("Rs. "+allItemsArrayList.get(position).getProductPrice());
        holder.cartItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductActivity.class);
                intent.putExtra("Title",allItemsArrayList.get(position).getProductName());
                intent.putExtra("productPrice",allItemsArrayList.get(position).getProductPrice());
                intent.putExtra("productImage",allItemsArrayList.get(position).getProductImg());
                intent.putExtra("productDescription",allItemsArrayList.get(position).getProductDescription());
                intent.putExtra("productNutritionValue",allItemsArrayList.get(position).getProductNutritionValue());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allItemsArrayList.size();
    }

    public class AllItemsViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cartItemCard;
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        public AllItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.product_img);
            productPrice = itemView.findViewById(R.id.item_price);
            cartItemCard = itemView.findViewById(R.id.cart_item_card);
        }
    }
}
