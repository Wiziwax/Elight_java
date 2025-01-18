package com.chestnut_java.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Adapters.DeviceListAdapter;
import com.chestnut_java.ApiService;
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.R;
import com.chestnut_java.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeviceListAdapter adapter;
    private CardView addDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        recyclerView = findViewById(R.id.installedDevicesRecyclerView);
        addDevice = findViewById(R.id.theRealAddDevice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDevices();

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceListActivity.this, RegisterDeviceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchDevices() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Call<List<DeviceDetails>> call = apiService.getDevices();
        call.enqueue(new Callback<List<DeviceDetails>>() {
            @Override
            public void onResponse(Call<List<DeviceDetails>> call, Response<List<DeviceDetails>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DeviceDetails> devices = response.body();
                    adapter = new DeviceListAdapter(DeviceListActivity.this, devices);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(DeviceListActivity.this, "Failed to retrieve devices", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DeviceDetails>> call, Throwable t) {
                Toast.makeText(DeviceListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
