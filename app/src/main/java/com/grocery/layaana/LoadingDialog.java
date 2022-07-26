package com.grocery.layaana;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog  {
    private final Activity activity;
    private AlertDialog dialog;
    private final int layout;
    public LoadingDialog(Activity myActivity,int layou1){
        activity = myActivity;
        layout = layou1;
    }
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(layout,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
    public void  dismissDialog(){
        dialog.dismiss();
    }
}
