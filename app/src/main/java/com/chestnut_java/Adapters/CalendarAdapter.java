package com.chestnut_java.Adapters;

// CalendarAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import android.graphics.Color;

import com.chestnut_java.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private ArrayList<Calendar> months;
    private Random random;

    public CalendarAdapter() {
        months = new ArrayList<>();
        random = new Random();

        // Initialize with 5 months starting from current month
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            Calendar monthCal = (Calendar) calendar.clone();
            monthCal.add(Calendar.MONTH, -i);
            months.add(monthCal);
        }
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_month_item, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        Calendar calendar = months.get(position);
        holder.bindMonth(calendar);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView monthTitle;
        GridLayout daysGrid;

        CalendarViewHolder(View itemView) {
            super(itemView);
            monthTitle = itemView.findViewById(R.id.month_title);
            daysGrid = itemView.findViewById(R.id.days_grid);
        }

        void bindMonth(Calendar calendar) {
            // Set month title
            String monthYear = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
                    java.util.Locale.getDefault()) + " " + calendar.get(Calendar.YEAR);
            monthTitle.setText(monthYear);

            // Clear previous days
            daysGrid.removeAllViews();

            // Add day headers
            String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            for (String day : weekDays) {
                TextView textView = new TextView(itemView.getContext());
                textView.setText(day);
                textView.setPadding(8, 8, 8, 8);
                daysGrid.addView(textView);
            }

            // Get first day of month and maximum days
            Calendar temp = (Calendar) calendar.clone();
            temp.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK) - 1;
            int maxDays = temp.getActualMaximum(Calendar.DAY_OF_MONTH);

            // Add empty spaces for first week
            for (int i = 0; i < firstDayOfWeek; i++) {
                TextView textView = new TextView(itemView.getContext());
                textView.setText("");
                daysGrid.addView(textView);
            }

            // Add days with random highlights
            for (int day = 1; day <= maxDays; day++) {
                TextView textView = new TextView(itemView.getContext());
                textView.setText(String.valueOf(day));
                textView.setPadding(8, 8, 8, 8);

                // Randomly highlight some days
                if (random.nextFloat() < 0.2) { // 20% chance of highlighting
                    textView.setBackgroundColor(random.nextBoolean() ?
                            Color.argb(50, 255, 0, 0) : // Red
                            Color.argb(50, 0, 255, 0)); // Green
                }

                daysGrid.addView(textView);
            }
        }
    }
}