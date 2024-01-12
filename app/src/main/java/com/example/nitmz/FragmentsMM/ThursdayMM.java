package com.example.nitmz.FragmentsMM;

import android.annotation.SuppressLint;
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

public class ThursdayMM extends Fragment {

    private FirebaseFirestore firestore;
    private ProgressBar progressBar;

    private TextView breakfast_th;
    private TextView lunch_th;
    private TextView snacks_th;
    private TextView dinner_th;

    private String hostel;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thursday_m_m, container, false);


        breakfast_th = view.findViewById(R.id.breakfast);
        lunch_th = view.findViewById(R.id.lunch);
        snacks_th = view.findViewById(R.id.snacks);
        dinner_th = view.findViewById(R.id.dinner);
        progressBar = view.findViewById(R.id.progress_bar);

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

                            hostel = document.getString("hostel");

                            // Construct the base collection path
                            String path = "Hostels/" + hostel + "/" + "Thursday";

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
        breakfast_th.setText(breakfast);
        lunch_th.setText(lunch);
        snacks_th.setText(snacks);
        dinner_th.setText(dinner);

    }
}