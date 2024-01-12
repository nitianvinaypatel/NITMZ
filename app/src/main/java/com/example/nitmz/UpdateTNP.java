package com.example.nitmz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nitmz.model.TnPData;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateTNP extends AppCompatActivity {

    private ImageButton back;
    private CircleImageView Pimage;
    private TextInputEditText Pname,Pdepartment ,Pphone, Pemail;
    private Button Updatebtn;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    private AutoCompleteTextView autoCompleteTextView_TNPpost;

    private String name_P,department_P,phone_P,email_P, post_name ,imageUrlP;


    private final String[] TNPpost ={"TnP Officer","Chief Coordinator","Coordinator 1","Coordinator 2","Coordinator 3","Coordinator 4","Coordinator 5"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tnp);

        back = findViewById(R.id.backbtn);
        Pimage = findViewById(R.id.select_image);
        Pname = findViewById(R.id.P_name);
        Pdepartment =findViewById(R.id.P_department);
        Pphone = findViewById(R.id.P_phone);
        Pemail =findViewById(R.id.P_email);
        Updatebtn = findViewById(R.id.Update_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading...");
        storage = FirebaseStorage.getInstance();

        back.setOnClickListener(view -> onBackPressed());

        autoCompleteTextView_TNPpost = findViewById(R.id.autoComplete_designationtnp);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateTNP.this, R.layout.dropdown_item, TNPpost);
        autoCompleteTextView_TNPpost.setAdapter(adapter);

        autoCompleteTextView_TNPpost.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });


        Pimage.setOnClickListener((view -> ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent(intent1 -> {
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
                            .into(Pimage);

                    // Now you can proceed to upload the selectedImageUri to the database
                    // using the steps mentioned in the previous response.
                }
            }
        });



        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                post_name = autoCompleteTextView_TNPpost.getText().toString().trim();
                name_P = Pname.getText().toString().trim();
                department_P =Pdepartment.getText().toString().trim();
                phone_P =Pphone.getText().toString().trim();
                email_P = Pemail.getText().toString().trim();

                if (validateInputs()) {
                    progressDialog.show();
                    uploadImage();
                }

            }


            private void uploadImage() {
                if (selectedImageUri != null) {
                    // New image is selected, upload the new image
                    StorageReference reference = storage.getReference().child("TnP Cell").child(post_name).child("imageUrl");

                    reference.putFile(selectedImageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                imageUrlP = uri.toString();
                                uploadData();
                            });
                        }
                    });
                } else {
                    // No new image selected, fetch the image URL from the database
                    databaseReference.child("TnP Cell").child(post_name).child("imageUrl").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String imageUrlFromDatabase = String.valueOf(task.getResult().getValue());
                            imageUrlP = imageUrlFromDatabase;
                            uploadData();
                        } else {
                            // Handle the case where fetching image URL fails
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateTNP.this, "Failed to fetch image URL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


            private void uploadData() {

                TnPData tnPData = new TnPData(imageUrlP,name_P,department_P,phone_P,email_P);

                databaseReference.child("TnP Cell").child(post_name).setValue(tnPData)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateTNP.this, "TnP details Added Successfully", Toast.LENGTH_SHORT).show();
                            // If needed, you can also navigate to a new activity or perform other actions here
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateTNP.this, "Failed to add TnP details", Toast.LENGTH_SHORT).show();
                        });
            }




            private boolean validateInputs() {

                if(post_name.equals("Select Designation")){
                    Toast.makeText(getApplicationContext(),"Please Select Designation",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (name_P.isEmpty()) {
                    Pname.setError("Name is Required");
                    return false;
                }
                if (department_P.isEmpty()) {
                    Pdepartment.setError("Designation is Required");
                    return false;
                }
                if (phone_P.isEmpty()) {
                    Pphone.setError("Department is Required");
                    return false;
                }
                if (email_P.isEmpty()) {
                    Pemail.setError("Contact is Required");
                    return false;
                }
                return true;
            }
        });

    }
}