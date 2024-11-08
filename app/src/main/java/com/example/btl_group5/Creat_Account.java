package com.example.btl_group5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Creat_Account extends Activity {
    //Khai báo các biến
    EditText edtUsername, edtEmail, edtPhone, edtPassword;
    Button btnSignup;
    TextView txtVLogin;

    //Khai báo Arraylist - Arrayadapters
    ArrayList<String> arrAccount;
    ArrayAdapter<String> adapAccount;
    SQLiteDatabase dtbAccount;

    //Hàm viết code chính
    @SuppressLint("MissingInflatedId")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_account);
        //Tham chiếu tìm Id
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignup = findViewById(R.id.btnSignup);
        txtVLogin = findViewById(R.id.txtVLogin);

        //Khởi tạo Arraylist và Arrayadapter
        arrAccount = new ArrayList<>();
        adapAccount = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrAccount);
        //Tạo CSDL
        dtbAccount =openOrCreateDatabase("Account.db", MODE_PRIVATE, null);

        //Tạo table để chứa dữ liệu
        try{
            String sql = "CREATE TABLE tblAccount(Username Text primary key, Email Text, Phone Integer, Password Text)";
            dtbAccount.execSQL(sql);
        }
        catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        btnSignup.setOnClickListener(new View.OnClickListener() {
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
                String tb = "";
                if(dtbAccount.insert("tblAccount", null, values) > 0 ){
                    tb = "Tạo tài khoản thành công";
                    Intent i = new Intent(Creat_Account.this, Login.class);
                    startActivity(i);
                    finish();
                }
                else{
                    tb = "Tạo tài khoản không thành công";
                }
                Toast.makeText(Creat_Account.this, tb, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Button login trong layout Creat_Account
    public void Login(View view) {
        Intent i = new Intent(Creat_Account.this, Login.class);
        startActivity(i);
    }
}