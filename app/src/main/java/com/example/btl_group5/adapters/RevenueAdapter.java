package com.example.btl_group5.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.btl_group5.DatabaseHelper;
import com.example.btl_group5.R;
import com.example.btl_group5.models.KhaiVi;
import com.example.btl_group5.models.Revenue;

import java.util.List;

public class RevenueAdapter extends BaseAdapter {
    private DatabaseHelper db;
    List<Revenue> revenueList;
    Context context;

    public RevenueAdapter(Context context, List<Revenue> revenueList) {
        this.context = context;
        this.revenueList = revenueList;
        db = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public int getCount() {
        return revenueList.size();
    }

    @Override
    public Object getItem(int i) {
        return revenueList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_revenue, parent, false);
        }
        TextView ten = convertView.findViewById(R.id.lv_tenkh);
        TextView tong = convertView.findViewById(R.id.lv_thanhtien);
        Revenue revenue = revenueList.get(position);
        ten.setText("Tên khách hàng: " + revenue.getClient());
        tong.setText("Tổng tiền: " + revenue.getTotal());
        return convertView;
    }
}
