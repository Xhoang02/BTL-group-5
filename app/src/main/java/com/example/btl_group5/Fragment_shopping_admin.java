package com.example.btl_group5;

import android.app.AlertDialog;
import android.database.Cursor;
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
    private ArrayAdapter<String> adapter;
    private DatabaseHelper db;
    private ListView cart_list_view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_admin, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);

        cart_list_view = rootView.findViewById(R.id.cart_list_view);
        cartItems = new ArrayList<>();
        LoadDTShoppingCart();

        // Thiết lập OnItemClickListener cho ListView
        cart_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmationDialog(position);
            }
        });
        return rootView;
    }

    // Load data shopping cart
    private void LoadDTShoppingCart() {
        cartItems.clear();
        Cursor cursor = db.getData("SELECT * FROM tblShoppingCart", null);
        if (cursor.moveToFirst()) {
            do {
                // Chỉ lấy 4 cột (cột 1, 2, 3, 4)
                String cd = cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getInt(3) + " - " + cursor.getString(4);
                cartItems.add(cd);
            } while (cursor.moveToNext());
        }
        cursor.close(); // Đóng cursor sau khi sử dụng
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cartItems);
        cart_list_view.setAdapter(adapter);
    }

    private void showConfirmationDialog(int position) {
        AlertDialog.Builder ad = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
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
                String selectedItem = cartItems.get(position);
                String[] itemDetails = selectedItem.split(" - ");

                String Username = itemDetails[0];
                String Tenmon = itemDetails[1];
                int Giatien;
                try {
                    Giatien = Integer.parseInt(itemDetails[2]);
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Giá tiền không hợp lệ.", Toast.LENGTH_SHORT).show();
                    b.dismiss();
                    return;
                }
                db.QueryData("INSERT INTO tblShoppingCart (Username, Tenmon, Giatien, Trangthai) VALUES (?, ?, ?, ?)",
                        new String[]{Username, Tenmon, String.valueOf(Giatien), "Hoàn thành"});
                db.QueryData("DELETE FROM tblShoppingCart WHERE Tenmon = ? AND Trangthai = ?", new String[]{Tenmon,"Chưa xác nhận"});

                cartItems.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(requireContext(), "Đơn hàng của " + Username + " đã được xác nhận.", Toast.LENGTH_SHORT).show();
                b.dismiss();
                LoadDTShoppingCart();
            }
        });

        // Xử lý sự kiện nhấn nút hủy
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lưu lại thông tin sản phẩm bị xóa
                String removedItem = cartItems.get(position);
                String[] itemDetails = removedItem.split(" - ");
                String Username = itemDetails[0];
                String Tenmon = itemDetails[1];

                db.QueryData("DELETE FROM tblShoppingCart WHERE Username = ? AND Tenmon = ?",
                        new String[]{Username, Tenmon});

                cartItems.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(requireContext(), "Đã xóa đơn hàng: " + removedItem, Toast.LENGTH_SHORT).show();
                b.dismiss();
            }
        });
    }
}