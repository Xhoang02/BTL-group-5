package com.example.btl_group5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class Welcome extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }

    public void Register(View view) {
        Intent i = new Intent(this, Creat_Account.class);
        startActivity(i);
    }

    public void SignIn(View view) {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
