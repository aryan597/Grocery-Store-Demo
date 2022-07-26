package com.grocery.layaana.model;

public class TopSellingProducts {
    private String productName;
    private String productPrice;
    private Integer productImg;
    private String productDescription;
    private String productNutritionValue;
    private int quantity;
    private String itemType;

    public TopSellingProducts(String productName, String productPrice, Integer productImg, String productDescription, String productNutritionValue,int quantity,String itemType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productDescription = productDescription;
        this.productNutritionValue = productNutritionValue;
        this.quantity = quantity;
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    public Integer getProductImg() {
        return this.productImg;
    }

    public void setProductImg(Integer productImg2) {
        this.productImg = productImg2;
    }
}
