package com.nitmizoram.nitmz;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Anunaad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunaad);
        ImageView backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(view -> onBackPressed());
    }
}