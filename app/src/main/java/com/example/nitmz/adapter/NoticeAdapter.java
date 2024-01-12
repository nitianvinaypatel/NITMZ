package com.example.nitmz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.R;
import com.example.nitmz.model.NoticeData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item_row,parent,false);

        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
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


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView Sender_Image;
        private TextView Sender_Name;
        private TextView Sender_RollNo;
        private TextView notice_date;
        private TextView notice_time;
        private TextView message;

        private ImageView Notice_Image;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            Sender_Name = itemView.findViewById(R.id.SenderName);
            Sender_Image = itemView.findViewById(R.id.SenderImage);
            Sender_RollNo = itemView.findViewById(R.id.SenderRollNo);
            notice_date = itemView.findViewById(R.id.Date);
            notice_time = itemView.findViewById(R.id.Time);
            message = itemView.findViewById(R.id.message_box);
            Notice_Image = itemView.findViewById(R.id.NoticeImage);

        }
    }
}
