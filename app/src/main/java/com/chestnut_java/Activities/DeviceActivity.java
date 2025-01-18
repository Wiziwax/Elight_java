package com.chestnut_java.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chestnut_java.Adapters.DeviceListAdapter;
import com.chestnut_java.ApiService;
import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.R;
import com.chestnut_java.RetrofitClient;

import java.sql.Timestamp;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceActivity extends AppCompatActivity {

    TextView deviceName, serialNumber, installedBy, installedOn, location, status, voltageReading, runningTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_device);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        serialNumber = findViewById(R.id.deviceInfoSerialNumber);
        deviceName = findViewById(R.id.deviceNameInfo);
        installedBy = findViewById(R.id.deviceInfoInstalledBy);
        installedOn = findViewById(R.id.deviceInfoInstalledOn);
        location = findViewById(R.id.deviceInfoLocation);
        status = findViewById(R.id.deviceInfoStatus);
        voltageReading = findViewById(R.id.deviceInfoVoltageReading);
        runningTime = findViewById(R.id.deviceInfoRunningTime);



        String deviceId = getIntent().getStringExtra("deviceId");
        System.out.println("The device ID is " + deviceId);
        getDeviceDetails(deviceId);
    }

    private void getDeviceDetails(String deviceId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Call<DeviceDetails> call = apiService.getDeviceById(deviceId);

        call.enqueue(new Callback<DeviceDetails>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DeviceDetails> call, @NonNull Response<DeviceDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DeviceDetails device = response.body();
                    // Print the details to the console
                    System.out.println("Device Details:");
                    System.out.println("ID: " + device.getId());
                    System.out.println("Serial Number: " + device.getSerialNumber());
                    System.out.println("Device Name: " + device.getDeviceName());
                    System.out.println("Location Name: " + device.getLocationName());
                    System.out.println("Is Active: " + device.getIsActive());
                    System.out.println("Created Date: " + device.getCreatedDate());
                    System.out.println("Days Running: " + device.getDaysRunning());
                    System.out.println("Voltage: " + device.getVoltage());
                    System.out.println("Last Updated Date: " + device.getLastUpdatedDate());

                    serialNumber.setText(device.getSerialNumber());
                    installedBy.setText(device.getInstalledBy());
                    installedOn.setText(String.valueOf(device.getCreatedDate()));
                    location.setText(device.getLocationName());
                    status.setText(String.valueOf(device.getActive()));
                    voltageReading.setText(String.valueOf(device.getVoltage()) + "V");
                    deviceName.setText(device.getDeviceName());



                    // Assuming device.getLastUpdatedDate() returns a Timestamp
                    Timestamp lastUpdatedTimestamp = device.getLastUpdatedDate();
                    if (lastUpdatedTimestamp != null) {
                        long lastUpdatedMillis = lastUpdatedTimestamp.getTime();
                        long currentMillis = System.currentTimeMillis();

                        // Calculate the time difference in milliseconds
                        long differenceMillis = currentMillis - lastUpdatedMillis;

                        // Convert to days and hours
                        long differenceInDays = differenceMillis / (1000 * 60 * 60 * 24);
                        long remainingHours = (differenceMillis / (1000 * 60 * 60)) % 24;

                        // Format the result
                        String runningTimeText = differenceInDays + " days " + remainingHours + " hours";
                        runningTime.setText(runningTimeText);
                    } else {
                        runningTime.setText("Unknown time");
                    }


                } else {
                    System.out.println("Failed to retrieve device details. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeviceDetails> call, @NonNull Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });

    }
}