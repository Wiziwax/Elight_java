package com.chestnut_java.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Adapters.DeviceListAdapter;
import com.chestnut_java.Entities.AddressEntity;
import com.chestnut_java.Entities.Area;
import com.chestnut_java.Adapters.AreaAdapter;
import com.chestnut_java.Adapters.LocationAdapter;
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.LocationApiService;
import com.chestnut_java.R;
import com.chestnut_java.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {
    private TextView selectedStateText;
    private TextView selectedLocationText;
    private TextView devicesFound;
    private AutoCompleteTextView stateAutoComplete;
    private RecyclerView locationsRecyclerView;
    private LocationAdapter locationAdapter;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private View blurOverlay;
    private View mainContent;
    private View bottomSheet;
    private View bottomSheetHeader;
    private RecyclerView areaRecyclerView;
    private AreaAdapter areaAdapter;
    private static final float MAX_HEIGHT_RATIO = 0.75f; // Maximum height will be 75% of screen

    // Constants for bottom sheet heights
    private static final float EXPANDED_HEIGHT_RATIO = 0.6f; // Maximum height (60% of screen)
    private static final float PEEK_HEIGHT_RATIO = 0.05f;    // Minimum visible height (10% of screen)

    private static final float HALF_EXPANDED_RATIO = 0.5f; // Half of screen height
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initViews();
        setupBottomSheet();
        setupStateDropdown();
        setupLocationsRecyclerView();
        setupTouchInteractions();
        setupAreaRecyclerView();
    }

    private void initViews() {
        selectedStateText = findViewById(R.id.selectedStateText);
        selectedLocationText = findViewById(R.id.selectedLocationText);
        devicesFound = findViewById(R.id.devicesFound);
        blurOverlay = findViewById(R.id.blurOverlay);
        mainContent = findViewById(R.id.mainContent);
        bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetHeader = bottomSheet.findViewById(R.id.bottomSheetHeader);

        stateAutoComplete = bottomSheet.findViewById(R.id.stateAutoComplete);
        locationsRecyclerView = bottomSheet.findViewById(R.id.locationsRecyclerView);
    }


    private void setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Essential settings to keep sheet anchored
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight((int)(getWindowHeight() * PEEK_HEIGHT_RATIO));
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setSkipCollapsed(false);

        // Set half expanded ratio
        bottomSheetBehavior.setHalfExpandedRatio(HALF_EXPANDED_RATIO);

        // Set expanded height limit
        bottomSheet.setBottom((int)(getWindowHeight() * EXPANDED_HEIGHT_RATIO));

        // Set initial state to half expanded
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                updateBlurOverlay(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Keep sheet anchored to bottom
                bottomSheet.setTranslationY(0);

                // Update blur overlay
                float adjustedOffset = Math.max(0, Math.min(slideOffset, 1));
                blurOverlay.setAlpha(adjustedOffset * 0.6f);
            }
        });
    }


    private void setupTouchInteractions() {
        // Tap outside to collapse
        blurOverlay.setOnClickListener(v -> collapseBottomSheet());

        // Tap header or drag up to expand
        bottomSheetHeader.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                expandBottomSheet();
            } else {
                collapseBottomSheet();
            }
        });
    }


    private void expandBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
    }

    private void collapseBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
    private void setupStateDropdown() {
        List<String> states = Arrays.asList("Lagos", "Abuja", "Rivers", "Kano"); // Add all Nigerian states




        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.getStates("NIGERIA").enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // No need to map since we're already getting List<String>
                    updateStateAdapter(response.body());
                } else {
                    // Use fallback data
                    updateStateAdapter(states);
                    showError("Failed to load states. Using offline data.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                updateStateAdapter(states);
//                showError("Network error. Using offline data.");
            }
        });





        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, states);
        stateAutoComplete.setAdapter(adapter);

        stateAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedState = (String) parent.getItemAtPosition(position);
            loadLocationsForState(selectedState);
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void updateStateAdapter(List<String> states) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                states
        );
        stateAutoComplete.setAdapter(adapter);
    }

    private void setupLocationsRecyclerView() {
        locationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationAdapter = new LocationAdapter(new ArrayList<>(), this::onLocationSelected);
        locationsRecyclerView.setAdapter(locationAdapter);
    }

    private void loadLocationsForState(String state) {
        List<String> locations = new ArrayList<>();


        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        apiService.getCities(state).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    updateCityAdapter(response.body());
                    locationAdapter.updateLocations(response.body());
                } else {
                    // Use fallback data
                    showError("Failed to load cities. Using offline data.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
//                updateCityAdapter();
                showError("Network error. Using offline data.");
            }
        });

    }

    private void onLocationSelected(String location) {
        String selectedState = stateAutoComplete.getText().toString();
        selectedStateText.setText("State: " + selectedState);
        selectedLocationText.setText("Location: " + location);
        devicesFound.setText("Devices Found");
        loadAreasForState(location);
        collapseBottomSheet();
    }

    private void animateBottomSheetCollapse() {
        ValueAnimator animator = ValueAnimator.ofFloat(
                bottomSheet.getY(),
                getWindowHeight() - bottomSheetBehavior.getPeekHeight()
        );

        animator.setDuration(500); // Animation duration in milliseconds
        animator.setInterpolator(new DecelerateInterpolator()); // Smooth deceleration

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            bottomSheet.setY(value);
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        animator.start();
    }

    private void showBlurOverlay(boolean show) {
        blurOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            collapseBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    private void updateBlurOverlay(int state) {
        boolean shouldShowBlur = state == BottomSheetBehavior.STATE_EXPANDED ||
                state == BottomSheetBehavior.STATE_HALF_EXPANDED;

        if (shouldShowBlur) {
            blurOverlay.setVisibility(View.VISIBLE);
        } else {
            blurOverlay.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            blurOverlay.setVisibility(View.GONE);
                        }
                    });
        }
    }


    private void setupAreaRecyclerView() {
        areaRecyclerView = findViewById(R.id.areaRecyclerView);
        areaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        areaAdapter = new AreaAdapter(new ArrayList<>(), new AreaAdapter.OnAreaClickListener() {
            @Override
            public void onAreaClick(Area area) {
                onAreaSelected(area);
            }

            @Override
            public void onDeviceCountClick(String areaId, String areaName) {
                openDeviceDetailsActivity(areaId, areaName);
            }
        });
        areaRecyclerView.setAdapter(areaAdapter);
    }

    private void openDeviceDetailsActivity(String areaId, String areaName) {
        Intent intent = new Intent(this, LocationDeviceActivity.class);
        intent.putExtra("AREA_ID", areaId);
        intent.putExtra("AREA_NAME", areaName);
        startActivity(intent);
    }


    private void loadAreasForState(String state) {
        List<Area> areas = new ArrayList<>();

        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        Call<List<Area>> call = apiService.getLocationsObject(state);


        call.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Area> addressEntities = response.body();
                    areaAdapter.updateAreas(addressEntities);
//                    adapter = new DeviceListAdapter(DeviceListActivity.this, devices);
//                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(LocationActivity.this, "Failed to retrieve locations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Toast.makeText(LocationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//        switch (state) {
//            case "Lagos":
//                areas.add(new Area("1", "Ikeja", "3", "LA-01"));
//                areas.add(new Area("2", "Lekki", "5", "LA-02"));
//                break;
//            case "Ikeja":
//                areas.add(new Area("3", "Wuse", "2", "AB-01"));
//                areas.add(new Area("4", "Garki", "4", "AB-02"));
//                break;
//            // Add cases for other states
//        }






    private void onAreaSelected(Area area) {
        String selectedState = stateAutoComplete.getText().toString();
        selectedStateText.setText("State: " + selectedState);
//        selectedLocationText.setText("Area: " + area.getAreaName() + " (" + area.getAreaCode() + ")");
        collapseBottomSheet();
    }


}