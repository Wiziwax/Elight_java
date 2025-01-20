package com.chestnut_java.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Activities.CalenderActivity;
import com.chestnut_java.Entities.DeviceLogLocation;
import com.chestnut_java.R;

import java.util.List;

public class DeviceLogLocationAdapter extends RecyclerView.Adapter<DeviceLogLocationAdapter.DeviceViewHolder> {

    private final List<DeviceLogLocation> deviceLogList;
    private final Context context;

    public DeviceLogLocationAdapter(List<DeviceLogLocation> deviceLogList, Context context) {
        this.deviceLogList = deviceLogList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_device_details, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        DeviceLogLocation deviceLog = deviceLogList.get(position);

        // Set the main text and subtext
        holder.mainText.setText(deviceLog.getTimePeriod() != null ? deviceLog.getTimePeriod() : "Unknown timeframe");
        holder.subText.setText(deviceLog.getTimeFrame() != null ? deviceLog.getTimeFrame() : "Unknown time period");

        // Set the icon based on the status
        if (deviceLog.getStatus() != null && deviceLog.getStatus()) {
            holder.startIcon.setImageResource(R.drawable.power_teal_big); // Replace with your active icon
        } else {
            holder.startIcon.setImageResource(R.drawable.red_power); // Replace with your inactive icon
        }

        // Set click listener to open a new activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CalenderActivity.class);
            intent.putExtra("locationId", deviceLog.getLocationId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return deviceLogList.size();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView mainText, subText;
        ImageView startIcon;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            mainText = itemView.findViewById(R.id.mainText);
            subText = itemView.findViewById(R.id.subText);
            startIcon = itemView.findViewById(R.id.startIcon);
        }
    }
}
