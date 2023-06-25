package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateProfileCus extends AppCompatActivity {
    private TextView nameCus, emailCus, nsCus, phaiCus, sdtCus, diachiCus, nsTemp, phaiTemp, sdtTemp, diaChiTemp;
    private ConstraintLayout btnUpdateCus;
    dbConnection dbConnect;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_cus);
        Init();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        customer = (Customer) bundle.get("info_cus");
        setTextInForCus(customer);
        setView();
        UpdateCus();
    }

    private void UpdateCus() {
        btnUpdateCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUpdateALL() == 1){
                    Update(customer);
                }
            }
        });
    }

    public void setView(){
        nsCus.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(nsCus.hasFocus()){
                    btnUpdateCus.setVisibility(View.VISIBLE);
                }
            }
        });

        phaiCus.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(phaiCus.hasFocus()){
                    btnUpdateCus.setVisibility(View.VISIBLE);
                }
            }
        });

        sdtCus.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(sdtCus.hasFocus()){
                    btnUpdateCus.setVisibility(View.VISIBLE);
                }
            }
        });

        diachiCus.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(diachiCus.hasFocus()){
                    btnUpdateCus.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void Update(Customer customer) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update KHACHHANG set NGAYSINH = ?, PHAI = ?, SDT = ?, DIACHI = ? where EMAIL = " + "'" + customer.getEmail() + "'";

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(nsCus.getText()));

            //Bug
            int i = -1;
            String phai = String.valueOf(phaiCus.getText());
            if(phai.equals("Nu")){
                i = 0;
                Log.d("CCC", String.valueOf(i));
            }
            if(phai.equals("Nam")){
                i = 1;
                Log.d("CCC", String.valueOf(i));
            }
            Log.d("CCC", String.valueOf(i));
            ps.setInt(2, Integer.parseInt(String.valueOf(i)));
            ps.setString(3, String.valueOf(sdtCus.getText()));
            ps.setString(4, String.valueOf(diachiCus.getText()));

            ps.executeUpdate();
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi");
        }
    }

    public void setTextInForCus(Customer customer) {
        nameCus.setText(customer.getHo() + " " + customer.getTen());
        emailCus.setText(customer.getEmail());

        if(customer.getNgaysinh() == null){
            nsCus.setText("Chưa có thông tin");
        }else {
            nsCus.setText(customer.getNgaysinh());
        }

        if(customer.getPhai() != 0 && customer.getPhai() != 1){
            phaiCus.setText("Chưa có thông tin");
        }else {
            if(customer.getPhai() == 0){
                phaiCus.setText("Nu");
            }

            if(customer.getPhai() == 1){
                phaiCus.setText("Nam");
            }

        }

        if(customer.getSdt() == null){
            sdtCus.setText("Chưa có thông tin");
        }else {
            sdtCus.setText(customer.getSdt());
        }

        if(customer.getDiachi() == null){
            diachiCus.setText("Chưa có thông tin");
        }else {
            diachiCus.setText(customer.getDiachi());
        }
    }

    private void Init(){
        nameCus = findViewById(R.id.tv_cus_name);
        emailCus = findViewById(R.id.edt_email_cus);
        nsCus = findViewById(R.id.edt_ngay_sinh_cus);
        phaiCus = findViewById(R.id.edt_phai_cus);
        sdtCus = findViewById(R.id.edt_sdt_cus);
        diachiCus = findViewById(R.id.edt_dia_chi_cus);
        btnUpdateCus = findViewById(R.id.btn_update_cus);

        nsTemp = findViewById(R.id.tv_ns_temp);
        phaiTemp = findViewById(R.id.tv_gt_temp);
        sdtTemp = findViewById(R.id.tv_sdt_temp);
        diaChiTemp = findViewById(R.id.tv_dc_temp);

        btnUpdateCus.setVisibility(View.INVISIBLE);
    }

    public int checkUpdateALL(){
        int n = isNgaySinh();
        int g = isGioiTinh();
        int s = isSDT();
        int d = isDiaChi();
        if(n == 1 && g == 1 && s == 1 && d == 1){
            nsTemp.setTextColor(Color.BLACK);
            return 1;
        }

        if(n != 1){
            nsTemp.setTextColor(Color.RED);
        }
        return 0;
    }

    private int isDiaChi() {
        String dc = diachiCus.getText().toString();
        if(dc.isEmpty() || dc.equals("Chưa có thông tin")){
            return 0;
        }
        return 1;
    }

    private int isSDT() {
        String sdt = sdtCus.getText().toString();
        if(sdt.isEmpty() || sdt.equals("Chưa có thông tin")){
            return 0;
        }
        return 1;
    }

    private int isGioiTinh() {
        String gt = phaiCus.getText().toString();
        if(gt.isEmpty() || gt.equals("Chưa có thông tin")){
            return 0;
        }
        return 1;
    }

    private int isNgaySinh() {
        String ns = nsCus.getText().toString();
        if(ns.isEmpty() || ns.equals("Chưa có thông tin")){
            return 0;
        }
        return 1;
    }
}