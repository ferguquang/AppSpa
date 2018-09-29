package com.ngo.ducquang.appspa.base.getAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
}
