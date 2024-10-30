package com.example.btl_group5;

import static com.example.btl_group5.R.id.lnKhaivi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_menu extends Fragment {
    public Fragment_menu() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        view.findViewById(R.id.lnKhaivi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khai_vi khaivi = new khai_vi();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, khaivi)
                        .addToBackStack(null) // Thêm vào back stack
                        .commit();
            }
        });
        view.findViewById(R.id.lnMonchinh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon_chinh monchinh = new Mon_chinh();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, monchinh)
                        .addToBackStack(null) // Thêm vào back stack
                        .commit();
            }
        });
        view.findViewById(R.id.lnMonphu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon_phu monPhu = new Mon_phu();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, monPhu)
                        .addToBackStack(null) // Thêm vào back stack
                        .commit();
            }
        });
        view.findViewById(R.id.lnMonannhanh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon_an_nhanh monAnNhanh = new Mon_an_nhanh();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, monAnNhanh)
                        .addToBackStack(null) // Thêm vào back stack
                        .commit();
            }
        });
        view.findViewById(R.id.lnMontrangmieng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mon_trang_mieng trangMieng = new Mon_trang_mieng();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, trangMieng)
                        .addToBackStack(null) // Thêm vào back stack
                        .commit();
            }
        });
        view.findViewById(R.id.lnDouong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Do_uong doUong = new Do_uong();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, doUong)
                        .addToBackStack(null) // Thêm vào back stack
                        .commit();
            }
        });
        return view;

    }
}
