package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderfood.model.Product;
import com.example.orderfood.model.Staff;

public class ShowProductActivity extends AppCompatActivity {
    private TextView name, price, soluong, mota, tinhtrang, nsx, hsd, malsp, mncc, mth;
    private ImageView hinhanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_product);
        Init();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Product product = (Product) bundle.get("info_product");
        setTextInForStaff(product);
//        setOnClickView(staff);
    }

//    private void setOnClickView(Staff staff) {
//        updateStaff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Staff staffTemp = staff;
//                startActivity(new Intent(ShowProductActivity.this, UpdateStaff.class)
//                        .putExtra("info_staff_up", staffTemp)
//                );
//            }
//        });
//    }

    public void setTextInForStaff(Product product) {
        name.setText(product.getTenSP());
        price.setText(String.valueOf(product.getDonGia()));
        Glide.with(this)
                .load(product.getHinhAnh())
                .into(hinhanh);
        soluong.setText(String.valueOf(product.getSoLuong()));

        mota.setText(product.getMoTa());

        nsx.setText("Ngày sản xuất : " + product.getNxs());
        hsd.setText("Hạn sử dụng : " + product.getHsd());
        tinhtrang.setText("Tình trạng :" + product.getTinhTrang());

        malsp.setText(String.valueOf("Mã loại sản phẩm : " + product.getMaLSP()));
        mncc.setText(String.valueOf("Mã nhà cung cấp : " + product.getMaNCC()));
        mth.setText(String.valueOf("Mã thương hiệu : " + product.getMaTH()));

    }

    private void Init(){
        name = findViewById(R.id.tv_product_title);
        price = findViewById(R.id.tv_price_product);
        hinhanh = findViewById(R.id.img_product_info);
        soluong = findViewById(R.id.tv_sl_product);
        mota = findViewById(R.id.tv_mota_product);
        nsx = findViewById(R.id.tv_nsx_product);
        hsd = findViewById(R.id.tv_hsd_product);
        tinhtrang = findViewById(R.id.tv_trang_thai);
        malsp = findViewById(R.id.tv_mlsp);
        mncc = findViewById(R.id.tv_ncc);
        mth = findViewById(R.id.tv_mth);
    }
}