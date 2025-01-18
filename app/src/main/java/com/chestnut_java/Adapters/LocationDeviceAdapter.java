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
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.R;

import java.util.List;

public class LocationDeviceAdapter  extends RecyclerView.Adapter<LocationDeviceAdapter.DeviceViewHolder> {

        private List<DeviceDetails> deviceList;
        private Context context;

        public LocationDeviceAdapter(List<DeviceDetails> deviceList, Context context) {
            this.deviceList = deviceList;
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
            DeviceDetails device = deviceList.get(position);

            holder.mainText.setText(device.getLocationName());
            holder.subText.setText(device.getDeviceName());

            // Set the icon based on the status
            if (device.getIsActive()) {
                holder.startIcon.setImageResource(R.drawable.google); // Replace with your active icon
            } else {
                holder.startIcon.setImageResource(R.drawable.power); // Replace with your inactive icon
            }

            // Set click listener to open a new activity
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, CalenderActivity.class);
                intent.putExtra("deviceId", device.getId());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return deviceList.size();
        }

        static class DeviceViewHolder extends RecyclerView.ViewHolder {
            TextView mainText, subText;
            ImageView startIcon, endIcon;

            public DeviceViewHolder(@NonNull View itemView) {
                super(itemView);
                mainText = itemView.findViewById(R.id.mainText);
                subText = itemView.findViewById(R.id.subText);
                startIcon = itemView.findViewById(R.id.startIcon);
                endIcon = itemView.findViewById(R.id.endIcon);
            }
        }
    }


