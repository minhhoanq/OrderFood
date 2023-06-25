package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateStaff extends AppCompatActivity {
    private TextView ho, ten, cmnd, ngay, phai, sdt, email, diachi;
    private Staff staff;
    dbConnection dbConnect;
    private ImageView btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        Init();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        staff = (Staff) bundle.get("info_staff_up");
        setView(staff);
        setOnClickView(staff);
    }

    private void setOnClickView(Staff staff) {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update(staff);
            }
        });
    }

    private void setView(Staff staff) {
        ho.setText(staff.getHoNV());
        ten.setText(staff.getTenNV());
        cmnd.setText(String.valueOf(staff.getCmnd()));
        ngay.setText(staff.getNgaySinh());
        phai.setText(String.valueOf(staff.getGioiTinh()));
        sdt.setText(staff.getSdt());
        email.setText(staff.getEmail());
        diachi.setText(staff.getDiaChi());
    }

    private void Init() {
        ho = findViewById(R.id.edt_ho_st);
        ten = findViewById(R.id.edt_ten_st);
        cmnd = findViewById(R.id.edt_cmnd_st);
        ngay = findViewById(R.id.edt_ngay_sinh_st);
        phai = findViewById(R.id.edt_phai_st);
        sdt = findViewById(R.id.edt_sdt_st);
        email = findViewById(R.id.edt_email_st);
        diachi = findViewById(R.id.edt_dia_chi_st);
        btnUpdate = findViewById(R.id.btn_update_st);
    }

    private void Update(Staff staff) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update NHANVIEN set HO = ?, TEN = ?, NGAYSINH = ?, PHAI = ?, SDT = ?, EMAIL = ?, DIACHI = ?, CMND = ? where EMAIL = " + "'" + staff.getEmail() + "'";

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(ho.getText()));
            ps.setString(2, String.valueOf(ten.getText()));
            ps.setString(3, String.valueOf(ngay.getText()));
            ps.setInt(4, Integer.parseInt(String.valueOf(phai.getText())));
            ps.setString(5, String.valueOf(sdt.getText()));
            ps.setString(6, String.valueOf(email.getText()));
            ps.setString(7, String.valueOf(diachi.getText()));
            ps.setInt(8, Integer.parseInt(String.valueOf(cmnd.getText())));

            ps.executeUpdate();
//            UpdateTaiKhoan(customer);
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi");
        }
    }
}