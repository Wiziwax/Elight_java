package com.chestnut_java.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.Adapters.DeviceListAdapter;
import com.chestnut_java.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_device_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.installedDevicesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for testing
        List<DeviceDetails> devices = new ArrayList<>();
        devices.add(new DeviceDetails("1", "SN123", "Device 1", "Location A", true));
        devices.add(new DeviceDetails("2", "SN456", "Device 2", "Location B", false));
        devices.add(new DeviceDetails("3", "SN789", "Device 3", "Location C", true));

        DeviceListAdapter adapter = new DeviceListAdapter(this, devices); // Pass context
        recyclerView.setAdapter(adapter);

    }
}
