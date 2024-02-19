package com.nitmizoram.nitmz.AdminSection;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.nitmizoram.nitmz.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateMessMenu extends AppCompatActivity {

    private TextView hostel_name;
    private ProgressDialog pd;

    private AutoCompleteTextView autoCompleteDay;
    private final String[] days = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private String hostel;
    private String selectedDay;

    private TextInputEditText breakfast, lunch, snaks, dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mess_menu);

        ImageButton back = findViewById(R.id.backbtn);
        breakfast = findViewById(R.id.breakfast);
        lunch = findViewById(R.id.lunch);
        snaks = findViewById(R.id.snacks);
        dinner = findViewById(R.id.dinner);
        hostel_name = findViewById(R.id.hostelName);

        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Updating...");
        pd.setCancelable(false);

        Button update_btn = findViewById(R.id.Update_mm_btn);

        autoCompleteDay = findViewById(R.id.autoComplete_day);

        fetchDataFromFireStore();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateMessMenu.this, R.layout.dropdown_item, days);
        autoCompleteDay.setAdapter(adapter);

        autoCompleteDay.setOnItemClickListener((adapterView, view, i, l) -> {
            // String items = adapterView.getItemAtPosition(i).toString();
        });

        back.setOnClickListener(view -> onBackPressed());


        update_btn.setOnClickListener(view -> {
            selectedDay = autoCompleteDay.getText().toString().trim();
            if ("Select the Day".equals(selectedDay)) {
                Toast.makeText(getApplicationContext(), "Please Select the Day", Toast.LENGTH_SHORT).show();
                return;  // Do not proceed if the day is not selected
            }
            if (validateInputs()) {
                pd.show();
                // Get the Firestore instance
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                // Create a Map to hold the updated values
                Map<String, Object> updates = new HashMap<>();
                updates.put("breakfast", Objects.requireNonNull(breakfast.getText()).toString());
                updates.put("lunch", Objects.requireNonNull(lunch.getText()).toString());
                updates.put("snacks", Objects.requireNonNull(snaks.getText()).toString());
                updates.put("dinner", Objects.requireNonNull(dinner.getText()).toString());

                // Create a reference to the Firestore document for the specific period
                firestore.collection("Mess Menu").document("Hostels").collection(hostel).document(selectedDay)
                        .set(updates, SetOptions.merge())  // Use set with merge option
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                onBackPressed();
                                Toast.makeText(UpdateMessMenu.this, "Mess Menu data Updated Successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                pd.dismiss();
                                onBackPressed();
                                Toast.makeText(UpdateMessMenu.this, "Error updating data!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
    private void fetchDataFromFireStore() {
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
                        // Get user data
                        hostel = document.getString("hostel");

                        if (hostel != null) {

                            hostel_name.setText(hostel);

                        }  // Handle the case where some data is null

                    }
                }
            });
        }
    }
    private boolean validateInputs() {
        if (TextUtils.isEmpty(Objects.requireNonNull(breakfast.getText()).toString())) {
            breakfast.setError("Breakfast Cannot be Empty.");
            return false;
        }
        if (TextUtils.isEmpty(Objects.requireNonNull(lunch.getText()).toString())) {
            lunch.setError("Lunch Cannot be Empty.");
            return false;
        }
        if (TextUtils.isEmpty(Objects.requireNonNull(snaks.getText()).toString())) {
            snaks.setError("Snaks Cannot be Empty.");
            return false;
        }
        if (TextUtils.isEmpty(Objects.requireNonNull(dinner.getText()).toString())) {
            dinner.setError("Dinner Cannot be Empty.");
            return false;
        }
        return true;
    }
}