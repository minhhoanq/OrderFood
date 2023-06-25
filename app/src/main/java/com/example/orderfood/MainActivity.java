package com.example.orderfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.adapter.CategoryAdapter;
import com.example.orderfood.adapter.NuocCoGasAdapter;
import com.example.orderfood.adapter.NuocNgotAdapter;
import com.example.orderfood.adapter.NuocTraAdapter;
import com.example.orderfood.adapter.PopularAdapter;
import com.example.orderfood.model.ACCOUNT;
import com.example.orderfood.model.Category;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.Popular;
import com.example.orderfood.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CategoryAdapter adapterCategory;
    PopularAdapter adapterPopular;
    NuocTraAdapter adapterNuocTra;
    NuocCoGasAdapter adapterNuocGas;
    NuocNgotAdapter adapterNuocNgot;
    TextView tenKH;
    ImageView imgProfile;
    RelativeLayout profileBtn;
    RecyclerView rcv;
    FloatingActionButton floatingActionButton;
    Connection cons;
    dbConnection dbConnect;
    String getEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        CreateRCVNS();
        CreateRCVNTL();
        CreateRCVNTr();
        CreateRCVNGAS();
        CreateRCVNN();
        int ma_kh = getCusbyEmail(getEmail).getId_kh();
        MyCartActivity.id_kh = ma_kh;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyCartActivity.class)
                        .putExtra("info_cus", getCusbyEmail(getEmail))
                );
            }
        });

        onClickProfile();
    }

    private void onClickProfile() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpdateProfileCus.class)
                        .putExtra("info_cus", getCusbyEmail(getEmail))
                );
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpdateProfileCus.class)
                        .putExtra("info_cus", getCusbyEmail(getEmail))
                );
            }
        });
    }

    private void Init(){
        floatingActionButton = findViewById(R.id.floatingActionButton);
        profileBtn = findViewById(R.id.profileBtn);
        tenKH = findViewById(R.id.tv_ten_kh_main);
        imgProfile = findViewById(R.id.img_profile);

        getEmail = getIntent().getStringExtra("key_email");

        String nameKH = getCusbyEmail(getEmail).getTen();
        tenKH.setText(nameKH);
    }

    private void CreateRCVNS(){
        rcv = findViewById(R.id.rcv_nuoc_suoi);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterCategory = new CategoryAdapter(getListProduct(4));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterCategory);
    }

    private void CreateRCVNTL(){
        rcv = findViewById(R.id.rcv_nuoc_tang_luc);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterPopular = new PopularAdapter(getListProduct(1));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterPopular);
    }

    private void CreateRCVNTr(){
        rcv = findViewById(R.id.rcv_tra);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterNuocTra = new NuocTraAdapter(getListProduct(3));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterNuocTra);
    }

    private void CreateRCVNGAS(){
        rcv = findViewById(R.id.rcv_nuoc_gas);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterNuocGas = new NuocCoGasAdapter(getListProduct(2));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterNuocGas);
    }

    private void CreateRCVNN(){
        rcv = findViewById(R.id.rcv_nuoc_ngot);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterNuocNgot = new NuocNgotAdapter(getListProduct(5));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterNuocNgot);
    }


    private List<Product> getListProduct(int mlsp) {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From SANPHAM Where MA_LSP="+mlsp;
            List<Product> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setMaSP(rs.getInt("MA_SP"));
                    product.setTenSP(rs.getString("TENSP"));
                    product.setNxs(rs.getString("NSX"));
                    product.setHsd(rs.getString("HSD"));
                    product.setDonGia(rs.getFloat("DONGIA"));
                    product.setHinhAnh(rs.getString("HINHANH"));
                    product.setSoLuong(rs.getInt("SOLUONG"));
                    product.setMoTa(rs.getString("MOTA"));
                    product.setTinhTrang(rs.getString("TINHTRANG"));
                    product.setMaLSP(rs.getInt("MA_LSP"));
                    product.setMaNCC(rs.getInt("MA_NCC"));
                    product.setMaTH(rs.getInt("MA_TH"));
                    product.setMaGiamGia(rs.getInt("MA_GIAMGIA"));
                    list.add(product);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        }catch (Exception e){
            Log.d("III", "lỗi");
        }
        return null;
    }

    private Customer getCusbyEmail(String emailKH){
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From KHACHHANG where EMAIL=" + "'" + emailKH + "'";
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Customer cs = new Customer();
            if(rs != null) {
                while(rs.next()){
                    cs.setId_kh(rs.getInt("ID_KH"));
                    cs.setEmail(rs.getString("EMAIL"));
                    cs.setHo(rs.getString("HO"));
                    cs.setTen(rs.getString("TEN"));
                    cs.setSdt(rs.getString("SDT"));
                    cs.setDiachi(rs.getString("DIACHI"));
                    cs.setPhai(rs.getInt("PHAI"));
                    cs.setNgaysinh(rs.getString("NGAYSINH"));
                }

            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return cs;
        } catch (Exception ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.d("III", "lỗi");
        }
        return null;
    }
}