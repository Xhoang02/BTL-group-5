package com.example.btl_group5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    //Client click vào món khai vị
    public void Khaivi(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
    }
    //Client click vào món chính
    public void Monchinh(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.chinh_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
    }
    //Client click vào món tráng miệng
    public void Montrangmieng(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.trangmieng_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
    }
    //Client click vào đồ uống
    public void Douong(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.douong_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
    }
}

