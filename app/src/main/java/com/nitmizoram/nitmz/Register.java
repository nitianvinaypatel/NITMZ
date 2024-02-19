package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nitmizoram.nitmz.model.User;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private TextInputEditText mName, mEmail, mPassword;
    Button buttonReg;

    private  ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.registerbtn);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        TextView goToLogin = findViewById(R.id.loginNow);
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);

        ImageView logo_image = findViewById(R.id.logo_image);
        TextView logo_name = findViewById(R.id.logo_name);


        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        logo_name.setAnimation(bottomAnim);
        logo_image.setAnimation(topAnim);

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
        String email = Objects.requireNonNull(mEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(mPassword.getText()).toString().trim();

        if (TextUtils.isEmpty(name)) {
            mName.setError("Name is Required.");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return false;
        }if (!email.contains("@nitmz.ac.in")) {
            mEmail.setError("Enter your institute`s e-mail only.");
            return false;
        }
        if (email.length()!=21) {
            mEmail.setError("Enter your institute`s e-mail only.");
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

        progressDialog.show();

        String email = Objects.requireNonNull(mEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(mPassword.getText()).toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

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
        String email = Objects.requireNonNull(mEmail.getText()).toString().trim();
        String phone = "Update Me";
        String rollNo = email.split("@")[0].toUpperCase();
        String prefixCourse = email.substring(0, 2);
        String Semester = "null";
        String Course;
        if (prefixCourse.equalsIgnoreCase("bt")) {
            Course = "Bachelor of Technology";
        } else if (prefixCourse.equalsIgnoreCase("mt")) {
            Course = "Master of Technology";
        } else {
            Course = "Doctor of Philosophy";
        }
        String prefixBranch = email.substring(4,6);
        String Branch = prefixBranch.toUpperCase();
        String Hostel ="Update Me";
        String isAdmin = "false";


        // Create a User object
        User user = new User(userID,name, rollNo, phone, email, Course, Branch, Semester, Hostel,isAdmin);

        // Save the user data to Firestore
        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.set(user).addOnSuccessListener(unused -> {

            // Send email verification
            sendEmailVerification();

            progressDialog.dismiss();

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
                    finish();
                    Toast.makeText(Register.this, "Verifiy Your Email Before Logging In", Toast.LENGTH_LONG).show();

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

