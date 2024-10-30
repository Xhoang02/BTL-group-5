package com.example.btl_group5;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

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

    public void ThemTK(View view) {
        //Alert Thêm tài khoản
        AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
        LayoutInflater inflater = Admin.this.getLayoutInflater();
        View dialog1 = inflater.inflate(R.layout.dialog_themtk, null);
        ad.setView(dialog1);
        AlertDialog b = ad.create();
        b.show();

        //Khai báo tìm ID
        Button btnThemTK= dialog1.findViewById(R.id.btnThemTK);
        ListView LstVAccount = findViewById(R.id.LstVAccount);
        EditText edtUsername = dialog1.findViewById(R.id.edtUsernameCR);
        EditText edtEmail = dialog1.findViewById(R.id.edtEmailCR);
        EditText edtPhone = dialog1.findViewById(R.id.edtPhoneCR);
        EditText edtPassword = dialog1.findViewById(R.id.edtPasswordCR);
        ArrayList<String> arrAcc = new ArrayList<>();
        ArrayAdapter<String> adapListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrAcc);
        SQLiteDatabase dtbAccount;
        //Thêm adapter vào listview
        LstVAccount.setAdapter(adapListView);
        dtbAccount = openOrCreateDatabase("Account.db", MODE_PRIVATE, null);
        btnThemTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = edtUsername.getText().toString();
                String Email = edtEmail.getText().toString();
                int Phone = Integer.parseInt(edtPhone.getText().toString());
                String Password = edtPassword.getText().toString();
                ContentValues values = new ContentValues();
                values.put("Username", Username);
                values.put("Email", Email);
                values.put("Phone", Phone);
                values.put("Password", Password);
                String msg = "";
                if(dtbAccount.insert("tblAccount", null, values) > 0 ){
                    msg = "Tạo tài khoản thành công";
                }
                else{
                    msg = "Tạo tài khoản không thành công";
                }
                Toast.makeText(Admin.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Lammoi(View view) {
        ListView LstVAccount = findViewById(R.id.LstVAccount);
        ArrayList<String> arrAcc = new ArrayList<>();
        ArrayAdapter<String> adapListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrAcc);
        SQLiteDatabase dtbAccount;
        //Thêm adapter vào listview
        LstVAccount.setAdapter(adapListView);
        dtbAccount = openOrCreateDatabase("Account.db", MODE_PRIVATE, null);
        arrAcc.clear();

        //
        Cursor cur = dtbAccount.query("tblAccount", null, null, null, null, null, null);
        // Kiểm tra nếu cursor có dữ liệu
        if (cur.moveToFirst()) { // Di chuyển đến bản ghi đầu tiên
            do {
                String data = cur.getString(0) + " - " + cur.getString(1) + " - " + cur.getInt(2); // Sử dụng getInt cho siso
                arrAcc.add(data);
            } while (cur.moveToNext()); // Di chuyển đến bản ghi tiếp theo
        }
        cur.close();
        adapListView.notifyDataSetChanged();
    }

}

