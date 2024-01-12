package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// ... (your imports remain the same)

public class UpdateBus extends AppCompatActivity {

    private TextInputEditText editTextBusNum;
    private EditText editTextTime;
    private AutoCompleteTextView autoCompleteTextView_from;
    private AutoCompleteTextView autoCompleteTextView_to;
    private String Unique_key;
    private DatabaseReference reference;
    private String hostel;
    private Button updatae_btn;
    private String  formattedTime;
    private String location[] = {"Hostel", "College"};

    private ImageButton back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bus);

        updatae_btn = findViewById(R.id.Update_btn);

        Intent intent = getIntent();
        String busNum = intent.getStringExtra("busNo");
        Unique_key = intent.getStringExtra("uniqueKey");
        String bus_time = intent.getStringExtra("busTime");
        hostel = intent.getStringExtra("hostel");

        back = findViewById(R.id.backbtn);

        editTextBusNum = findViewById(R.id.busNum);
        editTextTime = findViewById(R.id.editTextTime);
        autoCompleteTextView_from = findViewById(R.id.autoComplete_from);
        autoCompleteTextView_to = findViewById(R.id.autoComple_to);
        reference = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateBus.this, R.layout.dropdown_item, location);
        autoCompleteTextView_from.setAdapter(adapter);

        autoCompleteTextView_from.setOnItemClickListener((adapterView, view, i, l) -> {
            // Handle item selection if needed
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(UpdateBus.this, R.layout.dropdown_item, location);
        autoCompleteTextView_to.setAdapter(adapter2);

        autoCompleteTextView_to.setOnItemClickListener((adapterView, view, i, l) -> {
            // Handle item selection if needed
        });

        editTextBusNum.setText(busNum);
        editTextTime.setText(bus_time);

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        updatae_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBusData();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void updateBusData() {
        String updatedBusNum = editTextBusNum.getText().toString().trim();
        String updatedBusTime = formattedTime != null ? formattedTime.trim() : "";
        String updatedFrom = autoCompleteTextView_from.getText().toString().trim();
        String updatedTo = autoCompleteTextView_to.getText().toString().trim();

        if (updatedBusNum.isEmpty()) {
            editTextBusNum.setError("Bus number cannot be empty");
            return;
        }

        if (updatedBusTime.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please Select Time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (updatedFrom.equals("From")) {
            Toast.makeText(getApplicationContext(),"Source location cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (updatedTo.equals("To")) {
            Toast.makeText(getApplicationContext(),"Destination location cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to update only the necessary fields
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("busNo", updatedBusNum);
        updateMap.put("time", updatedBusTime);
        updateMap.put("from", updatedFrom);
        updateMap.put("to", updatedTo);

        reference.child("NIT Buses").child(hostel).child(Unique_key).updateChildren(updateMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Bus data updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Failed to update bus data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showTimePickerDialog() {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Process the selected time (e.g., update the EditText)
                        updateTime(selectedHour, selectedMinute);
                    }
                },
                hourOfDay,
                minute,
                false // 24-hour format (change to true for 12-hour format with AM/PM)
        );

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    private void updateTime(int selectedHour, int selectedMinute) {
        // Determine AM/PM
        String amPm;
        if (selectedHour < 12) {
            amPm = "AM";
        } else {
            amPm = "PM";
            // Convert to 12-hour format for PM
            selectedHour = selectedHour - 12;
        }

        // Handle the case where selectedHour is 0 (midnight)
        if (selectedHour == 0) {
            selectedHour = 12; // Set to 12 for 12:00 AM
        }

        // Format the time and update the EditText
        formattedTime = String.format(Locale.getDefault(), "%02d:%02d %s", selectedHour, selectedMinute, amPm);
        editTextTime.setText(formattedTime);
    }

}