package com.ngo.ducquang.appspa.modelStore;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ngo.ducquang.appspa.oder.model.Order;

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

    public static DataGetStore initialize(String jsonGson)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonGson, DataGetStore.class);
    }

    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
