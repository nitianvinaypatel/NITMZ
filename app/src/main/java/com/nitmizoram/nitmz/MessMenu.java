package com.nitmizoram.nitmz;

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
import com.nitmizoram.nitmz.adapter.MessMenuPagerAdapter;

import java.util.Calendar;
import java.util.Objects;

public class MessMenu extends AppCompatActivity {

    private String  hostel;
    private TextView hostelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        hostelName = findViewById(R.id.hostelName);
        ImageButton back = findViewById(R.id.backbtn);

        back.setOnClickListener(view -> onBackPressed());

        // Initialize ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);
        MessMenuPagerAdapter pagerAdapter = new MessMenuPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Initialize TabLayout and connect it with ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab text
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Sunday");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Monday");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("Tuesday");
        Objects.requireNonNull(tabLayout.getTabAt(3)).setText("Wednesday");
        Objects.requireNonNull(tabLayout.getTabAt(4)).setText("Thursday");
        Objects.requireNonNull(tabLayout.getTabAt(5)).setText("Friday");
        Objects.requireNonNull(tabLayout.getTabAt(6)).setText("Saturday");

        // Get the current day
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        // Set the initial tab based on the current day
        switch (currentDay) {
            case Calendar.SUNDAY:
                viewPager.setCurrentItem(0);
                break;
            case Calendar.MONDAY:
                viewPager.setCurrentItem(1);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(2);
                break;
            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(3);
                break;
            case Calendar.THURSDAY:
                viewPager.setCurrentItem(4);
                break;
            case Calendar.FRIDAY:
                viewPager.setCurrentItem(5);
                break;
            case Calendar.SATURDAY:
                viewPager.setCurrentItem(6);
                break;
        }

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

                        hostelName.setText(hostel);

                    }
                }
            });
        }
    }
}

