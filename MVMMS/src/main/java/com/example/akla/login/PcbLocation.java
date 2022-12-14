package com.example.akla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PcbLocation {

    private String equipmentId;

    private String gpsLatitude;

    private String gpsLongitude;

    private String locationDescription;

    private String mounting;

    private String typeOfLocated;

    public PcbLocation() {
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getMounting() {
        return mounting;
    }

    public void setMounting(String mounting) {
        this.mounting = mounting;
    }

    public String getTypeOfLocated() {
        return typeOfLocated;
    }

    public void setTypeOfLocated(String typeOfLocated) {
        this.typeOfLocated = typeOfLocated;
    }
}
