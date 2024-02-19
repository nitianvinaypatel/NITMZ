package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateStaff extends AppCompatActivity {

    private CircleImageView Fimage;
    private TextInputEditText Fname,Fdesignation,Fbranch,Fcontacts;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private String imageUrlFaculty,name_f, design_f, branch_f,contact_f;
    private String Ukey;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);


        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("fimageUrl");
        String name = intent.getStringExtra("name");
        String designation = intent.getStringExtra("designation");
        String department = intent.getStringExtra("department");
        String contact = intent.getStringExtra("contact");
        Ukey = intent.getStringExtra("key");


        ImageButton back = findViewById(R.id.backbtn);
        Fimage = findViewById(R.id.select_image);
        Fname = findViewById(R.id.F_name);
        Fdesignation =findViewById(R.id.F_designation);
        Fbranch = findViewById(R.id.F_department);
        Fcontacts =findViewById(R.id.F_contacts);
        Button add_btn = findViewById(R.id.Add_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Picasso.get().load(imageUrl).placeholder(R.drawable.user_profile_icon).into(Fimage);
        Fname.setText(name);
        Fdesignation.setText(designation);
        Fbranch.setText(department);
        Fcontacts.setText(contact);


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
                    // New image is selected, upload the new image
                    StorageReference reference = storage.getReference().child("Staff").child(Ukey);

                    reference.putFile(selectedImageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                imageUrlFaculty = uri.toString();
                                uploadData();
                            });
                        }
                    });
                } else {
                    // No new image selected, fetch the image URL from the database
                    databaseReference.child("Staff").child(Ukey).child("fimageUrl").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            imageUrlFaculty = String.valueOf(task.getResult().getValue());
                            uploadData();
                        } else {
                            // Handle the case where fetching image URL fails
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateStaff.this, "Failed to fetch image URL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


            private void uploadData() {

                // Assuming you want to update imageUrl, name, designation, department, and contact
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("fimageUrl", imageUrlFaculty);
                updateData.put("name", name_f);
                updateData.put("designation", design_f);
                updateData.put("department", branch_f);
                updateData.put("contact", contact_f);
                updateData.put("key",Ukey);


                databaseReference.child("Staff").child(Ukey).updateChildren(updateData)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateStaff.this, "Staff details Updated Successfully", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateStaff.this, "Failed to update staff details", Toast.LENGTH_SHORT).show();
                        });
            }




            private boolean validateInputs() {

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