package com.example.orderfood.model;

public class Category {
    private int imgcategory;
    private String namecategory;

    public Category(int imgcategory, String namecategory) {
        this.imgcategory = imgcategory;
        this.namecategory = namecategory;
    }

    public int getImgcategory() {
        return imgcategory;
    }

    public String getNamecategory() {
        return namecategory;
    }
}
