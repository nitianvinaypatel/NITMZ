package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.adapter.PdfAdapter;
import com.example.nitmz.model.PdfData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PyqShow extends AppCompatActivity {

    private   String course;
    private   String semester;
    private String branch;

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<PdfData> list;
    private PdfAdapter adapter;
    private ProgressDialog pd;

    private ImageButton back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyq_show);

        back =findViewById(R.id.backbtn);

        // Extracting values from Intent
        Intent intent = getIntent();
        course = intent.getStringExtra("course");
        semester = intent.getStringExtra("semester");
        branch = intent.getStringExtra("branch");
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Loading...");
        pd.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Constructing the database path
        String path = "PYQs/" + course + "/" + semester + "/" + branch;


        recyclerView = findViewById(R.id.recycler_view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(path);
        getData();

    }
    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pd.dismiss();
                list = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PdfData data = snapshot1.getValue(PdfData.class);
                    list.add(data);
                    Log.d("PyqShow", "Data size: " + list.size());
                }
                // Reverse the list to display newly added files on top
                Collections.reverse(list);
                adapter = new PdfAdapter(PyqShow.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(PyqShow.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed

            }
        });

    }
}