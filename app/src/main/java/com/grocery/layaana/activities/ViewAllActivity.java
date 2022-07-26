package com.grocery.layaana.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.grocery.layaana.R;
import com.grocery.layaana.adapters.CategoryAdapter;
import com.grocery.layaana.model.Categories;

import java.util.ArrayList;

public class ViewAllActivity extends AppCompatActivity {
    ArrayList<Categories> categoriesArrayList;
    RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        categoriesArrayList = (ArrayList<Categories>) args.getSerializable("ARRAYLIST");
        categoryRecyclerView = findViewById(R.id.categories_recycler_view);
        categoryRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        CategoryAdapter categoryAdapter3 = new CategoryAdapter(getApplicationContext(), categoriesArrayList);
        categoryRecyclerView.setAdapter(categoryAdapter3);
    }
}