package com.example.orderfood.model;

public class ACCOUNT {
    String email, password;
    int id_cv;

    public ACCOUNT(String email, String password, int id_cv) {
        this.email = email;
        this.password = password;
        this.id_cv = id_cv;
    }

    public ACCOUNT() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_cv() {
        return id_cv;
    }

    public void setId_cv(int id_cv) {
        this.id_cv = id_cv;
    }
}
