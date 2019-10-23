package com.oxycab.provider.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleTags {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_request_id")
    @Expose
    private Integer userRequestId;
    @SerializedName("vehicle_tag_id")
    @Expose
    private Integer vehicleTagId;
    @SerializedName("vehicle_tag")
    @Expose
    private VehicleTag vehicleTag;

    public VehicleTag getVehicleTag() {
        return vehicleTag;
    }
}
