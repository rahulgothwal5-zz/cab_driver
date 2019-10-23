package com.oxycab.provider.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderVehicle {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("fleet_id")
    @Expose
    private Integer fleetId;
    @SerializedName("fleet_vehicle_id")
    @Expose
    private Integer fleetVehicleId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fleet_vehicle")
    @Expose
    private Vehicle fleetVehicle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getFleetId() {
        return fleetId;
    }

    public void setFleetId(Integer fleetId) {
        this.fleetId = fleetId;
    }

    public Integer getFleetVehicleId() {
        return fleetVehicleId;
    }

    public void setFleetVehicleId(Integer fleetVehicleId) {
        this.fleetVehicleId = fleetVehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vehicle getFleetVehicle() {
        return fleetVehicle;
    }

    public void setFleetVehicle(Vehicle fleetVehicle) {
        this.fleetVehicle = fleetVehicle;
    }
}
