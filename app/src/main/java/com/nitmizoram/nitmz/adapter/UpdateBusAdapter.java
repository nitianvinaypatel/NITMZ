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
import com.nitmizoram.nitmz.UpdateBus;
import com.nitmizoram.nitmz.model.BusData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateBusAdapter extends RecyclerView.Adapter<UpdateBusAdapter.UpdateBusViewHolder> {

    DatabaseReference reference;

    private Context context;
    private ArrayList<BusData> list;

    public UpdateBusAdapter(Context context, ArrayList<BusData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UpdateBusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_bus_item_view, parent, false);
        return new UpdateBusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateBusViewHolder holder, int position) {
        BusData busData = list.get(position);
        if (busData != null) {
            holder.bus_no.setText(busData.getBusNo());
            holder.hostel_name.setText(busData.getHostel());
            holder.time.setText(busData.getTime());
            holder.from.setText(busData.getFrom());
            holder.to.setText(busData.getTo());
        }
        holder.Update_btn.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateBus.class);
            intent.putExtra("busNo", busData.getBusNo());
            intent.putExtra("busTime", busData.getTime());
            intent.putExtra("busFrom",busData.getFrom());
            intent.putExtra("busTo",busData.getTo());
            intent.putExtra("uniqueKey",busData.getUniqueKey());
            intent.putExtra("hostel",busData.getHostel());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

        holder.Delete_btn.setOnClickListener(view -> {
            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("NIT Buses").child(busData.getHostel()).child(busData.getUniqueKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context,"Bus data deleted successfully",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(context,"Failed to delete bus data",Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UpdateBusViewHolder extends RecyclerView.ViewHolder {

        private TextView bus_no;
        private TextView hostel_name;
        private TextView time;
        private TextView from;
        private TextView to;
        private Button Update_btn;

        private Button Delete_btn;

        public UpdateBusViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_no = itemView.findViewById(R.id.busNo);
            hostel_name = itemView.findViewById(R.id.hostelName);
            time = itemView.findViewById(R.id.time);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            Update_btn = itemView.findViewById(R.id.UpdateBtn);
            Delete_btn = itemView.findViewById(R.id.DeleteBtn);
        }
    }
}
