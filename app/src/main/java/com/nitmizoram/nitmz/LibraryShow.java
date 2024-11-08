package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmizoram.nitmz.adapter.PdfAdapter;
import com.nitmizoram.nitmz.model.PdfData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class LibraryShow extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<PdfData> list;
    private PdfAdapter adapter;

    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_show);

        ImageButton back = findViewById(R.id.backbtn);


        // Extracting values from Intent
        Intent intent = getIntent();
        String course = intent.getStringExtra("course");
        String semester = intent.getStringExtra("semester");
        String branch = intent.getStringExtra("branch");
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Loading...");
        pd.show();

        back.setOnClickListener(view -> onBackPressed());


        // Constructing the database path
        String path = "Books/" + course + "/" + semester + "/" + branch;


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
                }
                // Reverse the list to display newly added files on top
                Collections.reverse(list);
                adapter = new PdfAdapter(LibraryShow.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(LibraryShow.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}