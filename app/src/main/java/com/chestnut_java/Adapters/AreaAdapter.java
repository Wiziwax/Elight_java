package com.chestnut_java.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Entities.Area;
import com.chestnut_java.R;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {

    private final List<Area> areas;
//    private final List<Area> areas;
    private final OnAreaClickListener onAreaClickListener;

    public AreaAdapter(List<Area> areas, OnAreaClickListener onAreaClickListener) {
        this.areas = areas;
        this.onAreaClickListener = onAreaClickListener;
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);
        return new AreaViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        Area area = areas.get(position);

        holder.mainText.setText(area.getStreet());
        holder.subText.setText(area.getZip());
//        holder.deviceCountText.setText(area.getNumberOfDevices() + " devices found");

        // Handle click events
        holder.itemView.setOnClickListener(v -> onAreaClickListener.onAreaClick(area));

        // Handle click events for deviceCountText
        holder.deviceCountText.setOnClickListener(v -> {
            // Call a specific method for handling device count clicks
            onAreaClickListener.onDeviceCountClick(area.getId(), area.getStreet());
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public void updateAreas(List<Area> newAreas) {
        areas.clear();
        areas.addAll(newAreas);
        notifyDataSetChanged();
    }

    static class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView mainText, subText, deviceCountText;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            mainText = itemView.findViewById(R.id.mainText);
            subText = itemView.findViewById(R.id.subText);
            deviceCountText = itemView.findViewById(R.id.deviceCountText);
        }
    }

    public interface OnAreaClickListener {
        void onAreaClick(Area area);
        void onDeviceCountClick(String areaId, String areaName); // New method
    }

}
