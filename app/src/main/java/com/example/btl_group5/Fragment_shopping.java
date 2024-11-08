package com.example.btl_group5;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_shopping extends Fragment {
    private ArrayList<String> arrAcc; // Khai báo danh sách sản phẩm
    private ArrayAdapter<String> adapAcc;
    private SQLiteDatabase dtbAcc; // Khai báo adapter

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping, container, false);
        ListView cartListView = rootView.findViewById(R.id.LsvShopping);
        Button btnLammoidulieu1 = rootView.findViewById(R.id.btnLammoidulieu1);
        arrAcc = new ArrayList<>();
        adapAcc = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, arrAcc);
        cartListView.setAdapter(adapAcc);

        // Khởi tạo DatabaseHelper
        // Mở cơ sở dữ liệu

        loadFoodData2();
        btnLammoidulieu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFoodData2();
            }
        });
        return rootView;
    }

    private void loadFoodData2() {
        arrAcc.clear();
        Cursor cur = dtbAcc.query("tblShoppingCart", null, null, null, null, null, null);

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    String data = cur.getString(0) + " - " + cur.getString(1) + " - " + cur.getInt(2);
                    arrAcc.add(data);
                } while (cur.moveToNext());
            }
            cur.close();
        }
        adapAcc.notifyDataSetChanged();
    }
}