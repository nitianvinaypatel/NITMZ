package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class AddBuses extends AppCompatActivity {

    private TextInputEditText editTextBusNum;
    private EditText editTextTime;
    private AutoCompleteTextView autoCompleteTextView_from;
    private AutoCompleteTextView autoCompleteTextView_to;

    private DatabaseReference databaseReference;
    private  String formattedTime;
    private ImageButton back;

    private FirebaseFirestore firestore;

    private String  hostel;

    private ProgressDialog pd;
    private TextView hostelName;

    private Button add_btn;
    private String location[] = {"Hostel","College"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buses);

        editTextBusNum = findViewById(R.id.busNum);
        editTextTime = findViewById(R.id.editTextTime);
        autoCompleteTextView_from = findViewById(R.id.autoComplete_from);
        autoCompleteTextView_to = findViewById(R.id.autoComple_to);
        add_btn = findViewById(R.id.Add_btn);
        back = findViewById(R.id.backbtn);
        hostelName = findViewById(R.id.hostelName);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);



        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(userId);
            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {

                            hostel = document.getString("hostel");

                            hostelName.setText(hostel);

                        }
                    }
                }
            });
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBuses.this, R.layout.dropdown_item, location);
        autoCompleteTextView_from.setAdapter(adapter);

        autoCompleteTextView_from.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(AddBuses.this, R.layout.dropdown_item, location);
        autoCompleteTextView_to.setAdapter(adapter2);

        autoCompleteTextView_to.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        // Set up click listener on the EditText
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    uploadData();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // Modify the method to upload bus data to Firebase Realtime Database
    private void uploadData() {

        String uniqueKey = databaseReference.child("busData").push().getKey();

        String Bus_No = editTextBusNum.getText().toString().trim();
        String Bus_time = formattedTime != null ? formattedTime.trim() : "";
        String Bus_from = autoCompleteTextView_from.getText().toString().trim();
        String Bus_to = autoCompleteTextView_to.getText().toString().trim();

        if (Bus_No.isEmpty()) {
            editTextBusNum.setError("Bus number cannot be empty");
            pd.dismiss();
            return;
        }

        if (Bus_time.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please Select Time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Bus_from.equals("From")) {
            Toast.makeText(getApplicationContext(),"Source location cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Bus_to.equals("To")) {
            Toast.makeText(getApplicationContext(),"Destination location cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }


        pd.setTitle("Please Wait!");
        pd.setMessage("Uploading...");
        pd.show();


        HashMap<String, Object> data = new HashMap<>();
        data.put("busNo", Bus_No);
        data.put("hostel", hostel);
        data.put("time", Bus_time);
        data.put("from", Bus_from);
        data.put("to", Bus_to);
        data.put("uniqueKey", uniqueKey);

        databaseReference.child("NIT Buses").child(hostel).child(uniqueKey).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Bus data uploaded successfully", Toast.LENGTH_SHORT).show();
                        // Add your navigation logic here
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Failed to upload bus data", Toast.LENGTH_SHORT).show();
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

        // Format the time and update the EditText
        formattedTime = String.format("%02d:%02d %s", selectedHour, selectedMinute, amPm);
        editTextTime.setText(formattedTime);
    }

}
