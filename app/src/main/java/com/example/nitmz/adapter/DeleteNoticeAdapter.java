package com.example.nitmz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.R;
import com.example.nitmz.model.NoticeData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeleteNoticeAdapter extends RecyclerView.Adapter<DeleteNoticeAdapter.DeleteNoticeViewHolder> {
    private Context context;
    private ArrayList<NoticeData> list;
    DatabaseReference reference;

    public DeleteNoticeAdapter(Context context, ArrayList<NoticeData> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DeleteNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_notice_itemview,parent,false);

        return new DeleteNoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteNoticeViewHolder holder, int position) {
        NoticeData noticeData = list.get(position);
        String SenderImageUrl = noticeData.getSenderImage();
        Picasso.get().load(SenderImageUrl).placeholder(R.drawable.user_profile_icon).into(holder.Sender_Image);
        holder.Sender_Name.setText(noticeData.getSenderName()+" (Admin)");
        holder.Sender_RollNo.setText(noticeData.getSenderRollno());
        holder.notice_time.setText(noticeData.getTime());
        holder.notice_date.setText(noticeData.getDate());
        holder.message.setText(noticeData.getMessage());
        String NoticeImageUrl = noticeData.getImageUrl();
        Picasso.get().load(NoticeImageUrl).into(holder.Notice_Image);

        holder.Delete_btn.setOnClickListener(view -> {
            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Notices").child(noticeData.getKey()).removeValue().addOnCompleteListener(task -> Toast.makeText(context,"Notice data deleted successfully",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(context,"Failed to delete Notice data",Toast.LENGTH_SHORT).show());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DeleteNoticeViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView Sender_Image;
        private TextView Sender_Name;
        private TextView Sender_RollNo;
        private TextView notice_date;
        private TextView notice_time;
        private TextView message;

        private ImageView Notice_Image;
        private ImageView Delete_btn;

        public DeleteNoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            Sender_Name = itemView.findViewById(R.id.SenderName);
            Sender_Image = itemView.findViewById(R.id.SenderImage);
            Sender_RollNo = itemView.findViewById(R.id.SenderRollNo);
            notice_date = itemView.findViewById(R.id.Date);
            notice_time = itemView.findViewById(R.id.Time);
            message = itemView.findViewById(R.id.message_box);
            Notice_Image = itemView.findViewById(R.id.NoticeImage);
            Delete_btn = itemView.findViewById(R.id.DeleteNotice);

        }
    }
}
