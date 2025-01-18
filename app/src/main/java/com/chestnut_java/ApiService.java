package com.chestnut_java;

import com.chestnut_java.Entities.DeviceDetails;
import com.chestnut_java.Entities.DeviceRegistrationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("device/all") // Replace with your endpoint
    Call<List<DeviceDetails>> getDevices();

    @GET("device/id")
    Call<DeviceDetails> getDeviceById(@Query("id") String id);

}
