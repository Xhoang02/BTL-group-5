package com.example.btl_group5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        bottomNavigationView1 = findViewById(R.id.bottomNavigationView1);
        bottomNavigationView1.setOnNavigationItemSelectedListener(this);
    }
    Fragment_revenue_admin revenue = new Fragment_revenue_admin();
    Fragment_food_admin food = new Fragment_food_admin();
    Fragment_shopping_admin shopping = new Fragment_shopping_admin();
    Fragment_account_admin account = new Fragment_account_admin();
    //    @SuppressLint("NonConstantResourceId")
//    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Lấy ID của item được chọn
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.revenue) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment1, revenue).commit();
            return true;
        } else if (selectedItemId == R.id.food) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment1, food).commit();
            return true;
        } else if (selectedItemId == R.id.shoppingcart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment1, shopping).commit();
            return true;
        } else if (selectedItemId == R.id.account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment1, account).commit();
            return true;
        }
        return false;
    }
}

