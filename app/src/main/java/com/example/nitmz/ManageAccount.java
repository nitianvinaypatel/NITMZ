package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class ManageAccount extends AppCompatActivity {

    Uri selectedImageUri;

    ActivityResultLauncher<Intent> imagePickerLauncher;



//    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    private String userId;

    ProgressDialog progressDialog;

    private TextInputEditText manage_name, manage_rollno, manage_phone;

    private ImageView manage_profile;

    private AutoCompleteTextView manage_course;
    private AutoCompleteTextView manage_branch;
    private AutoCompleteTextView manage_semester;
    private AutoCompleteTextView manage_hostel;

    private final String[] Course ={"Bachelor of Technology","Master of Technology","Doctor of Philosophy"};

    private final String[] branch = {"CSE","ECE","EEE","ME","CE","MA","HSS","Physics","Chemistry","Mathematics","Economics"};

    private final String[] semester = {"1st Semester","2nd Semester","3rd Semester","4th Semester","5th Semester","6th Semester","7th Semester","8th Semester","9th Semester","10th Semester"};

    private final String[] hostel = {"Boy`s Hostel-1","Boy`s Hostel-2","Boy`s Hostel-3","Boy`s Hostel-4","Girl`s Hostel"};


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String rollNo = intent.getStringExtra("rollNo");
        String phone = intent.getStringExtra("phone");
        String imageUrl = intent.getStringExtra("imageUrl");


        Button update = findViewById(R.id.Update_btn);
        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();



//        progressBar = findViewById(R.id.progressBar);
        ImageView back_btn = findViewById(R.id.backbtn);
        manage_name = findViewById(R.id.manage_name);
        manage_rollno = findViewById(R.id.manage_rollNo);
        manage_phone = findViewById(R.id.manage_phone);
        manage_profile = findViewById(R.id.manage_profile);
        manage_branch = findViewById(R.id.manage_branch);
        manage_semester = findViewById(R.id.manage_semester);
        manage_hostel = findViewById(R.id.manage_hostel);
        manage_name.setText(name);
        manage_rollno.setText(rollNo);
        manage_phone.setText(phone);
        manage_course = findViewById(R.id.manage_course);

        progressDialog = new ProgressDialog(ManageAccount.this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Updating Profile...");
        progressDialog.setCancelable(false);


        manage_profile.setOnClickListener((view -> ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent(intent1 -> {
            imagePickerLauncher.launch((intent1));
            return null;
        })));

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null && data.getData() != null) {
                    selectedImageUri = data.getData();

                    // Corrected the variable name here from imageUri to selectedImageUri
                    Glide.with(getApplicationContext())
                            .load(selectedImageUri)
                            .apply(RequestOptions.circleCropTransform())
                            .into(manage_profile);

                    // Now you can proceed to upload the selectedImageUri to the database
                    // using the steps mentioned in the previous response.
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(ManageAccount.this, R.layout.dropdown_item, Course);
        manage_course.setAdapter(adapter);

//        manage_course.setText(course);


        adapter = new ArrayAdapter<>(ManageAccount.this, R.layout.dropdown_item, branch);
        manage_branch.setAdapter(adapter);

//        manage_branch.setText(Branch);


        adapter = new ArrayAdapter<>(ManageAccount.this, R.layout.dropdown_item, semester);
        manage_semester.setAdapter(adapter);

//        manage_semester.setText(Semester);


        adapter = new ArrayAdapter<>(ManageAccount.this, R.layout.dropdown_item, hostel);
        manage_hostel.setAdapter(adapter);

//        manage_hostel.setText(Hostel);

        manage_course.setOnItemClickListener((adapterView, view, i, l) -> {
//                String selectedCourse = adapterView.getItemAtPosition(i).toString();
        });


        manage_branch.setOnItemClickListener((adapterView, view, i, l) -> {
//                String selectedBranch = adapterView.getItemAtPosition(i).toString();
        });


        manage_semester.setOnItemClickListener((adapterView, view, i, l) -> {
//                String selectedSemester = adapterView.getItemAtPosition(i).toString();
        });


        manage_hostel.setOnItemClickListener((adapterView, view, i, l) -> {
//                String selectedHostel = adapterView.getItemAtPosition(i).toString();
        });


        back_btn.setOnClickListener(view -> onBackPressed());

        // ...

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    updateUserData();
                }


            }

            private void updateUserData() {
                progressDialog.show();
                String name = Objects.requireNonNull(manage_name.getText()).toString().trim();
                String rollNo = Objects.requireNonNull(manage_rollno.getText()).toString().trim().toUpperCase();
                String phone = Objects.requireNonNull(manage_phone.getText()).toString().trim();
                String branch = manage_branch.getText().toString();
                String semester = manage_semester.getText().toString();
                String course = manage_course.getText().toString();
                String hostel = manage_hostel.getText().toString();

                // Check if a new image is selected
                if (selectedImageUri == null) {
                    // If no new image is selected, fetch the existing image URL
                    firestore.collection("users").document(userId)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String existingImageUrl = document.getString("image");
                                        // Update the user data in Firestore
                                        updateUserDataInFirestore(existingImageUrl, name, rollNo, phone, course, branch, semester, hostel);
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ManageAccount.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(ManageAccount.this, "Error fetching document", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // If a new image is selected, proceed with image upload and update user data
                    StorageReference reference = storage.getReference().child("Profiles").child(userId);
                    reference.putFile(selectedImageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                // Update the user data in Firestore
                                updateUserDataInFirestore(imageUrl, name, rollNo, phone, course, branch, semester, hostel);
                            }).addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(ManageAccount.this, "Error getting download URL", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(ManageAccount.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private void updateUserDataInFirestore(String imageUrl, String name, String rollNo, String phone, String course, String branch, String semester, String hostel) {
                firestore.collection("users").document(userId)
                        .update("image", imageUrl, "name", name, "rollNo", rollNo,
                                "phone", phone, "course", course,
                                "branch", branch, "semester", semester, "hostel", hostel)
                        .addOnCompleteListener(task -> {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ManageAccount.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                Intent intent13 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent13);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(ManageAccount.this, "Error updating profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            }


            private boolean validateInputs() {
                String name = Objects.requireNonNull(manage_name.getText()).toString().trim();
                String rollNo = Objects.requireNonNull(manage_rollno.getText()).toString().trim().toUpperCase();
                String phone = Objects.requireNonNull(manage_phone.getText()).toString().trim();
                String branch = manage_branch.getText().toString();
                String semester = manage_semester.getText().toString();
                String course = manage_course.getText().toString();
                String hostel = manage_hostel.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    manage_name.setError("Name is Required.");
                    progressDialog.dismiss();
                    return false;
                }
                if (TextUtils.isEmpty(rollNo)) {
                    manage_rollno.setError("Roll Number is Required.");
                    progressDialog.dismiss();
                    return false;
                }
                if(course.equals("Select Your Course")){
                    Toast.makeText(getApplicationContext(),"Please Select Your Course",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(branch.equals("Select Your Branch")){
                    Toast.makeText(getApplicationContext(),"Please Select Your Branch",Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(semester.equals("Select Your Semester")){
                    Toast.makeText(getApplicationContext(),"Please Select Your Semester",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(hostel.equals("Select Your Hostel")){
                    Toast.makeText(getApplicationContext(),"Please Select Your Hostel",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(phone)) {
                    manage_phone.setError("Phone Number is Required.");
                    progressDialog.dismiss();
                    return false;
                }
                if (phone.length() != 10) {
                    manage_phone.setError("Phone Number must be of 10 digit");
                    progressDialog.dismiss();
                    return false;
                }
                return true;

            }
        });



        // Now you can use these values as needed
        manage_name.setText(name);
        manage_rollno.setText(rollNo);
        manage_phone.setText(phone);

// Load the image using Glide
        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(manage_profile);


    }

}
