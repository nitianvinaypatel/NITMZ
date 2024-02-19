package com.nitmizoram.nitmz.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtils
{

    public interface AdminCheckListener {
        void onAdminCheckComplete(boolean isAdmin);
    }

    public static void checkForAdmin(AdminCheckListener listener) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // Check the value of isAdmin field
                        String isAdminString = document.getString("isAdmin");
                        // Convert the string to boolean
                        boolean isAdmin = Boolean.parseBoolean(isAdminString);
                        if (listener != null) {
                            listener.onAdminCheckComplete(isAdmin);
                        }
                    } else {
                        // Handle the case when the document does not exist
                        if (listener != null) {
                            listener.onAdminCheckComplete(false);
                        }
                    }
                } else {
                    // Handle exceptions
                    if (listener != null) {
                        listener.onAdminCheckComplete(false);
                    }
                }
            });
        } else {
            // User is not authenticated
            if (listener != null) {
                listener.onAdminCheckComplete(false);
            }
        }
    }


    public interface SemesterCheckListener {
        void onSemesterCheckComplete(boolean isSemesterValid);
    }

    public static void checkForSemester(SemesterCheckListener listener) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // Check the value of the semester field
                        String semester = document.getString("semester");
                        boolean isSemesterValid = !"Update Me".equals(semester);
                        if (listener != null) {
                            listener.onSemesterCheckComplete(isSemesterValid);
                        }
                    } else {
                        // Handle the case when the document does not exist
                        if (listener != null) {
                            listener.onSemesterCheckComplete(false);
                        }
                    }
                } else {
                    // Handle exceptions
                    if (listener != null) {
                        listener.onSemesterCheckComplete(false);
                    }
                }
            });
        } else {
            // User is not authenticated
            if (listener != null) {
                listener.onSemesterCheckComplete(false);
            }
        }
    }

    public interface HostelCheckListener {
        void onHostelCheckComplete(boolean isHostelValid);
    }

    public static void checkForHostel(HostelCheckListener listener) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // Check the value of the hostel field
                        String hostel = document.getString("hostel");
                        boolean isHostelValid = !"Update Me".equals(hostel);
                        if (listener != null) {
                            listener.onHostelCheckComplete(isHostelValid);
                        }
                    } else {
                        // Handle the case when the document does not exist
                        if (listener != null) {
                            listener.onHostelCheckComplete(false);
                        }
                    }
                } else {
                    // Handle exceptions
                    if (listener != null) {
                        listener.onHostelCheckComplete(false);
                    }
                }
            });
        } else {
            // User is not authenticated
            if (listener != null) {
                listener.onHostelCheckComplete(false);
            }
        }
    }


    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static StorageReference getOtherProfilePicStorageRef(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("Profiles")
                .child(otherUserId);
    }

}
