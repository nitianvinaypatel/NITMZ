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

public class UploadFees extends AppCompatActivity {


    private ImageView selectPdf;
    private TextView selectedFileName;
    private TextInputEditText titleName;

    private String title;

    private ImageButton back;

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


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_fees);

        selectPdf = findViewById(R.id.select_pdf);
        selectedFileName = findViewById(R.id.selected_file_name);
        titleName = findViewById(R.id.title_name);
        Uploadbtn = findViewById(R.id.Upload_btn);
        back = findViewById(R.id.backbtn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);


        selectPdf.setOnClickListener(view -> openGallary());

        path = "Fees";

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



            if(title.isEmpty()){
                titleName.setError("Title is Required");
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
                    onBackPressed();
                    Toast.makeText(getApplicationContext(),"Pdf uploaded successfully",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    onBackPressed();
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
                        cursor = UploadFees.this.getContentResolver().query(pdfData,null,null,null,null);
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