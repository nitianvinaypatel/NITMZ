package com.example.nitmz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.R;
import com.example.nitmz.model.FacultyData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {


    private Context context;
    private ArrayList<FacultyData> list;

    public FacultyAdapter(Context context, ArrayList<FacultyData> list) {

        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_view,parent,false);

        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {

        FacultyData facultyData = list.get(position);
        String FImageUrl = facultyData.getFimageUrl();
        Picasso.get().load(FImageUrl).placeholder(R.drawable.user_profile_icon).into(holder.F_Image);
        holder.F_Name.setText(facultyData.getName());
        holder.F_designation.setText(facultyData.getDesignation());
        holder.F_branch.setText(facultyData.getDepartment());
        holder.F_contact.setText(facultyData.getContact());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class FacultyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView F_Image;
        private TextView F_Name;
        private TextView F_designation;
        private TextView F_branch;
        private TextView F_contact;



        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);
            F_Image = itemView.findViewById(R.id.FImage);
            F_Name = itemView.findViewById(R.id.FName);
            F_designation =itemView.findViewById(R.id.FDesignation);
            F_branch =itemView.findViewById(R.id.FBranch);
            F_contact =itemView.findViewById(R.id.FContact);
        }
    }


}
