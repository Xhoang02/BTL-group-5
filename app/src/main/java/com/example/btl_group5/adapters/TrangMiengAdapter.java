package com.example.btl_group5.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.btl_group5.DatabaseHelper;
import com.example.btl_group5.R;
import com.example.btl_group5.models.KhaiVi;
import com.example.btl_group5.models.TrangMieng;

import java.util.List;

public class TrangMiengAdapter extends BaseAdapter {
    private DatabaseHelper db;
    List<TrangMieng> trangMiengList;
    Context context;
    ImageButton btn_edit, btn_delete;
    private int[] imgs = {R.drawable.banh, R.drawable.kem};

    public TrangMiengAdapter(Context context, List<TrangMieng> trangMiengList) {
        this.context = context;
        this.trangMiengList = trangMiengList;
        db = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public int getCount() {
        return trangMiengList.size();
    }

    @Override
    public Object getItem(int i) {
        return trangMiengList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_lv_montrangmieng, viewGroup, false);
        }
        ImageView img = view.findViewById(R.id.lv_img_trangmieng);
        TextView gia, ten;
        ten = view.findViewById(R.id.lv_ten_trangmieng);
        gia = view.findViewById(R.id.lv_gia_trangmieng);
        TrangMieng trangMieng = trangMiengList.get(i);
        ten.setText("Tên món: " + trangMieng.getName());
        gia.setText("Giá: " + trangMieng.getPrice());
        int index_anh = trangMieng.getImg();
        if (index_anh != 0) {
            try {
                img.setImageResource(index_anh);
            } catch (Resources.NotFoundException e) {
                Log.e("Error", "", e);
                img.setImageResource(R.drawable.pho);
            }
        } else {
            img.setImageResource(R.drawable.pho);
        }

        btn_edit = view.findViewById(R.id.img_edit);
        btn_delete = view.findViewById(R.id.img_delete);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
                LayoutInflater inflater1 = LayoutInflater.from(context);
                View dialog1 = inflater1.inflate(R.layout.manage_food, null);
                ad1.setView(dialog1);
                AlertDialog b1 = ad1.create();
                b1.show();

                EditText edtTenmon = dialog1.findViewById(R.id.edtTenmon);
                EditText edtGiatien = dialog1.findViewById(R.id.edtGiatien);
                Button btnUpdateMon = dialog1.findViewById(R.id.btnUpdateMon);

                Spinner sp3 = dialog1.findViewById(R.id.spinner);
                SpinnerTrangMiengAdapter spinnerAdapter = new SpinnerTrangMiengAdapter(context);
                sp3.setAdapter(spinnerAdapter);

                edtTenmon.setText(trangMieng.getName());
                edtGiatien.setText(String.valueOf(trangMieng.getPrice()));

                int img = trangMieng.getImg();
                int position = index(img);

                sp3.setSelection(position);

                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        String i = sp3.getSelectedItem().toString().trim();
                        int anh = imgs[Integer.parseInt(i)];

                        int itemId = trangMieng.getId();
                        db.QueryData("UPDATE tblMontrangmieng SET Tenmon = ?, Giatien = ?, Image = ? WHERE id = ?",
                                new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh), String.valueOf(itemId)});


                        LoadDTMontrangmieng();
                        Toast.makeText(context, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                        b1.dismiss();
                    }
                });
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa món ăn")
                        .setMessage("Bạn có muốn xóa món ăn không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                db.QueryData("DELETE FROM tblMontrangmieng WHERE id = ?", new String[]{String.valueOf(trangMieng.getId())});

                                trangMiengList.remove(trangMieng);
                                LoadDTMontrangmieng();
                                Toast.makeText(context, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });

        return view;
    }
    private int index(int position) {
        for (int i = 0; i < imgs.length; i++) {
            if (imgs[i] == position) {
                return i;
            }
        }
        return 0;
    }
    private void LoadDTMontrangmieng() {
        trangMiengList.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMontrangmieng", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                trangMiengList.add(new TrangMieng(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}