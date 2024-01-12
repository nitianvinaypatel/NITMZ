package com.example.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.nitmz.adapter.TimeTablePageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


public class TimeTable extends AppCompatActivity {


    private FirebaseFirestore firestore;

    private String  course , semester , branch;
    private TextView branchName;
    private TextView semesterName;

    private TextView courseName;

    private ImageButton back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        back = findViewById(R.id.backbtn);
        courseName = findViewById(R.id.courseName);
        branchName = findViewById(R.id.branchName);
        semesterName = findViewById(R.id.semesterName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // Initialize ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager_tt);
        TimeTablePageAdapter pagerAdapter = new TimeTablePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Initialize TabLayout and connect it with ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout_tt);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab text
        tabLayout.getTabAt(0).setText("Sunday");
        tabLayout.getTabAt(1).setText("Monday");
        tabLayout.getTabAt(2).setText("Tuesday");
        tabLayout.getTabAt(3).setText("Wednesday");
        tabLayout.getTabAt(4).setText("Thursday");
        tabLayout.getTabAt(5).setText("Friday");
        tabLayout.getTabAt(6).setText("Saturday");

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
        firestore = FirebaseFirestore.getInstance();
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

                            course = document.getString("course");
                            semester = document.getString("semester");
                            branch = document.getString("branch");


                            // Set the Text
                            courseName.setText(course);
                            semesterName.setText(semester);
                            branchName.setText(branch);


                        }
                    }
                }
            });
        }

    }
}

