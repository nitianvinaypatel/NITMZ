package com.nitmizoram.nitmz.AdminSection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nitmizoram.nitmz.AddBuses;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.adapter.UpdateBusAdapter;
import com.nitmizoram.nitmz.model.BusData;

import java.util.ArrayList;

public class UpdateBusTiming extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<BusData> list;
    private UpdateBusAdapter adapter;
    private ProgressBar progressBar;
    private String hostel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bus_timing);

        FloatingActionButton addBus = findViewById(R.id.add_bus);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        ImageButton back = findViewById(R.id.backbtn);

        addBus.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddBuses.class);
            startActivity(intent);
        });
        back.setOnClickListener(view -> onBackPressed());
        // Initialize Firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = firestore.collection("users").document(userId);
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        hostel = document.getString("hostel");
                        loadData(); // Move the data loading logic here
                    }
                }
            });
        }
    }

    private void loadData() {
        // Constructing the database path
        String path = "NIT Buses/" + hostel;

        recyclerView = findViewById(R.id.recycler_view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(path);
        getData();
    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    BusData data = snapshot1.getValue(BusData.class);
                    list.add(data);
                }
                adapter = new UpdateBusAdapter(getApplicationContext(), list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
