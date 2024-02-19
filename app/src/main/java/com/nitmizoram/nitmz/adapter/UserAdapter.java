package com.nitmizoram.nitmz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmizoram.nitmz.ChatsActivity;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_conversation,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = users.get(position);

        holder.usernameText.setText(user.getName());
        String profilePicUrl = user.getImage(); // Replace with the actual method to get the URL
        Picasso.get().load(profilePicUrl)
                .placeholder(R.drawable.user_profile_icon) // Placeholder image while loading
                .error(R.drawable.user_profile_icon) // Error image if loading fails
                .into(holder.profilePic);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatsActivity.class);
                intent.putExtra("name",user.getName());
                intent.putExtra("uid",user.getuserId());
                intent.putExtra("profilePicUrl", user.getImage());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView usernameText;
        TextView timeText;
        TextView messText;
        ImageView profilePic;



        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            timeText = itemView.findViewById(R.id.user_time);
            messText = itemView.findViewById(R.id.last_text);
            profilePic = itemView.findViewById(R.id.user_pic);
        }
    }
}
