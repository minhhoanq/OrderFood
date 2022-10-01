package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyCartActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private MyCartAdapter adapter;
    private ManagementCart managementCart;
    private ConstraintLayout btnCheck;
    private TextView tvtotalprice, tvtotalcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initView();

        initList();
        calculaterCart();
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        rcv = findViewById(R.id.rcv_cart);
        btnCheck = findViewById(R.id.btnCheck);
        tvtotalprice = findViewById(R.id.tv_total);
        tvtotalcart = findViewById(R.id.tv_total_cart);
    }

    private void initList(){
        managementCart = new ManagementCart(MyCartActivity.this);
        LinearLayoutManager linearlayout = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        adapter = new MyCartAdapter(managementCart.getListCart(), this, new ChangeNumberItem() {
            @Override
            public void changed() {
                calculaterCart();
            }
        });
        rcv.setLayoutManager(linearlayout);
        rcv.setAdapter(adapter);
    }

    private void calculaterCart() {
        double totalprice = 0;
        for(int i = 0; i < managementCart.getListCart().size(); i++){
            totalprice = totalprice + managementCart.getTotalFood(managementCart.getListCart(),i);
        }

        tvtotalprice.setText(String.valueOf(Math.round(totalprice * 100.0)/100.0));
        tvtotalcart.setText(String.valueOf(((Math.round(totalprice + 10.0 + 1.0)*100.0)/100.0)));
    }
}