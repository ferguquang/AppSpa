package com.ngo.ducquang.appspa.report.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.base.getAddress.District;
import com.ngo.ducquang.appspa.base.getAddress.Province;

import java.util.List;

/**
 * Created by ducqu on 10/9/2018.
 */

public class DataReportByAddress {
    @SerializedName("Provinces")
    @Expose
    private List<Province> provinces = null;
    @SerializedName("Districts")
    @Expose
    private List<District> districts = null;
    @SerializedName("ReportByAddress")
    @Expose
    private List<ReportByAddress> reportByAddress = null;

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

    public List<ReportByAddress> getReportByAddress() {
        return reportByAddress;
    }

    public void setReportByAddress(List<ReportByAddress> reportByAddress) {
        this.reportByAddress = reportByAddress;
    }
}
