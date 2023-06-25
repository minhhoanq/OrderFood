package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.orderfood.Fragment.HomeFragment;
import com.example.orderfood.Fragment.ViewPagerFragment;
import com.example.orderfood.model.Staff;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ManagerActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        anhxa();
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment(this);
        viewPager2.setAdapter(viewPagerFragment);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        int check = (int) bundle.get("key_cv");
        Staff staff = (Staff) bundle.get("key_staff");
        Log.d("QQQ", "" + staff.getTenNV());

        HomeFragment.emailGlobal = String.valueOf(staff.getEmail());

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(false);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_love).setChecked(false);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_bank).setChecked(false);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_customers).setChecked(false);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_users).setChecked(false);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.menu_love:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.menu_bank:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.menu_customers:
                        viewPager2.setCurrentItem(3);
                        break;
                    case R.id.menu_users:
                        viewPager2.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }

    void anhxa(){
        viewPager2 = findViewById(R.id.viewpager2);
        bottomNavigationView = findViewById(R.id.bottomNav);
    }
}