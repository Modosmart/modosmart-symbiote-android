package com.modosmart.symbiote.modosmartsymbioteandroid.model;

public class AcSwitchResourceModel {
    private String type;
    private String symbiote_id;
    private String mac_address;
    private boolean status;

    public AcSwitchResourceModel(String type, String symbiote_id, String mac_address, boolean status) {
        this.type = type;
        this.symbiote_id = symbiote_id;
        this.mac_address = mac_address;
        this.status = status;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
