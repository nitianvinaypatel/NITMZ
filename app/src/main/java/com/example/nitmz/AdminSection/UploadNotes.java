package com.example.nitmz.AdminSection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nitmz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class UploadNotes extends AppCompatActivity {

    private ImageView selectPdf;
    private TextView selectedFileName;
    private TextInputEditText titleName;

    private String title;

    private ImageButton back;


    private AutoCompleteTextView autoCompleteTextView_course;
    private AutoCompleteTextView autoCompleteTextView_branch;
    private AutoCompleteTextView autoCompleteTextView_semester;

    private Button Uploadbtn;

    private final int REQ =1;
    private Uri pdfData;

    private  String pdfname;
    private Bitmap bitmap;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String downloadUrl = "";

    private ProgressDialog pd;

    private String path;

    private final String[] Course ={"Bachelor of Technology","Master of Technology","Doctor of Philosophy"};

    private final String[] branch = {"CSE","ECE","EEE","ME","CE","MA","HSS","Physics","Chemistry","Mathematics","Economics",};

    private final String[] semester = {"1st Semester","2nd Semester","3rd Semester","4th Semester","5th Semester","6th Semester","7th Semester","8th Semester","9th Semester","10th Semester"};





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        selectPdf = findViewById(R.id.select_pdf);
        selectedFileName = findViewById(R.id.selected_file_name);
        titleName = findViewById(R.id.title_name);
        Uploadbtn = findViewById(R.id.Upload_btn);
        back = findViewById(R.id.backbtn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);

        autoCompleteTextView_course = findViewById(R.id.autoComplete_course);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UploadNotes.this, R.layout.dropdown_item, Course);
        autoCompleteTextView_course.setAdapter(adapter);

        autoCompleteTextView_course.setOnItemClickListener((adapterView, view, i, l) -> {
            // Update path when course is selected
            String selectedCourse = autoCompleteTextView_course.getText().toString().trim();
            String selectedBranch = autoCompleteTextView_branch.getText().toString().trim();
            String selectedSemester = autoCompleteTextView_semester.getText().toString().trim();
            path = "Notes/" + selectedCourse + "/" + selectedSemester + "/" + selectedBranch + "/";
        });

        autoCompleteTextView_branch = findViewById(R.id.autoComplete_branch);

        adapter = new ArrayAdapter<>(UploadNotes.this,R.layout.dropdown_item,branch);
        autoCompleteTextView_branch.setAdapter(adapter);

        autoCompleteTextView_branch.setOnItemClickListener((adapterView, view, i, l) -> {
            // Update path when branch is selected
            String selectedCourse = autoCompleteTextView_course.getText().toString().trim();
            String selectedBranch = autoCompleteTextView_branch.getText().toString().trim();
            String selectedSemester = autoCompleteTextView_semester.getText().toString().trim();
            path = "Notes/" + selectedCourse + "/" + selectedSemester + "/" + selectedBranch + "/";
        });

        autoCompleteTextView_semester = findViewById(R.id.autoComplete_semester);

        adapter = new ArrayAdapter<>(UploadNotes.this,R.layout.dropdown_item,semester);
        autoCompleteTextView_semester.setAdapter(adapter);

        autoCompleteTextView_semester.setOnItemClickListener((adapterView, view, i, l) -> {
            // Update path when semester is selected
            String selectedCourse = autoCompleteTextView_course.getText().toString().trim();
            String selectedBranch = autoCompleteTextView_branch.getText().toString().trim();
            String selectedSemester = autoCompleteTextView_semester.getText().toString().trim();
            path = "Notes/" + selectedCourse + "/" + selectedSemester + "/" + selectedBranch + "/";
        });


        selectPdf.setOnClickListener(view -> openGallary());

        Uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleName.getText().toString();
                if (validateInputs()) {
                    uploadPdf();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private boolean validateInputs() {

        String selectedCourse = autoCompleteTextView_course.getText().toString().trim();
        String selectedBranch = autoCompleteTextView_branch.getText().toString().trim();
        String selectedSemester = autoCompleteTextView_semester.getText().toString().trim();


        if(title.isEmpty()){
            titleName.setError("Title is Required");
            return false;
        }if(selectedCourse.equals("Select Your Course")){
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
        }if (pdfData ==null) {
            Toast.makeText(getApplicationContext(),"Please Select Pdf",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    private void uploadPdf() {
        pd.setTitle("Please Wait!");
        pd.setMessage("Uploading...");
        pd.show();
        StorageReference reference = storageReference.child(path+pdfname+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUrl = uri.toString();
                        uploadData(downloadUrl); // Pass the updated downloadUrl
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String s) {
        String uniqeKey = databaseReference.child("pdf").push().getKey();

        HashMap data = new HashMap<>();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child(path).child(uniqeKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Pdf uploaded successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UploadNotes.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Failed to upload pdf",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallary(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf Title"),REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ && resultCode ==RESULT_OK){
            pdfData = data.getData();

            if(pdfData.toString().startsWith("content://")){
                try {
                    Cursor cursor = null;
                    cursor = UploadNotes.this.getContentResolver().query(pdfData,null,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()){
                        pdfname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (pdfData.toString().startsWith("file://")) {
                pdfname = new File(pdfData.toString()).getName();

            }
            selectedFileName.setText(pdfname);


        }
    }
}