package com.example.btl_group5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_shopping_admin extends Fragment {
    private ArrayList<String> cartItems; // Khai báo danh sách sản phẩm
    private ArrayAdapter<String> adapter; // Khai báo adapter

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_admin, container, false);
        ListView cartListView = rootView.findViewById(R.id.cart_list_view);

        // Khởi tạo danh sách sản phẩm
        cartItems = new ArrayList<>();
        cartItems.add("Sản phẩm 1");
        cartItems.add("Sản phẩm 2");
        cartItems.add("Sản phẩm 3");

        // Thiết lập adapter cho ListView
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, cartItems);
        cartListView.setAdapter(adapter);

        // Thiết lập OnItemClickListener cho ListView
        cartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmationDialog(position); // Truyền vị trí sản phẩm vào dialog
            }
        });

        return rootView;
    }

    private void showConfirmationDialog(int position) {
        AlertDialog.Builder ad = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = Fragment_shopping_admin.this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.activity_order_confirmation, null);
        ad.setView(dialog);
        AlertDialog b = ad.create();
        b.show();

        Button confirm_button = dialog.findViewById(R.id.confirm_button);
        Button cancel_button = dialog.findViewById(R.id.cancel_button);

        // Xử lý sự kiện nhấn nút xác nhận
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic xác nhận đơn hàng
                Toast.makeText(requireContext(), "Đơn hàng đã được xác nhận: " + cartItems.get(position), Toast.LENGTH_SHORT).show();
                b.dismiss(); // Đóng dialog
            }
        });

        // Xử lý sự kiện nhấn nút hủy
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa sản phẩm khỏi danh sách
                String removedItem = cartItems.get(position); // Lưu lại sản phẩm bị xóa
                cartItems.remove(position); // Xóa sản phẩm tại vị trí đã nhấn
                adapter.notifyDataSetChanged(); // Cập nhật ListView
                Toast.makeText(requireContext(), "Đã xóa đơn hàng: " + removedItem, Toast.LENGTH_SHORT).show();
                b.dismiss(); // Đóng dialog
            }
        });
    }
}