package com.example.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.adapter.DeleteStaffAdapter;
import com.example.nitmz.model.FacultyData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DeleteStaff extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<FacultyData> list;
    private DeleteStaffAdapter adapter;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_staff);


        ImageButton backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> onBackPressed());

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Staff");
        getData();
    }
    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    FacultyData data = snapshot1.getValue(FacultyData.class);
                    list.add(data);

                    Collections.reverse(list);
                    adapter = new DeleteStaffAdapter(getApplicationContext(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }
}