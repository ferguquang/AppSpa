package com.ngo.ducquang.appspa.modelStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducqu on 10/18/2018.
 */

public class DataGetStoreToOrder {
    @SerializedName("StoresToOrrder")
    @Expose
    private List<StoresToOrrder> storesToOrrder = null;

    public List<StoresToOrrder> getStoresToOrrder() {
        return storesToOrrder;
    }

    public void setStoresToOrrder(List<StoresToOrrder> storesToOrrder) {
        this.storesToOrrder = storesToOrrder;
    }
}
