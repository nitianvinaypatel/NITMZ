package com.nitmizoram.nitmz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final  int ITEM_SEND =1;
    final  int ITEM_RECIEVE=2;

    public MessagesAdapter(Context context, ArrayList<Message> messages){

        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            return new SendViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_recieve,parent,false);
            return new RecieveViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_RECIEVE;
        }else{
            return ITEM_SEND;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);
        if(holder.getClass()==SendViewHolder.class){
            SendViewHolder viewHolder = (SendViewHolder)holder;
            viewHolder.message.setText(message.getMessage());
        }else{
            RecieveViewHolder viewHolder = (RecieveViewHolder) holder;
            viewHolder.message.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SendViewHolder extends RecyclerView.ViewHolder{

        TextView message;
        ImageView feeling;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
//            feeling = itemView.findViewById(R.id.feeling);
        }
    }

    public class RecieveViewHolder extends RecyclerView.ViewHolder{

        TextView message;
        ImageView feeling;

        public RecieveViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
//            feeling = itemView.findViewById(R.id.feeling);
        }
    }
}
