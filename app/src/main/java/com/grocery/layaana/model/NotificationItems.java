package com.grocery.layaana.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class NotificationItems implements Serializable {
    String title;
    String message;
    String url;

    public NotificationItems(String title, String message,String url) {
        this.title = title;
        this.message = message;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
