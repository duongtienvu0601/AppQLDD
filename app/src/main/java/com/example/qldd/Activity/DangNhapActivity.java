package com.example.qldd.Activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qldd.R;

public class DangNhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HỆ HỐNG");
        String title = actionBar.getTitle().toString();
        actionBar.hide();
    }
}