package com.grocery.layaana.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocery.layaana.R;
import com.grocery.layaana.adapters.ProductsAdapter;
import com.grocery.layaana.model.TopSellingProducts;

import java.util.ArrayList;

public class CategoryProductsActivity extends AppCompatActivity {
    ArrayList<TopSellingProducts> products;
    ProductsAdapter productsAdapter;
    RecyclerView productsRecyclerView;
    TextView title;
    String tittle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        this.title = findViewById(R.id.title);
        this.productsRecyclerView = findViewById(R.id.products_recyclerview);
        tittle = getIntent().getStringExtra("CATEGORY_NAME");
        title.setText(tittle);
        products = new ArrayList<>();
        if (tittle.equals("Fruits")){
            products.add(new TopSellingProducts("Breaking Banana", "30rs/Kg", Integer.valueOf(R.drawable.banana),"A banana is an elongated, edible fruit – botanically a berry – produced by several kinds of large herbaceous flowering plants in the genus Musa. In some countries, bananas used for cooking may be called \"plantains\", distinguishing them from dessert bananas.","89\nCalories",1,"fruit"));
            products.add(new TopSellingProducts("Juicy Orange", "60rs/Kg", Integer.valueOf(R.drawable.orange),"An orange is a fruit of various citrus species in the family Rutaceae; it primarily refers to Citrus × sinensis, which is also called sweet orange, to distinguish it from the related Citrus × aurantium, referred to as bitter orange.","66\nCalories",1,"fruit"));
            products.add(new TopSellingProducts("Green Grapes", "50rs/Kg", Integer.valueOf(R.drawable.grapes),"The green grape is a sweet fruit that grows in bunches. The fruit makes a popular snack, both because of its sweet flavor and because you can one whole without making a mess. But green grapes are more than just a convenient snack.","52\nCalories",1,"fruit"));
            setProductsRecyclerView(products);

        }else if (tittle.equals("Vegetables")){

        }else if (tittle.equals("Dairy Products")){
            products.add(new TopSellingProducts("Cow Milk", "21rs/ltr", Integer.valueOf(R.drawable.milk),"Milk is a nutrient-rich liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for young mammals before they are able to digest solid food. Immune factors and immune-modulating components in milk contribute to milk immunity.","149\nCalories",1,"dairy"));
            setProductsRecyclerView(products);
        }else if (tittle.equals("Flours")){

        }else if (tittle.equals("Soap")){
            products.add(new TopSellingProducts("Butter Soap", "25rs/pc", Integer.valueOf(R.drawable.soap),"Soap is a salt of a fatty acid used in a variety of cleansing and lubricating products. In a domestic setting, soaps are surfactants usually used for washing, bathing, and other types of housekeeping.","No\nNutrition",1,"soap"));
            setProductsRecyclerView(products);
        }else if (tittle.equals("Soft Drinks")){
            products.add(new TopSellingProducts("Crunchy Chips", "10rs", Integer.valueOf(R.drawable.chips),"A potato chip is a thin slice of potato that has been either deep fried, baked, or air fried until crunchy. They are commonly served as a snack, side dish, or appetizer.","160 calories",1,"snack"));
            setProductsRecyclerView(products);
        }

    }

    public void setProductsRecyclerView(ArrayList<TopSellingProducts> arrayList) {
        this.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ProductsAdapter productsAdapter2 = new ProductsAdapter(this, arrayList);
        this.productsAdapter = productsAdapter2;
        this.productsRecyclerView.setAdapter(productsAdapter2);
    }
}
