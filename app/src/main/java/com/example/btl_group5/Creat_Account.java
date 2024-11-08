package com.example.btl_group5;

import static androidx.core.content.ContentProviderCompat.requireContext;

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

    //Gọi datahelper
    DatabaseHelper db = new DatabaseHelper(Creat_Account.this,DatabaseHelper.DATABASE_NAME,null,DatabaseHelper.DATABASE_VERSION);

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

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = edtUsername.getText().toString();
                String Email = edtEmail.getText().toString();
                int Phone = Integer.parseInt(edtPhone.getText().toString());
                String Password = edtPassword.getText().toString();
                db.QueryData("INSERT INTO tblAccount (Username,Email,Phone,Password) VALUES (?, ? , ? , ?)",new String[]{Username,Email,String.valueOf(Phone),Password});
                Toast.makeText(Creat_Account.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Creat_Account.this, Login.class);
                startActivity(i);
            }
        });
    }
    //Button login trong layout Creat_Account
    public void Login(View view) {
        Intent i = new Intent(Creat_Account.this, Login.class);
        startActivity(i);
    }
}