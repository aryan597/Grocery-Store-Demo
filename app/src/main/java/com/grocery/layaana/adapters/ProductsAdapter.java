package com.grocery.layaana.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.CheckoutActivity;
import com.grocery.layaana.activities.ProductActivity;
import com.grocery.layaana.model.TopSellingProducts;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    Context context;
    ArrayList<TopSellingProducts> productsArrayList;

    public ProductsAdapter(Context context2, ArrayList<TopSellingProducts> productsArrayList2) {
        this.context = context2;
        this.productsArrayList = productsArrayList2;
    }

    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));
    }

    public void onBindViewHolder(ProductsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TopSellingProducts products = productsArrayList.get(position);
        holder.productName.setText(products.getProductName());
        holder.productPrice.setText(products.getProductPrice());
        holder.productImage.setImageResource(products.getProductImg().intValue());
        holder.productCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProductActivity.class);
                intent.putExtra("Title",productsArrayList.get(position).getProductName());
                intent.putExtra("productPrice",productsArrayList.get(position).getProductPrice());
                intent.putExtra("productImage",productsArrayList.get(position).getProductImg());
                intent.putExtra("productDescription",productsArrayList.get(position).getProductDescription());
                intent.putExtra("productNutritionValue",productsArrayList.get(position).getProductNutritionValue());
                v.getContext().startActivity(intent);
            }
        });
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), CheckoutActivity.class));
            }
        });
    }

    public int getItemCount() {
        return this.productsArrayList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView buyBtn;
        ConstraintLayout productCard;
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.product_name);
            this.productPrice = itemView.findViewById(R.id.product_price);
            this.productImage = itemView.findViewById(R.id.product_img);
            this.productCard = itemView.findViewById(R.id.product_card);
            this.buyBtn = itemView.findViewById(R.id.buy_btn);
        }
    }
}
