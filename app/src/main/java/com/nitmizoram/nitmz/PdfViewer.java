package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PdfViewer extends AppCompatActivity {

    private PDFView pdfView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        String url = getIntent().getStringExtra("pdfUrl");
        pdfView = findViewById(R.id.pdfView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        new PdfDownloadTask().execute(url);
    }

    @SuppressLint("StaticFieldLeak")
    private class PdfDownloadTask extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

                if (httpsURLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            progressDialog.dismiss();

            if (inputStream != null) {
                pdfView.fromStream(inputStream).load();
            }  // Handle the case where the response is null or an error occurred

        }
    }
}
