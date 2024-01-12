package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nitmz.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private TextInputEditText mName, mRollno, mPhone, mEmail, mPassword;

    private AutoCompleteTextView autoCompleteTextView_course;
    private AutoCompleteTextView autoCompleteTextView_branch;
    private AutoCompleteTextView autoCompleteTextView_semester;

    private AutoCompleteTextView autoCompleteTextView_hostel;



   private final String[] Course ={"Bachelor of Technology","Master of Technology","Doctor of Philosophy"};

    private final String[] branch = {"CSE","ECE","EEE","ME","CE","MA","HSS","Physics","Chemistry","Mathematics","Economics",};

    private final String[] semester = {"1st Semester","2nd Semester","3rd Semester","4th Semester","5th Semester","6th Semester","7th Semester","8th Semester","9th Semester","10th Semester"};

    private final String[] hostel = {"Boy`s Hostel-1","Boy`s Hostel-2","Boy`s Hostel-3","Boy`s Hostel-4","Girl`s Hostel"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        autoCompleteTextView_course = findViewById(R.id.autoComplete_course);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Register.this, R.layout.dropdown_item, Course);
        autoCompleteTextView_course.setAdapter(adapter);

        autoCompleteTextView_course.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        autoCompleteTextView_branch = findViewById(R.id.autoComplete_branch);

        adapter = new ArrayAdapter<>(Register.this,R.layout.dropdown_item,branch);
        autoCompleteTextView_branch.setAdapter(adapter);

        autoCompleteTextView_branch.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        autoCompleteTextView_semester = findViewById(R.id.autoComplete_semester);

        adapter = new ArrayAdapter<>(Register.this,R.layout.dropdown_item,semester);
        autoCompleteTextView_semester.setAdapter(adapter);

        autoCompleteTextView_semester.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });


        autoCompleteTextView_hostel = findViewById(R.id.autoComplete_hostel);

        adapter = new ArrayAdapter<>(Register.this,R.layout.dropdown_item,hostel);
        autoCompleteTextView_hostel.setAdapter(adapter);

        autoCompleteTextView_hostel.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });





        mName = findViewById(R.id.name);
        mRollno = findViewById(R.id.rollNo);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        Button buttonReg = findViewById(R.id.registerbtn);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        TextView goToLogin = findViewById(R.id.loginNow);

        ImageView logo_image = findViewById(R.id.logo_image);
        TextView logo_name = findViewById(R.id.logo_name);


        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo_name.setAnimation(bottomAnim);
        logo_image.setAnimation(bottomAnim);

        goToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(view -> {
            if (validateInputs()) {
                performRegistration();
            }
        });
    }

    private boolean validateInputs() {
        String name = Objects.requireNonNull(mName.getText()).toString().trim();
        String rollNo = Objects.requireNonNull(mRollno.getText()).toString().trim().toUpperCase();
        String phone = Objects.requireNonNull(mPhone.getText()).toString().trim();
        String email = Objects.requireNonNull(mEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(mPassword.getText()).toString().trim();

        String selectedCourse = autoCompleteTextView_course.getText().toString().trim();
        String selectedBranch = autoCompleteTextView_branch.getText().toString().trim();
        String selectedSemester = autoCompleteTextView_semester.getText().toString().trim();
        String selectedHostel = autoCompleteTextView_hostel.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            mName.setError("Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(rollNo)) {
            mRollno.setError("Roll Number is Required.");
            return false;
        }
        if(selectedCourse.equals("Select Your Course")){
            Toast.makeText(getApplicationContext(),"Please Select Your Course",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedBranch.equals("Select Your Branch")){
            Toast.makeText(getApplicationContext(),"Please Select Your Branch",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedSemester.equals("Select Your Semester")){
            Toast.makeText(getApplicationContext(),"Please Select Your Semester",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedHostel.equals("Select Your Hostel")){
            Toast.makeText(getApplicationContext(),"Please Select Your Hostel",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError("Phone Number is Required.");
            return false;
        }
        if (phone.length() != 10) {
            mPhone.setError("Phone Number must be of 10 digit");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is Required.");
            return false;
        }
        if (password.length() < 6) {
            mPassword.setError("Password must be >= 6 characters.");
            return false;
        }

        return true;
    }

    private void performRegistration() {
        // Display a progress dialog
        ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String email = Objects.requireNonNull(mEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(mPassword.getText()).toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // Dismiss the progress dialog
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        saveUserDataToFirestore();
                    } else {
                        Toast.makeText(Register.this, "Registration failed. " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void saveUserDataToFirestore() {
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String name = Objects.requireNonNull(mName.getText()).toString().trim();
        String rollNo = Objects.requireNonNull(mRollno.getText()).toString().trim().toUpperCase();
        String phone = Objects.requireNonNull(mPhone.getText()).toString().trim();
        String email = Objects.requireNonNull(mEmail.getText()).toString().trim();

        String selectedCourse = autoCompleteTextView_course.getText().toString().trim();
        String selectedBranch = autoCompleteTextView_branch.getText().toString().trim();
        String selectedSemester = autoCompleteTextView_semester.getText().toString().trim();
        String selectedHostel = autoCompleteTextView_hostel.getText().toString().trim();
        String isAdmin = "false";


        // Create a User object
        User user = new User(userID,name, rollNo, phone, email, selectedCourse, selectedBranch, selectedSemester, selectedHostel,isAdmin);

        // Save the user data to Firestore
        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.set(user).addOnSuccessListener(unused -> {

            // Send email verification
            sendEmailVerification();

            // Display a success dialog
            showVerificationDialog();

        }).addOnFailureListener(e -> Toast.makeText(Register.this, "Registration Failed"+e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Verification Email Sent Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Register.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void showVerificationDialog() {
        // Display a dialog box informing the user that a verification email has been sent
        // You can customize the dialog content as needed
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Email Verification!")
                .setMessage("A verification email has been sent to your email address. Please check your email and verify your account.")
                .setPositiveButton("Ok", (dialogInterface, i) -> {

                    dialogInterface.dismiss();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    Toast.makeText(Register.this, "Please Verifiy Your Email Before Logging In", Toast.LENGTH_LONG).show();

                })
                .show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Navigate to the Login activity
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }



}

