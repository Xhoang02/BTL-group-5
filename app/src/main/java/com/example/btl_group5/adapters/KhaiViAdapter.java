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

import java.util.List;

public class KhaiViAdapter extends BaseAdapter {
    private DatabaseHelper db;
    List<KhaiVi> khaiViList;
    Context context;
    ImageButton btn_edit, btn_delete;
    private int[] imgs = {R.drawable.pho, R.drawable.suon, R.drawable.sup};

    public KhaiViAdapter(Context context, List<KhaiVi> khaiViList) {
        this.context = context;
        this.khaiViList = khaiViList;
        db = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public int getCount() {
        return khaiViList.size();
    }

    @Override
    public Object getItem(int i) {
        return khaiViList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_lv_khaivi, viewGroup, false);
        }
        ImageView img = view.findViewById(R.id.lv_img_khaivi);
        TextView gia, ten;
        ten = view.findViewById(R.id.lv_ten_khaivi);
        gia = view.findViewById(R.id.lv_gia_khaivi);
        KhaiVi khaiVi = khaiViList.get(i);
        ten.setText("Tên món: " + khaiVi.getName());
        gia.setText("Giá: " + khaiVi.getPrice());
        int index_anh = khaiVi.getImg();
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

                Spinner sp1 = dialog1.findViewById(R.id.spinner);
                SpinnerKhaiViAdapter spinnerAdapter = new SpinnerKhaiViAdapter(context);
                sp1.setAdapter(spinnerAdapter);

                edtTenmon.setText(khaiVi.getName());
                edtGiatien.setText(String.valueOf(khaiVi.getPrice()));

                int img = khaiVi.getImg();
                int position = index(img);

                sp1.setSelection(position);


                btnUpdateMon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenmon = edtTenmon.getText().toString();
                        int Giatien = Integer.parseInt(edtGiatien.getText().toString());
                        int imgPosition = sp1.getSelectedItemPosition();
                        int anh = imgs[imgPosition];

                        khaiVi.setName(Tenmon);
                        khaiVi.setPrice(Giatien);
                        khaiVi.setImg(anh);

                        int itemId = khaiVi.getId();
                        db.QueryData("UPDATE tblKhaivi SET Tenmon = ?, Giatien = ?, Image = ? WHERE id = ?",
                                new String[]{Tenmon, String.valueOf(Giatien), String.valueOf(anh), String.valueOf(itemId)});


                        LoadDTKhaivi();
                        Toast.makeText(context, "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
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

                                db.QueryData("DELETE FROM tblKhaivi WHERE id = ?", new String[]{String.valueOf(khaiVi.getId())});

                                khaiViList.remove(khaiVi);
                                LoadDTKhaivi();
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
    public void LoadDTKhaivi() {
        khaiViList.clear();
        Cursor cursor = db.getData("SELECT * FROM tblKhaivi", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ten_mon = cursor.getString(1);
                int gia = cursor.getInt(2);
                int anh = cursor.getInt(3);
                khaiViList.add(new KhaiVi(id, anh, ten_mon, gia));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}