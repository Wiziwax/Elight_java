package com.chestnut_java.Entities;

public class DeviceDetails {

    public DeviceDetails(String id, String serialNumber, String deviceName, String locationName, boolean status) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.deviceName = deviceName;
        this.locationName = locationName;
        this.status = status;
    }

    private String id;
    private String serialNumber;
    private String deviceName;
    private String locationName;
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
