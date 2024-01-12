package com.example.nitmz;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.nitmz.ui.Home.HomeFragment;
import com.example.nitmz.ui.Messages.MessagesFragment;
import com.example.nitmz.ui.Notifications.NotificationsFragment;
import com.example.nitmz.ui.Profile.ProfileFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @SuppressLint("StaticFieldLeak")
    static MeowBottomNavigation bottomNavigation;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    LinearLayout admin_work;
    ImageView search;
    TextView tittle_toolbar;
    Toolbar toolbar;
    ConstraintLayout constraintLayout;
    NavigationView navigationView;
    LinearLayout developer;

    private static final int REQUEST_CODE_FLEXIBLE_UPDATE = 123;
    private AppUpdateManager appUpdateManager;





    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigation = findViewById(R.id.meowBottomNavigation);
        bottomNavigation.show(1, true);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_View);
        View headerView = navigationView.getHeaderView(0);
        constraintLayout = findViewById(R.id.constraintLayout);
        toolbar = findViewById(R.id.toolbar);
        tittle_toolbar = findViewById(R.id.toolbar_title);
        search = findViewById(R.id.search);
        admin_work = findViewById(R.id.adminWork);
        developer = findViewById(R.id.developer);
        search.setVisibility(View.INVISIBLE);

        // Create an instance of the AppUpdateManager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Check for updates.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // An update is available, prompt the user to install.
                requestFlexibleUpdate(appUpdateInfo);
            }
        });


        admin_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminWork.class);
                startActivity(intent);
            }
        });

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Developer.class);
                startActivity(intent);
            }
        });




        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = db.collection("users").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Get user data
                        String name = document.getString("name");
                        String email = document.getString("email");
                        String imageUrl = document.getString("image"); // Assuming "image" is the field for the image URL

                        CircleImageView profileImg = headerView.findViewById(R.id.profileimg);
                        TextView profileName = headerView.findViewById(R.id.profilename);
                        TextView profileEmail = headerView.findViewById(R.id.profileEmail_header);

                        // Load the image into the profile_image CircleImageView using Picasso
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get().load(imageUrl).into(profileImg, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // Image loaded successfully
                                }

                                @Override
                                public void onError(Exception e) {
                                    // Handle error, for example, show a placeholder image
                                    profileImg.setImageResource(R.drawable.user_profile_icon);
                                }
                            });
                        } else {
                            // No image URL provided, show a placeholder image
                            profileImg.setImageResource(R.drawable.user_profile_icon);
                        }

                        // Set data to TextViews
                        profileName.setText(name);
                        profileEmail.setText(email);
                    }  // Handle the case when the document does not exist

                }  // Handle exceptions

            });
        }


        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        toolbar.setTitle("Dashboard");

        navigation();
        bottomNavigation();
        bottomNavigation.setCount(2, "1");
        bottomNavigation.setCount(3, "1");

        bottomNavigation.setOnClickMenuListener(model -> {
            switch (model.getId()) {
                case 1:
                    replace(new HomeFragment());
                    tittle_toolbar.setText("Dashboard");
                    search.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    replace(new MessagesFragment());
                    tittle_toolbar.setText("Messages");
                    search.setVisibility(View.VISIBLE);
                    bottomNavigation.clearCount(2);
                    break;
                case 3:
                    replace(new NotificationsFragment());
                    tittle_toolbar.setText("Notifications");
                    search.setVisibility(View.INVISIBLE);
                    bottomNavigation.clearCount(3);
                    break;
                case 4:
                    replace(new ProfileFragment());
                    tittle_toolbar.setText("My Profile");
                    search.setVisibility(View.INVISIBLE);
                    break;

            }
            return null;
        });


    }
    private void requestFlexibleUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    REQUEST_CODE_FLEXIBLE_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FLEXIBLE_UPDATE) {
            if (resultCode != RESULT_OK) {
                // If the update is cancelled or fails, you can retry.
                // For example, you can display a button to trigger the update again.
            }
        }
    }
    private void navigation() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

//        animateNavigationView();
    }



    private void bottomNavigation() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.microsoft));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_chat));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_notice));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_profile));
        replace(new HomeFragment());
    }





    private void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.clgWebsite) {
            String url = "https://www.nitmz.ac.in/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else if (itemId == R.id.the_institute) {
            Intent intent = new Intent(getApplicationContext(), TheInstitute.class);
            startActivity(intent);
        }else if (itemId == R.id.academic) {
            Intent intent = new Intent(getApplicationContext(), Academics.class);
            startActivity(intent);
        }else if (itemId == R.id.administration) {
            Intent intent = new Intent(getApplicationContext(), Administration.class);
            startActivity(intent);
        }else if (itemId == R.id.hostels) {
            Intent intent = new Intent(getApplicationContext(), Hostels.class);
            startActivity(intent);
        }
        else if (itemId == R.id.rateUs) {
            String url = "https://play.google.com/store/apps/details?id=com.example.nitmz&hl=en&gl=US";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }else if (itemId == R.id.termnC) {
            Intent intent = new Intent(getApplicationContext(), TnC.class);
            startActivity(intent);
        } else if (itemId == R.id.helpNfeedback) {
            openWhatsAppChat();
        }else if (itemId == R.id.logout) {
            showLogoutConfirmationDialog();
        }
        closeDrawer();
        return true;
    }

    private void openWhatsAppChat() {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + "+919305346457";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            // Handle exceptions, e.g., if the device doesn't have WhatsApp installed
            Toast.makeText(MainActivity.this, "Error opening WhatsApp.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLogoutConfirmationDialog() {
        // Create a confirmation dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Logout Confirmation");
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // User clicked Yes, perform logout
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
        alertDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {
            // User clicked No, dismiss the dialog
            dialogInterface.dismiss();
        });

        // Show the confirmation dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    private void closeDrawer() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Get the current fragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

            // Check if the current fragment is HomeFragment
            if (currentFragment instanceof HomeFragment) {
                // Handle the back press in HomeFragment, e.g., navigate to the previous fragment or activity
                // If you want to go back to the previous fragment, you can use the popBackStack method
                getSupportFragmentManager().popBackStack();
            } else {
                // If the current fragment is not HomeFragment, perform the default behavior of onBackPressed
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return true;
    }

    public void setToolbarTitle(String title) {

        tittle_toolbar.setText(title);

    }
    public static MeowBottomNavigation getBottomNavigation() {
        return bottomNavigation;
    }


}