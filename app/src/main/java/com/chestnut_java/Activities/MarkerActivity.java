package com.chestnut_java.Activities;

import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chestnut_java.Entities.AddressEntity;
import com.chestnut_java.Entities.CustomMarker;
import com.chestnut_java.LocationApiService;
import com.chestnut_java.MarkerDatabase;
import com.chestnut_java.R;
import com.chestnut_java.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MarkerDatabase markerDatabase;
    private List<CustomMarker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize database
        markerDatabase = new MarkerDatabase(this);

        // Load saved markers
        loadSavedMarkers();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Set night mode style (using custom JSON style)
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.night_mode_style));
            if (!success) {
                Log.e("MapStyle", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapStyle", "Can't find style. Error: ", e);
        }
        // Set default location (modify these coordinates as needed)
        LatLng defaultLocation = new LatLng(0, 0);
        LatLng beninLocation = new LatLng(6.3382, 5.6257);

        // Start smooth zoom-in animation
        startZoomInAnimation(beninLocation);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beninLocation, 15));


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);  // You can change this to MAP_TYPE_SATELLITE or other types if needed.

        // Alternatively, to disable individual layers like POI, Traffic, and Transit
        mMap.setTrafficEnabled(false);
        // Enable touch to add markers
        mMap.setOnMapClickListener(latLng -> addNewMarker(latLng));

        // Display saved markers
        displaySavedMarkers();

        // Customize map settings
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    private void addNewMarker(LatLng position) {
        // Create dialog for marker details

        String address = getAddressFromLatLng(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_marker_details, null);
        EditText titleInput = dialogView.findViewById(R.id.marker_title);
        EditText descriptionInput = dialogView.findViewById(R.id.marker_description);

        descriptionInput.setText(address);
//        String address1 = "11 Idahosa St, Avbiama, Benin City 300102, Edo, Nigeria";
        parseAddress(address);
        AddressEntity entity = parseAddress(address);
        System.out.println(entity);
        entity.setLatitude(String.valueOf(position.latitude));
        entity.setLongitude(String.valueOf(position.longitude));

        createLocation(entity);
        builder.setView(dialogView)
                .setTitle("Add New Marker")
                .setPositiveButton("Save", (dialog, which) -> {
                    String title = titleInput.getText().toString();
                    String description = descriptionInput.getText().toString();



                    // Create and save marker
                    CustomMarker marker = new CustomMarker(
                            position.latitude,
                            position.longitude,
                            title,
                            description
                    );
                    saveMarker(marker);

                    // Add marker to map
                    addMarkerToMap(marker);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addMarkerToMap(CustomMarker customMarker) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(customMarker.getLatitude(), customMarker.getLongitude()))
                .title(customMarker.getTitle())
                .snippet(customMarker.getDescription())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.sun16));

        // Customize marker icon if needed
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        Marker marker = mMap.addMarker(markerOptions);
        markers.add(customMarker);
    }

    private void saveMarker(CustomMarker marker) {
        markerDatabase.addMarker(marker);
    }

    private void loadSavedMarkers() {
        markers = markerDatabase.getAllMarkers();
    }

    private void displaySavedMarkers() {
        List<CustomMarker> markersToAdd = new ArrayList<>();  // To avoid concurrent modification
        for (CustomMarker marker : markers) {
            markersToAdd.add(marker);
        }

        // After collecting all markers to add, add them to the map
        for (CustomMarker marker : markersToAdd) {
            addMarkerToMap(marker);
        }
    }

    // Helper method to get address from LatLng
    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0); // Fetch the full address
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown Location"; // Fallback if no address is found
    }


    private void startZoomInAnimation(LatLng targetLocation) {
        final float startZoom = 3.0f;  // Continent zoom level
        final float endZoom = 15.0f;  // Desired zoom level
        final int steps = 100;         // Number of steps in the animation
        final long duration = 4000;   // Total duration in milliseconds
        final long interval = duration / steps;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, startZoom));

        new android.os.Handler().postDelayed(new Runnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep <= steps) {
                    float progress = (float) currentStep / steps;
                    float zoomLevel = startZoom + progress * (endZoom - startZoom);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, zoomLevel));
                    currentStep++;

                    if (currentStep <= steps) {
                        new android.os.Handler().postDelayed(this, interval);
                    }
                }
            }
        }, interval);
    }




    public static AddressEntity parseAddress(String address) {
        AddressEntity entity = new AddressEntity();

        // Split the address by commas
        String[] parts = address.split(",");

        // Trim whitespace from each part
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Parse from the end of the array
        int len = parts.length;

        if (len >= 1) {
            entity.setCountry(parts[len - 1]); // Country is the last part
        }
        if (len >= 2) {
            entity.setState(parts[len - 2]); // State is the second to last part
        }
        if (len >= 3) {
            // Extract city and zip code from the third to last part
            String cityPart = parts[len - 3];
            String[] cityAndZip = cityPart.split(" ");
            StringBuilder cityBuilder = new StringBuilder();
            String zipCode = "";

            for (String part : cityAndZip) {
                if (part.matches("\\d{5,}")) { // Check for 5+ digit number
                    zipCode = part;
                } else {
                    if (cityBuilder.length() > 0) {
                        cityBuilder.append(" ");
                    }
                    cityBuilder.append(part);
                }
            }

            entity.setCity(cityBuilder.toString());
            entity.setZip(zipCode);
        }
        if (len >= 4) {
            // Combine the remaining parts as the street address
            StringBuilder street = new StringBuilder();
            for (int i = 0; i < len - 3; i++) {
                street.append(parts[i]);
                if (i < len - 4) {
                    street.append(", ");
                }
            }
            entity.setStreet(street.toString());
        }

        return entity;
    }


    public void createLocation(AddressEntity addressEntity){

        // Show loading indicator
        showLoading();

        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        // Make API call
        apiService.registerLocation(addressEntity).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    showSuccess("Location registered successfully!");
                    // Maybe navigate back or clear form
                } else {
                    showError("Failed to register location. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideLoading();
                showError("Network error. Please check your connection.");
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

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
