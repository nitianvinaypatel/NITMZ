package com.example.nitmz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeScreen extends AppCompatActivity {

    Button getStarted;
    Animation leftAnim;
    TextView textView3;
    TextView textView2;
    FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        checkCurrentUser();
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null  && currentUser.isEmailVerified()) {
            // User is logged in and email is verified, go to MainActivity
            startMainActivity();
        } else {
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
//                finish();
            }
        }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        mAuth = FirebaseAuth.getInstance();
        textView2= findViewById(R.id.textView2);

        leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_animation);
        textView3= findViewById(R.id.textView3);

        textView3.setAnimation(leftAnim);

        getStarted = findViewById(R.id.getstarted);

        getStarted.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TnC.class);
                startActivity(intent);
            }
        });

    }
}