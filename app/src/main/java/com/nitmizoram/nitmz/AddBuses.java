package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class AddBuses extends AppCompatActivity {

    private TextInputEditText editTextBusNum ,editTextBusTime,editTextBusFrom, editTextBusTo;

    private DatabaseReference databaseReference;

    private String  hostel;

    private ProgressDialog pd;
    private TextView hostelName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buses);

        editTextBusNum = findViewById(R.id.busNum);
        editTextBusTime = findViewById(R.id.busTime);
        editTextBusFrom = findViewById(R.id.busFrom);
        editTextBusTo = findViewById(R.id.busTo);
        Button add_btn = findViewById(R.id.Add_btn);
        ImageButton back = findViewById(R.id.backbtn);
        hostelName = findViewById(R.id.hostelName);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);



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

                        hostelName.setText(hostel);

                    }
                }
            });
        }


        add_btn.setOnClickListener(view -> uploadData());
        back.setOnClickListener(view -> onBackPressed());
    }
    private void uploadData() {

        String uniqueKey = databaseReference.child("busData").push().getKey();
        String Bus_No = Objects.requireNonNull(editTextBusNum.getText()).toString().trim();
        String Bus_time = Objects.requireNonNull(editTextBusTime.getText()).toString().trim();
        String Bus_from = Objects.requireNonNull(editTextBusFrom.getText()).toString().trim();
        String Bus_to = Objects.requireNonNull(editTextBusTo.getText()).toString().trim();

        if (Bus_No.isEmpty()) {
            editTextBusNum.setError("Bus number cannot be empty");
            pd.dismiss();
            return;
        }
        if (Bus_time.isEmpty()) {
            editTextBusTime.setError("Bus time cannot be empty");
            pd.dismiss();
            return;
        }
        if (Bus_from.isEmpty()) {
            editTextBusFrom.setError("Source location cannot be empty");
            pd.dismiss();
            return;
        }
        if (Bus_to.isEmpty()) {
            editTextBusFrom.setError("Destination location cannot be empty");
            pd.dismiss();
            return;
        }


        pd.setTitle("Please Wait!");
        pd.setMessage("Uploading...");
        pd.setCancelable(false);
        pd.show();


        HashMap<String, Object> data = new HashMap<>();
        data.put("busNo", Bus_No);
        data.put("hostel", hostel);
        data.put("time", Bus_time);
        data.put("from", Bus_from);
        data.put("to", Bus_to);
        data.put("uniqueKey", uniqueKey);

        databaseReference.child("NIT Buses").child(hostel).child(Objects.requireNonNull(uniqueKey)).setValue(data)
                .addOnCompleteListener(task -> {
                    pd.dismiss();
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Bus data uploaded successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Failed to upload bus data", Toast.LENGTH_SHORT).show();
                });
    }

}
