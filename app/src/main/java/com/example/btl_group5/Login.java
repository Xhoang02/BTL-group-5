package com.example.btl_group5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Login extends Activity {
    // Khai báo các biến
    EditText edtUsername, edtPassword;
    Button btnSignin;
    SQLiteDatabase dtbAccount;

    // Hàm viết code chính
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Tham chiếu tìm Id
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);

        // Mở cơ sở dữ liệu
        dtbAccount = openOrCreateDatabase("Account.db", MODE_PRIVATE, null);

        // Xử lý sự kiện nút đăng nhập
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = edtUsername.getText().toString();
                String Password = edtPassword.getText().toString();

                // Câu truy vấn
                String selection = "Username = ? AND Password = ?";
                String[] selectionArgs1 = new String[] {Username, Password};
                String[] selectionArgs2 = new String[] {"Admin", "admin"};
                Cursor cur1 = dtbAccount.query("tblAccount", null, selection, selectionArgs1, null, null, null);
                Cursor cur2 = dtbAccount.query("tblAccount", null, selection, selectionArgs2, null, null, null);
                // Kiểm tra kết quả truy vấn
                if (cur1 != null && cur1.moveToFirst()) {
                    // Kiểm tra nếu tài khoản là Admin
                    if (Username.equals("Admin") && Password.equals("admin")) {
                        Toast.makeText(Login.this, "Xin chào Admin", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, Admin.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, Home.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(Login.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }

                // Đóng Cursor để giải phóng tài nguyên
                if (cur1 != null) {
                    cur1.close();
                }
                if (cur2 != null) {
                    cur2.close();
                }
            }
        });
    }

    public void Forget(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Login.this);
        LayoutInflater inflater = Login.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.forget_password, null);
        ad.setView(dialog);
        EditText edtUsernameFG = dialog.findViewById(R.id.edtUsernameFG);
        EditText edtEmailFG = dialog.findViewById(R.id.edtEmailFG);
        EditText edtPhoneFG = dialog.findViewById(R.id.edtPhoneFG);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        AlertDialog b = ad.create();
        b.show();
        dtbAccount = openOrCreateDatabase("Account.db", MODE_PRIVATE, null);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = edtUsernameFG.getText().toString();
                String Email = edtEmailFG.getText().toString();
                String PhoneString = edtPhoneFG.getText().toString();
                int Phone = Integer.parseInt(PhoneString);
                ContentValues values = new ContentValues();
                values.put("Username", Username);
                values.put("Email", Email);
                values.put("Phone", Phone);
                String selections = "Username = ? AND Email = ? AND Phone = ?";
                String[] selectionArgs = new String[] {Username, Email, PhoneString};
                Cursor cur = dtbAccount.query("tblAccount", null, selections, selectionArgs, null, null,null);
                if(cur != null && cur.moveToFirst()){
                    Toast.makeText(Login.this, "Hãy cập nhật lại mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    //Cập nhật cửa sổ mới để reset password
                    b.dismiss();
                    AlertDialog.Builder ad1 = new AlertDialog.Builder(Login.this);
                    LayoutInflater inflater1 = Login.this.getLayoutInflater();
                    View dialog1 = inflater1.inflate(R.layout.reset_password, null);
                    ad1.setView(dialog1);
                    AlertDialog b1 = ad1.create();
                    b1.show();
                    EditText edtPasswordRS = dialog1.findViewById(R.id.edtPasswordRS);
                    EditText edtPasswordRS_RS = dialog1.findViewById(R.id.edtPasswordRS_RS);
                    Button btnUpdatePass = dialog1.findViewById(R.id.btnUpdatePass);
                    btnUpdatePass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String PasswordRS = edtPasswordRS.getText().toString();
                            String PasswordRS_RS = edtPasswordRS_RS.getText().toString();
                            ContentValues values1 = new ContentValues();
                            values1.put("Password", PasswordRS);
                            int n = dtbAccount.update("tblAccount", values1, "Username = ?", new String[] {Username});
                            String msg = "";
                            if(n == 0) msg = "Cập nhật mật khẩu không thành công";
                            else msg = "Cập nhật mật khẩu thành công";
                            Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(cur != null) {
                    cur.close();
                }
            }
        });
    }

    public void Signup(View view) {
        Intent i = new Intent(Login.this, Creat_Account.class);
        startActivity(i);
    }
}