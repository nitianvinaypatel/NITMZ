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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nitmizoram.nitmz.DeleteNotice;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.model.NoticeData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class UploadNotice extends AppCompatActivity {

    private ImageView selected_image;

    private FirebaseStorage storage;
    private TextInputEditText message_box;

    private Uri selectedImageUri;

    private String imageUrlNotice;
    private DatabaseReference databaseReference;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private String message;
    private ProgressDialog progressDialog;
    private String Sender_name, rollNum, imageURl, unikey, date, time;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        ImageButton back = findViewById(R.id.backbtn);
        message_box = findViewById(R.id.messageBox_notice);
        selected_image = findViewById(R.id.select_image);
        Button upload_btn = findViewById(R.id.Upload_btn);
        ImageView delete_notice = findViewById(R.id.DeleteNotice);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        storage = FirebaseStorage.getInstance();

        back.setOnClickListener(view -> onBackPressed());

        selected_image.setOnClickListener((view -> ImagePicker.with(this).createIntent(intent1 -> {
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
                            .into(selected_image);

                    // Now you can proceed to upload the selectedImageUri to the database
                    // using the steps mentioned in the previous response.
                }
            }
        });

        delete_notice.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeleteNotice.class);
            startActivity(intent);
        });

        unikey = databaseReference.child("Notices").push().getKey();

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateInputs()) {
                    progressDialog.show();
                    uploadImage();
                }

            }

            private void uploadImage() {
                if (selectedImageUri != null) {
                    StorageReference reference = storage.getReference().child("Notices").child(unikey);

                    reference.putFile(selectedImageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                imageUrlNotice = uri.toString();
                                // Continue with the rest of your upload process or call uploadData() here
                                fetchDataFromFireStore();
                            });
                        }
                    });
                } else {
                    // Handle the case where selectedImageUri is null (show a message, log, etc.)
                    Toast.makeText(getApplicationContext(), "Selected image is null", Toast.LENGTH_SHORT).show();
                }
            }

            private void uploadData() {

                Calendar calDate = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat currDate = new SimpleDateFormat("dd-MM-yy");
                date = currDate.format(calDate.getTime());

                Calendar calTime = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat currTime = new SimpleDateFormat("hh:mm a");
                time = currTime.format(calTime.getTime());

                NoticeData noticeData = new NoticeData(imageUrlNotice, message, Sender_name, rollNum, imageURl, date, time, unikey);

                databaseReference.child("Notices").child(unikey).setValue(noticeData)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UploadNotice.this, "Notice Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            // If needed, you can also navigate to a new activity or perform other actions here
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            onBackPressed();
                            Toast.makeText(UploadNotice.this, "Failed to upload notice data", Toast.LENGTH_SHORT).show();
                        });
            }

            private void fetchDataFromFireStore() {
                // Initialize Firestore
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DocumentReference userRef = firestore.collection("users").document(userId);

                    userRef.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Sender_name = document.getString("name");
                                rollNum = document.getString("rollNo");
                                imageURl = document.getString("image");

                                uploadData();
                            }
                        }
                    });
                }
            }

            private boolean validateInputs() {
                if (selectedImageUri == null || selectedImageUri.toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select an Image", Toast.LENGTH_SHORT).show();
                    return false;
                }
                message = Objects.requireNonNull(message_box.getText()).toString().trim();
                if (message.isEmpty()) {
                    message_box.setError("Message Is Required");
                    return false;
                }
                return true;
            }
        });

    }
}