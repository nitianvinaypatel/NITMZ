package com.example.nitmz.ui.Messages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.R;
import com.example.nitmz.adapter.UserAdapter;
import com.example.nitmz.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ArrayList<User> users;

    UserAdapter userAdapter;

    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.chat_recycler_view);

        firestore = FirebaseFirestore.getInstance();
        users = new ArrayList<>();
        userAdapter = new UserAdapter(requireContext(), users);
        recyclerView.setAdapter(userAdapter);
        progressBar = view.findViewById(R.id.chat_progress);
        progressBar.setVisibility(View.VISIBLE);

        // Assuming you have a "users" collection in Firestore
        firestore.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                users.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    users.add(user);
                }
                userAdapter.notifyDataSetChanged();
            } else {
                // Handle errors
            }
        });

        return view;
    }
}
