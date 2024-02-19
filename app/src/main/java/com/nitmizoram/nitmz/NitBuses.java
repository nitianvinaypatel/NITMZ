package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nitmizoram.nitmz.adapter.NitBusesPagerAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class NitBuses extends AppCompatActivity {

    private String hostel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nit_buses);

        ImageButton back = findViewById(R.id.backbtn);
        TextView date_name = findViewById(R.id.date);
        TextView day_name = findViewById(R.id.day);

        // Get the current date
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }

        // Create a formatter for the desired date format
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }

        // Format the date using the formatter
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDate = currentDate.format(formatter);
        }

        String dayName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        day_name.setText(dayName);
        date_name.setText(formattedDate);


        back.setOnClickListener(view -> onBackPressed());

        // Initialize ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);
        NitBusesPagerAdapter pagerAdapter = new NitBusesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Initialize TabLayout and connect it with ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab text
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("BH-1");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("BH-2");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("BH-3");
        Objects.requireNonNull(tabLayout.getTabAt(3)).setText("BH-4");
        Objects.requireNonNull(tabLayout.getTabAt(4)).setText("GH-1");


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

                        hostel = document.getString("hostel");

                        if (hostel != null) {
                            switch (hostel) {
                                case "Boy`s Hostel-1":
                                    viewPager.setCurrentItem(0);
                                    break;
                                case "Boy`s Hostel-2":
                                    viewPager.setCurrentItem(1);
                                    break;
                                case "Boy`s Hostel-3":
                                    viewPager.setCurrentItem(2);
                                    break;
                                case "Boy`s Hostel-4":
                                    viewPager.setCurrentItem(3);
                                    break;
                                case "Girl`s Hostel":
                                    viewPager.setCurrentItem(4);
                                    break;
                            }
                        }

                    }
                }
            });
        }



    }
}