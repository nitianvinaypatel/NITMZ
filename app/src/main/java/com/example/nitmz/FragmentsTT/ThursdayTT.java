package com.example.nitmz.FragmentsTT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nitmz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ThursdayTT extends Fragment {

    private FirebaseFirestore firestore;
    private ProgressBar progressBar;

    private String semester,course,branch;


    private TextView subject1,subject2,subject3,subject4,subject5,faculty1,faculty2,faculty3,faculty4,faculty5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thursday_t_t, container, false);


        subject1 = view.findViewById(R.id.subject1);
        faculty1 = view.findViewById(R.id.faculty1);
        subject2 = view.findViewById(R.id.subject2);
        faculty2 = view.findViewById(R.id.faculty2);
        subject3 = view.findViewById(R.id.subject3);
        faculty3 = view.findViewById(R.id.faculty3);
        subject4 = view.findViewById(R.id.subject4);
        faculty4 = view.findViewById(R.id.faculty4);
        subject5 = view.findViewById(R.id.subject5);
        faculty5 = view.findViewById(R.id.faculty5);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);


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
                            // Get user data
                            semester = document.getString("semester");
                            course = document.getString("course");
                            branch = document.getString("branch");


                            // Construct the base collection path
                            String baseCollectionPath = course + "/" + semester + "/" + branch + "/" + "Thursday";

                            // Continue with the Firestore queries...

                            // Now, you can proceed with Firestore queries or any other logic
                            for (int i = 1; i <= 5; i++) {
                                String path = baseCollectionPath + "/Period" + i;


                                // Get a reference to the document at the specified path in the TimeTable collection
                                int finalI = i;
                                firestore.collection("Time Table").document(path).get()
                                        .addOnCompleteListener(innerTask -> {
                                            if (innerTask.isSuccessful()) {
                                                progressBar.setVisibility(View.GONE);
                                                DocumentSnapshot innerDocument = innerTask.getResult();
                                                if (innerDocument != null && innerDocument.exists()) {
                                                    String subject = innerDocument.getString("subject");
                                                    String faculty = innerDocument.getString("faculty");

                                                    // Set the data to the corresponding TextViews based on the period
                                                    setPeriodData(finalI, subject, faculty);
                                                } else {
                                                    // Document does not exist
                                                    progressBar.setVisibility(View.GONE);
                                                    Log.d("Debug", "Document does not exist for path: " + path);
                                                }
                                            } else {
                                                // Handle failures
                                                progressBar.setVisibility(View.GONE);
                                                Log.w("Debug", "Error getting document for path: " + path, task.getException());
                                            }
                                        });



                            }
                        }
                    } else {
                        // Handle the case where user data retrieval fails
                        progressBar.setVisibility(View.GONE);
                        Log.e("Debug", "Error getting user document", task.getException());
                    }
                }
            });

        }


        return view;
    }

    private void setPeriodData(int finalI, String subject, String faculty) {
        switch (finalI) {
            case 1:
                subject1.setText(subject);
                faculty1.setText(faculty);
                break;
            case 2:
                subject2.setText(subject);
                faculty2.setText(faculty);
                break;
            case 3:
                subject3.setText(subject);
                faculty3.setText(faculty);
                break;
            case 4:
                subject4.setText(subject);
                faculty4.setText(faculty);
                break;
            case 5:
                subject5.setText(subject);
                faculty5.setText(faculty);
                break;
            // Add more cases if you have more periods
        }
    }
}