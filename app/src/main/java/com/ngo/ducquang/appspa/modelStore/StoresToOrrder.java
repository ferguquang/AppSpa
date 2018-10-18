package com.ngo.ducquang.appspa.modelStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/18/2018.
 */

public class StoresToOrrder {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Stores")
    @Expose
    private List<StoreOrder> stores = null;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StoreOrder> getStores() {
        return stores;
    }

    public void setStores(List<StoreOrder> stores) {
        this.stores = stores;
    }
}
