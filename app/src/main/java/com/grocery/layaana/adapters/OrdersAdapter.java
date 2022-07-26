package com.grocery.layaana.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.ProductActivity;
import com.grocery.layaana.model.CartItems;
import com.grocery.layaana.model.OrderItems;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    Context context;
    ArrayList<OrderItems> ordersList;
    ArrayList<CartItems> cartItemsList;

    public OrdersAdapter(Context context2, ArrayList<OrderItems> ordersList2) {
        this.context = context2;
        this.ordersList = ordersList2;
    }

    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrdersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false));
    }

    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        holder.productName.setText(ordersList.get(position).getProductName());
        holder.orderTotalPrice.setText("Rs. "+ordersList.get(position).getProductTotalPrice());
        holder.orderStatus.setText(ordersList.get(position).getOrderStatus());
        holder.productImage.setImageResource(ordersList.get(position).getProductImage());
        holder.orderQuantity.setText("Quantity : "+ordersList.get(position).getQuantity());
        holder.dateTime.setText(ordersList.get(position).getDateTime());
        holder.orderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersList.removeAll(ordersList);
                SharedPreferences sharedPreferences =view.getContext().getSharedPreferences("MyUserOrderedItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // creating a new variable for gson.
                Gson gson = new Gson();

                // getting data from gson and storing it in a string.
                String json = gson.toJson(ordersList);

                // below line is to save data in shared
                // prefs in the form of string.
                editor.putString("items1", json);

                // below line is to apply changes
                // and save data in shared prefs.
                editor.apply();
                notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return this.ordersList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout orderCard;
        TextView orderStatus;
        TextView orderTotalPrice;
        ImageView productImage;
        TextView productName,orderQuantity;
        TextView dateTime;

        public OrdersViewHolder(View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.product_name);
            this.orderTotalPrice = itemView.findViewById(R.id.order_price);
            this.orderStatus = itemView.findViewById(R.id.order_status);
            this.productImage = itemView.findViewById(R.id.product_img);
            this.orderCard = itemView.findViewById(R.id.order_card);
            orderQuantity = itemView.findViewById(R.id.textView9);
            dateTime = itemView.findViewById(R.id.date_time);
        }
    }

}
