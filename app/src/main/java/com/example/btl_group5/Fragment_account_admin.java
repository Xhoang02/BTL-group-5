package com.example.btl_group5;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_account_admin extends Fragment {
    private DatabaseHelper db;
    private ListView LstVAccount;
    private ArrayAdapter<String> adap;
    private List<String> acc = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_admin, container, false);
        db = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);

        Button btnThem = rootView.findViewById(R.id.btnThem);
        LstVAccount = rootView.findViewById(R.id.LstVAccount);
        LoadDT();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog1 = inflater.inflate(R.layout.dialog_themtk, null);
                ad.setView(dialog1);
                AlertDialog b = ad.create();
                b.show();

                Button btnThemTK = dialog1.findViewById(R.id.btnThemTK);
                EditText edtUsername = dialog1.findViewById(R.id.edtUsernameCR);
                EditText edtEmail = dialog1.findViewById(R.id.edtEmailCR);
                EditText edtPhone = dialog1.findViewById(R.id.edtPhoneCR);
                EditText edtPassword = dialog1.findViewById(R.id.edtPasswordCR);

                btnThemTK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Username = edtUsername.getText().toString();
                        String Email = edtEmail.getText().toString();
                        int Phone = Integer.parseInt(edtPhone.getText().toString());
                        String Password = edtPassword.getText().toString();
                        db.QueryData("INSERT INTO tblAccount (Username, Email, Phone, Password) VALUES (?, ?, ?, ?)", new String[]{Username, Email, String.valueOf(Phone), Password});
                        LoadDT();
                        Toast.makeText(getActivity(), "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        LstVAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAccount = acc.get(position);
                String[] accountDetails = selectedAccount.split(" - ");
                String username = accountDetails[0];
                String email = accountDetails[1];
                String phone = accountDetails[2];

                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_themtk, null);
                ad.setView(dialogView);
                AlertDialog dialog = ad.create();
                dialog.show();

                EditText edtUsername = dialogView.findViewById(R.id.edtUsernameCR);
                EditText edtEmail = dialogView.findViewById(R.id.edtEmailCR);
                EditText edtPhone = dialogView.findViewById(R.id.edtPhoneCR);
                EditText edtPassword = dialogView.findViewById(R.id.edtPasswordCR);
                Button btnUpdate = dialogView.findViewById(R.id.btnThemTK);

                edtUsername.setText(username);
                edtEmail.setText(email);
                edtPhone.setText(phone);

                btnUpdate.setOnClickListener(v -> {
                    String newUsername = edtUsername.getText().toString();
                    String newEmail = edtEmail.getText().toString();
                    int newPhone = Integer.parseInt(edtPhone.getText().toString());
                    String newPassword = edtPassword.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put("Username", newUsername);
                    values.put("Email", newEmail);
                    values.put("Phone", newPhone);
                    if (!newPassword.isEmpty()) {
                        values.put("Password", newPassword);
                    }

                    db.QueryData("UPDATE tblAccount SET Username = ?, Email = ?, Phone = ?, Password = ? WHERE Username = ?",
                            new String[]{newUsername, newEmail, String.valueOf(newPhone), newPassword, username});
                    acc.set(position, newUsername + " - " + newEmail + " - " + newPhone);
                    adap.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Cập nhật tài khoản thành công", Toast.LENGTH_SHORT).show();
                });
            }
        });
        LstVAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Xóa tài khoản")
                        .setMessage("Bạn có chắc chắn muốn xóa tài khoản này?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            String[] accountDetails = acc.get(position).split(" - ");
                            String usernameToDelete = accountDetails[0];
                            db.QueryData("DELETE FROM tblAccount WHERE Username = ?", new String[]{usernameToDelete});
                            acc.remove(position);
                            adap.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Tài khoản đã được xóa", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Không", null)
                        .show();
                return true;
            }
        });
        return rootView;
    }

    private void LoadDT() {
        acc.clear();
        Cursor cursor = db.getData("SELECT * FROM tblAccount", null);
        if(cursor.moveToFirst()){
            do {
                String cd = cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getInt(2) + " - " + cursor.getString(3);
                acc.add(cd);
            } while(cursor.moveToNext());
        }
        adap = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, acc);
        LstVAccount.setAdapter(adap);
    }
}

