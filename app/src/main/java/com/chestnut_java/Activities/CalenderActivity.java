package com.chestnut_java.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Adapters.CalendarAdapter;
import com.chestnut_java.R;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;


public class CalenderActivity extends AppCompatActivity {
    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calender);

//        // Initialize views
//        calendarView = findViewById(R.id.calendarView);
//        setupSpinners();
//        setupCalendarView();

        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendarAdapter = new CalendarAdapter();
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

//    private void setupSpinners() {
//        // Get current date for validation
//        int currentYear = currentDate.get(Calendar.YEAR);
//        int currentMonth = currentDate.get(Calendar.MONTH);
//
//        // Create year list (from 2020 to current year)
//        ArrayList<Integer> years = new ArrayList<>();
//        for (int year = 2020; year <= currentYear; year++) {
//            years.add(year);
//        }
//
//        // Create month list
//        String[] months = new DateFormatSymbols().getMonths();
//        ArrayList<String> validMonths = new ArrayList<>();
//        for (int i = 0; i <= currentMonth; i++) {
//            if (currentYear == years.get(years.size() - 1)) {
//                validMonths.add(months[i]);
//            }
//        }
//
//        // Setup spinners
//        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_spinner_item, years);
//        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_spinner_item, validMonths);
//        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        monthSpinner.setAdapter(monthAdapter);
//        yearSpinner.setAdapter(yearAdapter);
//
//        // Add listeners
//        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                updateValidMonths();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//
//        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                updateValidMonths();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
//    }
//
//    private void updateValidMonths() {
//        int selectedYear = (Integer) yearSpinner.getSelectedItem();
//        int selectedMonth = monthSpinner.getSelectedItemPosition();
//
//        // Update available months based on selection
//        String[] months = new DateFormatSymbols().getMonths();
//        ArrayList<String> validMonths = new ArrayList<>();
//
//        if (selectedYear == currentDate.get(Calendar.YEAR)) {
//            // Only show months up to current month
//            for (int i = 0; i <= currentDate.get(Calendar.MONTH); i++) {
//                validMonths.add(months[i]);
//            }
//        } else {
//            // Show all months for past years
//            Collections.addAll(validMonths, months);
//        }
//
//        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_spinner_item, validMonths);
//        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        monthSpinner.setAdapter(monthAdapter);
//
//        // Try to maintain previous selection if still valid
//        if (selectedMonth < validMonths.size()) {
//            monthSpinner.setSelection(selectedMonth);
//        }
//    }
//
//    private void setupCalendarView() {
//        // Set min date to 2020
//        Calendar minDate = Calendar.getInstance();
//        minDate.set(2020, Calendar.JANUARY, 1);
//        calendarView.setMinDate(minDate.getTimeInMillis());
//
//        // Set max date to current date
//        calendarView.setMaxDate(currentDate.getTimeInMillis());
//
//        // Add random highlighting
//        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
//            // Generate random colors for days
//            Calendar selectedDate = Calendar.getInstance();
//            selectedDate.set(year, month, dayOfMonth);
//
//            // Randomly color 30% of days red and 30% green
//            if (random.nextFloat() < 0.3) {
//                highlightDate(selectedDate, Color.RED);
//            } else if (random.nextFloat() < 0.6) {
//                highlightDate(selectedDate, Color.GREEN);
//            }
//        });
//    }
//
//    private void highlightDate(Calendar date, int color) {
//        // Note: The default CalendarView doesn't support direct day coloring
//        // You would need to create a custom calendar view or use a third-party library
//        // for actual day highlighting. This is a placeholder for that functionality.
//        Toast.makeText(this,
//                "Date " + date.get(Calendar.DAY_OF_MONTH) + " would be highlighted " +
//                        (color == Color.RED ? "red" : "green"),
//                Toast.LENGTH_SHORT).show();
//    }
}