package com.example.orderfood.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int maSP,  maLSP, maNCC, maTH, maGiamGia, soLuong;
    private String tenSP, nxs, hsd, moTa,  hinhAnh, tinhTrang;
    private Float donGia;

    public Product(int maSP, int maLSP, int maNCC, int maTH, int maGiamGia, int soLuong, String tenSP, String nxs, String hsd, String moTa, String hinhAnh, String tinhTrang, Float donGia) {
        this.maSP = maSP;
        this.maLSP = maLSP;
        this.maNCC = maNCC;
        this.maTH = maTH;
        this.maGiamGia = maGiamGia;
        this.soLuong = soLuong;
        this.tenSP = tenSP;
        this.nxs = nxs;
        this.hsd = hsd;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.tinhTrang = tinhTrang;
        this.donGia = donGia;
    }

    public Product() {
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(int maLSP) {
        this.maLSP = maLSP;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaTH() {
        return maTH;
    }

    public void setMaTH(int maTH) {
        this.maTH = maTH;
    }

    public int getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(int maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getNxs() {
        return nxs;
    }

    public void setNxs(String nxs) {
        this.nxs = nxs;
    }

    public String getHsd() {
        return hsd;
    }

    public void setHsd(String hsd) {
        this.hsd = hsd;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Float getDonGia() {
        return donGia;
    }

    public void setDonGia(Float donGia) {
        this.donGia = donGia;
    }
}

