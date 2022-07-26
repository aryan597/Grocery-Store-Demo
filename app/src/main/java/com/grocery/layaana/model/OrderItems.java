package com.grocery.layaana.model;

public class OrderItems {
    private String orderStatus;
    private Integer productImage;
    private String productName;
    private String productTotalPrice;
    private int quantity;
    private String dateTime;

    public OrderItems(String productName2, String productTotalPrice2, String orderStatus2, Integer productImage2,int quantity,String dateTime) {
        this.productName = productName2;
        this.productTotalPrice = productTotalPrice2;
        this.orderStatus = orderStatus2;
        this.productImage = productImage2;
        this.quantity = quantity;
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName2) {
        this.productName = productName2;
    }

    public String getProductTotalPrice() {
        return this.productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice2) {
        this.productTotalPrice = productTotalPrice2;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus2) {
        this.orderStatus = orderStatus2;
    }

    public Integer getProductImage() {
        return this.productImage;
    }

    public void setProductImage(Integer productImage2) {
        this.productImage = productImage2;
    }
}
