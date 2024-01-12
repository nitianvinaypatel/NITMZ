package com.example.nitmz.AdminSection;

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
import com.example.nitmz.DeleteNotice;
import com.example.nitmz.R;
import com.example.nitmz.model.NoticeData;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity {

    private ImageButton back;
    private ImageView Notice_image;

    private FirebaseStorage storage;
    private TextInputEditText message_box;
    private Button Upload_btn;

    private Uri selectedImageUri;

    private String imageUrlNotice;
    private ImageView delete_notice;
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

        back = findViewById(R.id.backbtn);
        Notice_image = findViewById(R.id.select_image);
        message_box = findViewById(R.id.messageBox_notice);
        Upload_btn = findViewById(R.id.Upload_btn);
        delete_notice = findViewById(R.id.DeleteNotice);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Uploading...");
        storage = FirebaseStorage.getInstance();

        back.setOnClickListener(view -> onBackPressed());

        Notice_image.setOnClickListener((view -> ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent(intent1 -> {
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
                            .into(Notice_image);

                    // Now you can proceed to upload the selectedImageUri to the database
                    // using the steps mentioned in the previous response.
                }
            }
        });

        delete_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteNotice.class);
                startActivity(intent);
            }
        });

        unikey = databaseReference.child("Notices").push().getKey();

        Upload_btn.setOnClickListener(new View.OnClickListener() {
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
                SimpleDateFormat currDate = new SimpleDateFormat("dd-MM-yy");
                date = currDate.format(calDate.getTime());

                Calendar calTime = Calendar.getInstance();
                SimpleDateFormat currTime = new SimpleDateFormat("hh:mm a");
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
                message = message_box.getText().toString().trim();
                if (message.isEmpty()) {
                    message_box.setError("Message Is Required");
                    return false;
                }
                return true;
            }
        });

    }
}