package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orderfood.model.MyCart;
import com.example.orderfood.model.Popular;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ManagementCart managementCart;
    private ConstraintLayout btnAdd;
    private TextView title, price, count, description;
    private ImageView product, minus, plus;
    private ArrayList<Popular> list = new ArrayList<>();;
    private int id;
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
        list = (ArrayList) bundle.getParcelableArrayList("keyname");
        id = bundle.getInt("idobj");
        list.get(id).setCount(1);

        product.setImageResource(list.get(id).getImg_popular());
        title.setText(list.get(id).getTitle());
        price.setText(list.get(id).getPrice());
        description.setText(list.get(id).getDescription());
        count.setText(String.valueOf(list.get(id).getCount()));
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOder < 20){
                    numberOder = numberOder + 1;
                    list.get(id).setCount(numberOder);
                    count.setText(String.valueOf(numberOder));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOder > 1){
                    numberOder = numberOder - 1;
                    list.get(id).setCount(numberOder);
                    count.setText(String.valueOf(numberOder));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popular = new Popular(list.get(id).getTitle(), list.get(id).getImg_popular(), list.get(id).getPrice(),
                        " ", list.get(id).getCount());
                list.get(id).setCount(numberOder);
                managementCart.insertFood(popular);
            }
        });

    }

    private void Init(){
        btnAdd = findViewById(R.id.layout_add_to_cart);
        title = findViewById(R.id.tv_detail_title);
        price = findViewById(R.id.tv_price_detail);
        count = findViewById(R.id.tv_count);
        description = findViewById(R.id.detail_description);
        product = findViewById(R.id.detail_product);
        minus = findViewById(R.id.img_minus);
        plus = findViewById(R.id.img_plus);
    }
}