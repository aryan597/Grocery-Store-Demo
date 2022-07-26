package com.grocery.layaana.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.grocery.layaana.R;
import com.grocery.layaana.model.AddressItems;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{
    private final ArrayList<AddressItems> addressItemsArrayList;
    private final Context context;

    public AddressAdapter(ArrayList<AddressItems> addressItemsArrayList, Context context) {
        this.addressItemsArrayList = addressItemsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.AddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.addressType.setText(addressItemsArrayList.get(position).getType()+" Address");
        holder.fLane.setText(addressItemsArrayList.get(position).getFirstLine());
        holder.lLane.setText(addressItemsArrayList.get(position).getLastLine());
        holder.city.setText(addressItemsArrayList.get(position).getCity());
        holder.pinCode.setText(addressItemsArrayList.get(position).getPincode());
        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressItemsArrayList.remove(addressItemsArrayList.get(position));
                PreferenceManager.getDefaultSharedPreferences(context).edit().remove("MyUserAddressItems").apply();
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyUserAddressItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(addressItemsArrayList);
                editor.putString("items4", json);
                editor.apply();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressItemsArrayList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView addressType,fLane,lLane,city,pinCode;
        ImageView deleteAddress;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            addressType = itemView.findViewById(R.id.addressType);
            fLane = itemView.findViewById(R.id.fLane);
            lLane = itemView.findViewById(R.id.lLane);
            city = itemView.findViewById(R.id.city);
            pinCode = itemView.findViewById(R.id.pinCode);
            deleteAddress = itemView.findViewById(R.id.deleteAddress);
        }
    }
}
