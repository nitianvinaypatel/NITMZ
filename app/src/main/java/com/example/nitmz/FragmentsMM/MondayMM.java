package com.example.nitmz.FragmentsMM;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MondayMM extends Fragment {

    private FirebaseFirestore firestore;
    private ProgressBar progressBar;

    private TextView breakfast_m;
    private TextView lunch_m;
    private TextView snacks_m;
    private TextView dinner_m;

    private String hostel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monday_m_m, container, false);

        breakfast_m = view.findViewById(R.id.breakfast);
        lunch_m = view.findViewById(R.id.lunch);
        snacks_m = view.findViewById(R.id.snacks);
        dinner_m = view.findViewById(R.id.dinner);
        progressBar = view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        // Get the current day of the week

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

                            hostel = document.getString("hostel");

                            // Construct the base collection path
                            String path = "Hostels/" + hostel + "/" + "Monday";

                            firestore.collection("Mess Menu").document(path).get()
                                    .addOnCompleteListener(innerTask -> {

                                        if (innerTask.isSuccessful()) {
                                            progressBar.setVisibility(View.GONE);
                                            DocumentSnapshot innerDocument = innerTask.getResult();
                                            if (innerDocument != null && innerDocument.exists()) {
                                                String breakfast = innerDocument.getString("breakfast");
                                                String lunch = innerDocument.getString("lunch");
                                                String snacks = innerDocument.getString("snacks");
                                                String dinner = innerDocument.getString("dinner");


                                                // Set the data to the corresponding TextViews
                                                setMessMenuData(breakfast,lunch,snacks,dinner);

                                            } else {
                                                // Document does not exist
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(getContext(),"Document does not exist",Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Handle failures
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(),"Document does not exist",Toast.LENGTH_SHORT).show();
                                        }

                                    });


                        }
                    }
                }
            });
        }


        return view;
    }
    private void setMessMenuData(String breakfast, String lunch, String snacks, String dinner) {
        breakfast_m.setText(breakfast);
        lunch_m.setText(lunch);
        snacks_m.setText(snacks);
        dinner_m.setText(dinner);

    }

}