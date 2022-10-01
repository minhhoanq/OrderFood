package com.example.orderfood.model;

import java.io.Serializable;

public class Popular implements Serializable {
    private String title;
    private int img_popular;
    private String price, description;
    private int count;
    private double total;

    public Popular(String title, int img_popular, String price, String description, int count) {
        this.title = title;
        this.img_popular = img_popular;
        this.price = price;
        this.description = description;
        this.count = count;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public String getTitle() {
        return title;
    }

    public int getImg_popular() {
        return img_popular;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
