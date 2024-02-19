package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class UpdateBus extends AppCompatActivity {

    private TextInputEditText editTextBusNum ,editTextBusTime,editTextBusFrom, editTextBusTo;
    private TextView hostel_name;
    private String Unique_key;
    private ProgressDialog pd;
    private DatabaseReference reference;
    private String hostel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bus);

        pd = new ProgressDialog(this);

        Button update_btn = findViewById(R.id.Update_btn);
        hostel_name = findViewById(R.id.hostelName);

        Intent intent = getIntent();
        String busNum = intent.getStringExtra("busNo");
        Unique_key = intent.getStringExtra("uniqueKey");
        String bus_time = intent.getStringExtra("busTime");
        String bus_from = intent.getStringExtra("busFrom");
        String bus_to = intent.getStringExtra("busTo");
        hostel = intent.getStringExtra("hostel");

        ImageButton back = findViewById(R.id.backbtn);

        editTextBusNum = findViewById(R.id.busNum);
        editTextBusTime = findViewById(R.id.busTime);
        editTextBusFrom = findViewById(R.id.busFrom);
        editTextBusTo = findViewById(R.id.busTo);
        reference = FirebaseDatabase.getInstance().getReference();


        editTextBusNum.setText(busNum);
        editTextBusTime.setText(bus_time);
        editTextBusFrom.setText(bus_from);
        editTextBusTo.setText(bus_to);
        hostel_name.setText(hostel);


        update_btn.setOnClickListener(view -> updateBusData());
        back.setOnClickListener(view -> onBackPressed());
    }

    private void updateBusData() {
        pd.setTitle("Please Wait!");
        pd.setMessage("Uploading...");
        pd.setCancelable(false);
        pd.show();
        String updatedBusNum = Objects.requireNonNull(editTextBusNum.getText()).toString().trim();
        String updatedBusTime = Objects.requireNonNull(editTextBusTime.getText()).toString().trim();
        String updatedFrom =  Objects.requireNonNull(editTextBusFrom.getText()).toString().trim();
        String updatedTo =  Objects.requireNonNull(editTextBusTo.getText()).toString().trim();

        if (updatedBusNum.isEmpty()) {
            editTextBusNum.setError("Bus number cannot be empty");
            pd.dismiss();
            return;
        }
        if (updatedBusTime.isEmpty()) {
            editTextBusTime.setError("Bus time cannot be empty");
            pd.dismiss();
            return;
        }
        if (updatedFrom.isEmpty()) {
            editTextBusFrom.setError("Source location cannot be empty");
            pd.dismiss();
            return;
        }
        if (updatedTo.isEmpty()) {
            editTextBusTo.setError("Destination location cannot be empty");
            pd.dismiss();
            return;
        }

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("busNo", updatedBusNum);
        updateMap.put("time", updatedBusTime);
        updateMap.put("from", updatedFrom);
        updateMap.put("to", updatedTo);

        reference.child("NIT Buses").child(hostel).child(Unique_key).updateChildren(updateMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Bus data updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Failed to update bus data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}