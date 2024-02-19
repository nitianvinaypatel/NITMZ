package com.nitmizoram.nitmz;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Ce_dept extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_dept);

        ImageButton back = findViewById(R.id.backbtn);

        back.setOnClickListener(view -> onBackPressed());
    }
}