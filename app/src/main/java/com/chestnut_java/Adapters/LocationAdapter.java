package com.chestnut_java.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<String> locations;
    private final OnLocationSelectedListener listener;

    public interface OnLocationSelectedListener {
        void onLocationSelected(String location);
    }

    public LocationAdapter(List<String> locations, OnLocationSelectedListener listener) {
        this.locations = locations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        String location = locations.get(position);
        holder.textView.setText(location);
        holder.itemView.setOnClickListener(v -> listener.onLocationSelected(location));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void updateLocations(List<String> newLocations) {
        this.locations = newLocations;
        notifyDataSetChanged();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}