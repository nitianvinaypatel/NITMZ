package com.nitmizoram.nitmz.ui.Profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nitmizoram.nitmz.Login;
import com.nitmizoram.nitmz.MainActivity;
import com.nitmizoram.nitmz.ManageAccount;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.ui.Home.HomeFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView user_profile;
    private TextView userNameTextView, userRollNoTextView, userBranchTextView, userSemesterTextView, userMobNoTextView, userEmailIdTextView, userCourseTextView, userHostelTextView;

    private  ProgressBar progressBar;


    String name, rollNo, mobNo, branch, course, hostel, imageUrl, email, semester;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        user_profile = view.findViewById(R.id.user_profile);
        userNameTextView = view.findViewById(R.id.user_name);
        userRollNoTextView = view.findViewById(R.id.user_rollno);
        userBranchTextView = view.findViewById(R.id.user_branch);
        userSemesterTextView = view.findViewById(R.id.user_semester);
        userMobNoTextView = view.findViewById(R.id.user_mobno);
        userEmailIdTextView = view.findViewById(R.id.user_emailid);
        userCourseTextView = view.findViewById(R.id.user_course);
        userHostelTextView = view.findViewById(R.id.user_hostel);

        LinearLayout manage_account = view.findViewById(R.id.manage_account);
        LinearLayout change_password = view.findViewById(R.id.change_password);
        LinearLayout ll_logout = view.findViewById(R.id.ll_logout);
        progressBar = view.findViewById(R.id.progressBar);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        fetchDataFromFirebase();

        ll_logout.setOnClickListener(view13 -> {
            // Create a confirmation dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
            alertDialogBuilder.setTitle("Logout Confirmation");
            alertDialogBuilder.setMessage("Are you sure you want to logout?");
            alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                // User clicked Yes, perform logout
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(requireContext(), Login.class);
                startActivity(intent);
                requireActivity().finish();
            });
            alertDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {
                // User clicked No, dismiss the dialog
                dialogInterface.dismiss();
            });

            // Show the confirmation dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
        manage_account.setOnClickListener(view12 -> {
            Intent intent = new Intent(requireContext(), ManageAccount.class);
            intent.putExtra("name", name);
            intent.putExtra("phone", mobNo);
            startActivity(intent);
        });




        change_password.setOnClickListener(view1 -> {
            // Use a custom layout for the dialog to include both old and new password inputs
            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_password, null);

            final EditText oldPasswordEditText = dialogView.findViewById(R.id.old_password);
            final EditText newPasswordEditText = dialogView.findViewById(R.id.new_password);

            AlertDialog.Builder changePassDialog = new AlertDialog.Builder(view1.getContext());
            changePassDialog.setTitle("Update Your Password!");
            changePassDialog.setView(dialogView);

            changePassDialog.setPositiveButton("Update", (dialog, which) -> {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
                    // Update the user's password after re-authentication
                    reauthenticateAndUpdatePassword(oldPassword, newPassword);
                } else {
                    Toast.makeText(requireActivity(), "Please enter both old and new passwords", Toast.LENGTH_SHORT).show();
                }
            });

            changePassDialog.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle cancellation if needed
            });

            changePassDialog.show();
        });

        return view;
    }


    private void reauthenticateAndUpdatePassword(String oldPassword, String newPassword) {
        // Re-authenticate the user before updating the password
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User has been successfully re-authenticated, proceed with password update
                        updatePassword(newPassword);
                    } else {
                        // Re-authentication failed, handle the error
                        Toast.makeText(requireActivity(), "Re-authentication failed. Please check your old password.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updatePassword(String newPassword){
        // Update the user's password
        user.updatePassword(newPassword)
                .addOnSuccessListener(unused -> {
                    // Password updated successfully
                    Toast.makeText(requireActivity(), "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to update password, handle the error
                    if (e instanceof FirebaseAuthWeakPasswordException) {
                        Toast.makeText(requireActivity(), "Password is too weak", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof FirebaseAuthRecentLoginRequiredException) {
                        Toast.makeText(requireActivity(), "Recent login is required", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("UpdatePassword", "Failed to update password", e);
                        Toast.makeText(requireActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void fetchDataFromFirebase() {

        progressBar.setVisibility(View.VISIBLE);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // Get user data
                        name = document.getString("name");
                        rollNo = document.getString("rollNo");
                        branch = document.getString("branch");
                        semester = document.getString("semester");
                        hostel = document.getString("hostel");
                        mobNo = document.getString("phone");
                        email = document.getString("email");
                        course = document.getString("course");
                        imageUrl = document.getString("image");

                        // Load the image into the user_profile ImageView using Picasso
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get().load(imageUrl).into(user_profile, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // Image loaded successfully
                                }

                                @Override
                                public void onError(Exception e) {
                                    // Handle error, for example, show a placeholder image
                                    user_profile.setImageResource(R.drawable.user_profile_icon);
                                }
                            });
                        } else {
                            // No image URL provided, show a placeholder image
                            user_profile.setImageResource(R.drawable.user_profile_icon);
                        }

                        // Assuming you have a reference to the TextViews
                        userNameTextView.setText(name);
                        userRollNoTextView.setText(rollNo);
                        userBranchTextView.setText(branch);
                        userSemesterTextView.setText(semester);
                        userHostelTextView.setText(hostel);
                        userMobNoTextView.setText(mobNo);
                        userEmailIdTextView.setText(email);
                        userCourseTextView.setText(course);
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).setToolbarTitle("My Profile");


        // Handle back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with HomeFragment
                replaceFragment(new HomeFragment());
                MainActivity.getBottomNavigation().show(1, true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
   }
}
