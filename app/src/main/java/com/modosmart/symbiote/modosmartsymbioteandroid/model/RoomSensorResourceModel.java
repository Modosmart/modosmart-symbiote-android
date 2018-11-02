package com.modosmart.symbiote.modosmartsymbioteandroid.model;

public class RoomSensorResourceModel {
    private String type;
    private String symbiote_id;
    private String mac_address;
    private String temperature;
    private String humidity;
    private String presence;
    private String battery;
    private String firmware;

    public RoomSensorResourceModel(String type, String symbiote_id,
                                   String mac_address, String temperature, String humidity,
                                   String presence, String battery, String firmware) {
        this.type = type;
        this.symbiote_id = symbiote_id;
        this.mac_address = mac_address;
        this.temperature = temperature;
        this.humidity = humidity;
        this.presence = presence;
        this.battery = battery;
        this.firmware = firmware;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbioteId() {
        return symbiote_id;
    }

    public void setSymbioteId(String symbiote_id) {
        this.symbiote_id = symbiote_id;
    }

    public String getMacAddress() {
        return mac_address;
    }

    public void setMacAddress(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }
}
