package com.ngo.ducquang.appspa.base.getAddress;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.modelStore.DataGetStore;

import java.util.List;

/**
 * Created by ducqu on 9/26/2018.
 */

public class DataGetAddress {
    @SerializedName("Provinces")
    @Expose
    private List<Province> provinces = null;
    @SerializedName("Districts")
    @Expose
    private List<District> districts = null;

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public static DataGetAddress initialize(String jsonGson)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonGson, DataGetAddress.class);
    }

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
