package com.chestnut_java.Entities;

public class Area {

    public Area(String id, String street, String numberOfDevices, String zip) {
        this.id = id;
        this.street = street;
        this.numberOfDevices = numberOfDevices;
        this.zip = zip;
    }

    private String id;
    private String street;
    private String numberOfDevices;
    private String zip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumberOfDevices() {
        return numberOfDevices;
    }

    public void setNumberOfDevices(String numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
