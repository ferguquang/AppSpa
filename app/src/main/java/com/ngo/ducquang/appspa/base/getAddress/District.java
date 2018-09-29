package com.ngo.ducquang.appspa.base.getAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 9/26/2018.
 */

public class District {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("ProvinceCode")
    @Expose
    private String provinceCode;
    @SerializedName("Name")
    @Expose
    private String name;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
