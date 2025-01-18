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

import com.chestnut_java.Activities.DeviceActivity;
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.R;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {

    private final List<DeviceDetails> deviceList;

    private final Context context; // Added to start a new activity

    public DeviceListAdapter(Context context, List<DeviceDetails> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }


    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_entry, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        DeviceDetails device = deviceList.get(position);

        // Bind the deviceName to mainText
        holder.mainText.setText(device.getDeviceName());

        // Bind the locationName to subText
        holder.subText.setText(device.getLocationName());

        // Set the startIcon based on the device status
        if (device.getIsActive()) {
            holder.startIcon.setImageResource(R.drawable.power_teal_big); // Replace with your active icon drawable
        } else {
            holder.startIcon.setImageResource(R.drawable.power_red_big); // Replace with your inactive icon drawable
        }

        // Set OnClickListener to open a new activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DeviceActivity.class);
            // Optionally pass data using intent.putExtra("key", value);
            intent.putExtra("deviceId", device.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
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
