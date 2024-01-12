package com.example.nitmz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitmz.R;
import com.example.nitmz.model.BusData;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder>{

    private Context context;
    private ArrayList<BusData> list;

    public BusAdapter(Context context, ArrayList<BusData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item_view, parent, false);
        return new BusAdapter.BusViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        BusData busData = list.get(position);
        if (busData != null) {
            holder.bus_no.setText(busData.getBusNo());
            holder.hostel_name.setText(busData.getHostel());
            holder.time.setText(busData.getTime());
            holder.from.setText(busData.getFrom());
            holder.to.setText(busData.getTo());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder{

        private TextView bus_no;
        private TextView hostel_name;
        private TextView time;
        private TextView from;
        private TextView to;


        public BusViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_no = itemView.findViewById(R.id.busNo);
            hostel_name = itemView.findViewById(R.id.hostelName);
            time = itemView.findViewById(R.id.time);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
        }
    }
}
