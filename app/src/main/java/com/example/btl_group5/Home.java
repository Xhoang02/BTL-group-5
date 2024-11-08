package com.example.btl_group5;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    ArrayList<String> arrFood;
    ArrayAdapter<String> adapFood;
    SQLiteDatabase dtbFood;
    SQLiteDatabase dtbAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Khởi tạo Adapter
        //adapListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrMon);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        arrFood = new ArrayList<>();
        adapFood = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrFood);
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

    ListView LsvKhaivi;
    Button btnLammoidulieu;
    //Client click vào món khai vị
    public void Khaivi(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();

        LsvKhaivi = dialog.findViewById(R.id.LsvKhaivi);
        btnLammoidulieu = dialog.findViewById(R.id.btnLammoidulieu);

        LsvKhaivi.setAdapter(adapFood);

        dtbFood = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblKhaivi(Tenmon Text primary key, Giatien Integer)";
            dtbFood.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        loadFoodData1();
        btnLammoidulieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFoodData1();
            }
        });
        dtbAcc = openOrCreateDatabase("Account.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblShoppingCart(Username Text primary key, Tenmon Text, Giatien Integer)";
            dtbAcc.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        LsvKhaivi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cur1 = dtbAcc.query("tblAccount", null, null, null, null, null, null);
                Cursor cur2 = dtbFood.query("tblKhaivi", null, null, null, null, null, null);

                // Di chuyển con trỏ đến hàng đầu tiên
                if (cur1.moveToFirst() && cur2.moveToFirst()) {
                    String Username = cur1.getString(0);
                    String Tenmon = cur2.getString(0);
                    int Giatien = cur2.getInt(1);

                    new AlertDialog.Builder(Home.this)
                            .setTitle("Chọn món ăn")
                            .setMessage("Bạn có chắc muốn chọn món này không")
                            .setPositiveButton("Có", (dialog, which) -> {
                                ContentValues values = new ContentValues();
                                values.put("Username", Username);
                                values.put("Tenmon", Tenmon);
                                values.put("Giatien", Giatien);
                                String msg = "";
                                if(dtbFood.insert("tblShoppingCart", null, values) > 0){
                                    msg = "Chọn món ăn thành công";
                                } else {
                                    msg = "Chọn món ăn không thành công vui lòng chọn lại!";
                                }
                                Toast.makeText(Home.this, msg, Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Không", null)
                            .show();
                } else {
                    Toast.makeText(Home.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
                cur1.close();
                cur2.close();
            }
        });
    }
    private void loadFoodData1() {
        arrFood.clear();
        Cursor cur = dtbFood.query("tblKhaivi", null, null, null, null, null, null);

        if (cur.moveToFirst()) {
            do {
                String data = cur.getString(0) + " - " + cur.getInt(1);
                arrFood.add(data);
            } while (cur.moveToNext());
        }
        cur.close();
        adapFood.notifyDataSetChanged();
    }

    //Client click vào món chính
    public void Monchinh(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();

        LsvKhaivi = dialog.findViewById(R.id.LsvKhaivi);
        btnLammoidulieu = dialog.findViewById(R.id.btnLammoidulieu);

        LsvKhaivi.setAdapter(adapFood);

        dtbFood = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblKhaivi(Tenmon Text primary key, Giatien Integer)";
            dtbFood.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }

        loadFoodData2();

        btnLammoidulieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFoodData2();
            }
        });
    }
    private void loadFoodData2() {
        arrFood.clear();
        Cursor cur = dtbFood.query("tblMonchinh", null, null, null, null, null, null);

        if (cur.moveToFirst()) {
            do {
                String data = cur.getString(0) + " - " + cur.getInt(1);
                arrFood.add(data);
            } while (cur.moveToNext());
        }
        cur.close();
        adapFood.notifyDataSetChanged();
    }

    //Client click vào món tráng miệng
    public void Montrangmieng(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();

        LsvKhaivi = dialog.findViewById(R.id.LsvKhaivi);
        btnLammoidulieu = dialog.findViewById(R.id.btnLammoidulieu);

        LsvKhaivi.setAdapter(adapFood);

        dtbFood = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblMontrangmieng(Tenmon Text primary key, Giatien Integer)";
            dtbFood.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }

        loadFoodData3();

        btnLammoidulieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFoodData3();
            }
        });
    }
    private void loadFoodData3() {
        arrFood.clear();
        Cursor cur = dtbFood.query("tblMontrangmieng", null, null, null, null, null, null);

        if (cur.moveToFirst()) {
            do {
                String data = cur.getString(0) + " - " + cur.getInt(1);
                arrFood.add(data);
            } while (cur.moveToNext());
        }
        cur.close();
        adapFood.notifyDataSetChanged();
    }

    //Client click vào đồ uống
    public void Douong(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_client, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();

        LsvKhaivi = dialog.findViewById(R.id.LsvKhaivi);
        btnLammoidulieu = dialog.findViewById(R.id.btnLammoidulieu);

        LsvKhaivi.setAdapter(adapFood);

        dtbFood = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE tblDouong(Tenmon Text primary key, Giatien Integer)";
            dtbFood.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        loadFoodData4();
        btnLammoidulieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFoodData4();
            }
        });
    }
    private void loadFoodData4() {
        arrFood.clear();
        Cursor cur = dtbFood.query("tblDouong", null, null, null, null, null, null);

        if (cur.moveToFirst()) {
            do {
                String data = cur.getString(0) + " - " + cur.getInt(1);
                arrFood.add(data);
            } while (cur.moveToNext());
        }
        cur.close();
        adapFood.notifyDataSetChanged();
    }
}