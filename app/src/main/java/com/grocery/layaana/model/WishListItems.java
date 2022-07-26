package com.grocery.layaana.model;

import java.io.Serializable;

public class WishListItems implements Serializable {
    private String productName;
    private String productPrice;
    private Integer productImage;
    private String productDescription;
    private String productNutritionValue;

    public WishListItems(String productName, String productPrice, Integer productImage, String productDescription, String productNutritionValue) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productNutritionValue = productNutritionValue;
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

    public Integer getProductImage() {
        return productImage;
    }

    public void setProductImage(Integer productImage) {
        this.productImage = productImage;
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
}
