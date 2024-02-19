package com.nitmizoram.nitmz;

import android.app.ProgressDialog;
import android.content.Intent;
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
    private Button buttonLogin;
    private FirebaseAuth mAuth;
    private Button forgotPassword;
    private TextView goToRegister;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        initUI();
        setListeners();
    }

    private void initUI() {
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        ImageView logo_image = findViewById(R.id.logo_image);
        forgotPassword = findViewById(R.id.forgotPassword);
        TextView logo_name = findViewById(R.id.logo_name);

        logo_name.setAnimation(bottomAnim);
        logo_image.setAnimation(topAnim);

        editTextEmail = findViewById(R.id.email);
        getEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.loginbtn);
        mAuth = FirebaseAuth.getInstance();
        goToRegister = findViewById(R.id.RegisterNow);
    }

    private void setListeners() {
        forgotPassword.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ForgotPassword.class)));
        goToRegister.setOnClickListener(view -> startRegisterActivity());
        buttonLogin.setOnClickListener(view -> loginUser());
    }
    private void loginUser() {
        String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(getEditTextPassword.getText()).toString().trim();

        if (TextUtils.isEmpty(email) || !email.contains("@nitmz.ac.in") || email.length() != 21) {
            editTextEmail.setError("Enter a valid institute email.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            getEditTextPassword.setError("Password is Required.");
            return;
        }
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait!");
        pd.setMessage("Taking you in...");
        pd.setCancelable(false);
        pd.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::handleLoginResult);
    }
    private void handleLoginResult(Task<AuthResult> task) {
        pd.dismiss();
        if (task.isSuccessful()) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null && currentUser.isEmailVerified()) {
                Toast.makeText(Login.this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                startMainActivity();
            } else {
                Toast.makeText(Login.this, "Please Verify Your Email.", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
            }
        } else {
            Toast.makeText(Login.this, "Invalid Student Details.", Toast.LENGTH_SHORT).show();
        }
    }
    private void startRegisterActivity() {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        checkCurrentUser();
    }
    private void checkCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            Toast.makeText(Login.this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
            startMainActivity();
        } else {
//            Toast.makeText(Login.this, "Please Verify Your Email.", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
    private void startMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
