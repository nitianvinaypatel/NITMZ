package com.nitmizoram.nitmz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Developer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        ImageView linkedin = findViewById(R.id.vp_linkedin);
        ImageView github = findViewById(R.id.vp_github);
        ImageView insta = findViewById(R.id.vp_insta);
        ImageView fb = findViewById(R.id.vp_fb);
        ImageView thread = findViewById(R.id.vp_x);
        ImageView call = findViewById(R.id.vp_call);
        ImageView whatsapp = findViewById(R.id.vp_whatsapp);
        ImageView mail = findViewById(R.id.vp_mail);

        ImageButton backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> onBackPressed());

        linkedin.setOnClickListener(view -> {
            String url = "https://www.linkedin.com/in/nitianvinaypatel/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        github.setOnClickListener(view -> {
            String url = "https://github.com/nitianvinaypatel";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        insta.setOnClickListener(view -> {
            String url = "https://www.instagram.com/nitian_vinaypatel/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        fb.setOnClickListener(view -> {
            String url = "https://www.facebook.com/people/Vinay-Patel/pfbid0BXfP1MADRHrZrVs9ueUXY3yxBdP7Bh9MCkS1DTWdbijok8xgZ77mF5WNBvACYFsLl/?ref=xav_ig_profile_web";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        thread.setOnClickListener(view -> {
            String url = "https://www.threads.net/@nitian_vinaypatel";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        call.setOnClickListener(view -> {
            String phoneNumber = "+919305346457";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Handle case where no dialer app is available
                Toast.makeText(getApplicationContext(), "No dialer app found", Toast.LENGTH_SHORT).show();
            }
        });

        whatsapp.setOnClickListener(view -> openWhatsAppChat());
        mail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:nitianvinaypatel@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject"); // You can add a subject if needed

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Handle case where no email app is available
                Toast.makeText(getApplicationContext(), "No email app found", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void openWhatsAppChat() {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + "+919305346457";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            // Handle exceptions, e.g., if the device doesn't have WhatsApp installed
            Toast.makeText(Developer.this, "Error opening WhatsApp.", Toast.LENGTH_SHORT).show();
        }
    }
}