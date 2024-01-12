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

public class UpdateSociety extends AppCompatActivity {

    private ImageButton back;
    private CircleImageView Pimage;
    private TextInputEditText Pname,Prollno ,Pphone, Pemail;
    private Button Updatebtn;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    private AutoCompleteTextView autoCompleteTextView_society;

    private AutoCompleteTextView autoCompleteTextView_post;

    private String name_P,rollno_P,phone_P,email_P,society_name, post_name ,imageUrlP;


    private final String[] society ={"CSS","EES","ECE","MES"};
    private final String[] post ={"President","Secretary","Technical Head","Marketing Head","Cultural Head","Event Management Head","Graphic Designing Head","Content Head","Student Coordinator"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_society);


        back = findViewById(R.id.backbtn);
        Pimage = findViewById(R.id.select_image);
        Pname = findViewById(R.id.P_name);
        Prollno =findViewById(R.id.P_rollno);
        Pphone = findViewById(R.id.P_phone);
        Pemail =findViewById(R.id.P_email);
        Updatebtn = findViewById(R.id.Update_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading...");
        storage = FirebaseStorage.getInstance();

        back.setOnClickListener(view -> onBackPressed());


        autoCompleteTextView_post = findViewById(R.id.autoComplete_designation);
        autoCompleteTextView_society = findViewById(R.id.autoComplete_society);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateSociety.this, R.layout.dropdown_item, society);
        autoCompleteTextView_society.setAdapter(adapter);

        autoCompleteTextView_society.setOnItemClickListener((adapterView, view, i, l) -> {
//                String items = adapterView.getItemAtPosition(i).toString();
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(UpdateSociety.this, R.layout.dropdown_item, post);
        autoCompleteTextView_post.setAdapter(adapter2);

        autoCompleteTextView_post.setOnItemClickListener((adapterView, view, i, l) -> {
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

                society_name = autoCompleteTextView_society.getText().toString().trim();
                post_name = autoCompleteTextView_post.getText().toString().trim();
                name_P = Pname.getText().toString().trim();
                rollno_P =Prollno.getText().toString().trim();
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
                    StorageReference reference = storage.getReference().child("Society").child(society_name).child(post_name).child("imageUrl");

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
                    databaseReference.child("Society").child(society_name).child(post_name).child("imageUrl").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String imageUrlFromDatabase = String.valueOf(task.getResult().getValue());
                            imageUrlP = imageUrlFromDatabase;
                            uploadData();
                        } else {
                            // Handle the case where fetching image URL fails
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateSociety.this, "Failed to fetch image URL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


            private void uploadData() {

                TnPData tnPData = new TnPData(imageUrlP,name_P,rollno_P,phone_P,email_P);

                databaseReference.child("Society").child(society_name).child(post_name).setValue(tnPData)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateSociety.this, "Society details Added Successfully", Toast.LENGTH_SHORT).show();
                            // If needed, you can also navigate to a new activity or perform other actions here
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UpdateSociety.this, "Failed to add Society details", Toast.LENGTH_SHORT).show();
                        });
            }




            private boolean validateInputs() {

                if(society_name.equals("Select Society")){
                    Toast.makeText(getApplicationContext(),"Please Select Society",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(post_name.equals("Select Designation")){
                    Toast.makeText(getApplicationContext(),"Please Select Designation",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (name_P.isEmpty()) {
                    Pname.setError("Name is Required");
                    return false;
                }
                if (rollno_P.isEmpty()) {
                    Prollno.setError("Designation is Required");
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