package com.example.btl_group5;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_food_admin extends Fragment {
    private DatabaseHelper db;
    private ListView LstVAccount;
    private ListView LstVAccount2;
    private ListView LstVAccount3;
    private ListView LstVAccount4;
    private List<String> arrMon = new ArrayList<>();
    private List<String> arrMon2 = new ArrayList<>();
    private List<String> arrMon3 = new ArrayList<>();
    private List<String> arrMon4 = new ArrayList<>();
    private ArrayAdapter<String> adapMon;
    private ArrayAdapter<String> adapMon2;
    private ArrayAdapter<String> adapMon3;
    private ArrayAdapter<String> adapMon4;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_admin, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);

        LinearLayout lnKhaivi1 = rootView.findViewById(R.id.lnKhaivi1);
        LinearLayout lnMonchinh1 = rootView.findViewById(R.id.lnMonchinh1);
        LinearLayout lnMontrangmieng1 = rootView.findViewById(R.id.lnMontrangmieng1);
        LinearLayout lnDouong1 = rootView.findViewById(R.id.lnDouong1);
        lnKhaivi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.khaivi_admin, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
                LstVAccount = dialog.findViewById(R.id.LstVTenmon);
                LstVAccount.setAdapter(adapMon);
                LoadDTKhaivi();
                // Thêm món ăn mới
                btnThemmon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ad1 = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater1 = getLayoutInflater();
                        View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                        ad1.setView(dialog1);
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
                                db.QueryData("INSERT INTO tblKhaivi (Tenmon, Giatien) VALUES (?, ?)", new String[]{Tenmon, String.valueOf(Giatien)});
                                LoadDTKhaivi();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        lnMonchinh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.chinh_admin, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
                LstVAccount2 = dialog.findViewById(R.id.LstVTenmon2);
                LstVAccount2.setAdapter(adapMon2);
                LoadDTMonchinh();
                // Thêm món ăn mới
                btnThemmon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ad1 = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater1 = getLayoutInflater();
                        View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                        ad1.setView(dialog1);
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
                                db.QueryData("INSERT INTO tblMonchinh (Tenmon, Giatien) VALUES (?, ?)", new String[]{Tenmon, String.valueOf(Giatien)});
                                LoadDTMonchinh();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        lnMontrangmieng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.mon_trang_mieng_admin, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
                LstVAccount3 = dialog.findViewById(R.id.LstVTenmon);
                LstVAccount3.setAdapter(adapMon3);
                LoadDTMontrangmieng();
                // Thêm món ăn mới
                btnThemmon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ad1 = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater1 = getLayoutInflater();
                        View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                        ad1.setView(dialog1);
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
                                db.QueryData("INSERT INTO tblMontrangmieng (Tenmon, Giatien) VALUES (?, ?)", new String[]{Tenmon, String.valueOf(Giatien)});
                                LoadDTMontrangmieng();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        lnDouong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.do_uong_admin, null);
                ad.setView(dialog);
                AlertDialog b = ad.create();
                b.show();

                Button btnThemmon = dialog.findViewById(R.id.btnThemmon);
                LstVAccount4 = dialog.findViewById(R.id.LstVTenmon);
                LstVAccount4.setAdapter(adapMon4);
                LoadDTDouong();
                // Thêm món ăn mới
                btnThemmon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ad1 = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater1 = getLayoutInflater();
                        View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                        ad1.setView(dialog1);
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
                                db.QueryData("INSERT INTO tblDouong (Tenmon, Giatien) VALUES (?, ?)", new String[]{Tenmon, String.valueOf(Giatien)});
                                LoadDTDouong();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        return rootView;
    }
    private void LoadDTKhaivi() {
        arrMon.clear();
        Cursor cursor = db.getData("SELECT * FROM tblKhaivi", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(0) + " - " + cursor.getInt(1);
                arrMon.add(cd);
            } while(cursor.moveToNext());
        }
        adapMon = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrMon);
        LstVAccount.setAdapter(adapMon);
    }

    private void LoadDTMonchinh() {
        arrMon2.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMonchinh", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(0) + " - " + cursor.getInt(1);
                arrMon2.add(cd);
            } while(cursor.moveToNext());
        }
        adapMon2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrMon2);
        LstVAccount2.setAdapter(adapMon2);
    }

    private void LoadDTMontrangmieng() {
        arrMon3.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMontrangmieng", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(0) + " - " + cursor.getInt(1);
                arrMon3.add(cd);
            } while(cursor.moveToNext());
        }
        adapMon3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrMon3);
        LstVAccount3.setAdapter(adapMon3);
    }

    private void LoadDTDouong() {
        arrMon4.clear();
        Cursor cursor = db.getData("SELECT * FROM tblDouong", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(0) + " - " + cursor.getInt(1);
                arrMon4.add(cd);
            } while(cursor.moveToNext());
        }
        adapMon4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrMon4);
        LstVAccount4.setAdapter(adapMon4);
    }
}
