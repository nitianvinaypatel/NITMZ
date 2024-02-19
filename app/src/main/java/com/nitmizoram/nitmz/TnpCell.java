package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TnpCell extends AppCompatActivity {

    private CircleImageView tnpOfficer_dp;
    private CircleImageView cheifCordinator_dp;
    private CircleImageView coordinator1_dp;
    private CircleImageView coordinator2_dp;
    private CircleImageView coordinator3_dp;
    private CircleImageView coordinator4_dp;
    private CircleImageView coordinator5_dp;

    private TextView tnpOfficer_name;
    private TextView cheifCordinator_name;
    private TextView coordinator1_name;
    private TextView coordinator2_name;
    private TextView coordinator3_name;
    private TextView coordinator4_name;
    private TextView coordinator5_name;

    private TextView tnpOfficer_branch;
    private TextView cheifCordinator_branch;
    private TextView coordinator1_branch;
    private TextView coordinator2_branch;
    private TextView coordinator3_branch;
    private TextView coordinator4_branch;
    private TextView coordinator5_branch;

    private TextView tnpOfficer_phone;
    private TextView cheifCordinator_phone;
    private TextView coordinator1_phone;
    private TextView coordinator2_phone;
    private TextView coordinator3_phone;
    private TextView coordinator4_phone;
    private TextView coordinator5_phone;

    private TextView tnpOfficer_email;
    private TextView cheifCordinator_email;
    private TextView coordinator1_email;
    private TextView coordinator2_email;
    private TextView coordinator3_email;
    private TextView coordinator4_email;
    private TextView coordinator5_email;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnp_cell);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        ImageButton backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> onBackPressed());

        tnpOfficer_dp = findViewById(R.id.tnp_officer_dp);
        tnpOfficer_name = findViewById(R.id.tnp_officer_name);
        tnpOfficer_branch = findViewById(R.id.tnp_officer_branch);
        tnpOfficer_phone = findViewById(R.id.tnp_officer_phone);
        tnpOfficer_email = findViewById(R.id.tnp_officer_email);


        cheifCordinator_dp = findViewById(R.id.chief_coordinator_dp);
        cheifCordinator_name = findViewById(R.id.chief_coordinator_name);
        cheifCordinator_branch = findViewById(R.id.chief_coordinator_branch);
        cheifCordinator_phone = findViewById(R.id.chief_coordinator_phone);
        cheifCordinator_email = findViewById(R.id.chief_coordinator_email);


        coordinator1_dp = findViewById(R.id.coodinator1_dp);
        coordinator1_name = findViewById(R.id.coordinator1_name);
        coordinator1_phone = findViewById(R.id.coodinator1_phone);
        coordinator1_branch = findViewById(R.id.coordinator1_branch);
        coordinator1_email = findViewById(R.id.coordinator1_email);

        coordinator2_dp = findViewById(R.id.coordinator2_dp);
        coordinator2_name = findViewById(R.id.coordinator2_name);
        coordinator2_phone = findViewById(R.id.coordinator2_phone);
        coordinator2_branch = findViewById(R.id.coordinator2_branch);
        coordinator2_email = findViewById(R.id.coordinator2_email);

        coordinator3_dp = findViewById(R.id.coordinator3_dp);
        coordinator3_name = findViewById(R.id.coordinator3_name);
        coordinator3_phone = findViewById(R.id.coordinator3_phone);
        coordinator3_branch = findViewById(R.id.coordinator3_branch);
        coordinator3_email = findViewById(R.id.coordinator3_email);


        coordinator4_dp = findViewById(R.id.coordinator4_dp);
        coordinator4_name = findViewById(R.id.coordinator4_name);
        coordinator4_phone = findViewById(R.id.coordinator4_phone);
        coordinator4_branch = findViewById(R.id.coordinator4_branch);
        coordinator4_email = findViewById(R.id.coordinator4_email);

        coordinator5_dp = findViewById(R.id.coordinator5_dp);
        coordinator5_name = findViewById(R.id.coordinator5_name);
        coordinator5_phone = findViewById(R.id.coordinator5_phone);
        coordinator5_branch = findViewById(R.id.coordinator5_branch);
        coordinator5_email = findViewById(R.id.coordinator5_email);



        TextView walkingTextView = findViewById(R.id.walkingTextView);
        // Create a TranslateAnimation to move the TextView from right to left
        Animation walkingAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );

        // Set the animation properties
        walkingAnimation.setDuration(10000); // Set the duration of the animation in milliseconds
        walkingAnimation.setRepeatMode(Animation.RESTART); // Set the repeat mode to restart
        walkingAnimation.setRepeatCount(Animation.INFINITE); // Set the repeat count to infinite

        walkingTextView.startAnimation(walkingAnimation);

        DatabaseReference tnpOfficer = database.getReference("TnP Cell/TnP Officer");

        tnpOfficer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String tnp_Officer_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String tnp_Officer_Name = dataSnapshot.child("name").getValue(String.class);
                    String tnp_Officer_branch = dataSnapshot.child("branch").getValue(String.class);
                    String tnp_Officer_phone = dataSnapshot.child("phone").getValue(String.class);
                    String tnp_Officer_email = dataSnapshot.child("email").getValue(String.class);

                    if (tnp_Officer_image != null && tnp_Officer_Name != null && tnp_Officer_branch != null && tnp_Officer_phone != null && tnp_Officer_email != null) {
                        Picasso.get().load(tnp_Officer_image).placeholder(R.drawable.user_profile_icon).into(tnpOfficer_dp);
                        tnpOfficer_name.setText(tnp_Officer_Name);
                        tnpOfficer_branch.setText(tnp_Officer_branch);
                        tnpOfficer_phone.setText(tnp_Officer_phone);
                        tnpOfficer_email.setText(tnp_Officer_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference chief_C = database.getReference("TnP Cell/Chief Coordinator");

        chief_C.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String chief_C_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String chief_C_Name = dataSnapshot.child("name").getValue(String.class);
                    String chief_C_branch = dataSnapshot.child("branch").getValue(String.class);
                    String chief_C_phone = dataSnapshot.child("phone").getValue(String.class);
                    String chief_C_email = dataSnapshot.child("email").getValue(String.class);

                    if (chief_C_image != null && chief_C_Name != null && chief_C_branch != null && chief_C_phone != null && chief_C_email != null) {
                        Picasso.get().load(chief_C_image).placeholder(R.drawable.user_profile_icon).into(cheifCordinator_dp);
                        cheifCordinator_name.setText(chief_C_Name);
                        cheifCordinator_branch.setText(chief_C_branch);
                        cheifCordinator_phone.setText(chief_C_phone);
                        cheifCordinator_email.setText(chief_C_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference C_1 = database.getReference("TnP Cell/Coordinator 1");

        C_1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String C_1_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String C_1_Name = dataSnapshot.child("name").getValue(String.class);
                    String C_1_branch = dataSnapshot.child("branch").getValue(String.class);
                    String C_1_phone = dataSnapshot.child("phone").getValue(String.class);
                    String C_1_email = dataSnapshot.child("email").getValue(String.class);

                    if (C_1_image != null && C_1_Name != null && C_1_branch != null && C_1_phone != null && C_1_email != null) {
                        Picasso.get().load(C_1_image).placeholder(R.drawable.user_profile_icon).into(coordinator1_dp);
                        coordinator1_name.setText(C_1_Name);
                        coordinator1_branch.setText(C_1_branch);
                        coordinator1_phone.setText(C_1_phone);
                        coordinator1_email.setText(C_1_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference C_2 = database.getReference("TnP Cell/Coordinator 2");
        C_2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String C_2_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String C_2_Name = dataSnapshot.child("name").getValue(String.class);
                    String C_2_branch = dataSnapshot.child("branch").getValue(String.class);
                    String C_2_phone = dataSnapshot.child("phone").getValue(String.class);
                    String C_2_email = dataSnapshot.child("email").getValue(String.class);

                    if (C_2_image != null && C_2_Name != null && C_2_branch != null && C_2_phone != null && C_2_email != null) {
                        Picasso.get().load(C_2_image).placeholder(R.drawable.user_profile_icon).into(coordinator2_dp);
                        coordinator2_name.setText(C_2_Name);
                        coordinator2_branch.setText(C_2_branch);
                        coordinator2_phone.setText(C_2_phone);
                        coordinator2_email.setText(C_2_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference C_3 = database.getReference("TnP Cell/Coordinator 3");
        C_3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String C_3_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String C_3_Name = dataSnapshot.child("name").getValue(String.class);
                    String C_3_branch = dataSnapshot.child("branch").getValue(String.class);
                    String C_3_phone = dataSnapshot.child("phone").getValue(String.class);
                    String C_3_email = dataSnapshot.child("email").getValue(String.class);

                    if (C_3_image != null && C_3_Name != null && C_3_branch != null && C_3_phone != null && C_3_email != null) {
                        Picasso.get().load(C_3_image).placeholder(R.drawable.user_profile_icon).into(coordinator3_dp);
                        coordinator3_name.setText(C_3_Name);
                        coordinator3_branch.setText(C_3_branch);
                        coordinator3_phone.setText(C_3_phone);
                        coordinator3_email.setText(C_3_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference C_4 = database.getReference("TnP Cell/Coordinator 4");
        C_4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String C_4_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String C_4_Name = dataSnapshot.child("name").getValue(String.class);
                    String C_4_branch = dataSnapshot.child("branch").getValue(String.class);
                    String C_4_phone = dataSnapshot.child("phone").getValue(String.class);
                    String C_4_email = dataSnapshot.child("email").getValue(String.class);

                    if (C_4_image != null && C_4_Name != null && C_4_branch != null && C_4_phone != null && C_4_email != null) {
                        Picasso.get().load(C_4_image).placeholder(R.drawable.user_profile_icon).into(coordinator4_dp);
                        coordinator4_name.setText(C_4_Name);
                        coordinator4_branch.setText(C_4_branch);
                        coordinator4_phone.setText(C_4_phone);
                        coordinator4_email.setText(C_4_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference C_5 = database.getReference("TnP Cell/Coordinator 5");
        C_5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String C_5_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String C_5_Name = dataSnapshot.child("name").getValue(String.class);
                    String C_5_branch = dataSnapshot.child("branch").getValue(String.class);
                    String C_5_phone = dataSnapshot.child("phone").getValue(String.class);
                    String C_5_email = dataSnapshot.child("email").getValue(String.class);

                    if (C_5_image != null && C_5_Name != null && C_5_branch != null && C_5_phone != null && C_5_email != null) {
                        Picasso.get().load(C_5_image).placeholder(R.drawable.user_profile_icon).into(coordinator5_dp);
                        coordinator5_name.setText(C_5_Name);
                        coordinator5_branch.setText(C_5_branch);
                        coordinator5_phone.setText(C_5_phone);
                        coordinator5_email.setText(C_5_email);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

    }
}