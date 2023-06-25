package com.example.orderfood.model;

public class DonDatHang {
    private int MA_DDH, ID_NV, ID_KH, tinhTrang;
    private float phiVC;
    private String ngayDH;

    public DonDatHang(int MA_DDH, int ID_NV, int ID_KH, int tinhTrang, float phiVC, String ngayDH) {
        this.MA_DDH = MA_DDH;
        this.ID_NV = ID_NV;
        this.ID_KH = ID_KH;
        this.tinhTrang = tinhTrang;
        this.phiVC = phiVC;
        this.ngayDH = ngayDH;
    }

    public DonDatHang() {
    }

    public int getMA_DDH() {
        return MA_DDH;
    }

    public void setMA_DDH(int MA_DDH) {
        this.MA_DDH = MA_DDH;
    }

    public int getID_NV() {
        return ID_NV;
    }

    public void setID_NV(int ID_NV) {
        this.ID_NV = ID_NV;
    }

    public int getID_KH() {
        return ID_KH;
    }

    public void setID_KH(int ID_KH) {
        this.ID_KH = ID_KH;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public float getPhiVC() {
        return phiVC;
    }

    public void setPhiVC(float phiVC) {
        this.phiVC = phiVC;
    }

    public String getNgayDH() {
        return ngayDH;
    }

    public void setNgayDH(String ngayDH) {
        this.ngayDH = ngayDH;
    }
}
