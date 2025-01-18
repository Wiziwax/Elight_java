package com.chestnut_java.Entities;

import java.sql.Timestamp;

public class DeviceDetails {

    public DeviceDetails(String id, String serialNumber, String deviceName, String locationName, boolean isActive) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.deviceName = deviceName;
        this.locationName = locationName;
        this.isActive = isActive;
    }

    private String id;
    private String serialNumber;
    private String deviceName;
    private String locationName;
    private Boolean isActive;
    private String installedBy;
    private String locationId;
    private Timestamp createdDate;
    private Integer daysRunning;
    private Double voltage;
    private Timestamp lastUpdatedDate;

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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getDaysRunning() {
        return daysRunning;
    }

    public void setDaysRunning(Integer daysRunning) {
        this.daysRunning = daysRunning;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }
}
