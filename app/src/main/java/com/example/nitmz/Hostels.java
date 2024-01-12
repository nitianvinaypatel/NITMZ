package com.example.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Hostels extends AppCompatActivity {

    private ImageButton backbtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels);

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> onBackPressed());
    }
}