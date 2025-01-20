package com.chestnut_java.Activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Adapters.DeviceLogLocationAdapter;
import com.chestnut_java.Adapters.LocationDeviceAdapter;
import com.chestnut_java.Entities.Area;
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.Entities.DeviceLogLocation;
import com.chestnut_java.LocationApiService;
import com.chestnut_java.R;
import com.chestnut_java.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDeviceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationDeviceAdapter adapter;
    private DeviceLogLocationAdapter adapter2;
//    private List<DeviceDetails> deviceDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_device);

        String areaId = getIntent().getStringExtra("AREA_ID");
        String areaName = getIntent().getStringExtra("AREA_NAME");

        TextView areaIdTextView = findViewById(R.id.areaIdTextView);
        areaIdTextView.setText("Location: " + areaName);

        // Load devices based on the areaId



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadDevices(areaId);

        // Sample data
//        deviceDetailsList = new ArrayList<>();
//        deviceDetailsList.add(new DeviceDetails("1", "SN12345", "Device A", "Broad Street", true));
//        deviceDetailsList.add(new DeviceDetails("2", "SN67890", "Device B", "Park Lane", false));
//
//        adapter = new LocationDeviceAdapter(deviceDetailsList, this);
//        recyclerView.setAdapter(adapter);
    }

    private void loadDevices(String locationId) {
        LocationApiService apiService = RetrofitClient.getInstance().create(LocationApiService.class);
        Call<List<DeviceLogLocation>> call = apiService.getDeviceLogByLocationId(locationId);


        call.enqueue(new Callback<List<DeviceLogLocation>>() {
            @Override
            public void onResponse(Call<List<DeviceLogLocation>> call, Response<List<DeviceLogLocation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DeviceLogLocation>deviceDetailsList = response.body();
                    adapter2 = new DeviceLogLocationAdapter(deviceDetailsList, LocationDeviceActivity.this);
                    recyclerView.setAdapter(adapter2);
                } else {
                    Toast.makeText(LocationDeviceActivity.this, "Failed to retrieve locations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DeviceLogLocation>> call, Throwable t) {
                Toast.makeText(LocationDeviceActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}