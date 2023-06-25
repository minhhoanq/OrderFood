package com.example.orderfood.model;

public class NhanVien {
    String ID_NV, CMND, HO, TEN, NGAYSINH, PHAI, SDT, EMAIL, DIACHI;

    public NhanVien(String ID_NV, String CMND, String HO, String TEN, String NGAYSINH, String PHAI, String SDT, String EMAIL, String DIACHI) {
        this.ID_NV = ID_NV;
        this.CMND = CMND;
        this.HO = HO;
        this.TEN = TEN;
        this.NGAYSINH = NGAYSINH;
        this.PHAI = PHAI;
        this.SDT = SDT;
        this.EMAIL = EMAIL;
        this.DIACHI = DIACHI;
    }

    public NhanVien() {
    }

    public String getID_NV() {
        return ID_NV;
    }

    public void setID_NV(String ID_NV) {
        this.ID_NV = ID_NV;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getHO() {
        return HO;
    }

    public void setHO(String HO) {
        this.HO = HO;
    }

    public String getTEN() {
        return TEN;
    }

    public void setTEN(String TEN) {
        this.TEN = TEN;
    }

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public String getPHAI() {
        return PHAI;
    }

    public void setPHAI(String PHAI) {
        this.PHAI = PHAI;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }
}
