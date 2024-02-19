package com.nitmizoram.nitmz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmizoram.nitmz.adapter.MessagesAdapter;
import com.nitmizoram.nitmz.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView user_name_other;
    ImageView back_btn;

    MessagesAdapter adapter;
    ArrayList<Message> messages;

    String senderRoom, recieverRoom;

    ImageView send_button;
    EditText messageBox;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        messages = new ArrayList<>();
        adapter = new MessagesAdapter(this, messages);
        user_name_other = findViewById(R.id.chat_other_username);
        circleImageView = findViewById(R.id.chat_other_profileimg);
        back_btn = findViewById(R.id.backbtn);
        send_button = findViewById(R.id.send_btn);
        messageBox = findViewById(R.id.messageBox);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view); // Add this line to initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String profilePicUrl = intent.getStringExtra("profilePicUrl");

            String senderUid = intent.getStringExtra("uid");
            String recieverUid = FirebaseAuth.getInstance().getUid();

            senderRoom = senderUid + recieverUid;
            recieverRoom = recieverUid + senderUid;

            firestore.collection("chats").document(senderRoom).collection("messages")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            // Handle error
                            return;
                        }

//                        messages.clear();
                        for (DocumentChange documentChange : Objects.requireNonNull(value).getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                Message message = documentChange.getDocument().toObject(Message.class);
                                messages.add(message);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    });

            send_button.setOnClickListener(view -> {
                String messagetxt = messageBox.getText().toString();
                Date date = new Date();
                Message message = new Message(messagetxt, senderUid, date.getTime());

                messageBox.setText("");
                firestore.collection("chats").document(senderRoom).collection("messages")
                        .add(message)
                        .addOnSuccessListener(documentReference -> {
                            Map<String, Object> lastMessageMap = new HashMap<>();
                            lastMessageMap.put("lastMessage", messagetxt);
                            lastMessageMap.put("timestamp", date.getTime());

                            firestore.collection("chats").document(senderRoom)
                                    .update(lastMessageMap)
                                    .addOnSuccessListener(aVoid -> {
                                        // Do something on success
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle failure
                                    });

                            firestore.collection("chats").document(recieverRoom).collection("messages")
                                    .add(message)
                                    .addOnSuccessListener(documentReference1 -> {
                                        Map<String, Object> lastMessageMap1 = new HashMap<>();
                                        lastMessageMap1.put("lastMessage", messagetxt);
                                        lastMessageMap1.put("timestamp", date.getTime());

                                        firestore.collection("chats").document(recieverRoom)
                                                .update(lastMessageMap1)
                                                .addOnSuccessListener(aVoid1 -> {
                                                    // Do something on success
                                                })
                                                .addOnFailureListener(e1 -> {
                                                    // Handle failure
                                                });
                                    });
                        });
            });

            // Set the name to the TextView
            user_name_other.setText(name);

            // Load the image using Picasso into the CircleImageView
            Picasso.get().load(profilePicUrl)
                    .placeholder(R.drawable.user_profile_icon) // Placeholder image while loading
                    .error(R.drawable.user_profile_icon) // Error image if loading fails
                    .into(circleImageView);
        }

        back_btn.setOnClickListener(view -> onBackPressed());
    }
}
