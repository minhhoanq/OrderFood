package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.Connection.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddProduct extends AppCompatActivity {
    private EditText ten, nsx, hsd, dongia, hinhanh, soluong, mota, tinhtrang;
    private ImageView btn_add_sp;
    private TextView notify_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Init();
        addSPDB();
    }

    private void addSPDB() {
        btn_add_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertSP();
                nsx.setText("");
                hsd.setText("");
                ten.setText("");
                dongia.setText("");
                hinhanh.setText("");
                soluong.setText("");
                mota.setText("");
                tinhtrang.setText("");
            }
        });
    }

    private void InsertSP() {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into SANPHAM(TENSP, NSX, HSD, DONGIA, HINHANH, SOLUONG, MOTA, TINHTRANG)Values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(ten.getText()));
            ps.setString(2, String.valueOf(nsx.getText()));
            ps.setString(3, String.valueOf(hsd.getText()));
            ps.setFloat(4, Float.parseFloat(String.valueOf(dongia.getText())));
            ps.setString(5, String.valueOf(hinhanh.getText()));
            ps.setInt(6, Integer.parseInt(String.valueOf(soluong.getText())));
            ps.setString(7, String.valueOf(mota.getText()));
            ps.setInt(8, Integer.parseInt(String.valueOf(tinhtrang.getText())));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
            notify_sp.setText("Thành công!");
            notify_sp.setTextColor(getResources().getColor(R.color.green));
        } catch (Exception ex) {
            notify_sp.setText("Thất bại, hãy thử lại sau!");
            notify_sp.setTextColor(getResources().getColor(R.color.red));
        }
    }

    public void Init(){
        nsx = findViewById(R.id.edt_nsx);
        ten = findViewById(R.id.edt_ten_sp);
        hsd = findViewById(R.id.edt_hsd);
        dongia = findViewById(R.id.edt_don_gia);
        hinhanh = findViewById(R.id.edt_hinh_anh);
        soluong = findViewById(R.id.edt_so_luong);
        mota = findViewById(R.id.edt_mo_ta);
        tinhtrang = findViewById(R.id.edt_tinh_trang);
        btn_add_sp = findViewById(R.id.btn_add_sp);
        notify_sp = findViewById(R.id.tv_notify_sp);
    }
}