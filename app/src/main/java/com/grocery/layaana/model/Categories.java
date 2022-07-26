package com.grocery.layaana.model;

import java.io.Serializable;

public class Categories implements Serializable {
    private Integer categoryImage;
    private String categoryName;

    public Categories(String categoryName2, Integer categoryImage2) {
        this.categoryName = categoryName2;
        this.categoryImage = categoryImage2;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName2) {
        this.categoryName = categoryName2;
    }

    public Integer getCategoryImage() {
        return this.categoryImage;
    }

    public void setCategoryImage(Integer categoryImage2) {
        this.categoryImage = categoryImage2;
    }
}
