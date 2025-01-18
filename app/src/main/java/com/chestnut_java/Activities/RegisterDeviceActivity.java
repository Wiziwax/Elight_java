package com.chestnut_java.Activities;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chestnut_java.ApiService;
import com.chestnut_java.Entities.DeviceRegistrationRequest;
import com.chestnut_java.KeyboardAwareScrollHelper;
import com.chestnut_java.LocationApiService;
import com.chestnut_java.R;
import com.chestnut_java.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDeviceActivity extends AppCompatActivity {

    private AutoCompleteTextView countryAutoComplete;
    private AutoCompleteTextView stateAutoComplete;
    private AutoCompleteTextView locationAutoComplete;
    private AutoCompleteTextView streetAutoComplete;
    private ProgressBar streetProgress;
    private ProgressBar countryProgress;
    private ProgressBar stateProgress;
    private ProgressBar cityProgress;

    private View mainContainer;
    private LinearLayout contentLayout;
    private TextInputEditText zipCodeInput;
    private LinearLayout zipCodeLayout, adjuster;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener;


    private KeyboardAwareScrollHelper keyboardHelper;


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
        initializeViews();
        initProgressDialog();

        // Setup keyboard handling
        ScrollView scrollView = findViewById(R.id.theScroll);
        keyboardHelper = new KeyboardAwareScrollHelper(scrollView, 20); // 20dp extra padding

        // Get references to containers
        mainContainer = findViewById(R.id.main);
        // Set window soft input mode
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );
        // Load countries immediately
        loadCountries();


        // Set up listeners
        setupListeners();
        setupSubmitButton();
    }

    private void initializeViews() {
        countryAutoComplete = findViewById(R.id.countryAutoComplete);
        stateAutoComplete = findViewById(R.id.stateAutoComplete);
        locationAutoComplete = findViewById(R.id.locationAutoComplete);
        streetAutoComplete = findViewById(R.id.streetAutoComplete);

        // ProgressBars
        countryProgress = findViewById(R.id.countryProgress);
        stateProgress = findViewById(R.id.stateProgress);
        cityProgress = findViewById(R.id.cityProgress);
        streetProgress = findViewById(R.id.streetProgress);

        // Get references to containers
        mainContainer = findViewById(R.id.main);
        contentLayout = findViewById(R.id.contentLayout);

        zipCodeInput = findViewById(R.id.zipCodeInput);
        zipCodeLayout = findViewById(R.id.zipCodeLayout);
        adjuster = findViewById(R.id.adjuster);

    }

    private void setupListeners() {
        countryAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCountry = (String) parent.getItemAtPosition(position);
            loadStates(selectedCountry);
            // Clear dependent fields
            stateAutoComplete.setText("", false);
            locationAutoComplete.setText("", false);
        });

        stateAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedState = (String) parent.getItemAtPosition(position);
            loadCities(selectedState);
            // Clear dependent field
            locationAutoComplete.setText("", false);
        });


        locationAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = (String) parent.getItemAtPosition(position);
            loadStreets(selectedCity);
            // Clear dependent field
            streetAutoComplete.setText("", false);
        });
    }

    private void loadCountries() {

        showProgress(countryProgress);

        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.getCountries().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                hideProgress(countryProgress);
                if (response.isSuccessful() && response.body() != null) {
                    updateCountryAdapter(response.body());
                } else {
                    // Use fallback data
                    updateCountryAdapter(getFallbackCountries());
                    showError("Failed to load countries. Using offline data.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                hideProgress(countryProgress);
                updateCountryAdapter(getFallbackCountries());
                showError("Network error. Using offline data.");
            }
        });
    }

    private void loadStates(String country) {
        showProgress(stateProgress);

        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.getStates(country).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                hideProgress(stateProgress);
                if (response.isSuccessful() && response.body() != null) {
                    // No need to map since we're already getting List<String>
                    updateStateAdapter(response.body());
                } else {
                    // Use fallback data
                    updateStateAdapter(getFallbackStates(country));
                    showError("Failed to load states. Using offline data.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                hideProgress(stateProgress);
                updateStateAdapter(getFallbackStates(country));
//                showError("Network error. Using offline data.");
            }
        });
    }

    private void loadCities(String state) {
        showProgress(cityProgress);

        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.getCities(state).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                hideProgress(cityProgress);
                if (response.isSuccessful() && response.body() != null) {
                    updateCityAdapter(response.body());
                } else {
                    // Use fallback data
                    updateCityAdapter(getFallbackCities(state));
                    showError("Failed to load cities. Using offline data.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                hideProgress(cityProgress);
                updateCityAdapter(getFallbackCities(state));
                showError("Network error. Using offline data.");
            }
        });
    }

    private void updateCountryAdapter(List<String> countries) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                countries
        );
        countryAutoComplete.setAdapter(adapter);
    }

    private void updateStateAdapter(List<String> states) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                states
        );
        stateAutoComplete.setAdapter(adapter);
    }

    private void updateCityAdapter(List<String> cities) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cities
        );
        locationAutoComplete.setAdapter(adapter);
    }

    private void showProgress(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Fallback data methods (your existing static data)
    private List<String> getFallbackCountries() {
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

    private List<String> getFallbackStates(String country) {
        return countryStateMap.getOrDefault(country, new ArrayList<>());
    }

    private List<String> getFallbackCities(String state) {
        return stateLocationMap.getOrDefault(state, new ArrayList<>());
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

    private void setupSubmitButton() {
        findViewById(R.id.submitLayout).setOnClickListener(v -> submitDeviceRegistration());
    }

    private void submitDeviceRegistration() {
        // Get values from all input fields
        String serialNumber = Objects.requireNonNull(
                ((TextInputEditText) findViewById(R.id.serialNumberInput)).getText()).toString().trim();
        String deviceName = Objects.requireNonNull(
                ((TextInputEditText) findViewById(R.id.deviceNameInputField)).getText()).toString().trim();

        // Get location details
        String country = countryAutoComplete.getText().toString().trim();
        String state = stateAutoComplete.getText().toString().trim();
        String city = locationAutoComplete.getText().toString().trim();
        String street = streetAutoComplete.getText().toString().trim();
        String zipCode = Objects.requireNonNull(zipCodeInput.getText()).toString().trim();

        // Validate required fields
        if (serialNumber.isEmpty() || deviceName.isEmpty() || country.isEmpty() ||
                state.isEmpty() || city.isEmpty() || street.isEmpty()) {
            showError("Please fill in all required fields");
            return;
        }

        // Create request object
        DeviceRegistrationRequest request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            request = new DeviceRegistrationRequest(
                    deviceName,
                    serialNumber,
                    null,
                    street,
                    city,
                    true,  // isActive - you might want to make this configurable
                    "Elizabeth Omokhaye Donald-Ase",  // ownerName - you might want to get this from somewhere
                    String.valueOf(Timestamp.from(Instant.now()))
            );
        }

        // Show loading indicator
//        progressDialog.show();

        // Make API call
        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.registerDevice(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    showSuccess("Device registered successfully!");
                    // Optional: Clear form or finish activity
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error";
                        showError("Registration failed: " + errorBody);
                    } catch (Exception e) {
                        showError("Registration failed: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                showError("Network error: " + t.getMessage());
            }
        });
    }

    private void showLoading() {
        // Show a progress dialog or disable submit button
    }

    private void hideLoading() {
        // Hide progress dialog or enable submit button
    }

    private void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ProgressDialog progressDialog;

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering device...");
        progressDialog.setCancelable(false);
    }


    private void loadStreets(String city) {
        showProgress(streetProgress);

        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.getLocations(city).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                hideProgress(streetProgress);
                if (response.isSuccessful() && response.body() != null) {
                    updateStreetAdapter(response.body());
                } else {
                    // Use fallback data
                    updateStreetAdapter(getFallbackStreets(city));
                    showError("Failed to load streets. Using offline data.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                hideProgress(streetProgress);
                updateStreetAdapter(getFallbackStreets(city));
                showError("Network error. Using offline data.");
            }
        });
    }


    private void updateStreetAdapter(List<String> streets) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                streets
        );
        streetAutoComplete.setAdapter(adapter);
    }


    private List<String> getFallbackStreets(String city) {
        return cityStreetMap.getOrDefault(city, new ArrayList<>());
    }

    private Map<String, List<String>> cityStreetMap = new HashMap<String, List<String>>() {{
        put("Los Angeles", Arrays.asList("Hollywood Blvd", "Sunset Blvd", "Santa Monica Blvd"));
        put("New York City", Arrays.asList("5th Avenue", "Broadway", "Wall Street"));
        put("Houston", Arrays.asList("Westheimer Rd", "Main St", "San Felipe St"));
        // Add more cities and their streets as needed
    }};

}