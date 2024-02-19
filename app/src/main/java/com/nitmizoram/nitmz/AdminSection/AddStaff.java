package com.nitmizoram.nitmz.AdminSection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nitmizoram.nitmz.DeleteStaff;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.model.FacultyData;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStaff extends AppCompatActivity {

    private CircleImageView Fimage;
    private TextInputEditText Fname,Fdesignation,Fbranch,Fcontacts;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private String imageUrlFaculty,name_f, design_f, branch_f,contact_f, unikey;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        ImageButton back = findViewById(R.id.backbtn);
        Fimage = findViewById(R.id.select_image);
        Fname = findViewById(R.id.F_name);
        Fdesignation =findViewById(R.id.F_designation);
        Fbranch = findViewById(R.id.F_department);
        Fcontacts =findViewById(R.id.F_contacts);
        Button add_btn = findViewById(R.id.Add_btn);
        ImageView deleteF = findViewById(R.id.DeleteF);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        storage = FirebaseStorage.getInstance();

        back.setOnClickListener(view -> onBackPressed());

        Fimage.setOnClickListener((view -> ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent(intent1 -> {
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
                            .into(Fimage);

                    // Now you can proceed to upload the selectedImageUri to the database
                    // using the steps mentioned in the previous response.
                }
            }
        });

        deleteF.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeleteStaff.class);
            startActivity(intent);
        });

        unikey = databaseReference.child("Staff").push().getKey();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                name_f = Objects.requireNonNull(Fname.getText()).toString().trim();
                design_f = Objects.requireNonNull(Fdesignation.getText()).toString().trim();
                branch_f = Objects.requireNonNull(Fbranch.getText()).toString().trim();
                contact_f = Objects.requireNonNull(Fcontacts.getText()).toString().trim();

                if (validateInputs()) {
                    progressDialog.show();
                    uploadImage();
                }

            }


            private void uploadImage() {
                if (selectedImageUri != null) {
                    StorageReference reference = storage.getReference().child("Staff").child(unikey);

                    reference.putFile(selectedImageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                imageUrlFaculty = uri.toString();
                                uploadData();
                            });
                        }
                    });
                } else {
                    // Handle the case where selectedImageUri is null (show a message, log, etc.)
                    Toast.makeText(getApplicationContext(), "Selected image is null", Toast.LENGTH_SHORT).show();
                }
            }

            private void uploadData() {

                FacultyData facultyData = new FacultyData(imageUrlFaculty,name_f,design_f,branch_f,contact_f,unikey);

                databaseReference.child("Staff").child(unikey).setValue(facultyData)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(AddStaff.this, "Staff details added Successfully", Toast.LENGTH_SHORT).show();
                            // If needed, you can also navigate to a new activity or perform other actions here
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(AddStaff.this, "Failed to add staff details", Toast.LENGTH_SHORT).show();
                        });
            }


            private boolean validateInputs() {

                if (selectedImageUri == null || selectedImageUri.toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select an Image", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (name_f.isEmpty()) {
                    Fname.setError("Name is Required");
                    return false;
                }
                if (design_f.isEmpty()) {
                    Fdesignation.setError("Designation is Required");
                    return false;
                }
                if (branch_f.isEmpty()) {
                    Fbranch.setError("Department is Required");
                    return false;
                }
                if (contact_f.isEmpty()) {
                    Fcontacts.setError("Contact is Required");
                    return false;
                }
                return true;
            }
        });
    }
}