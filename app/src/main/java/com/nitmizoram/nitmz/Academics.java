package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Academics extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);
        ImageButton backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> onBackPressed());
    }
}