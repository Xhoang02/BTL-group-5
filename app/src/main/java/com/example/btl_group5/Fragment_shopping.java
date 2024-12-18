package com.example.btl_group5;

import android.content.Intent;
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
    private ArrayList<String> arrAcc;
    private ArrayAdapter<String> adapAcc;
    private DatabaseHelper db;
    private ListView LsvShopping;
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            username = intent.getStringExtra("USERNAME");
        }
        LsvShopping = rootView.findViewById(R.id.LsvShopping);
        arrAcc = new ArrayList<>();
        LoadDTKhaivi();
        return rootView;
    }

    private void LoadDTKhaivi() {
        arrAcc.clear();
        Cursor cursor = db.getData("SELECT * FROM tblShoppingCart WHERE Username = ?", new String[]{username});
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(1) +  " - " + cursor.getString(2) + " - " + cursor.getInt(3) + " - " + cursor.getString(4);
                arrAcc.add(cd);
            } while(cursor.moveToNext());
        }
        adapAcc = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrAcc);
        LsvShopping.setAdapter(adapAcc);
    }
}