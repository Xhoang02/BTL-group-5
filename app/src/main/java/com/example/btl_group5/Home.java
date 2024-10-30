package com.example.btl_group5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
    Fragment_home fragmentHome = new Fragment_home();
    Fragment_menu fragmentMenu = new Fragment_menu();
    Fragment_shopping fragmentShopping = new Fragment_shopping();
    Fragment_account fragmentAccount = new Fragment_account();
//    @SuppressLint("NonConstantResourceId")
//    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Lấy ID của item được chọn
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragmentHome).commit();
            return true;
        } else if (selectedItemId == R.id.menu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragmentMenu).commit();
            return true;
        } else if (selectedItemId == R.id.shoppingcart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragmentShopping).commit();
            return true;
        } else if (selectedItemId == R.id.account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragmentAccount).commit();
        return true;
        }
        return false; // Trả về false nếu không có item nào được chọn
    }

    public void Khaivi(View view) {
        Intent i = new Intent(Home.this, khai_vi.class);
        startActivity(i);
    }
    public void Monchinh(View view) {
        Intent i = new Intent(Home.this, khai_vi.class);
        startActivity(i);
    }
    public void Monphu(View view) {
        Intent i = new Intent(Home.this, khai_vi.class);
        startActivity(i);
    }
    public void Monannhanh(View view) {
        Intent i = new Intent(Home.this, khai_vi.class);
        startActivity(i);
    }
    public void Montrangmieng(View view) {
        Intent i = new Intent(Home.this, khai_vi.class);
        startActivity(i);
    }
    public void Douong(View view) {
        Intent i = new Intent(Home.this, khai_vi.class);
        startActivity(i);
    }
}

