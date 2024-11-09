package com.example.btl_group5;

import static com.example.btl_group5.R.id.lnKhaivi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_menu extends Fragment {
    private DatabaseHelper db;
    private ListView LsvKhaivi;
    private ListView LsvMonchinh;
    private ListView LsvMontrangmieng;
    private ListView LsvDouong;
    private List<String> arrKhaivi = new ArrayList<>();
    private List<String> arrMonchinh = new ArrayList<>();
    private List<String> arrMontrangmieng = new ArrayList<>();
    private List<String> arrDouong = new ArrayList<>();
    private ArrayAdapter<String> adapKhaivi;
    private ArrayAdapter<String> adapMonchinh;
    private ArrayAdapter<String> adapMontrangmieng;
    private ArrayAdapter<String> adapDouong;
    private String username;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            username = intent.getStringExtra("USERNAME");
        }

        LinearLayout lnKhaivi = rootView.findViewById(R.id.lnKhaivi);
        LinearLayout lnMonchinh = rootView.findViewById(R.id.lnMonchinh);
        LinearLayout lnMontrangmieng = rootView.findViewById(R.id.lnMontrangmieng);
        LinearLayout lnDouong = rootView.findViewById(R.id.lnDouong);

        adapKhaivi = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrKhaivi);
        adapMonchinh = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrMonchinh);
        adapMontrangmieng = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrMontrangmieng);
        adapDouong = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrDouong);

        //Client click vào món khai vị
        lnKhaivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.khaivi_client, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                LsvKhaivi = dialog.findViewById(R.id.LsvKhaivi);
                LoadDTKhaivi();

                LsvKhaivi.setAdapter(adapKhaivi);
                LsvKhaivi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Lấy dữ liệu cho món ăn đã nhấn
                        Cursor cur2 = db.getData("SELECT * FROM tblKhaivi", null);

                        if (cur2.move(i+1)) { // Di chuyển đến vị trí đã nhấn
                            String Tenmon = cur2.getString(1); // Lấy tên món ăn
                            int Giatien = cur2.getInt(2); // Lấy giá tiền

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Chọn món ăn")
                                    .setMessage("Bạn có chắc muốn chọn món này không")
                                    .setPositiveButton("Có", (dialog, which) -> {
                                        db.QueryData("INSERT INTO tblShoppingCart (Username, Tenmon, Giatien, Trangthai) VALUES (?, ?, ?, ?)", new String[]{username, Tenmon, String.valueOf(Giatien), "Chưa xác nhận"});
                                        LoadDTKhaivi();
                                        Toast.makeText(getActivity(), "Chọn món ăn thành công", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Không", null)
                                    .show();
                        } else {
                            Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                        cur2.close();
                    }
                });
            }
        });

        //Client click vào món chính
        lnMonchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.chinh_client, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                LsvMonchinh = dialog.findViewById(R.id.LsvMonchinh);
                LoadDTMonchinh();

                LsvMonchinh.setAdapter(adapMonchinh);
                LsvMonchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Lấy dữ liệu cho món ăn đã nhấn
                        Cursor cur2 = db.getData("SELECT * FROM tblMonchinh", null);

                        if (cur2.move(i+1)) { // Di chuyển đến vị trí đã nhấn
                            String Tenmon = cur2.getString(1); // Lấy tên món ăn
                            int Giatien = cur2.getInt(2); // Lấy giá tiền

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Chọn món ăn")
                                    .setMessage("Bạn có chắc muốn chọn món này không")
                                    .setPositiveButton("Có", (dialog, which) -> {
                                        db.QueryData("INSERT INTO tblShoppingCart (Username, Tenmon, Giatien, Trangthai) VALUES (?, ?, ?, ?)", new String[]{username, Tenmon, String.valueOf(Giatien), "Chưa xác nhận"});
                                        LoadDTMonchinh();
                                        Toast.makeText(getActivity(), "Chọn món ăn thành công", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Không", null)
                                    .show();
                        } else {
                            Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                        cur2.close();
                    }
                });
            }
        });

        //Client click vào món tráng miệng
        lnMontrangmieng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.trangmieng_client, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                LsvMontrangmieng = dialog.findViewById(R.id.LsvMontrangmieng);
                LoadDTTrangmieng();

                LsvMontrangmieng.setAdapter(adapMontrangmieng);
                LsvMontrangmieng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Lấy dữ liệu cho món ăn đã nhấn
                        Cursor cur2 = db.getData("SELECT * FROM tblMontrangmieng", null);

                        if (cur2.move(i+1)) { // Di chuyển đến vị trí đã nhấn
                            String Tenmon = cur2.getString(1); // Lấy tên món ăn
                            int Giatien = cur2.getInt(2); // Lấy giá tiền

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Chọn món ăn")
                                    .setMessage("Bạn có chắc muốn chọn món này không")
                                    .setPositiveButton("Có", (dialog, which) -> {
                                        db.QueryData("INSERT INTO tblShoppingCart (Username, Tenmon, Giatien, Trangthai) VALUES (?, ?, ?, ?)", new String[]{username, Tenmon, String.valueOf(Giatien), "Chưa xác nhận"});
                                        LoadDTTrangmieng();
                                        Toast.makeText(getActivity(), "Chọn món ăn thành công", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Không", null)
                                    .show();
                        } else {
                            Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                        cur2.close();
                    }
                });
            }
        });

        //Client click vào đồ uống
        lnDouong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.douong_client, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                LsvDouong = dialog.findViewById(R.id.LsvDouong);
                LoadDTDouong();

                LsvDouong.setAdapter(adapDouong);
                LsvDouong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Lấy dữ liệu cho món ăn đã nhấn
                        Cursor cur2 = db.getData("SELECT * FROM tblDouong", null);

                        if (cur2.move(i+1)) { // Di chuyển đến vị trí đã nhấn
                            String Tenmon = cur2.getString(1); // Lấy tên món ăn
                            int Giatien = cur2.getInt(2); // Lấy giá tiền

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Chọn món ăn")
                                    .setMessage("Bạn có chắc muốn chọn món này không")
                                    .setPositiveButton("Có", (dialog, which) -> {
                                        db.QueryData("INSERT INTO tblShoppingCart (Username, Tenmon, Giatien, Trangthai) VALUES (?, ?, ?, ?)", new String[]{username, Tenmon, String.valueOf(Giatien), "Chưa xác nhận"});
                                        LoadDTDouong();
                                        Toast.makeText(getActivity(), "Chọn món ăn thành công", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Không", null)
                                    .show();
                        } else {
                            Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                        cur2.close();
                    }
                });
            }
        });
        return rootView;
    }
    //Load data khai vị
    private void LoadDTKhaivi() {
        arrKhaivi.clear();
        Cursor cursor = db.getData("SELECT * FROM tblKhaivi", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(1) + " - " + cursor.getString(2);
                arrKhaivi.add(cd);
            } while(cursor.moveToNext());
        }
        adapKhaivi.notifyDataSetChanged();
    }

    //Load data món chính
    private void LoadDTMonchinh() {
        arrMonchinh.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMonchinh", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(1) + " - " + cursor.getString(2);
                arrMonchinh.add(cd);
            } while(cursor.moveToNext());
        }
        adapMonchinh.notifyDataSetChanged();
    }

    //Load data món tráng miệng
    private void LoadDTTrangmieng() {
        arrMontrangmieng.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMontrangmieng", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(1) + " - " + cursor.getString(2);
                arrMontrangmieng.add(cd);
            } while(cursor.moveToNext());
        }
        adapMontrangmieng.notifyDataSetChanged();
    }

    //Load data đồ uống
    private void LoadDTDouong() {
        arrDouong.clear();
        Cursor cursor = db.getData("SELECT * FROM tblDouong", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(1) + " - " + cursor.getString(2);
                arrDouong.add(cd);
            } while(cursor.moveToNext());
        }
        adapDouong.notifyDataSetChanged();
    }
}
