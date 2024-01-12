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

import java.util.HashMap;
import java.util.Map;

public class UpdateTimeTable extends AppCompatActivity {

    private ImageButton back;
    private AutoCompleteTextView autoCompleteDay;
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private TextView course_name, semester_name, branch_name;

    private TextInputEditText subject1, subject2, subject3, subject4, subject5, faculty1, faculty2, faculty3, faculty4, faculty5;

    private Button Update_btn;
    private String course, semester, branch, path;

    private String selectedDay;
    private ProgressDialog pd;
    private  int  val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_table);

        back = findViewById(R.id.backbtn);
        course_name = findViewById(R.id.courseName);
        semester_name = findViewById(R.id.semesterName);
        branch_name = findViewById(R.id.branchName);

        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Updating...");

        subject1 = findViewById(R.id.subject1);
        faculty1 = findViewById(R.id.faculty1);
        subject2 = findViewById(R.id.subject2);
        faculty2 = findViewById(R.id.faculty2);
        subject3 = findViewById(R.id.subject3);
        faculty3 = findViewById(R.id.faculty3);
        subject4 = findViewById(R.id.subject4);
        faculty4 = findViewById(R.id.faculty4);
        subject5 = findViewById(R.id.subject5);
        faculty5 = findViewById(R.id.faculty5);

        Update_btn = findViewById(R.id.Update_tt_btn);

        autoCompleteDay = findViewById(R.id.autoComplete_day);

        fetchDataFromFireStore();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateTimeTable.this, R.layout.dropdown_item, days);
        autoCompleteDay.setAdapter(adapter);

        autoCompleteDay.setOnItemClickListener((adapterView, view, i, l) -> {
            // String items = adapterView.getItemAtPosition(i).toString();
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

                    // Iterate over periods (Period1 to Period5)
                    for (int i = 1; i <= 5; i++) {
                        String periodKey = "Period" + i;

                        // Get subject and faculty values from EditText
                        String subjectValue = ((TextInputEditText) findViewById(getResources().getIdentifier("subject" + i, "id", getPackageName()))).getText().toString().trim();
                        String facultyValue = ((TextInputEditText) findViewById(getResources().getIdentifier("faculty" + i, "id", getPackageName()))).getText().toString().trim();

                        // Create a Map for each subject and faculty pair
                        Map<String, Object> subjectFacultyMap = new HashMap<>();
                        subjectFacultyMap.put("subject", subjectValue);
                        subjectFacultyMap.put("faculty", facultyValue);

                        // Create a reference to the Firestore document for the specific period
                        DocumentReference periodRef = firestore.collection("Time Table").document(path).collection(selectedDay).document(periodKey);

                        // Set the subject and faculty data for the specific period
                        int finalI = i;
                        periodRef.set(subjectFacultyMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pd.dismiss();

                                            val = finalI;
                                            if(val ==5){
                                                Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }

                                        } else {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(), "Failed to update data", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                    }
                                });
                    }
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
                            semester = document.getString("semester");
                            course = document.getString("course");
                            branch = document.getString("branch");

                            if (semester != null && course != null && branch != null) {
                                course_name.setText(course);
                                semester_name.setText(semester);
                                branch_name.setText(branch);

                                path = course + "/" + semester + "/" + branch + "/";
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
        if (TextUtils.isEmpty(subject1.getText().toString())) {
            subject1.setError("Subject Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(faculty1.getText().toString())) {
            faculty1.setError("Faculty Name is Required");
            return false;
        }
        if (TextUtils.isEmpty(subject2.getText().toString())) {
            subject2.setError("Subject Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(faculty2.getText().toString())) {
            faculty2.setError("Faculty Name is Required");
            return false;
        }
        if (TextUtils.isEmpty(subject3.getText().toString())) {
            subject3.setError("Subject Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(faculty3.getText().toString())) {
            faculty3.setError("Faculty Name is Required");
            return false;
        }
        if (TextUtils.isEmpty(subject4.getText().toString())) {
            subject4.setError("Subject Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(faculty4.getText().toString())) {
            faculty4.setError("Faculty Name is Required");
            return false;
        }
        if (TextUtils.isEmpty(subject5.getText().toString())) {
            subject5.setError("Subject Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(faculty5.getText().toString())) {
            faculty5.setError("Faculty Name is Required");
            return false;
        }
        return true;
    }
}
