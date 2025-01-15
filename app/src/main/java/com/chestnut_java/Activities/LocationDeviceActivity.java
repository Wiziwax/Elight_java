package com.chestnut_java.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chestnut_java.R;

public class LocationDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_device);

        String areaId = getIntent().getStringExtra("AREA_ID");

        TextView areaIdTextView = findViewById(R.id.areaIdTextView);
        areaIdTextView.setText("Area ID: " + areaId);

        // Load devices based on the areaId
        loadDevices(areaId);
    }

    private void loadDevices(String areaId) {
        // Add logic to fetch and display devices for the given areaId
    }
}