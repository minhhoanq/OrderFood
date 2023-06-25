package com.example.orderfood.model;

public class Bill {
    private int mHD, mLKM;
    private String ngayHD;
    private float tongTien;

    public Bill(int mHD, int mLKM, String ngayHD, float tongTien) {
        this.mHD = mHD;
        this.mLKM = mLKM;
        this.ngayHD = ngayHD;
        this.tongTien = tongTien;
    }

    public Bill() {
    }

    public int getmHD() {
        return mHD;
    }

    public void setmHD(int mHD) {
        this.mHD = mHD;
    }

    public int getmLKM() {
        return mLKM;
    }

    public void setmLKM(int mLKM) {
        this.mLKM = mLKM;
    }

    public String getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(String ngayHD) {
        this.ngayHD = ngayHD;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
}
