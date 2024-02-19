package com.nitmizoram.nitmz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmizoram.nitmz.adapter.PdfAdapter;
import com.nitmizoram.nitmz.model.PdfData;

import java.util.ArrayList;
import java.util.Collections;

public class CalenderU extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<PdfData> list;
    private PdfAdapter adapter;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        ImageButton back = findViewById(R.id.backbtn);
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Loading...");
        pd.show();

        back.setOnClickListener(view -> onBackPressed());

        recyclerView = findViewById(R.id.recycler_view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Academic Calender");
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
                adapter = new PdfAdapter(CalenderU.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(CalenderU.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });

    }
}