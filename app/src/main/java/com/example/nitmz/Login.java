package com.example.nitmz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {

   private TextInputEditText editTextEmail, getEditTextPassword;
    private ImageView logohelp;
    private   Button buttonLogin;
   private FirebaseAuth mAuth;

   private TextView forgotPassword;

   private TextView goToRegister;

   private ProgressDialog pd;


        @Override
        public void onStart() {
            super.onStart();
            checkCurrentUser();
        }

        private void checkCurrentUser() {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null && currentUser.isEmailVerified())  {
                    // User is logged in and email is verified, go to MainActivity
                    startMainActivity();
                } else {
                if (currentUser != null) {
                    currentUser.isEmailVerified();
                }//                    Toast.makeText(Login.this,"",Toast.LENGTH_SHORT).show();
            }
            }

        private void startMainActivity() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        private void initUI() {
            Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
            ImageView logo_image = findViewById(R.id.logo_image);
            forgotPassword = findViewById(R.id.forgotPassword);
            TextView logo_name = findViewById(R.id.logo_name);
            logohelp = findViewById(R.id.logo_help);

            logo_name.setAnimation(bottomAnim);
            logo_image.setAnimation(topAnim);

            editTextEmail = findViewById(R.id.email);
            getEditTextPassword = findViewById(R.id.password);
            buttonLogin = findViewById(R.id.loginbtn);
            mAuth = FirebaseAuth.getInstance();
            goToRegister = findViewById(R.id.RegisterNow);
            pd = new ProgressDialog(this);
            pd.setTitle("Please Wait!");
            pd.setMessage("Taking you in...");
        }

        private void setListeners() {

            logohelp.setOnClickListener(view -> {
                // Open WhatsApp chat with your phone number
                openWhatsAppChat();  // Replace with your actual phone number
            });

            forgotPassword.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);
            });

            goToRegister.setOnClickListener(view -> startRegisterActivity());

            buttonLogin.setOnClickListener(view -> loginUser());
        }

    private void openWhatsAppChat() {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + "+919305346457";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            // Handle exceptions, e.g., if the device doesn't have WhatsApp installed
            Toast.makeText(Login.this, "Error opening WhatsApp.", Toast.LENGTH_SHORT).show();
        }
    }


    private void startRegisterActivity() {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        }

        private void loginUser() {
            String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
            String password = Objects.requireNonNull(getEditTextPassword.getText()).toString().trim();

            if (TextUtils.isEmpty(email)) {
                editTextEmail.setError("Email is Required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                getEditTextPassword.setError("Password is Required.");
                return;
            }

            pd.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::handleLoginResult);
        }

        private void handleLoginResult(Task<AuthResult> task) {
            pd.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(Login.this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                startMainActivity();
            } else {
                Toast.makeText(Login.this, "Invalid User Details.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            FirebaseApp.initializeApp(this);

            initUI();
            setListeners();
        }
    }