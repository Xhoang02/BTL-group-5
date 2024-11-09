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
import com.example.btl_group5.models.MonChinh;

import java.util.List;

public class MonChinhAdapter extends BaseAdapter {
    private DatabaseHelper db;
    List<MonChinh> monChinhList;
    Context context;
    ImageButton btn_edit, btn_delete;
    private int[] imgs = {R.drawable.garan, R.drawable.lauthai};

    public MonChinhAdapter(Context context, List<MonChinh> monChinhList) {
        this.context = context;
        this.monChinhList = monChinhList;
        db = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public int getCount() {
        return monChinhList.size();
    }

    @Override
    public Object getItem(int i) {
        return monChinhList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_lv_monchinh, viewGroup, false);
        }
        ImageView img1 = view.findViewById(R.id.lv_img_monchinh);
        TextView gia1, ten1;
        ten1 = view.findViewById(R.id.lv_ten_monchinh);
        gia1 = view.findViewById(R.id.lv_gia_monchinh);
        MonChinh monChinh = monChinhList.get(i);
        ten1.setText("Tên món: " + monChinh.getName1());
        gia1.setText("Giá: " + monChinh.getPrice1());
        int index_anh1 = monChinh.getImg1();
        if (index_anh1 != 0) {
            try {
                img1.setImageResource(index_anh1);
            } catch (Resources.NotFoundException e) {
                Log.e("Error", "", e);
                img1.setImageResource(R.drawable.garan);
            }
        } else {
            img1.setImageResource(R.drawable.garan);
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

                Spinner sp2 = dialog1.findViewById(R.id.spinner);
                SpinnerMonChinhAdapter spinnerAdapter = new SpinnerMonChinhAdapter(context);
                sp2.setAdapter(spinnerAdapter);

                edtTenmon.setText(monChinh.getName1());
                edtGiatien.setText(String.valueOf(monChinh.getPrice1()));

                int img = monChinh.getImg1();
                int position = index(img);

                sp2.setSelection(position);

                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        String i = sp2.getSelectedItem().toString().trim();
                        int anh = imgs[Integer.parseInt(i)];

                        int itemId = monChinh.getId1();
                        db.QueryData("UPDATE tblMonchinh SET Tenmon = ?, Giatien = ?, Image = ? WHERE id = ?",
                                new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh), String.valueOf(itemId)});


                        LoadDTMonchinh();
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
                                db.QueryData("DELETE FROM tblMonchinh WHERE id = ?", new String[]{String.valueOf(monChinh.getId1())});

                                monChinhList.remove(monChinh);
                                LoadDTMonchinh();
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
    private void LoadDTMonchinh() {
        monChinhList.clear();
        Cursor cursor = db.getData("SELECT * FROM tblMonchinh", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                monChinhList.add(new MonChinh(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}