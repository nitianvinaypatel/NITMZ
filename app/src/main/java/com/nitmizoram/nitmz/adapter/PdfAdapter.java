package com.nitmizoram.nitmz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmizoram.nitmz.PdfViewer;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.model.PdfData;

import java.util.ArrayList;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.NotesViewHolder> {

    private Context context;
    private ArrayList<PdfData> list;

    public PdfAdapter(Context context, ArrayList<PdfData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pdf_item_view,parent,false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.pdf_name.setText(list.get(position).getPdfTitle());
        holder.linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, PdfViewer.class);
            intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView pdf_name;
        private LinearLayout linearLayout;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            pdf_name = itemView.findViewById(R.id.pdfName);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}
