package com.chestnut_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ENTRY = 1;

    private List<Object> items = new ArrayList<>(); // Holds both headers (String) and entries (ActivityEntry)

    public ActivityAdapter(List<ActivityEntry> activityEntries) {
        groupActivitiesByMonth(activityEntries);
    }

    private void groupActivitiesByMonth(List<ActivityEntry> activityEntries) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy"); // Example: "January 2025"

        // Grouping logic
        String currentMonth = "";
        List<ActivityEntry> monthActivities = new ArrayList<>();

        for (ActivityEntry entry : activityEntries) {
            try {
                Date date = dateFormat.parse(entry.getDateTime().split(" ")[0]); // Parse only the date part
                String monthName = monthFormat.format(date);

                if (!monthName.equals(currentMonth)) {
                    // Add previous month's activities to the list
                    if (!monthActivities.isEmpty()) {
                        items.add(currentMonth);
                        items.addAll(monthActivities);
                        monthActivities.clear();
                    }
                    currentMonth = monthName;
                }

                monthActivities.add(entry);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Add the last group
        if (!monthActivities.isEmpty()) {
            items.add(currentMonth);
            items.addAll(monthActivities);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (items.get(position) instanceof String) ? VIEW_TYPE_HEADER : VIEW_TYPE_ENTRY;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_entry, parent, false);
            return new EntryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            String monthName = (String) items.get(position);
            ((HeaderViewHolder) holder).bind(monthName);
        } else if (holder instanceof EntryViewHolder) {
            ActivityEntry activity = (ActivityEntry) items.get(position);
            ((EntryViewHolder) holder).bind(activity);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder for the month header
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView headerText;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.headerText);
        }

        public void bind(String monthName) {
            headerText.setText(monthName);
        }
    }

    // ViewHolder for the activity entry
    static class EntryViewHolder extends RecyclerView.ViewHolder {
        private ImageView startIcon, endIcon;
        private TextView mainText, subText;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            startIcon = itemView.findViewById(R.id.startIcon);
            endIcon = itemView.findViewById(R.id.endIcon);
            mainText = itemView.findViewById(R.id.mainText);
            subText = itemView.findViewById(R.id.subText);
        }

        public void bind(ActivityEntry activity) {
            startIcon.setImageResource(activity.getStartIconResId());
            endIcon.setImageResource(activity.getEndIconResId());
            mainText.setText(activity.getDescription());
            subText.setText(activity.getDateTime());
        }
    }
}
