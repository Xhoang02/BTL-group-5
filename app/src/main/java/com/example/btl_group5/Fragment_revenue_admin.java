package com.example.btl_group5;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.btl_group5.adapters.RevenueAdapter;
import com.example.btl_group5.models.Revenue;

import java.util.ArrayList;
import java.util.List;

public class Fragment_revenue_admin extends Fragment {
    private DatabaseHelper db;
    private ListView LstRevenue;
    private List<Revenue> revenues;
    private RevenueAdapter revenueAdapter;
    private TextView txtThanhTien;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_revenue_admin, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
        revenues = new ArrayList<>();
        LstRevenue = rootView.findViewById(R.id.lv_revenue);
        txtThanhTien = rootView.findViewById(R.id.txt_thanhtien);
        revenueAdapter = new RevenueAdapter(getActivity(), revenues);
        LstRevenue.setAdapter(revenueAdapter);

        LoadDTRevenue();
        return rootView;
    }

    private void LoadDTRevenue() {
        revenues.clear();
        Cursor cursor = db.getData("SELECT * FROM tblShoppingCart WHERE Trangthai = ?", new String[]{"Hoàn thành"});
        int totalRevenue = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int tenIndex = cursor.getColumnIndex("Username");
            int giatienIndex = cursor.getColumnIndex("Giatien");
            do {
                int id = cursor.getInt(idIndex);
                String client = cursor.getString(tenIndex);
                int giatien = cursor.getInt(giatienIndex);
                revenues.add(new Revenue(id, client, giatien));
                totalRevenue += giatien;
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Không có dữ liệu doanh thu", Toast.LENGTH_SHORT).show();
        }
        txtThanhTien.setText("Tổng doanh thu: " + totalRevenue + " VND");
        revenueAdapter.notifyDataSetChanged();
    }
}