package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.orderfood.model.Category;
import com.example.orderfood.model.Popular;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CategoryAdapter adapterCategory;
    PopularAdapter adapterPopular;
    RecyclerView rcv;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        CreateRCVCategory();
        CreateRCVPopular();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Init(){
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void CreateRCVCategory(){
        rcv = findViewById(R.id.rcv_category);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterCategory = new CategoryAdapter(getListCategory());
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterCategory);
    }

    private void CreateRCVPopular(){
        rcv = findViewById(R.id.rcv_popular);
        LinearLayoutManager linear = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adapterPopular = new PopularAdapter(getListPopular());
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapterPopular);
    }

    private List<Popular> getListPopular() {
        List<Popular> list = new ArrayList<>();
        list.add(new Popular("Pepperoni pizza", R.drawable.pop_1, "9.67", "", 0));
        list.add(new Popular("Chesse burger", R.drawable.pop_2, "8.79",  "", 0));
        list.add(new Popular("Vegetable pizza", R.drawable.pop_3, "5.82",  "", 0));
        list.add(new Popular("Sandwich", R.drawable.hotpot, "6.73",  "", 0));
        list.add(new Popular("Broken rice", R.drawable.comtam, "10.2",  "", 0));

        return list;
    }

    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();

        list.add(new Category(R.drawable.cat_1, "Pizza"));
        list.add(new Category(R.drawable.cat_2, "Humberger"));
        list.add(new Category(R.drawable.cat_3, "Hot Dog"));
        list.add(new Category(R.drawable.cat_4, "Soda"));
        list.add(new Category(R.drawable.cat_5, "DoNut"));

        return  list;
    }
}