package com.nitmizoram.nitmz.AdminSection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nitmizoram.nitmz.R;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class UploadCalender extends AppCompatActivity {

    private TextView selectedFileName;
    private TextInputEditText titleName;

    private String title;

    private final int REQ =1;
    private Uri pdfData;

    private  String pdfname;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String downloadUrl = "";

    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_calender);

        ImageView selectPdf = findViewById(R.id.select_pdf);
        selectedFileName = findViewById(R.id.selected_file_name);
        titleName = findViewById(R.id.title_name);
        Button uploadbtn = findViewById(R.id.Upload_btn);
        ImageButton back = findViewById(R.id.backbtn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);
        selectPdf.setOnClickListener(view -> openGallary());

        uploadbtn.setOnClickListener(view -> {
            title = Objects.requireNonNull(titleName.getText()).toString();
            if (validateInputs()) {
                uploadPdf();
            }
        });
        back.setOnClickListener(view -> onBackPressed());
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
        pd.setCancelable(false);
        pd.show();
        StorageReference reference = storageReference.child("Academic Calender/"+pdfname+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            uriTask.addOnSuccessListener(uri -> {
                downloadUrl = uri.toString();
                uploadData(downloadUrl); // Pass the updated downloadUrl
            });
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        });

    }

    private void uploadData(String ignoredS) {
        String uniqeKey = databaseReference.child("pdf").push().getKey();

        HashMap<Object, Object> data = new HashMap<>();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child("Academic Calender/").child(Objects.requireNonNull(uniqeKey)).setValue(data).addOnCompleteListener(task -> {
            pd.dismiss();
            Toast.makeText(getApplicationContext(),"Pdf uploaded successfully",Toast.LENGTH_SHORT).show();
            titleName.setText("");
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(getApplicationContext(),"Failed to upload pdf",Toast.LENGTH_SHORT).show();
        });

    }

    private void openGallary(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf Title"),REQ);
    }

    @SuppressLint({"Range", "Recycle"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ && resultCode ==RESULT_OK){
            pdfData = Objects.requireNonNull(data).getData();

            if(Objects.requireNonNull(pdfData).toString().startsWith("content://")){
                try {
                    Cursor cursor;
                    cursor = UploadCalender.this.getContentResolver().query(pdfData,null,null,null,null);
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