package com.example.btl_group5;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.btl_group5.models.User;

public class Fragment_account extends Fragment {
    private DatabaseHelper db;

    // Views
    private Button btnEdit, btnSavePassword, btnLogout;
    private EditText editPhone, editEmail, editName, editPassword;

    private boolean isEditing = false;
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);

        // Lấy username từ Intent

        username = requireActivity().getIntent().getStringExtra("USERNAME");


        // Khởi tạo các Views
        btnEdit = rootView.findViewById(R.id.btn_edit);
        //btnSavePassword = rootView.findViewById(R.id.btn_save_password);
        btnLogout = rootView.findViewById(R.id.btndangxuat);

        editPhone = rootView.findViewById(R.id.text_phone_value);
        editEmail = rootView.findViewById(R.id.text_email_value);
        editName = rootView.findViewById(R.id.text_name_value);
        editPassword = rootView.findViewById(R.id.text_password_label);
        loadUserData();

        btnEdit.setOnClickListener(v -> {
            saveUserData();
        });
        // Xử lý nút Đăng xuất
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Welcome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }

    private void loadUserData() {
        if (username == null) {
            Toast.makeText(getActivity(), "Không tìm thấy dữ liệu người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sử dụng getData để lấy dữ liệu người dùng từ tblAccount
        Cursor cursor = db.getData("SELECT Phone, Email, Password, Username FROM tblAccount WHERE Username=?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            editPhone.setText(cursor.getString(0));
            editEmail.setText(cursor.getString(1));
            editPassword.setText(cursor.getString(2));
            editName.setText(cursor.getString(3));

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Không tìm thấy dữ liệu người dùng", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserData() {
        String newPhone = editPhone.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();
        String newName = editName.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();

        if (newPhone.isEmpty() || newEmail.isEmpty() || newName.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        String sql = "UPDATE tblAccount SET Phone = ?, Email = ?, Password = ?, Username = ? WHERE Username = ?";
        db.QueryData(sql, new String[]{newPhone, newEmail, newPassword, newName, username});
        Toast.makeText(getActivity(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
    }
}