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
    DatabaseHelper db = new DatabaseHelper(Login.this, DatabaseHelper.DATABASE_NAME,null,DatabaseHelper.DATABASE_VERSION);

    // Hàm viết code chính
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Tham chiếu tìm Id
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignin);

        // Xử lý sự kiện nút đăng nhập
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = edtUsername.getText().toString().trim();
                String Password = edtPassword.getText().toString().trim();

                // Câu truy vấn
                Cursor cur = db.getData("Select * From tblAccount Where Username = ? AND Password = ?", new String[]{Username, Password});

                // Kiểm tra kết quả truy vấn
                if (cur != null && cur.moveToFirst()) {
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
                if (cur != null) {
                    cur.close();
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

//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String Username = edtUsernameFG.getText().toString().trim();
//                String Email = edtEmailFG.getText().toString().trim();
//                String PhoneString = edtPhoneFG.getText().toString().trim();
//
//                if (Username.isEmpty() || Email.isEmpty() || PhoneString.isEmpty()) {
//                    Toast.makeText(Login.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                int Phone;
//                try {
//                    Phone = Integer.parseInt(PhoneString);
//                } catch (NumberFormatException e) {
//                    Toast.makeText(Login.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                ContentValues values = new ContentValues();
//                values.put("Username", Username);
//                values.put("Email", Email);
//                values.put("Phone", Phone);
//                String selections = "Username = ? AND Email = ? AND Phone = ?";
//                String[] selectionArgs = new String[] {Username, Email, PhoneString};
//                Cursor cur = dtbAccount.query("tblAccount", null, selections, selectionArgs, null, null, null);
//
//                if (cur != null && cur.moveToFirst()) {
//                    Toast.makeText(Login.this, "Hãy cập nhật lại mật khẩu của bạn", Toast.LENGTH_SHORT).show();
//                    // Cập nhật cửa sổ mới để reset password
//                    b.dismiss();
//                    AlertDialog.Builder ad1 = new AlertDialog.Builder(Login.this);
//                    LayoutInflater inflater1 = Login.this.getLayoutInflater();
//                    View dialog1 = inflater1.inflate(R.layout.reset_password, null);
//                    ad1.setView(dialog1);
//                    AlertDialog b1 = ad1.create();
//                    b1.show();
//                    EditText edtPasswordRS = dialog1.findViewById(R.id.edtPasswordRS);
//                    EditText edtPasswordRS_RS = dialog1.findViewById(R.id.edtPasswordRS_RS);
//                    Button btnUpdatePass = dialog1.findViewById(R.id.btnUpdatePass);
//
//                    btnUpdatePass.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String PasswordRS = edtPasswordRS.getText().toString().trim();
//                            String PasswordRS_RS = edtPasswordRS_RS.getText().toString().trim();
//
//                            if (PasswordRS.isEmpty() || PasswordRS_RS.isEmpty()) {
//                                Toast.makeText(Login.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                            if (!PasswordRS.equals(PasswordRS_RS)) {
//                                Toast.makeText(Login.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                            ContentValues values1 = new ContentValues();
//                            values1.put("Password", PasswordRS);
//                            int n = dtbAccount.update("tblAccount", values1, "Username = ?", new String[] {Username});
//                            String msg = (n == 0) ? "Cập nhật mật khẩu không thành công" : "Cập nhật mật khẩu thành công";
//                            Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(Login.this, "Thông tin không đúng", Toast.LENGTH_SHORT).show();
//                }
//
//                if (cur != null) {
//                    cur.close();
//                }
//            }
//        });
    }

    public void Signup(View view) {
        Intent i = new Intent(Login.this, Creat_Account.class);
        startActivity(i);
    }
}