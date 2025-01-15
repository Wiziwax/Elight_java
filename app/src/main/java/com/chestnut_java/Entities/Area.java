package com.chestnut_java.Entities;

public class Area {

    public Area(String id, String areaName, String numberOfDevices, String areaCode) {
        this.id = id;
        this.areaName = areaName;
        this.numberOfDevices = numberOfDevices;
        this.areaCode = areaCode;
    }

    private String id;
    private String areaName;
    private String numberOfDevices;
    private String areaCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getNumberOfDevices() {
        return numberOfDevices;
    }

    public void setNumberOfDevices(String numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
