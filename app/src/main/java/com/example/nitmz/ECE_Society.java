package com.example.nitmz;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class ECE_Society extends AppCompatActivity {

    private CircleImageView president_dp;
    private CircleImageView secretary_dp;
    private CircleImageView technical_dp;
    private CircleImageView marketing_dp;
    private CircleImageView cultural_dp;
    private CircleImageView event_dp;
    private CircleImageView graphic_dp;
    private CircleImageView content_dp;
    private CircleImageView student_dp;

    private TextView president_name;
    private TextView secretary_name;
    private TextView technical_name;
    private TextView marketing_name;
    private TextView cultural_name;
    private TextView event_name;
    private TextView graphic_name;
    private TextView content_name;
    private TextView student_name;

    private TextView president_rollno;
    private TextView secretary_rollno;
    private TextView technical_rollno;
    private TextView marketing_rollno;
    private TextView cultural_rollno;
    private TextView event_rollno;
    private TextView graphic_rollno;
    private TextView content_rollno;
    private TextView student_rollno;

    private TextView president_phone;
    private TextView secretary_phone;
    private TextView technical_phone;
    private TextView marketing_phone;
    private TextView cultural_phone;
    private TextView event_phone;
    private TextView graphic_phone;
    private TextView content_phone;
    private TextView student_phone;

    private TextView president_email;
    private TextView secretary_email;
    private TextView technical_email;
    private TextView marketing_email;
    private TextView cultural_email;
    private TextView event_email;
    private TextView graphic_email;
    private TextView content_email;
    private TextView student_email;

    private ImageButton backbtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ece_society);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> onBackPressed());


        president_dp = findViewById(R.id.president_image);
        president_name = findViewById(R.id.president_name);
        president_rollno = findViewById(R.id.president_rollno);
        president_phone = findViewById(R.id.president_phone);
        president_email = findViewById(R.id.president_email);

        secretary_dp = findViewById(R.id.secretary_image);
        secretary_name= findViewById(R.id.secretary_name);
        secretary_rollno = findViewById(R.id.secretary_rollno);
        secretary_phone = findViewById(R.id.secretary_phone);
        secretary_email = findViewById(R.id.secretary_email);

        technical_dp = findViewById(R.id.technical_image);
        technical_name= findViewById(R.id.technical_name);
        technical_rollno = findViewById(R.id.technical_rollno);
        technical_phone = findViewById(R.id.technical_phone);
        technical_email = findViewById(R.id.technical_email);

        marketing_dp = findViewById(R.id.marketing_image);
        marketing_name = findViewById(R.id.marketing_name);
        marketing_rollno = findViewById(R.id.marketing_rollno);
        marketing_phone = findViewById(R.id.marketing_phone);
        marketing_email = findViewById(R.id.marketing_email);

        cultural_dp = findViewById(R.id.cultural_image);
        cultural_name = findViewById(R.id.cultural_name);
        cultural_rollno = findViewById(R.id.cultural_rollno);
        cultural_phone = findViewById(R.id.cultural_phone);
        cultural_email = findViewById(R.id.cultural_email);

        event_dp  = findViewById(R.id.event_image);
        event_name = findViewById(R.id.event_name);
        event_rollno = findViewById(R.id.event_rollno);
        event_phone = findViewById(R.id.event_phone);
        event_email = findViewById(R.id.event_email);

        graphic_dp = findViewById(R.id.graphic_image);
        graphic_name = findViewById(R.id.graphic_name);
        graphic_rollno = findViewById(R.id.graphic_rollno);
        graphic_phone = findViewById(R.id.graphic_phone);
        graphic_email = findViewById(R.id.graphic_email);

        content_dp = findViewById(R.id.content_image);
        content_name = findViewById(R.id.content_name);
        content_rollno = findViewById(R.id.content_rollno);
        content_phone = findViewById(R.id.content_phone);
        content_email = findViewById(R.id.content_email);

        student_dp = findViewById(R.id.student_image);
        student_name = findViewById(R.id.student_name);
        student_rollno = findViewById(R.id.student_rollno);
        student_phone = findViewById(R.id.student_phone);
        student_email = findViewById(R.id.student_email);



        DatabaseReference president = database.getReference("Society/ECE/President");
        president.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Presi_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Presi_Name = dataSnapshot.child("name").getValue(String.class);
                    String Presi_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Presi_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Presi_email = dataSnapshot.child("email").getValue(String.class);

                    if (Presi_image != null && Presi_Name != null && Presi_branch != null && Presi_phone != null && Presi_email != null) {
                        Picasso.get().load(Presi_image).placeholder(R.drawable.user_profile_icon).into(president_dp);
                        president_name.setText(Presi_Name);
                        president_rollno.setText(Presi_branch);
                        president_phone.setText(Presi_phone);
                        president_email.setText(Presi_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference secretary= database.getReference("Society/ECE/Secretary");
        secretary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Secri_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Secri_Name = dataSnapshot.child("name").getValue(String.class);
                    String Secri_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Secri_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Secri_email = dataSnapshot.child("email").getValue(String.class);

                    if (Secri_image != null && Secri_Name != null && Secri_branch != null && Secri_phone != null && Secri_email != null) {
                        Picasso.get().load(Secri_image).placeholder(R.drawable.user_profile_icon).into(secretary_dp);
                        secretary_name.setText(Secri_Name);
                        secretary_rollno.setText(Secri_branch);
                        secretary_phone.setText(Secri_phone);
                        secretary_email.setText(Secri_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference tech= database.getReference("Society/ECE/Technical Head");
        tech.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Tech_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Tech_Name = dataSnapshot.child("name").getValue(String.class);
                    String Tech_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Tech_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Tech_email = dataSnapshot.child("email").getValue(String.class);

                    if (Tech_image != null && Tech_Name != null && Tech_branch != null && Tech_phone != null && Tech_email != null) {
                        Picasso.get().load(Tech_image).placeholder(R.drawable.user_profile_icon).into(technical_dp);
                        technical_name.setText(Tech_Name);
                        technical_rollno.setText(Tech_branch);
                        technical_phone.setText(Tech_phone);
                        technical_email.setText(Tech_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference mark= database.getReference("Society/ECE/Marketing Head");
        mark.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Mark_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Mark_Name = dataSnapshot.child("name").getValue(String.class);
                    String Mark_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Mark_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Mark_email = dataSnapshot.child("email").getValue(String.class);

                    if (Mark_image != null && Mark_Name != null && Mark_branch != null && Mark_phone != null && Mark_email != null) {
                        Picasso.get().load(Mark_image).placeholder(R.drawable.user_profile_icon).into(marketing_dp);
                        marketing_name.setText(Mark_Name);
                        marketing_rollno.setText(Mark_branch);
                        marketing_phone.setText(Mark_phone);
                        marketing_email.setText(Mark_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference cult= database.getReference("Society/ECE/Cultural Head");
        cult.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Cult_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Cult_Name = dataSnapshot.child("name").getValue(String.class);
                    String Cult_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Cult_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Cult_email = dataSnapshot.child("email").getValue(String.class);

                    if (Cult_image != null && Cult_Name != null && Cult_branch != null && Cult_phone != null && Cult_email != null) {
                        Picasso.get().load(Cult_image).placeholder(R.drawable.user_profile_icon).into(cultural_dp);
                        cultural_name.setText(Cult_Name);
                        cultural_rollno.setText(Cult_branch);
                        cultural_phone.setText(Cult_phone);
                        cultural_email.setText(Cult_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference event= database.getReference("Society/ECE/Event Management Head");
        event.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Event_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Event_Name = dataSnapshot.child("name").getValue(String.class);
                    String Event_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Event_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Event_email = dataSnapshot.child("email").getValue(String.class);

                    if (Event_image != null && Event_Name != null && Event_branch != null && Event_phone != null && Event_email != null) {
                        Picasso.get().load(Event_image).placeholder(R.drawable.user_profile_icon).into(event_dp);
                        event_name.setText(Event_Name);
                        event_rollno.setText(Event_branch);
                        event_phone.setText(Event_phone);
                        event_email.setText(Event_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference graph= database.getReference("Society/ECE/Graphic Designing Head");
        graph.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Graph_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Graph_Name = dataSnapshot.child("name").getValue(String.class);
                    String Graph_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Graph_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Graph_email = dataSnapshot.child("email").getValue(String.class);

                    if (Graph_image != null && Graph_Name != null && Graph_branch != null && Graph_phone != null && Graph_email != null) {
                        Picasso.get().load(Graph_image).placeholder(R.drawable.user_profile_icon).into(graphic_dp);
                        graphic_name.setText(Graph_Name);
                        graphic_rollno.setText(Graph_branch);
                        graphic_phone.setText(Graph_phone);
                        graphic_email.setText(Graph_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference content= database.getReference("Society/ECE/Content Head");
        content.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Content_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Content_Name = dataSnapshot.child("name").getValue(String.class);
                    String Content_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Content_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Content_email = dataSnapshot.child("email").getValue(String.class);

                    if (Content_image != null && Content_Name != null && Content_branch != null && Content_phone != null && Content_email != null) {
                        Picasso.get().load(Content_image).placeholder(R.drawable.user_profile_icon).into(content_dp);
                        content_name.setText(Content_Name);
                        content_rollno.setText(Content_branch);
                        content_phone.setText(Content_phone);
                        content_email.setText(Content_email);

                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        DatabaseReference student= database.getReference("Society/ECE/Student Coordinator");
        student.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get president data
                    String Student_image = dataSnapshot.child("imageUrl").getValue(String.class);
                    String Student_Name = dataSnapshot.child("name").getValue(String.class);
                    String Student_branch = dataSnapshot.child("branch").getValue(String.class);
                    String Student_phone = dataSnapshot.child("phone").getValue(String.class);
                    String Student_email = dataSnapshot.child("email").getValue(String.class);

                    if (Student_image != null && Student_Name != null && Student_branch != null && Student_phone != null && Student_email != null) {
                        Picasso.get().load(Student_image).placeholder(R.drawable.user_profile_icon).into(student_dp);
                        student_name.setText(Student_Name);
                        student_rollno.setText(Student_branch);
                        student_phone.setText(Student_phone);
                        student_email.setText(Student_email);

                    } else {

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