package com.grocery.layaana.model;

import java.io.Serializable;

public class AllItems implements Serializable {
    private String productName;
    private String productPrice;
    private Integer productImg;
    private String productDescription;
    private String productNutritionValue;
    private int quantity;
    private String itemType;

    public AllItems(String productName, String productPrice, Integer productImg, String productDescription, String productNutritionValue, int quantity, String itemType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productDescription = productDescription;
        this.productNutritionValue = productNutritionValue;
        this.quantity = quantity;
        this.itemType = itemType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductImg() {
        return productImg;
    }

    public void setProductImg(Integer productImg) {
        this.productImg = productImg;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
