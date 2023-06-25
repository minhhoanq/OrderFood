package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orderfood.model.GioHang;
import com.example.orderfood.model.MyCart;
import com.example.orderfood.model.Popular;
import com.example.orderfood.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ManagementCart managementCart;
    private ConstraintLayout btnAdd;
    private TextView title, price, count, description, tvslconlai;
    private ImageView imgProduct, minus, plus;
    private ArrayList<Popular> list = new ArrayList<>();;
    private int numberOder = 1;
    private Popular popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        managementCart = new ManagementCart(DetailActivity.this);
        Init();

        getBundle();
    }

    private void getBundle(){
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String key = (String) bundle.get("id_key");
        Product product = (Product) bundle.get(key);

        Glide.with(this)
                .load(product.getHinhAnh())
                .into(imgProduct);
        title.setText(product.getTenSP());
        price.setText(String.valueOf(product.getDonGia()));
        description.setText(product.getMoTa());

        tvslconlai.setText("Số lượng : "+ String.valueOf(product.getSoLuong()));

        int cnt = 1;
        count.setText(String.valueOf(cnt));
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOder < product.getSoLuong()){
                    numberOder = numberOder + 1;
                    count.setText(String.valueOf(numberOder));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOder > 1){
                    numberOder = numberOder - 1;
                    count.setText(String.valueOf(numberOder));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                popular = new Popular(list.get(id).getTitle(), list.get(id).getImg_popular(), list.get(id).getPrice(),
//                        " ", list.get(id).getCount());
//                list.get(id).setCount(numberOder);
//                managementCart.insertFood(popular);

                GioHang gh = new GioHang();
                gh.setMASP(product.getMaSP());
                gh.setSoLuong(numberOder);
                float t = product.getDonGia();
                gh.setTongTien(t * numberOder);
                gh.setAnhSp(product.getHinhAnh());
                gh.setTenSP(product.getTenSP());
                gh.setTienGoc(product.getDonGia());

                MyCartActivity myCartActivity = new MyCartActivity();
                myCartActivity.listGHGlobal.add(gh);
                finish();
            }
        });

    }

    private void Init(){
        btnAdd = findViewById(R.id.layout_add_to_cart);
        title = findViewById(R.id.tv_detail_title);
        price = findViewById(R.id.tv_price_detail);
        count = findViewById(R.id.tv_count);
        tvslconlai = findViewById(R.id.tv_sl_con_lai);
        description = findViewById(R.id.detail_description);
        imgProduct = findViewById(R.id.detail_product);
        minus = findViewById(R.id.img_minus);
        plus = findViewById(R.id.img_plus);
    }


}