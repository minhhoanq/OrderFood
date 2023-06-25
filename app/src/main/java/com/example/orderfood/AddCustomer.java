package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCustomer extends AppCompatActivity {
//    private EditText ho, ten, ngaysinh, phai, sdt, email, diachi;
//    private Customer customer;
//    private ImageView btnAddCus;
//    dbConnection dbConnect;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_customer);
//        Init();
//        Intent i = getIntent();
//        Bundle bundle = i.getExtras();
//        customer = (Customer) bundle.get("full_cus");
//        setView();
//        UpdateCustomer();
//    }
//
//    private void UpdateCustomer() {
//        btnAddCus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Update(customer);
//            }
//        });
//    }
//
//    private void Init(){
//        ho = findViewById(R.id.edt_ho_cus);
//        ten = findViewById(R.id.edt_ten_cus);
//        ngaysinh = findViewById(R.id.edt_ngay_sinh_cus);
//        phai = findViewById(R.id.edt_phai_cus);
//        sdt = findViewById(R.id.edt_sdt_cus);
//        email = findViewById(R.id.edt_email_cus);
//        diachi = findViewById(R.id.edt_dia_chi_cus);
//        btnAddCus = findViewById(R.id.btn_add_cus);
//    }
//
//    private void setView(){
//        ho.setText(String.valueOf(customer.getHo()));
//        ten.setText(String.valueOf(customer.getTen()));
//        ngaysinh.setText(String.valueOf(customer.getNgaysinh()));
//        phai.setText(String.valueOf(customer.getPhai()));
//        sdt.setText(String.valueOf(customer.getSdt()));
//        email.setText(String.valueOf(customer.getEmail()));
//        diachi.setText(String.valueOf(customer.getDiachi()));
//    }
//
//    private void Update(Customer customer) {
//        try {
//            Connection cons = (Connection) dbConnect.connectionClass();
//            String sql = "Update KHACHHANG set HO = ?, TEN = ?, NGAYSINH = ?, PHAI = ?, SDT = ?, EMAIL = ?, DIACHI = ? where EMAIL = " + "'" + customer.getEmail() + "'";
//
//            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            ps.setString(1, String.valueOf(ho.getText()));
//            ps.setString(2, String.valueOf(ten.getText()));
//            ps.setString(3, String.valueOf(ngaysinh.getText()));
//            ps.setInt(4, Integer.parseInt(String.valueOf(phai.getText())));
//            ps.setString(5, String.valueOf(sdt.getText()));
//            ps.setString(6, String.valueOf(email.getText()));
//            ps.setString(7, String.valueOf(diachi.getText()));
//
//            ps.executeUpdate();
////            UpdateTaiKhoan(customer);
//            ps.close();
//            cons.close();
//        } catch (Exception ex) {
//            Log.d("III", "lỗi");
//        }
//    }

//    private void UpdateTaiKhoan(Customer customer){
//        try {
//            Connection cons = (Connection) dbConnect.connectionClass();
//            String sql = "Update TAIKHOAN set EMAIL = ? where EMAIL = " + "'" + customer.getEmail() + "'";
//
//            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            Log.d("III", "lỗi tài khoản nè");
//            if(!email.getText().equals(customer.getEmail())){
//                ps.setString(1, String.valueOf(email.getText()));
//            }
//
//            ps.executeUpdate();
//            ps.close();
//            cons.close();
//        } catch (Exception ex) {
//            Log.d("III", "lỗi tài khoản");
//        }
//    }
}