package com.example.orderfood.model;

public class GioHang {
    private int MASP, soLuong;
    private float tienGoc,tongTien;
    private String anhSp, tenSP;

    public GioHang(int MASP, int soLuong, float tienGoc, float tongTien, String anhSp, String tenSP) {
        this.MASP = MASP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.anhSp = anhSp;
        this.tenSP = tenSP;
        this.tienGoc = tienGoc;
    }

    public GioHang() {
    }

    public int getMASP() {
        return MASP;
    }

    public void setMASP(int MASP) {
        this.MASP = MASP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public String getAnhSp() {
        return anhSp;
    }

    public void setAnhSp(String anhSp) {
        this.anhSp = anhSp;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public float getTienGoc() {
        return tienGoc;
    }

    public void setTienGoc(float tienGoc) {
        this.tienGoc = tienGoc;
    }
}
