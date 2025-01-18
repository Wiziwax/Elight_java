package com.chestnut_java.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Adapters.LocationDeviceAdapter;
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.R;

import java.util.ArrayList;
import java.util.List;

public class LocationDeviceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationDeviceAdapter adapter;
    private List<DeviceDetails> deviceDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_device);

        String areaId = getIntent().getStringExtra("AREA_ID");

        TextView areaIdTextView = findViewById(R.id.areaIdTextView);
        areaIdTextView.setText("Area ID: " + areaId);

        // Load devices based on the areaId
        loadDevices(areaId);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        deviceDetailsList = new ArrayList<>();
        deviceDetailsList.add(new DeviceDetails("1", "SN12345", "Device A", "Broad Street", true));
        deviceDetailsList.add(new DeviceDetails("2", "SN67890", "Device B", "Park Lane", false));

        adapter = new LocationDeviceAdapter(deviceDetailsList, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadDevices(String areaId) {
        // Add logic to fetch and display devices for the given areaId
    }
}