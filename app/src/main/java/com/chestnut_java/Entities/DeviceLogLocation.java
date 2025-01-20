package com.chestnut_java.Entities;

import java.util.List;

public class DeviceLogLocation {

    public DeviceLogLocation(Long locationId, Boolean status, String timePeriod, String timeFrame, List<String> deviceIds) {
        this.locationId = locationId;
        this.status = status;
        this.timePeriod = timePeriod;
        this.timeFrame = timeFrame;
        this.deviceIds = deviceIds;
    }

    public DeviceLogLocation() {
    }

    private Long locationId;
    private Boolean status;
    private String timePeriod; // e.g., "January 1st 12am to January 27th 1pm"
    private String timeFrame; // e.g., in hours, or both days and hours if needed
    private List<String> deviceIds; // List of device IDs in this time frame

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }
}
