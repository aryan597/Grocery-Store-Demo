package com.grocery.layaana.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.adapters.OrdersAdapter;
import com.grocery.layaana.model.OrderItems;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class YourOrdersFragment extends Fragment {
    Context context;
    OrdersAdapter ordersAdapter;
    ArrayList<OrderItems> ordersArrayList;
    RecyclerView ordersRecyclerview;
    TextView empty_orders_textView;
    ImageView empty_orders;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_orders, container, false);
        ordersRecyclerview = view.findViewById(R.id.orders_recyclerview);
        empty_orders = view.findViewById(R.id.empty_orders);
        empty_orders_textView = view.findViewById(R.id.empty_orders_textView);
        loadData(view);
        if (ordersArrayList.isEmpty()){
            ordersRecyclerview.setVisibility(View.GONE);
            empty_orders.setVisibility(View.VISIBLE);
            empty_orders_textView.setVisibility(View.VISIBLE);
        }else {
            empty_orders.setVisibility(View.GONE);
            empty_orders_textView.setVisibility(View.GONE);
            ordersRecyclerview.setVisibility(View.VISIBLE);
            setOrdersRecyclerview(ordersArrayList,view);
        }
        return view;
    }

    public void setOrdersRecyclerview(ArrayList<OrderItems> arrayList, View view) {
        ordersRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        OrdersAdapter ordersAdapter2 = new OrdersAdapter(view.getContext(), arrayList);
        ordersAdapter = ordersAdapter2;
        ordersRecyclerview.setAdapter(ordersAdapter2);
    }
    private void loadData(View v) {
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyUserOrderedItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items1", null);
        Type type = new TypeToken<ArrayList<OrderItems>>() {}.getType();
        ordersArrayList = gson.fromJson(json, type);
        if (ordersArrayList == null) {
            ordersArrayList = new ArrayList<>();
        }
    }
}
