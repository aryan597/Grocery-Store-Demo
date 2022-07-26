package com.grocery.layaana.model;

import java.io.Serializable;

public class AddressItems implements Serializable {
    String firstLine;
    String lastLine;
    String city;
    String pincode;
    String type;

    public AddressItems(String firstLine, String lastLine, String city, String pincode,String type) {
        this.firstLine = firstLine;
        this.lastLine = lastLine;
        this.city = city;
        this.pincode = pincode;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getLastLine() {
        return lastLine;
    }

    public void setLastLine(String lastLine) {
        this.lastLine = lastLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
