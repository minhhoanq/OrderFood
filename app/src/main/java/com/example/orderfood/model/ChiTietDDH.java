package com.example.orderfood.model;

public class ChiTietDDH {
    private int ma_ddh, soluong, ma_sp, ma_hd;
    private Float dongia;

    public ChiTietDDH(int ma_ddh, int soluong, int ma_sp, int ma_hd, Float dongia) {
        this.ma_ddh = ma_ddh;
        this.soluong = soluong;
        this.ma_sp = ma_sp;
        this.ma_hd = ma_hd;
        this.dongia = dongia;
    }

    public ChiTietDDH() {
    }

    public int getMa_ddh() {
        return ma_ddh;
    }

    public void setMa_ddh(int ma_ddh) {
        this.ma_ddh = ma_ddh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getMa_sp() {
        return ma_sp;
    }

    public void setMa_sp(int ma_sp) {
        this.ma_sp = ma_sp;
    }

    public int getMa_hd() {
        return ma_hd;
    }

    public void setMa_hd(int ma_hd) {
        this.ma_hd = ma_hd;
    }

    public Float getDongia() {
        return dongia;
    }

    public void setDongia(Float dongia) {
        this.dongia = dongia;
    }
}
