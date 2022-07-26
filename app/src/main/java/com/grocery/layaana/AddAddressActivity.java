package com.grocery.layaana;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.activities.AddressActivity;
import com.grocery.layaana.adapters.AddressAdapter;
import com.grocery.layaana.model.AddressItems;
import com.grocery.layaana.model.WishListItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddAddressActivity extends AppCompatActivity {

    private EditText firstLine,lastLine,city,pincode,type;
    private Button submit;
    private ArrayList<AddressItems> addressItemsArrayList;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address_dialog);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        loadDataAddress();
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        firstLine = findViewById(R.id.first_line_ed);
        lastLine = findViewById(R.id.last_line_ed);
        city = findViewById(R.id.city_ed);
        pincode = findViewById(R.id.pincode_ed);
        type = findViewById(R.id.type_ed);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddressToSp(firstLine.getText().toString(), lastLine.getText().toString(), city.getText().toString(), pincode.getText().toString(), type.getText().toString());
                Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });


    }
    private void loadDataAddress() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserAddressItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items4", null);
        Type type = new TypeToken<ArrayList<AddressItems>>() {}.getType();
        addressItemsArrayList = gson.fromJson(json, type);
        if (addressItemsArrayList== null) {
            // if the array list is empty
            // creating a new array list.
            addressItemsArrayList = new ArrayList<>();
        }
    }
    private void addAddressToSp(String fl,String ll,String city,String pinCode,String type) {
        addressItemsArrayList.add(new AddressItems(fl,ll,city,pinCode,type));
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserAddressItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(addressItemsArrayList);
        editor.putString("items4", json);
        editor.apply();
    }
}
