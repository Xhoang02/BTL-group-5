package com.example.btl_group5;

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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.btl_group5.adapters.DoUongAdapter;
import com.example.btl_group5.adapters.KhaiViAdapter;
import com.example.btl_group5.adapters.MonChinhAdapter;
import com.example.btl_group5.adapters.SpinnerDoUongAdapter;
import com.example.btl_group5.adapters.SpinnerKhaiViAdapter;
import com.example.btl_group5.adapters.SpinnerMonChinhAdapter;
import com.example.btl_group5.adapters.SpinnerTrangMiengAdapter;
import com.example.btl_group5.adapters.TrangMiengAdapter;
import com.example.btl_group5.models.DoUong;
import com.example.btl_group5.models.KhaiVi;
import com.example.btl_group5.models.MonChinh;
import com.example.btl_group5.models.TrangMieng;

import java.util.ArrayList;
import java.util.List;

public class Fragment_food_admin extends Fragment {
    private DatabaseHelper db;
    private ListView LstVKhaiVi;
    private ListView LstVMonChinh;
    private ListView LstVTrangMieng;
    private ListView LstVDoUong;
    private List<KhaiVi> khaiVis = new ArrayList<>();
    private List<MonChinh> monChinhs = new ArrayList<>();
    private List<TrangMieng> trangMiengs = new ArrayList<>();
    private List<DoUong> doUongs = new ArrayList<>();
    //private ArrayAdapter<String> adapMon;
    private KhaiViAdapter khaiViAdapter;
    private MonChinhAdapter monChinhAdapter;
    private TrangMiengAdapter trangMiengAdapter;
    private DoUongAdapter doUongAdapter;
    private int[] imgs = {R.drawable.pho, R.drawable.suon, R.drawable.sup};
    private int[] imgs1 = {R.drawable.garan, R.drawable.lauthai};
    private int[] imgs2 = {R.drawable.banh, R.drawable.kem};
    private int[] imgs3 = {R.drawable.coca, R.drawable.pepsi, R.drawable.lavie};

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
                LstVKhaiVi = dialog.findViewById(R.id.LstVTenmon);
                khaiViAdapter = new KhaiViAdapter(getActivity(), khaiVis);
                LstVKhaiVi.setAdapter(khaiViAdapter);
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

                        Spinner sp1 = dialog1.findViewById(R.id.spinner);
                        SpinnerKhaiViAdapter spinnerAdapter = new SpinnerKhaiViAdapter(getActivity());
                        sp1.setAdapter(spinnerAdapter);

                        btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Tenmon = edtTenmon.getText().toString();
                                int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                                String i = sp1.getSelectedItem().toString().trim();
                                int anh = imgs[Integer.parseInt(i)];
                                db.QueryData("INSERT INTO tblKhaivi (Tenmon, Giatien, Image) VALUES (?, ?, ?)", new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh)});
                                LoadDTKhaivi();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                                b1.dismiss();
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
                LstVMonChinh = dialog.findViewById(R.id.LstVTenmon2);
                monChinhAdapter = new MonChinhAdapter(getActivity(), monChinhs);
                LstVMonChinh.setAdapter(monChinhAdapter);
                LoadDTMonchinh();
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

                        Spinner sp2 = dialog1.findViewById(R.id.spinner);
                        SpinnerMonChinhAdapter spinnerAdapter = new SpinnerMonChinhAdapter(getActivity());
                        sp2.setAdapter(spinnerAdapter);

                        btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Tenmon = edtTenmon.getText().toString();
                                int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                                String i = sp2.getSelectedItem().toString().trim();
                                int anh = imgs1[Integer.parseInt(i)];
                                db.QueryData("INSERT INTO tblMonchinh (Tenmon, Giatien, Image) VALUES (?, ?, ?)", new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh)});
                                LoadDTMonchinh();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                                b1.dismiss();
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
                LstVTrangMieng = dialog.findViewById(R.id.LstVTenmon);
                trangMiengAdapter = new TrangMiengAdapter(getActivity(), trangMiengs);
                LstVTrangMieng.setAdapter(trangMiengAdapter);
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

                        Spinner sp3 = dialog1.findViewById(R.id.spinner);
                        SpinnerTrangMiengAdapter spinnerAdapter = new SpinnerTrangMiengAdapter(getActivity());
                        sp3.setAdapter(spinnerAdapter);

                        btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Tenmon = edtTenmon.getText().toString();
                                int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                                String i = sp3.getSelectedItem().toString().trim();
                                int anh = imgs2[Integer.parseInt(i)];
                                db.QueryData("INSERT INTO tblMontrangmieng (Tenmon, Giatien, Image) VALUES (?, ?, ?)", new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh)});
                                LoadDTMontrangmieng();
                                Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                                b1.dismiss();
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
                LstVDoUong = dialog.findViewById(R.id.LstVTenmon);
                doUongAdapter = new DoUongAdapter(getActivity(), doUongs);
                LstVDoUong.setAdapter(doUongAdapter);
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

                        Spinner sp4 = dialog1.findViewById(R.id.spinner);
                        SpinnerDoUongAdapter spinnerAdapter = new SpinnerDoUongAdapter(getActivity());
                        sp4.setAdapter(spinnerAdapter);

                        btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Tenmon = edtTenmon.getText().toString();
                                int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                                String i = sp4.getSelectedItem().toString().trim();
                                int anh = imgs3[Integer.parseInt(i)];
                                db.QueryData("INSERT INTO tblDouong (Tenmon, Giatien, Image) VALUES (?, ?, ?)", new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh)});
                                LoadDTDouong();
                                Toast.makeText(getActivity(), "Thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
                                b1.dismiss();
                            }
                        });
                    }
                });
            }
        });
        return rootView;
    }

    public void LoadDTKhaivi() {
        khaiVis.clear();
        Cursor cursor = db.getData("SELECT * FROM tblKhaivi", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                khaiVis.add(new KhaiVi(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        khaiViAdapter.notifyDataSetChanged();
    }

    private void LoadDTMonchinh() {
        monChinhs.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMonchinh", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                monChinhs.add(new MonChinh(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        monChinhAdapter.notifyDataSetChanged();
    }

    private void LoadDTMontrangmieng() {
        trangMiengs.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMontrangmieng", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                trangMiengs.add(new TrangMieng(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        trangMiengAdapter.notifyDataSetChanged();
    }

    private void LoadDTDouong() {
        doUongs.clear();
        Cursor cursor = db.getData("SELECT * FROM tblDouong", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                doUongs.add(new DoUong(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        doUongAdapter.notifyDataSetChanged();
    }
}