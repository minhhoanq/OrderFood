package com.example.orderfood.model;

public class MyCart {
    private int hinhCart;
    private int countCart;
    private String titleCart, priceCart;

    public MyCart(int hinhCart, int countCart, String priceCart, String titleCart) {
        this.hinhCart = hinhCart;
        this.countCart = countCart;
        this.priceCart = priceCart;
        this.titleCart = titleCart;
    }

    public int getHinhCart() {
        return hinhCart;
    }

    public int getCountCart() {
        return countCart;
    }

    public String getPriceCart() {
        return priceCart;
    }

    public String getTitleCart() {
        return titleCart;
    }
}
