package com.grocery.layaana.model;

import java.io.Serializable;

public class CartItems implements Serializable {
    private String productName;
    private String productPrice;
    private Integer productImage;
    private String productDescription;
    private String productNutritionValue;
    private int quantity;

    public CartItems(String productName, String productPrice, Integer productImage, String productDescription, String productNutritionValue,int quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productNutritionValue = productNutritionValue;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductNutritionValue() {
        return productNutritionValue;
    }

    public void setProductNutritionValue(String productNutritionValue) {
        this.productNutritionValue = productNutritionValue;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName2) {
        this.productName = productName2;
    }

    public String getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(String productPrice2) {
        this.productPrice = productPrice2;
    }

    public Integer getProductImage() {
        return this.productImage;
    }

    public void setProductImage(Integer productImage2) {
        this.productImage = productImage2;
    }
}
