package com.grocery.layaana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grocery.layaana.R;
import com.grocery.layaana.model.NotificationItems;
import com.grocery.layaana.model.WishListItems;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    Context context;
    ArrayList<NotificationItems> notificationItemsArrayList;

    public NotificationAdapter(Context context, ArrayList<NotificationItems> notificationItemsArrayList) {
        this.context = context;
        this.notificationItemsArrayList = notificationItemsArrayList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.notificationTitle.setText(notificationItemsArrayList.get(position).getTitle());
        holder.notificationBody.setText(notificationItemsArrayList.get(position).getMessage());
        Glide.with(holder.itemView)
                .load(notificationItemsArrayList.get(position).getUrl())
                .fitCenter()
                .into(holder.notificationImage);

    }

    @Override
    public int getItemCount() {
        return notificationItemsArrayList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView notificationImage;
        TextView notificationTitle,notificationBody;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationImage = itemView.findViewById(R.id.notification_img);
            notificationBody = itemView.findViewById(R.id.notification_body);
            notificationTitle = itemView.findViewById(R.id.notification_title);
        }
    }
}
