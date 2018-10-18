package com.ngo.ducquang.appspa.modelStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ducqu on 10/18/2018.
 */

public class StoreOrder {
    @SerializedName("IDStore")
    @Expose
    private int iDStore;
    @SerializedName("NameStore")
    @Expose
    private String nameStore;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ProvinceName")
    @Expose
    private String provinceName;
    @SerializedName("DistrictName")
    @Expose
    private String districtName;
    @SerializedName("Line")
    @Expose
    private Float line;

    private boolean isSelected = false;

    public int getiDStore() {
        return iDStore;
    }

    public void setiDStore(int iDStore) {
        this.iDStore = iDStore;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Float getLine() {
        return line;
    }

    public void setLine(Float line) {
        this.line = line;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
