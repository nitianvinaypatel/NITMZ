package com.example.nitmz.AdminSection;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nitmz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UpdateMessMenu extends AppCompatActivity {

    private ImageButton back;

    private TextView hostel_name;
    private ProgressDialog pd;

    private AutoCompleteTextView autoCompleteDay;
    private String[] days = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private Button Update_btn;
    private String hostel;
    private String selectedDay;

    private TextInputEditText breakfast, lunch, snaks, dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mess_menu);

        back = findViewById(R.id.backbtn);
        breakfast = findViewById(R.id.breakfast);
        lunch = findViewById(R.id.lunch);
        snaks = findViewById(R.id.snacks);
        dinner = findViewById(R.id.dinner);
        hostel_name = findViewById(R.id.hostelName);

        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Updating...");

        Update_btn = findViewById(R.id.Update_mm_btn);

        autoCompleteDay = findViewById(R.id.autoComplete_day);

        fetchDataFromFireStore();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateMessMenu.this, R.layout.dropdown_item, days);
        autoCompleteDay.setAdapter(adapter);

        autoCompleteDay.setOnItemClickListener((adapterView, view, i, l) -> {
            // String items = adapterView.getItemAtPosition(i).toString();
        });

        back.setOnClickListener(view -> onBackPressed());


        Update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    updates.put("breakfast", breakfast.getText().toString());
                    updates.put("lunch", lunch.getText().toString());
                    updates.put("snacks", snaks.getText().toString());
                    updates.put("dinner", dinner.getText().toString());

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
            }
        });


    }
    private void fetchDataFromFireStore() {
        // Initialize Firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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
                            // Get user data
                            hostel = document.getString("hostel");

                            if (hostel != null) {

                                hostel_name.setText(hostel);

                            } else {
                                // Handle the case where some data is null
                            }
                        }
                    }
                }
            });
        }
    }
    private boolean validateInputs() {
        if (TextUtils.isEmpty(breakfast.getText().toString())) {
            breakfast.setError("Breakfast Cannot be Empty.");
            return false;
        }
        if (TextUtils.isEmpty(lunch.getText().toString())) {
            lunch.setError("Lunch Cannot be Empty.");
            return false;
        }
        if (TextUtils.isEmpty(snaks.getText().toString())) {
            snaks.setError("Snaks Cannot be Empty.");
            return false;
        }
        if (TextUtils.isEmpty(dinner.getText().toString())) {
            dinner.setError("Dinner Cannot be Empty.");
            return false;
        }
        return true;
    }
}