package com.grocery.layaana.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grocery.layaana.R;
import com.grocery.layaana.activities.NotificationActivity;
import com.grocery.layaana.activities.ViewAllActivity;
import com.grocery.layaana.adapters.AllItemsAdapter;
import com.grocery.layaana.adapters.CategoryAdapter;
import com.grocery.layaana.adapters.SliderAdapterExample;
import com.grocery.layaana.adapters.TopSellingAdapter;
import com.grocery.layaana.model.AllItems;
import com.grocery.layaana.model.Categories;
import com.grocery.layaana.model.NotificationItems;
import com.grocery.layaana.model.SliderItem;
import com.grocery.layaana.model.TopSellingProducts;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {
    ArrayList<Categories> categoriesList;
    RecyclerView categoriesRecyclerview,allItemsRecyclerView;
    CategoryAdapter categoryAdapter;
    Context context;
    ConstraintLayout layout_remove,notificationLayout;
    TopSellingAdapter topSellingAdapter;
    ArrayList<TopSellingProducts> topSellingProductsList;
    ArrayList<AllItems> allItemsArrayList;
    RecyclerView topSellingRecyclerview;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView locationText,viewAll,notificationNumber;
    SliderView sliderView;
    ImageView notification;
    AllItemsAdapter allItemsAdapter;
    ArrayList<NotificationItems> notificationItemsArrayList;
    SearchView searchView;
    private SliderAdapterExample adapter;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationText = view.findViewById(R.id.addr);
        sliderView = view.findViewById(R.id.imageSlider);
        viewAll = view.findViewById(R.id.view_all);
        searchView = view.findViewById(R.id.search_input);
        layout_remove = view.findViewById(R.id.layout_remove);
        loadDataNotification();
        notificationLayout = view.findViewById(R.id.notification_layout);
        notificationNumber = view.findViewById(R.id.notification_number);
        if (!notificationItemsArrayList.isEmpty()){
            notificationLayout.setVisibility(View.VISIBLE);
            notificationNumber.setText(String.valueOf(notificationItemsArrayList.size()));
        }
        notification = view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });
        /////////////////Search View
        searchView.clearFocus();
        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchView.setIconified(false);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    // Search
                    layout_remove.setVisibility(View.GONE);
                    allItemsRecyclerView.setVisibility(View.VISIBLE);
                    filterList(s);
                } else {
                    // Do something when there's no input
                    hideKeyboard(getActivity());
                    layout_remove.setVisibility(View.VISIBLE);
                    allItemsRecyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });
        /////////////////Search View
        //////////////////Adapter
        adapter = new SliderAdapterExample(getContext());
        renewItems(view);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        //////////////////Adapter
        if (ActivityCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 44);
            if (ActivityCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
                getLocation();
            }
        }

        this.categoriesRecyclerview = view.findViewById(R.id.categories_recyclerview);
        this.topSellingRecyclerview = view.findViewById(R.id.top_selling_recyclerview);
        allItemsRecyclerView = view.findViewById(R.id.allItemsRecyclerView);
        ArrayList<Categories> arrayList = new ArrayList<>();
        this.categoriesList = arrayList;
        arrayList.add(new Categories("Fruits", Integer.valueOf(R.drawable.fruits_png)));
        categoriesList.add(new Categories("Vegetables",Integer.valueOf(R.drawable.vegetables_png)));
        this.categoriesList.add(new Categories("Dairy Products", Integer.valueOf(R.drawable.dairy_products_png)));
        this.categoriesList.add(new Categories("Flours", Integer.valueOf(R.drawable.flours_png)));
        this.categoriesList.add(new Categories("Soap", Integer.valueOf(R.drawable.soap_png)));
        this.categoriesList.add(new Categories("Soft Drinks", Integer.valueOf(R.drawable.soft_drinks)));
        setCategoriesRecyclerview(this.categoriesList);
        ArrayList<TopSellingProducts> arrayList2 = new ArrayList<>();
        this.topSellingProductsList = arrayList2;
        arrayList2.add(new TopSellingProducts("Banana 1kg", "30", Integer.valueOf(R.drawable.banana),"A banana is an elongated, edible fruit – botanically a berry – produced by several kinds of large herbaceous flowering plants in the genus Musa. In some countries, bananas used for cooking may be called \"plantains\", distinguishing them from dessert bananas.","89\nCalories",1,"fruit"));
        topSellingProductsList.add(new TopSellingProducts("Juicy Orange 1kg", "60", Integer.valueOf(R.drawable.orange),"An orange is a fruit of various citrus species in the family Rutaceae; it primarily refers to Citrus × sinensis, which is also called sweet orange, to distinguish it from the related Citrus × aurantium, referred to as bitter orange.","66\nCalories",1,"fruit"));
        topSellingProductsList.add(new TopSellingProducts("Crunchy Chips", "10", Integer.valueOf(R.drawable.chips),"A potato chip is a thin slice of potato that has been either deep fried, baked, or air fried until crunchy. They are commonly served as a snack, side dish, or appetizer.","160 calories",1,"snack"));
        topSellingProductsList.add(new TopSellingProducts("Cow Milk 1ltr", "21", Integer.valueOf(R.drawable.milk),"Milk is a nutrient-rich liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for young mammals before they are able to digest solid food. Immune factors and immune-modulating components in milk contribute to milk immunity.","149\nCalories",1,"dairy"));
        topSellingProductsList.add(new TopSellingProducts("Butter Soap 1pc", "25", Integer.valueOf(R.drawable.soap),"Soap is a salt of a fatty acid used in a variety of cleansing and lubricating products. In a domestic setting, soaps are surfactants usually used for washing, bathing, and other types of housekeeping.","No\nNutrition",1,"soap"));
        topSellingProductsList.add(new TopSellingProducts("Green Grapes 1kg", "50", Integer.valueOf(R.drawable.grapes),"The green grape is a sweet fruit that grows in bunches. The fruit makes a popular snack, both because of its sweet flavor and because you can one whole without making a mess. But green grapes are more than just a convenient snack.","52\nCalories",1,"fruit"));
        topSellingProductsList.add(new TopSellingProducts("Juicy Orange 1kg", "60", Integer.valueOf(R.drawable.orange),"An orange is a fruit of various citrus species in the family Rutaceae; it primarily refers to Citrus × sinensis, which is also called sweet orange, to distinguish it from the related Citrus × aurantium, referred to as bitter orange.","66\nCalories",1,"fruit"));
        topSellingProductsList.add(new TopSellingProducts("Crunchy Chips", "10", Integer.valueOf(R.drawable.chips),"A potato chip is a thin slice of potato that has been either deep fried, baked, or air fried until crunchy. They are commonly served as a snack, side dish, or appetizer.","160 calories",1,"snack"));
        topSellingProductsList.add(new TopSellingProducts("Cow Milk 1ltr", "21", Integer.valueOf(R.drawable.milk),"Milk is a nutrient-rich liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for young mammals before they are able to digest solid food. Immune factors and immune-modulating components in milk contribute to milk immunity.","149\nCalories",1,"dairy"));
        topSellingProductsList.add(new TopSellingProducts("Butter Soap 1pc", "25", Integer.valueOf(R.drawable.soap),"Soap is a salt of a fatty acid used in a variety of cleansing and lubricating products. In a domestic setting, soaps are surfactants usually used for washing, bathing, and other types of housekeeping.","No\nNutrition",1,"soap"));
        topSellingProductsList.add(new TopSellingProducts("Green Grapes 1kg", "50", Integer.valueOf(R.drawable.grapes),"The green grape is a sweet fruit that grows in bunches. The fruit makes a popular snack, both because of its sweet flavor and because you can one whole without making a mess. But green grapes are more than just a convenient snack.","52\nCalories",1,"fruit"));

        setTopSellingRecyclerview(this.topSellingProductsList);

        allItemsArrayList = new ArrayList<>();
        allItemsArrayList.add(new AllItems("Banana 1kg", "30", Integer.valueOf(R.drawable.banana),"A banana is an elongated, edible fruit – botanically a berry – produced by several kinds of large herbaceous flowering plants in the genus Musa. In some countries, bananas used for cooking may be called \"plantains\", distinguishing them from dessert bananas.","89\nCalories",1,"fruit"));
        allItemsArrayList.add(new AllItems("Juicy Orange 1kg", "60", Integer.valueOf(R.drawable.orange),"An orange is a fruit of various citrus species in the family Rutaceae; it primarily refers to Citrus × sinensis, which is also called sweet orange, to distinguish it from the related Citrus × aurantium, referred to as bitter orange.","66\nCalories",1,"fruit"));
        allItemsArrayList.add(new AllItems("Crunchy Chips", "10", Integer.valueOf(R.drawable.chips),"A potato chip is a thin slice of potato that has been either deep fried, baked, or air fried until crunchy. They are commonly served as a snack, side dish, or appetizer.","160 calories",1,"snack"));
        allItemsArrayList.add(new AllItems("Cow Milk 1ltr", "21", Integer.valueOf(R.drawable.milk),"Milk is a nutrient-rich liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for young mammals before they are able to digest solid food. Immune factors and immune-modulating components in milk contribute to milk immunity.","149\nCalories",1,"dairy"));
        allItemsArrayList.add(new AllItems("Butter Soap 1pc", "25", Integer.valueOf(R.drawable.soap),"Soap is a salt of a fatty acid used in a variety of cleansing and lubricating products. In a domestic setting, soaps are surfactants usually used for washing, bathing, and other types of housekeeping.","No\nNutrition",1,"soap"));
        allItemsArrayList.add(new AllItems("Green Grapes 1kg", "50", Integer.valueOf(R.drawable.grapes),"The green grape is a sweet fruit that grows in bunches. The fruit makes a popular snack, both because of its sweet flavor and because you can one whole without making a mess. But green grapes are more than just a convenient snack.","52\nCalories",1,"fruit"));
        allItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        allItemsAdapter = new AllItemsAdapter(allItemsArrayList,getContext());
        allItemsRecyclerView.setAdapter(allItemsAdapter);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", categoriesList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
        return view;
    }

    private void filterList(String s) {
        ArrayList<AllItems> filteredList = new ArrayList<>();
        for (AllItems allItems:allItemsArrayList){
            if (allItems.getProductName().toLowerCase().contains(s.toLowerCase()) || allItems.getItemType().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(allItems);
            }
        }
        if (filteredList.isEmpty()){
            allItemsRecyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Item Not Available..!", Toast.LENGTH_SHORT).show();
        }else{
            allItemsAdapter.setFilteredList(filteredList);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 3; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i == 0) {
                sliderItem.setImageUrl("https://drscdn.500px.org/photo/1051358231/m%3D900/v2?sig=3d619b3e1d6818841bbcb8f81cde606fd89f7c7cd77c5d1a5bbd6934120a5a75");
            } else if (i==1){
                sliderItem.setImageUrl("https://drscdn.500px.org/photo/1051633040/m%3D900/v2?sig=4aeca758ea712b2023ae4cbdf66fd4871eeacd9a4933ee1d5afd1da40bb0ef5f");
            }else if (i==2){
                sliderItem.setImageUrl("https://drscdn.500px.org/photo/1051633041/m%3D256/v2?sig=75136f4044f1f6e3fcea901c683fb46a4742f7b379cbd2dbc10ccf1ce023aac6");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }

    public void setCategoriesRecyclerview(ArrayList<Categories> arrayList) {
        categoriesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        CategoryAdapter categoryAdapter2 = new CategoryAdapter(this.context, this.categoriesList);
        categoryAdapter = categoryAdapter2;
        categoriesRecyclerview.setAdapter(categoryAdapter2);
    }

    public void setTopSellingRecyclerview(ArrayList<TopSellingProducts> arrayList) {
        topSellingRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        TopSellingAdapter topSellingAdapter2 = new TopSellingAdapter(getContext(), this.topSellingProductsList);
        topSellingAdapter = topSellingAdapter2;
        topSellingRecyclerview.setAdapter(topSellingAdapter2);
    }
    @SuppressLint("MissingPermission")
    public void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            public void onComplete(Task<Location> task) {
                Location location = task.getResult();
                double MyLat = location.getLatitude();
                double MyLong = location.getLongitude();
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
                    String cityName = addresses.get(0).getLocality();
                    String fullAddress = addresses.get(0).getAddressLine(0);
                    String countryName = addresses.get(0).getCountryName();
                    locationText.setText(cityName+", "+countryName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void loadDataNotification() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyUserNotificationItems", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items3", null);
        Type type = new TypeToken<ArrayList<NotificationItems>>() {}.getType();
        notificationItemsArrayList = gson.fromJson(json, type);
        if (notificationItemsArrayList== null) {
            // if the array list is empty
            // creating a new array list.
            notificationItemsArrayList = new ArrayList<>();
        }
    }

}
