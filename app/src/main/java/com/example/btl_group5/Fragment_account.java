package com.example.btl_group5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment_account extends Fragment {
    private DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);        Button btndangxuat = rootView.findViewById(R.id.btndangxuat);
        //Button đăng xuất
        btndangxuat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Welcome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }
}
