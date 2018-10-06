package com.ngo.ducquang.appspa.modelStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/6/2018.
 */

public class DataGetStore {

    @SerializedName("Stores")
    @Expose
    private List<Store> stores = null;

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
