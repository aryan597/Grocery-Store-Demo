package com.grocery.layaana.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.AddressActivity;
import com.grocery.layaana.activities.IntroActivity;
import com.grocery.layaana.activities.MainActivity;
import com.grocery.layaana.activities.WishlistActivity;

public class AccountFragment extends Fragment {
    TextView username;
    MaterialCardView whishlist;
    LinearLayout logout,orders,manageAddress;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        username = view.findViewById(R.id.username);
        whishlist = view.findViewById(R.id.whishlist);
        logout = view.findViewById(R.id.logout);
        orders = view.findViewById(R.id.your_orders);
        manageAddress = view.findViewById(R.id.manage_address);
        manageAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sp = getContext().getSharedPreferences("MyUserData", Context.MODE_PRIVATE);
        if (sp.equals(null)){
            FirebaseUser f = FirebaseAuth.getInstance().getCurrentUser();
            String na = f.getDisplayName();
            username.setText(na);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userName",na);
            editor.apply();
        }else if(!sp.equals(null)){
            String userName = sp.getString("userName","");
            username.setText(userName);
        }
        if (username.getText() == ""){
            username.setText("User");
        }
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("cart","order");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), IntroActivity.class));
        });
        whishlist.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), WishlistActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
