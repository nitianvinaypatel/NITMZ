package com.example.nitmz.AdminSection;

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
import com.example.nitmz.DeleteEvents;
import com.example.nitmz.R;
import com.example.nitmz.model.FacultyData;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEvents extends AppCompatActivity {

    private ImageButton back;
    private CircleImageView Fimage;
    private TextInputEditText Fname,Fdesignation,Fbranch,Fcontacts;
    private Button Add_btn;
    private ImageView deleteF;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private String imageUrlFaculty,name_f, design_f, branch_f,contact_f, unikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        back = findViewById(R.id.backbtn);
        Fimage = findViewById(R.id.select_image);
        Fname = findViewById(R.id.F_name);
        Fdesignation =findViewById(R.id.F_designation);
        Fbranch = findViewById(R.id.F_department);
        Fcontacts =findViewById(R.id.F_contacts);
        Add_btn = findViewById(R.id.Add_btn);
        deleteF = findViewById(R.id.DeleteF);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading...");
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

        deleteF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteEvents.class);
                startActivity(intent);
            }
        });

        unikey = databaseReference.child("Events").push().getKey();

        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                name_f = Fname.getText().toString().trim();
                design_f =Fdesignation.getText().toString().trim();
                branch_f =Fbranch.getText().toString().trim();
                contact_f = Fcontacts.getText().toString().trim();

                if (validateInputs()) {
                    progressDialog.show();
                    uploadImage();
                }

            }


            private void uploadImage() {
                if (selectedImageUri != null) {
                    StorageReference reference = storage.getReference().child("Events").child(unikey);

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

                databaseReference.child("Events").child(unikey).setValue(facultyData)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(AddEvents.this, "Event details Added Successfully", Toast.LENGTH_SHORT).show();
                            // If needed, you can also navigate to a new activity or perform other actions here
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(AddEvents.this, "Failed to add event details", Toast.LENGTH_SHORT).show();
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
                    Fdesignation.setError("Venue is Required");
                    return false;
                }
                if (branch_f.isEmpty()) {
                    Fbranch.setError("Date is Required");
                    return false;
                }
                if (contact_f.isEmpty()) {
                    Fcontacts.setError("Time is Required");
                    return false;
                }
                return true;
            }
        });
    }
}