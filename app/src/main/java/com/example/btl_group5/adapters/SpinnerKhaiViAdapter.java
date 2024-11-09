package com.example.btl_group5.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.btl_group5.R;

public class SpinnerKhaiViAdapter extends BaseAdapter {
    private int[] imgs={R.drawable.pho,R.drawable.suon,R.drawable.sup};
    Context context;

    public SpinnerKhaiViAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_sp_khaivi,viewGroup,false);
        ImageView imageView = item.findViewById(R.id.img);
        imageView.setImageResource(imgs[i]);
        return item;
    }
}