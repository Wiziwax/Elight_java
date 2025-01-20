package com.chestnut_java;

import com.chestnut_java.Entities.AddressEntity;
import com.chestnut_java.Entities.Area;
import com.chestnut_java.Entities.DeviceLogLocation;
import com.chestnut_java.Entities.DeviceRegistrationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationApiService {

    @GET("locations/all-countries")
    Call<List<String>> getCountries();

    @GET("locations/all-states-by-country")
    Call<List<String>> getStates(@Query("country") String country);

    @GET("locations/all-cities-by-state")
    Call<List<String>> getCities(@Query("state") String state);

    @GET("locations/all-locations-by-city")
    Call<List<String>> getLocations(@Query("city") String city);

    @GET("locations/city")
    Call<List<Area>> getLocationsObject(@Query("city") String city);

    @POST("device/register") // Replace with your actual endpoint
    Call<Void> registerDevice(@Body DeviceRegistrationRequest request);

    @POST("locations/register") // Replace with your actual endpoint
    Call<Void> registerLocation(@Body AddressEntity request);

    @GET("device/log/locationId")
    Call<List<DeviceLogLocation>> getDeviceLogByLocationId(@Query("locationId") String locationId);
}
