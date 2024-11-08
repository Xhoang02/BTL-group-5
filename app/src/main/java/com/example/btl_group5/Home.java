package com.example.btl_group5;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    SQLiteDatabase dtbAccount_mon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Khởi tạo Adapter
        //adapListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrMon);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Khởi tạo cơ sở dữ liệu
        dtbAccount_mon = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
    }

    Fragment_home fragmentHome = new Fragment_home();
    Fragment_menu fragmentMenu = new Fragment_menu();
    Fragment_shopping fragmentShopping = new Fragment_shopping();
    Fragment_account fragmentAccount = new Fragment_account();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        return false;
    }
    public void Khaivi(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();

    }

}