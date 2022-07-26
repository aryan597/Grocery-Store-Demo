package com.grocery.layaana.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.layaana.R;
import com.grocery.layaana.activities.CategoryProductsActivity;
import com.grocery.layaana.model.Categories;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    ArrayList<Categories> categoriesArrayList;
    Context context;

    public CategoryAdapter(Context context2, ArrayList<Categories> categoriesArrayList2) {
        this.context = context2;
        this.categoriesArrayList = categoriesArrayList2;
    }

    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false));
    }

    public void onBindViewHolder(CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Categories categories = this.categoriesArrayList.get(position);
        holder.categoryImg.setImageResource(this.categoriesArrayList.get(position).getCategoryImage().intValue());
        holder.CategoryLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryProductsActivity.class);
                intent.putExtra("CATEGORY_NAME", categoriesArrayList.get(position).getCategoryName());
                v.getContext().startActivity(intent);
            }
        });
        holder.categoryTextview.setText(categoriesArrayList.get(position).getCategoryName());
    }

    public int getItemCount() {
        return this.categoriesArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout CategoryLayout;
        ImageView categoryImg;
        TextView categoryTextview;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryTextview = itemView.findViewById(R.id.category_textView);
            this.categoryImg = itemView.findViewById(R.id.category_image);
            CategoryLayout = itemView.findViewById(R.id.category_container);
        }
    }
}
