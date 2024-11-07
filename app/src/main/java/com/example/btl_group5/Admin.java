package com.example.btl_group5;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.ConsumerIrManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Admin extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView1;
    private android.graphics.BitmapFactory BitmapFactory;

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

    //Button thêm tài khoản
    public void ThemTK(View view) {
        //Alert Thêm tài khoản
        AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
        LayoutInflater inflater = Admin.this.getLayoutInflater();
        View dialog1 = inflater.inflate(R.layout.dialog_themtk, null);
        ad.setView(dialog1);
        AlertDialog b = ad.create();
        b.show();

        //Khai báo tìm ID
        Button btnThemTK = dialog1.findViewById(R.id.btnThemTK);
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
                if (dtbAccount.insert("tblAccount", null, values) > 0) {
                    msg = "Tạo tài khoản thành công";
                } else {
                    msg = "Tạo tài khoản không thành công";
                }
                Toast.makeText(Admin.this, msg, Toast.LENGTH_SHORT).show();

            }
        });
        adapListView.notifyDataSetChanged();
    }
    //Button làm mới ListView
    public void Lammoi(View view) {
        ListView LstVAccount = findViewById(R.id.LstVAccount);
        ArrayList<String> arrAcc = new ArrayList<>();
        ArrayAdapter<String> adapListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrAcc);
        SQLiteDatabase dtbAccount;

        // Thêm adapter vào listview
        LstVAccount.setAdapter(adapListView);
        dtbAccount = openOrCreateDatabase("Account.db", MODE_PRIVATE, null);
        arrAcc.clear();

        // Lấy dữ liệu từ bảng
        Cursor cur = dtbAccount.query("tblAccount", null, null, null, null, null, null);

        // Kiểm tra nếu cursor có dữ liệu
        cur = dtbAccount.query("tblAccount", null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                String data = cur.getString(0) + " - " + cur.getString(1) + " - " + cur.getInt(2) + " - " + cur.getInt(3);
                arrAcc.add(data);
            } while (cur.moveToNext());
        }
        cur.close();
        adapListView.notifyDataSetChanged();
        LstVAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy dữ liệu của tài khoản được chọn
                String selectedAccount = arrAcc.get(position);
                String[] accountDetails = selectedAccount.split(" - ");
                String username = accountDetails[0];
                String email = accountDetails[1];
                String phone = accountDetails[2];

                // Hiển thị hộp thoại để sửa tài khoản
                AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
                LayoutInflater inflater = Admin.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_themtk, null);
                ad.setView(dialogView);
                AlertDialog dialog = ad.create();
                dialog.show();

                // Khai báo các thành phần trong hộp thoại

                EditText edtUsername = dialogView.findViewById(R.id.edtUsernameCR);
                EditText edtEmail = dialogView.findViewById(R.id.edtEmailCR);
                EditText edtPhone = dialogView.findViewById(R.id.edtPhoneCR);
                EditText edtPassword = dialogView.findViewById(R.id.edtPasswordCR);
                Button btnUpdate = dialogView.findViewById(R.id.btnThemTK);

                // Điền dữ liệu cũ vào các EditText
                edtUsername.setText(username);
                edtEmail.setText(email);
                edtPhone.setText(String.valueOf(phone)); // Chuyển đổi số thành chuỗi

                // Cập nhật tài khoản khi nhấn nút sửa
                btnUpdate.setOnClickListener(v -> {
                    String newUsername = edtUsername.getText().toString();
                    String newEmail = edtEmail.getText().toString();
                    int newPhone = Integer.parseInt(edtPhone.getText().toString());
                    String newPassword = edtPassword.getText().toString(); // Nếu bạn muốn cho phép thay đổi mật khẩu

                    // Cập nhật cơ sở dữ liệu
                    ContentValues values = new ContentValues();
                    values.put("Username", newUsername);
                    values.put("Email", newEmail);
                    values.put("Phone", newPhone);
                    if (!newPassword.isEmpty()) {
                        values.put("Password", newPassword);
                    }

                    // Cập nhật bản ghi trong bảng
                    dtbAccount.update("tblAccount", values, "Username = ?", new String[]{username});

                    // Cập nhật danh sách và đóng hộp thoại
                    arrAcc.set(position, newUsername + " - " + newEmail + " - " + newPhone);
                    adapListView.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(Admin.this, "Cập nhật tài khoản thành công", Toast.LENGTH_SHORT).show();
                });

                // Thêm sự kiện nhấn và giữ vào ListView
                LstVAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        // Hiển thị hộp thoại xác nhận
                        new AlertDialog.Builder(Admin.this)
                                .setTitle("Xóa tài khoản")
                                .setMessage("Bạn có chắc chắn muốn xóa tài khoản này?")
                                .setPositiveButton("Có", (dialog, which) -> {
                                    // Xóa tài khoản
                                    String[] accountDetails = arrAcc.get(position).split(" - ");
                                    String usernameToDelete = accountDetails[0]; // Giả sử username là phần đầu tiên

                                    // Xóa tài khoản khỏi database
                                    dtbAccount.delete("tblAccount", "Username = ?", new String[]{usernameToDelete});
                                    arrAcc.remove(position); // Loại bỏ tài khoản khỏi danh sách
                                    adapListView.notifyDataSetChanged(); // Cập nhật ListView
                                    Toast.makeText(Admin.this, "Tài khoản đã được xóa", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Không", null)
                                .show();
                        return true; // Trả về true để đánh dấu sự kiện đã được xử lý
                    }
                });
            }
        });
    }

    ArrayList<String> arrMon;
    ArrayAdapter<String> adapMon;
    SQLiteDatabase dtbAccount_mon;

//    private static final int PICK_IMAGE = 1;
//    private ImageView imageView;
//    Uri selectedImageUri = null;
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
//            selectedImageUri = data.getData(); // Cập nhật biến selectedImageUri
//            imageView.setImageURI(selectedImageUri);
//        }
//    }
//    private void saveImageToDatabase(Uri imageUri) {
//        try {
//            InputStream inputStream = getContentResolver().openInputStream(imageUri);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        } catch (FileNotFoundException e) {
////            e.printStackTrace();
//        }
//    }

    //Quản lý menu
    //Admin click vào khai vị
    public void Khaivi1(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
        LayoutInflater inflater = Admin.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.khaivi_admin, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
        dtbAccount_mon = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE tblKhaivi(Tenmon Text primary key, Giatien Integer, Image Blob)";
            dtbAccount_mon.execSQL(sql);
        }
        catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
        Button btnLammoimon1 = dialog.findViewById(R.id.btnLammoimon1);
        ListView LstVTenmon = dialog.findViewById(R.id.LstVTenmon);
        arrMon = new ArrayList<>();
        adapMon = new ArrayAdapter<>(Admin.this, android.R.layout.simple_list_item_1, arrMon);
        LstVTenmon.setAdapter(adapMon);

        btnThemmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad1 = new AlertDialog.Builder(Admin.this);
                LayoutInflater inflater1 = Admin.this.getLayoutInflater();
                View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                ad1 .setView(dialog1);
                AlertDialog b1 = ad1.create();
                b1.show();
                EditText edtTenmon = dialog1.findViewById(R.id.edtTenmon);
                EditText edtGiatien = dialog1.findViewById(R.id.edtGiatien);
                Button btnUpdateMon = dialog1.findViewById(R.id.btnUpdateMon);

//                Button btnThemanh = dialog1.findViewById(R.id.btnThemanh);
//                imageView = dialog1.findViewById(R.id.ImageView);

//                btnThemanh.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                        intent.setType("image/*"); // Chỉ định loại tệp là hình ảnh
//                        intent.addCategory(Intent.CATEGORY_OPENABLE); // Cho phép mở thư mục
//                        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE);
//                    }
//                });

                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        ContentValues values = new ContentValues();
                        values.put("Tenmon", Tenmon);
                        values.put("Giatien", Giatien);

//                        if (selectedImageUri != null) {
//                            try {
//                                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
//                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                                byte[] imageBytes = byteArrayOutputStream.toByteArray();
//                                values.put("Image", imageBytes); // Lưu mảng byte vào cột Image
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                        }

                        String tb = "";
                        if(dtbAccount_mon.insert("tblKhaivi", null, values) > 0){
                            tb = "Thêm món ăn thành công";
                        }
                        else{
                            tb = "Thêm món ăn không thành công";
                        }
                        Toast.makeText(Admin.this, tb, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnLammoimon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrMon.clear();
                Cursor cur = dtbAccount_mon.query("tblKhaivi", null, null, null, null, null, null);
                if (cur != null && cur.moveToFirst()) {
                    int indexTenmon = cur.getColumnIndex("Tenmon");
                    int indexGiatien = cur.getColumnIndex("Giatien");

                    if (indexTenmon >= 0 && indexGiatien >= 0) {
                        do {
                            String Tenmon = cur.getString(indexTenmon);
                            int Giatien = cur.getInt(indexGiatien);
                            String data = Tenmon + " - " + Giatien;
                            arrMon.add(data);
                        } while (cur.moveToNext());
                    } else {
                        Log.e("Error", "Tên cột không hợp lệ");
                    }
                }
                if (cur != null) {
                    cur.close();
                }
                adapMon.notifyDataSetChanged();
            }
        });
    }
    //Admin click vào món chính
    public void Monchinh1(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
        LayoutInflater inflater = Admin.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.chinh_admin, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
        dtbAccount_mon = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE tblMonchinh(Tenmon Text primary key, Giatien Integer)";
            dtbAccount_mon.execSQL(sql);
        }
        catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
        Button btnLammoimon1 = dialog.findViewById(R.id.btnLammoimon1);
        ListView LstVTenmon = dialog.findViewById(R.id.LstVTenmon);
        arrMon = new ArrayList<>();
        adapMon = new ArrayAdapter<>(Admin.this, android.R.layout.simple_list_item_1, arrMon);
        LstVTenmon.setAdapter(adapMon);

        btnThemmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad1 = new AlertDialog.Builder(Admin.this);
                LayoutInflater inflater1 = Admin.this.getLayoutInflater();
                View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                ad1 .setView(dialog1);
                AlertDialog b1 = ad1.create();
                b1.show();
                EditText edtTenmon = dialog1.findViewById(R.id.edtTenmon);
                EditText edtGiatien = dialog1.findViewById(R.id.edtGiatien);
                Button btnUpdateMon = dialog1.findViewById(R.id.btnUpdateMon);
                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        ContentValues values = new ContentValues();
                        values.put("Tenmon", Tenmon);
                        values.put("Giatien", Giatien);
                        String tb = "";
                        if(dtbAccount_mon.insert("tblMonchinh", null, values) > 0){
                            tb = "Thêm món ăn thành công";
                        }
                        else{
                            tb = "Thêm món ăn không thành công";
                        }
                        Toast.makeText(Admin.this, tb, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnLammoimon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrMon.clear();
                Cursor cur = dtbAccount_mon.query("tblMonchinh", null, null, null, null, null, null);
                if (cur != null && cur.moveToFirst()) {
                    int indexTenmon = cur.getColumnIndex("Tenmon");
                    int indexGiatien = cur.getColumnIndex("Giatien");

                    if (indexTenmon >= 0 && indexGiatien >= 0) {
                        do {
                            String Tenmon = cur.getString(indexTenmon);
                            int Giatien = cur.getInt(indexGiatien);
                            String data = Tenmon + " - " + Giatien;
                            arrMon.add(data);
                        } while (cur.moveToNext());
                    } else {
                        Log.e("Error", "Tên cột không hợp lệ");
                    }
                }
                if (cur != null) {
                    cur.close();
                }
                adapMon.notifyDataSetChanged();
            }
        });
    }
    //Admin click vào món tráng miệng
    public void Montrangmieng1(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
        LayoutInflater inflater = Admin.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.mon_trang_mieng_admin, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
        dtbAccount_mon = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE tblMontrangmieng(Tenmon Text primary key, Giatien Integer)";
            dtbAccount_mon.execSQL(sql);
        }
        catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
        Button btnLammoimon1 = dialog.findViewById(R.id.btnLammoimon1);
        ListView LstVTenmon = dialog.findViewById(R.id.LstVTenmon);
        arrMon = new ArrayList<>();
        adapMon = new ArrayAdapter<>(Admin.this, android.R.layout.simple_list_item_1, arrMon);
        LstVTenmon.setAdapter(adapMon);

        btnThemmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad1 = new AlertDialog.Builder(Admin.this);
                LayoutInflater inflater1 = Admin.this.getLayoutInflater();
                View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                ad1 .setView(dialog1);
                AlertDialog b1 = ad1.create();
                b1.show();
                EditText edtTenmon = dialog1.findViewById(R.id.edtTenmon);
                EditText edtGiatien = dialog1.findViewById(R.id.edtGiatien);
                Button btnUpdateMon = dialog1.findViewById(R.id.btnUpdateMon);
                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        ContentValues values = new ContentValues();
                        values.put("Tenmon", Tenmon);
                        values.put("Giatien", Giatien);
                        String tb = "";
                        if(dtbAccount_mon.insert("tblMontrangmieng", null, values) > 0){
                            tb = "Thêm món ăn thành công";
                        }
                        else{
                            tb = "Thêm món ăn không thành công";
                        }
                        Toast.makeText(Admin.this, tb, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnLammoimon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrMon.clear();
                Cursor cur = dtbAccount_mon.query("tblMontrangmieng", null, null, null, null, null, null);
                if (cur != null && cur.moveToFirst()) {
                    int indexTenmon = cur.getColumnIndex("Tenmon");
                    int indexGiatien = cur.getColumnIndex("Giatien");

                    if (indexTenmon >= 0 && indexGiatien >= 0) {
                        do {
                            String Tenmon = cur.getString(indexTenmon);
                            int Giatien = cur.getInt(indexGiatien);
                            String data = Tenmon + " - " + Giatien;
                            arrMon.add(data);
                        } while (cur.moveToNext());
                    } else {
                        Log.e("Error", "Tên cột không hợp lệ");
                    }
                }
                if (cur != null) {
                    cur.close();
                }
                adapMon.notifyDataSetChanged();
            }
        });
    }
    //Admin click vào đồ uống
    public void Douong1(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(Admin.this);
        LayoutInflater inflater = Admin.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.do_uong_admin, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();
        dtbAccount_mon = openOrCreateDatabase("Food.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE tblDouong(Tenmon Text primary key, Giatien Integer)";
            dtbAccount_mon.execSQL(sql);
        }
        catch (Exception e) {
            Log.e("Error", "Table đã có sẵn");
        }
        Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
        Button btnLammoimon1 = dialog.findViewById(R.id.btnLammoimon1);
        ListView LstVTenmon = dialog.findViewById(R.id.LstVTenmon);
        arrMon = new ArrayList<>();
        adapMon = new ArrayAdapter<>(Admin.this, android.R.layout.simple_list_item_1, arrMon);
        LstVTenmon.setAdapter(adapMon);

        btnThemmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad1 = new AlertDialog.Builder(Admin.this);
                LayoutInflater inflater1 = Admin.this.getLayoutInflater();
                View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                ad1 .setView(dialog1);
                AlertDialog b1 = ad1.create();
                b1.show();
                EditText edtTenmon = dialog1.findViewById(R.id.edtTenmon);
                EditText edtGiatien = dialog1.findViewById(R.id.edtGiatien);
                Button btnUpdateMon = dialog1.findViewById(R.id.btnUpdateMon);
                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        ContentValues values = new ContentValues();
                        values.put("Tenmon", Tenmon);
                        values.put("Giatien", Giatien);
                        String tb = "";
                        if(dtbAccount_mon.insert("tblDouong", null, values) > 0){
                            tb = "Thêm món ăn thành công";
                        }
                        else{
                            tb = "Thêm món ăn không thành công";
                        }
                        Toast.makeText(Admin.this, tb, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnLammoimon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrMon.clear();
                Cursor cur = dtbAccount_mon.query("tblDouong", null, null, null, null, null, null);
                if (cur != null && cur.moveToFirst()) {
                    int indexTenmon = cur.getColumnIndex("Tenmon");
                    int indexGiatien = cur.getColumnIndex("Giatien");

                    if (indexTenmon >= 0 && indexGiatien >= 0) {
                        do {
                            String Tenmon = cur.getString(indexTenmon);
                            int Giatien = cur.getInt(indexGiatien);
                            String data = Tenmon + " - " + Giatien;
                            arrMon.add(data);
                        } while (cur.moveToNext());
                    } else {
                        Log.e("Error", "Tên cột không hợp lệ");
                    }
                }
                if (cur != null) {
                    cur.close();
                }
                adapMon.notifyDataSetChanged();
            }
        });
    }
}