package com.chestnut_java.Entities;

import java.time.LocalDateTime;

public class DeviceLog {

    public DeviceLog(String id, String deviceId, Long locationId, Boolean status, String message, Double voltage, LocalDateTime lastActivityTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.locationId = locationId;
        this.status = status;
        this.message = message;
        this.voltage = voltage;
        this.lastActivityTime = lastActivityTime;
    }

    private String id;
    private String deviceId;
    private Long locationId;
    private Boolean status;
    private String message;
    private Double voltage;
    private LocalDateTime lastActivityTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public LocalDateTime getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(LocalDateTime lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }
}