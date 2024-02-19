package com.nitmizoram.nitmz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.UpdateFaculty;
import com.nitmizoram.nitmz.model.FacultyData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeleteFacultyAdapter extends RecyclerView.Adapter<DeleteFacultyAdapter.DeleteFacultyViewHolder> {

    DatabaseReference reference;
    private Context context;
    private ArrayList<FacultyData> list;

    public DeleteFacultyAdapter(Context context, ArrayList<FacultyData> list) {

        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public DeleteFacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_faculty_item_view,parent,false);

        return new DeleteFacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteFacultyViewHolder holder, int position) {

        FacultyData facultyData = list.get(position);
        String FImageUrl = facultyData.getFimageUrl();
        Picasso.get().load(FImageUrl).placeholder(R.drawable.user_profile_icon).into(holder.F_Image);
        holder.F_Name.setText(facultyData.getName());
        holder.F_designation.setText(facultyData.getDesignation());
        holder.F_branch.setText(facultyData.getDepartment());
        holder.F_contact.setText(facultyData.getContact());
        holder.update.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateFaculty.class);
            intent.putExtra("fimageUrl",facultyData.getFimageUrl());
            intent.putExtra("name", facultyData.getName());
            intent.putExtra("designation", facultyData.getDesignation());
            intent.putExtra("department",facultyData.getDepartment());
            intent.putExtra("contact",facultyData.getContact());
            intent.putExtra("key",facultyData.getKey());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.delete.setOnClickListener(view -> {
            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Faculty").child(facultyData.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context,"Faculty details deleted successfully",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(context,"Failed to delete faculty details",Toast.LENGTH_SHORT).show());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class DeleteFacultyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView F_Image;
        private TextView F_Name;
        private TextView F_designation;
        private TextView F_branch;
        private TextView F_contact;
        private Button delete, update;



        public DeleteFacultyViewHolder(@NonNull View itemView) {
            super(itemView);
            F_Image = itemView.findViewById(R.id.FImage);
            F_Name = itemView.findViewById(R.id.FName);
            F_designation =itemView.findViewById(R.id.FDesignation);
            F_branch =itemView.findViewById(R.id.FBranch);
            F_contact =itemView.findViewById(R.id.FContact);
            delete = itemView.findViewById(R.id.DeleteBtn);
            update =itemView.findViewById(R.id.UpdateBtn);
        }
    }


}
