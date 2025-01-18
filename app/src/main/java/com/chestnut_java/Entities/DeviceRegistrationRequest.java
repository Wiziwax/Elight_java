package com.chestnut_java.Entities;

public class DeviceRegistrationRequest {

    public DeviceRegistrationRequest(String id, String deviceName, String serialNumber, Long locationId, String locationName, String city, Boolean isActive, String installedBy, Double voltage, String createdDate, String lastUpdatedDate) {
        this.id = id;
        this.deviceName = deviceName;
        this.serialNumber = serialNumber;
        this.locationId = locationId;
        this.locationName = locationName;
        this.city = city;
        this.isActive = isActive;
        this.installedBy = installedBy;
        this.voltage = voltage;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public DeviceRegistrationRequest(String deviceName, String serialNumber, Long locationId, String locationName, String city, Boolean isActive, String installedBy, String createdDate) {
        this.deviceName = deviceName;
        this.serialNumber = serialNumber;
        this.locationId = locationId;
        this.locationName = locationName;
        this.city = city;
        this.isActive = isActive;
        this.installedBy = installedBy;
        this.createdDate = createdDate;
    }

    private String id;
    private String deviceName;
    private String serialNumber;
    private Long locationId;
    private String locationName;
    private String city;
    private Boolean isActive;
    private String installedBy;
    private Double voltage;
    private String createdDate;
    private String lastUpdatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}