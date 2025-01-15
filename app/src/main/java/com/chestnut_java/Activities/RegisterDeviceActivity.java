package com.chestnut_java.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chestnut_java.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterDeviceActivity extends AppCompatActivity {

    private AutoCompleteTextView countryAutoComplete;
    private AutoCompleteTextView stateAutoComplete;
    private AutoCompleteTextView locationAutoComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_device);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        countryAutoComplete = findViewById(R.id.countryAutoComplete);
        stateAutoComplete = findViewById(R.id.stateAutoComplete);
        locationAutoComplete = findViewById(R.id.locationAutoComplete);

        // Set up country adapter (example with simple array)
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getCountries() // Your method to get countries
        );
        countryAutoComplete.setAdapter(countryAdapter);

        // Listen for country selection
        countryAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCountry = (String) parent.getItemAtPosition(position);
            updateStates(selectedCountry); // Your method to update states based on country
        });

        // Listen for state selection
        stateAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedState = (String) parent.getItemAtPosition(position);
            updateLocations(selectedState); // Your method to update locations based on state
        });
    }


    private void updateStates(String country) {
        // Get states for selected country and update stateAutoComplete adapter
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getStatesForCountry(country) // Your method to get states
        );
        stateAutoComplete.setAdapter(stateAdapter);
        stateAutoComplete.setText("", false);
        locationAutoComplete.setText("", false);
    }

    private void updateLocations(String state) {
        // Get locations for selected state and update locationAutoComplete adapter
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getLocationsForState(state) // Your method to get locations
        );
        locationAutoComplete.setAdapter(locationAdapter);
        locationAutoComplete.setText("", false);
    }


    // Option 1: Static Data Implementation
    private List<String> getCountries() {
        return Arrays.asList(
                "United States",
                "Canada",
                "United Kingdom",
                "Australia",
                "Germany",
                "France",
                "Japan"
        );
    }

    private Map<String, List<String>> countryStateMap = new HashMap<String, List<String>>() {{
        put("United States", Arrays.asList(
                "Alabama", "Alaska", "Arizona", "California", "Colorado", "Florida",
                "New York", "Texas", "Washington"
        ));
        put("Canada", Arrays.asList(
                "Alberta", "British Columbia", "Ontario", "Quebec", "Manitoba"
        ));
        put("United Kingdom", Arrays.asList(
                "England", "Scotland", "Wales", "Northern Ireland"
        ));
    }};

    private Map<String, List<String>> stateLocationMap = new HashMap<String, List<String>>() {{
        put("California", Arrays.asList(
                "Los Angeles", "San Francisco", "San Diego", "Sacramento"
        ));
        put("New York", Arrays.asList(
                "New York City", "Buffalo", "Albany", "Rochester"
        ));
        put("Texas", Arrays.asList(
                "Houston", "Austin", "Dallas", "San Antonio"
        ));
        // Add more states and their locations as needed
    }};

    private List<String> getStatesForCountry(String country) {
        return countryStateMap.getOrDefault(country, new ArrayList<>());
    }

    private List<String> getLocationsForState(String state) {
        return stateLocationMap.getOrDefault(state, new ArrayList<>());
    }
    
}