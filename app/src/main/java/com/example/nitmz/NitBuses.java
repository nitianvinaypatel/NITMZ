package com.example.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.nitmz.adapter.NitBusesPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class NitBuses extends AppCompatActivity {

    private ImageButton back;
    private TextView date_name;
    private String hostel;
    private TextView day_name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nit_buses);

        back = findViewById(R.id.backbtn);
        date_name = findViewById(R.id.date);
        day_name = findViewById(R.id.day);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Create a formatter for the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Format the date using the formatter
        String formattedDate = currentDate.format(formatter);

        String dayName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        day_name.setText(dayName);
        date_name.setText(formattedDate);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Initialize ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);
        NitBusesPagerAdapter pagerAdapter = new NitBusesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Initialize TabLayout and connect it with ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab text
        tabLayout.getTabAt(0).setText("BH-1");
        tabLayout.getTabAt(1).setText("BH-2");
        tabLayout.getTabAt(2).setText("BH-3");
        tabLayout.getTabAt(3).setText("BH-4");
        tabLayout.getTabAt(4).setText("GH-1");


        // Initialize Firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(userId);
            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {

                            hostel = document.getString("hostel");

                            if (hostel != null) {
                                if (hostel.equals("Boy`s Hostel-1")) {
                                    viewPager.setCurrentItem(0);
                                } else if (hostel.equals("Boy`s Hostel-2")) {
                                    viewPager.setCurrentItem(1);
                                } else if (hostel.equals("Boy`s Hostel-3")) {
                                    viewPager.setCurrentItem(2);
                                } else if (hostel.equals("Boy`s Hostel-4")) {
                                    viewPager.setCurrentItem(3);
                                } else if (hostel.equals("Girl`s Hostel")) {
                                    viewPager.setCurrentItem(4);
                                }
                            }

                        }
                    }
                }
            });
        }



    }
}